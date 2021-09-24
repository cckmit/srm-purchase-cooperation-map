package org.srm.purchasecooperation.cux.infra.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.imported.app.service.ValidatorHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 采购计划行表导入校验
 *
 * @author tianjing.gui@hand-china.com 2021-09-07 16:31:02
 */
@ImportValidators({
        @ImportValidator(templateCode = "SCUX.ZHNY.IMPORT.PO-PLAN")
})
public class SinochemintlPoPlanImportValidator extends ValidatorHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository;

    @Autowired
    private SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SinochemintlPoPlanImportValidator.class);

    @Override
    public boolean validate(String data) {
        LOGGER.info("param detail import data examine: [{}]", data);
        boolean isBlank = true;
        try {
            SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO = objectMapper.readValue(data, SinochemintlPoPlanLineDTO.class);
            sinochemintlPoPlanLineDTO.setTenantId(DetailsHelper.getUserDetails().getTenantId());
            isBlank = checkData(sinochemintlPoPlanLineDTO);
        } catch (Exception e) {
            LOGGER.error("import data examine error: [{}] ,data: [{}]", e, data);
            return false;
        }
        return isBlank;
    }

    /**
     * 数据校验
     *
     * @return 校验结果
     */
    private boolean checkData(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        //供应商名称必填
        SinochemintlPoPlanLineDTO importVerify;
        if ("人民币".equals(sinochemintlPoPlanLineDTO.getCurrencyName())) {
            sinochemintlPoPlanLineDTO.setCurrencyName("");
            importVerify = sinochemintlPoPlanHeaderRepository.importVerify(sinochemintlPoPlanLineDTO);
            sinochemintlPoPlanLineDTO.setCurrencyId("304");
        } else {
            importVerify = sinochemintlPoPlanHeaderRepository.importVerify(sinochemintlPoPlanLineDTO);
        }
        List<String> planSharedProvince = Arrays.asList(sinochemintlPoPlanLineDTO.getPlanSharedProvince().split("、"));
        List<SinochemintlPoPlanLineDTO> planSharedProvinceName = sinochemintlPoPlanLineRepository.sharedProvinceVerify(planSharedProvince);
        if (planSharedProvinceName.size() != planSharedProvince.size()) {
            getContext().addErrorMsg("共享计划省区:" + sinochemintlPoPlanLineDTO.getPlanSharedProvince() + "不存在！");
            return false;
        }
        if (StringUtils.isEmpty(importVerify.getProvinceCompanyId())) {
            getContext().addErrorMsg("省公司/项目:" + sinochemintlPoPlanLineDTO.getProvinceCompany() + ",不存在！");
            return false;
        }
        if (StringUtils.isEmpty(importVerify.getInitialSupplierId())) {
            getContext().addErrorMsg("初步沟通供应商:" + sinochemintlPoPlanLineDTO.getInitialSupplier() + ",不存在！");
            return false;
        }
        if (!StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getMaterialCoding()) && StringUtils.isEmpty(importVerify.getMaterialId())) {
            getContext().addErrorMsg("物料编码:" + sinochemintlPoPlanLineDTO.getMaterialCoding() + ",不存在！");
            return false;
        }
        if (StringUtils.isEmpty(importVerify.getUomId())) {
            getContext().addErrorMsg("单位:" + sinochemintlPoPlanLineDTO.getUnitName() + ",不存在！");
            return false;
        }
        if (StringUtils.isEmpty(importVerify.getCurrencyId())) {
            getContext().addErrorMsg("币种:" + sinochemintlPoPlanLineDTO.getCurrencyName() + ",不存在！");
            return false;
        }
        if (!StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getTaxType()) && StringUtils.isEmpty(importVerify.getTaxId())) {
            getContext().addErrorMsg("税种:" + sinochemintlPoPlanLineDTO.getTaxType() + ",不存在！");
            return false;
        }
        return true;
    }

}
