package org.srm.purchasecooperation.cux.infra.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.imported.app.service.BatchImportHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;

import java.io.IOException;
import java.util.*;

/**
 * 采购计划行表导入
 *
 * @author tianjing.gui@hand-china.com 2021-09-07 16:31:02
 */
@Service
@ImportService(templateCode = "SCUX.ZHNY.IMPORT.PO-PLAN")
public class SinochemintlPoPlanImportServiceImpl extends BatchImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(SinochemintlPoPlanImportServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository;

    @Autowired
    private SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean doImport(List<String> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            logger.info("传入数据为空");
            return false;
        }
        try {
            //获取参数
            Integer poPlanHeaderId = getArgs("poPlanHeaderId");
            for (String data : datas) {
                CustomUserDetails user = DetailsHelper.getUserDetails();
                SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO = objectMapper.readValue(data, SinochemintlPoPlanLineDTO.class);
                sinochemintlPoPlanLineDTO.setPoPlanHeaderId(Long.valueOf(poPlanHeaderId));
                sinochemintlPoPlanLineDTO.setTenantId(user.getTenantId());
                //将值集转换为id字段并保存
                SinochemintlPoPlanLineDTO importVerify;
                if ("人民币".equals(sinochemintlPoPlanLineDTO.getCurrencyName())) {
                    sinochemintlPoPlanLineDTO.setCurrencyName("");
                    importVerify = sinochemintlPoPlanHeaderRepository.importVerify(sinochemintlPoPlanLineDTO);
                    SinochemintlPoPlanLineDTO cnyCurrency = sinochemintlPoPlanLineRepository.getCnyCurrency(user.getTenantId());
                    importVerify.setCurrencyId(cnyCurrency.getCurrencyId());
                } else {
                    importVerify = sinochemintlPoPlanHeaderRepository.importVerify(sinochemintlPoPlanLineDTO);
                }
                List<String> planSharedProvince = Arrays.asList(sinochemintlPoPlanLineDTO.getPlanSharedProvince().split("、"));
                List<SinochemintlPoPlanLineDTO> planSharedProvinceName = sinochemintlPoPlanLineRepository.sharedProvinceVerify(planSharedProvince);
                List<Map<String, Object>> arrayList = new ArrayList<>();
                for (SinochemintlPoPlanLineDTO poPlanLineDTO : planSharedProvinceName) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("companyId", poPlanLineDTO.getProvinceCompanyId());
                    map.put("companyName", poPlanLineDTO.getProvinceCompany());
                    arrayList.add(map);
                }
                sinochemintlPoPlanLineDTO.setSharedProvinceId(0L);
                sinochemintlPoPlanLineDTO.setPlanSharedProvinceName(arrayList);
                sinochemintlPoPlanLineDTO.setPlanSharedProvince(objectMapper.writeValueAsString(arrayList));
                sinochemintlPoPlanLineDTO.setProvinceCompanyId(importVerify.getProvinceCompanyId());
                sinochemintlPoPlanLineDTO.setInitialSupplierId(importVerify.getInitialSupplierId());
                sinochemintlPoPlanLineDTO.setMaterialId(importVerify.getMaterialId());
                sinochemintlPoPlanLineDTO.setUomId(importVerify.getUomId());
                sinochemintlPoPlanLineDTO.setCurrencyId(importVerify.getCurrencyId());
                sinochemintlPoPlanLineDTO.setTaxId(importVerify.getTaxId());
                //业务需求
                Date date = new Date();
                sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                sinochemintlPoPlanLineDTO.setApplicant(user.getRealName());
                sinochemintlPoPlanLineDTO.setApplicantId(user.getUserId());
                sinochemintlPoPlanLineDTO.setCreationDate(date);
                sinochemintlPoPlanLineDTO.setCreatedBy(user.getUserId());
                sinochemintlPoPlanLineDTO.setLastUpdateDate(date);
                sinochemintlPoPlanLineDTO.setLastUpdatedBy(user.getUserId());
                sinochemintlPoPlanLineDTO.setSpellDocProvince(0L);
                sinochemintlPoPlanLineDTO.setTotalPurchasePrice(sinochemintlPoPlanLineDTO.getRequiredQuantity().multiply(sinochemintlPoPlanLineDTO.getFactoryPrice()));
                Integer serialNum = sinochemintlPoPlanLineRepository.getSerialNum(sinochemintlPoPlanLineDTO);
                sinochemintlPoPlanLineDTO.setSerialNum(serialNum == null ? 1 : serialNum + 1);
                sinochemintlPoPlanLineDTO.setDisplayLineNum(sinochemintlPoPlanLineDTO.getSerialNum());
                sinochemintlPoPlanLineRepository.setOne(sinochemintlPoPlanLineDTO);
            }
        } catch (IOException e) {
            logger.error("import data error: [{}] ,data: [{}]", e, datas);
            return false;
        }
        return true;
    }
}
