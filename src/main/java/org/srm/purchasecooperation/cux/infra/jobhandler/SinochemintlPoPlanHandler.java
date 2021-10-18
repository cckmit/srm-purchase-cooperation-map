package org.srm.purchasecooperation.cux.infra.jobhandler;

import org.hzero.boot.scheduler.infra.annotation.JobHandler;
import org.hzero.boot.scheduler.infra.enums.ReturnT;
import org.hzero.boot.scheduler.infra.handler.IJobHandler;
import org.hzero.boot.scheduler.infra.tool.SchedulerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.app.service.SinochemintlPoPlanService;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;

import java.util.*;

/**
 * 定时任务 : 根据拼单截至时间修改状态
 *
 * @author gui-tian-jing
 * @date 2021/9/15 16:04
 */
@Component
@JobHandler(SinochemintlConstant.CodingCode.SPUC_ZHNY_PO_PLAN_HANDLER)
public class SinochemintlPoPlanHandler implements IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(SinochemintlPoPlanHandler.class);

    @Autowired
    private SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository;

    @Autowired
    private SinochemintlPoPlanService sinochemintlPoPlanService;

    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {
        tool.updateProgress(1, "任务开始...");
        sinochemintlPoPlanService.timedTaskHeader();
        sinochemintlPoPlanHeaderRepository.timedTaskAlterState(new Date(), SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_COMPLETE);
        tool.updateProgress(99, "任务结束...");
        tool.info("任务执行完毕了...");
        return ReturnT.SUCCESS;
    }

}
