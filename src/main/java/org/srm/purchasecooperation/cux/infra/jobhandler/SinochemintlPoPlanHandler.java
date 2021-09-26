package org.srm.purchasecooperation.cux.infra.jobhandler;

import org.hzero.boot.scheduler.infra.annotation.JobHandler;
import org.hzero.boot.scheduler.infra.enums.ReturnT;
import org.hzero.boot.scheduler.infra.handler.IJobHandler;
import org.hzero.boot.scheduler.infra.tool.SchedulerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;

import java.util.Date;
import java.util.Map;

/**
 * 定时任务 : 根据拼单截至时间修改状态
 * @author gui-tian-jing
 * @date 2021/9/15 16:04
 */
@Component
@JobHandler(SinochemintlConstant.CodingCode.SPUC_ZHNY_PO_PLAN_HANDLER)
public class SinochemintlPoPlanHandler implements IJobHandler {

    @Autowired
    private SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository;

    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {
        sinochemintlPoPlanHeaderRepository.timedTaskAlterState(new Date());
        return ReturnT.SUCCESS;
    }

}
