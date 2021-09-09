package org.srm.purchasecooperation.cux.infra.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.imported.app.service.BatchImportHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srm.purchasecooperation.cux.domain.entity.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;

import java.io.IOException;
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
            String poPlanHeaderId = getArgs(SinochemintlPoPlanLineDTO.FIELD_PO_PLAN_HEADER_ID);
            for (String data : datas) {
                SinochemintlPoPlanLineDTO SinochemintlPoPlanLineDTO = objectMapper.readValue(data, SinochemintlPoPlanLineDTO.class);
                SinochemintlPoPlanLineDTO.setPoPlanHeaderId(Long.valueOf(poPlanHeaderId));
                //业务需求
                sinochemintlPoPlanLineRepository.insert(SinochemintlPoPlanLineDTO);
            }
        } catch (IOException e) {
            logger.error("import data error: [{}] ,data: [{}]", e, datas);
            return false;
        }
        return true;
    }
}
