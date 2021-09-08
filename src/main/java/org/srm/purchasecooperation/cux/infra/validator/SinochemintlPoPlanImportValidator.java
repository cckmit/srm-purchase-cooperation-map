package org.srm.purchasecooperation.cux.infra.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.imported.app.service.ValidatorHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.domain.entity.SinochemintlPoPlanLine;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;

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
    private SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SinochemintlPoPlanImportValidator.class);

    @Override
    public boolean validate(String data) {
        LOGGER.info("param detail import data examine: [{}]", data);
        boolean isBlank = true;
        try {
            SinochemintlPoPlanLine sinochemintlPoPlanLine = objectMapper.readValue(data, SinochemintlPoPlanLine.class);
            sinochemintlPoPlanLine.setTenantId(DetailsHelper.getUserDetails().getTenantId());
            isBlank = checkData(sinochemintlPoPlanLine);
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
    private boolean checkData(SinochemintlPoPlanLine sinochemintlPoPlanLine) {
        //供应商名称必填
        return sinochemintlPoPlanLine != null;
    }

}
