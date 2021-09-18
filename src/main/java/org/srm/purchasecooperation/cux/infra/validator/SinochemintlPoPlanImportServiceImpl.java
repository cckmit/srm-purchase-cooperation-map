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
import java.util.Date;
import java.util.List;

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
                SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO = objectMapper.readValue(data, SinochemintlPoPlanLineDTO.class);
                sinochemintlPoPlanLineDTO.setPoPlanHeaderId(Long.valueOf(poPlanHeaderId));
                //将值集转换为id字段并保存
                SinochemintlPoPlanLineDTO importVerify = sinochemintlPoPlanHeaderRepository.importVerify(sinochemintlPoPlanLineDTO);
                sinochemintlPoPlanLineDTO.setProvinceCompanyId(importVerify.getProvinceCompanyId());
                sinochemintlPoPlanLineDTO.setInitialSupplierId(importVerify.getInitialSupplierId());
                sinochemintlPoPlanLineDTO.setMaterialId(importVerify.getMaterialId());
                sinochemintlPoPlanLineDTO.setUomId(importVerify.getUomId());
                sinochemintlPoPlanLineDTO.setCurrencyId(importVerify.getCurrencyId());
                sinochemintlPoPlanLineDTO.setTaxId(importVerify.getTaxId());
                //业务需求
                CustomUserDetails user = DetailsHelper.getUserDetails();
                Date date = new Date();
                sinochemintlPoPlanLineDTO.setTenantId(user.getTenantId());
                sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                sinochemintlPoPlanLineDTO.setApplicant(user.getRealName());
                sinochemintlPoPlanLineDTO.setCreationDate(date);
                sinochemintlPoPlanLineDTO.setCreatedBy(user.getUserId());
                sinochemintlPoPlanLineDTO.setLastUpdateDate(date);
                sinochemintlPoPlanLineDTO.setLastUpdatedBy(user.getUserId());
                sinochemintlPoPlanLineDTO.setSerialNum(sinochemintlPoPlanLineRepository.getSerialNum(String.valueOf(poPlanHeaderId)) + 1);
                sinochemintlPoPlanLineRepository.setOne(sinochemintlPoPlanLineDTO);
            }
        } catch (IOException e) {
            logger.error("import data error: [{}] ,data: [{}]", e, datas);
            return false;
        }
        return true;
    }
}
