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
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanLine;
import org.srm.purchasecooperation.cux.domain.repository.ZhnyPoPlanLineRepository;

import java.io.IOException;
import java.util.List;

/**
 * 采购计划行表导入
 *
 * @author tianjing.gui@hand-china.com 2021-09-07 16:31:02
 */
@Service
@ImportService(templateCode = "SCUX.ZHNY.IMPORT.PO-PLAN")
public class ZhnyPoPlanImportServiceImpl extends BatchImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(ZhnyPoPlanImportServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZhnyPoPlanLineRepository zhnyPoPlanLineRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean doImport(List<String> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            logger.info("传入数据为空");
            return false;
        }
        try {
            //获取参数
            String poPlanHeaderId = getArgs(ZhnyPoPlanLine.FIELD_PO_PLAN_HEADER_ID);
            for (String data : datas) {
                ZhnyPoPlanLine zhnyPoPlanLine = objectMapper.readValue(data, ZhnyPoPlanLine.class);
                zhnyPoPlanLine.setPoPlanHeaderId(Long.valueOf(poPlanHeaderId));
                //业务需求
                zhnyPoPlanLineRepository.insert(zhnyPoPlanLine);
            }
        } catch (IOException e) {
            logger.error("import data error: [{}] ,data: [{}]", e, datas);
            return false;
        }
        return true;
    }
}
