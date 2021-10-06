package org.srm.purchasecooperation.cux.infra.jobhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.boot.scheduler.infra.annotation.JobHandler;
import org.hzero.boot.scheduler.infra.enums.ReturnT;
import org.hzero.boot.scheduler.infra.handler.IJobHandler;
import org.hzero.boot.scheduler.infra.tool.SchedulerTool;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.srm.purchasecooperation.cux.api.dto.MessageSenderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.app.service.impl.SinochemintlSendMessageService;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlMessageConstant;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时任务 : 根据拼单截至时间提前发起通知
 *
 * @author gui-tian-jing
 * @date 2021/10/5 11:32
 */
@Component
@JobHandler(SinochemintlConstant.CodingCode.SPUC_ZHNY_ADVANCED_PO_PLAN)
public class SinochemintlAdvancedPoPlanHandler implements IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(SinochemintlAdvancedPoPlanHandler.class);

    @Autowired
    private SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository;

    @Autowired
    private SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository;

    @Autowired
    private SinochemintlSendMessageService sinochemintlSendMessageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {
        //校验快到期采购计划
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        List<SinochemintlPoPlanHeaderDTO> list = sinochemintlPoPlanHeaderRepository.getExpirationTime(cal.getTime());
        if (!list.isEmpty()) {
            for (SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO : list) {
                Set<Integer> longs = new HashSet<>();
                SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = new SinochemintlPoPlanLineDTO();
                sinochemintlPoPlanLine.setPoPlanHeaderId(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
                sinochemintlPoPlanLine.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
                List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanLineRepository.selectByHeaderId(sinochemintlPoPlanLine);
                for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineDTOS) {
                    String planSharedProvince = sinochemintlPoPlanLineDTO.getPlanSharedProvince();
                    if (!StringUtils.isEmpty(planSharedProvince)) {
                        ArrayList<Map<String, Object>> arrayList = null;
                        try {
                            arrayList = objectMapper.readValue(planSharedProvince, ArrayList.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (Map<String, Object> stringMap : arrayList) {
                            longs.add((Integer) stringMap.get("companyId"));
                        }
                    }
                }
                List<Receiver> receiverList = new ArrayList<>(sinochemintlSendMessageService.getReceiverList(longs));
                receiverList = receiverList.stream().distinct().collect(Collectors.toList());
                try {
                    Map<String, String> paramMap = new HashMap<>(BaseConstants.Digital.SIXTEEN);
                    paramMap.putAll(sinochemintlSendMessageService.getCommonParam(sinochemintlPoPlanHeaderDTO));
                    sinochemintlSendMessageService.sendEmail(new MessageSenderDTO(sinochemintlPoPlanHeaderDTO.getTenantId(), SinochemintlMessageConstant.MESSAGE_INFORM_ADVANCE_CODE, SinochemintlMessageConstant.Email_Server_Code, receiverList, paramMap, null));
                    sinochemintlSendMessageService.sendSms(new MessageSenderDTO(sinochemintlPoPlanHeaderDTO.getTenantId(), SinochemintlMessageConstant.MESSAGE_INFORM_ADVANCE_CODE, SinochemintlMessageConstant.Sms_Server_Code, receiverList, paramMap, null));
                    sinochemintlSendMessageService.sendWebMessage(new MessageSenderDTO(sinochemintlPoPlanHeaderDTO.getTenantId(), SinochemintlMessageConstant.MESSAGE_INFORM_ADVANCE_CODE, null, receiverList, paramMap, SinochemintlMessageConstant.Web_Message_Language_Chinese));
                } catch (IllegalArgumentException e) {
                    logger.error("Message sending failure:{}", receiverList);
                }
            }
        }
        sinochemintlPoPlanHeaderRepository.timedTaskAlterState(cal.getTime(), SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_MIDDLE);
        return ReturnT.SUCCESS;
    }
}
