package org.srm.purchasecooperation.cux.app.service.impl;

import cfca.org.slf4j.Logger;
import cfca.org.slf4j.LoggerFactory;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.MessageSenderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlMessageConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author jiaxing.huang@hand-china.com  2021/08/16 15:49
 */
@Component
public class SinochemintlSendMessageService{

    @Autowired
    private MessageClient messageClient;
    @Autowired
    private LovAdapter lovAdapter;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BaseConstants.Pattern.DATE);

    private static final Logger LOGGER = LoggerFactory.getLogger(SinochemintlSendMessageService.class);

    /**
     * 发送邮件
     * @param messageSender
     */
    public void sendEmail(MessageSenderDTO messageSender) {
        messageClient.async().sendEmail(messageSender.getTenantId(), messageSender.getServerCode(), messageSender.getMessageCode(), messageSender.getReceiverList(), messageSender.getParamMap());
    }

    /**
     * 发送短信
     * @param messageSender
     */
    public void sendSms(MessageSenderDTO messageSender) {
        messageClient.async().sendSms(messageSender.getTenantId(), messageSender.getServerCode(), messageSender.getMessageCode(), messageSender.getReceiverList(), messageSender.getParamMap());
    }

    /**
     * 发送站内消息
     * @param messageSender
     */
    public void sendWebMessage(MessageSenderDTO messageSender) {
        messageClient.async().sendWebMessage(messageSender.getTenantId(), messageSender.getMessageCode(), messageSender.getLang(), messageSender.getReceiverList(), messageSender.getParamMap());
    }

    /**
     * 获取消息模板参数
     * @param poPlanHeader
     * @return
     */
    public Map<String, String> getCommonParam(SinochemintlPoPlanHeaderDTO poPlanHeader) {
        Map<String, String> paramMap = new HashMap<>(BaseConstants.Digital.SIXTEEN);
        paramMap.put(SinochemintlMessageConstant.MessageTemplateParameters.Parameter_Company_Name, poPlanHeader.getCompanyName());
        paramMap.put(SinochemintlMessageConstant.MessageTemplateParameters.Parameter_Applicant, poPlanHeader.getApplicant());
        paramMap.put(SinochemintlMessageConstant.MessageTemplateParameters.Parameter_Po_Plan_Number, poPlanHeader.getPoPlanNumber());
        paramMap.put(SinochemintlMessageConstant.MessageTemplateParameters.Parameter_Title, poPlanHeader.getTitle());
        paramMap.put(SinochemintlMessageConstant.MessageTemplateParameters.Parameter_Deadline, poPlanHeader.getDeadline().toString());
        return paramMap;
    }

    /**
     * 获取接受者
     * @param organizationId
     * @param string
     * @return
     */
    public List<Receiver> getReceiverList(Long organizationId, String string) {
        List<Receiver> receiverList = new ArrayList<>();
        List<String> params = new ArrayList<>();
        //lovAdapter.queryLovValue(SinochemintlMessageConstant.Lov_Code, organizationId, params);


        return receiverList;
    }
}

