package org.srm.purchasecooperation.cux.api.dto;

import org.hzero.boot.message.entity.Receiver;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author jiaxing.huang@hand-china.com  2021/09/16 9:57
 */
public class MessageSenderDTO {
    private Long tenantId;
    private String messageCode;
    private String serverCode;
    private String lang;
    private List<Receiver> receiverList;
    private Map<String, String> paramMap;

    public MessageSenderDTO() {
    }

    public MessageSenderDTO(Long tenantId, String messageCode, String serverCode, List<Receiver> receiverList, Map<String, String> paramMap, String lang) {
        this.tenantId = tenantId;
        this.messageCode = messageCode;
        this.serverCode = serverCode;
        this.receiverList = receiverList;
        this.paramMap = paramMap;
        this.lang = lang;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }


    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }
    public List<Receiver> getReceiverList() {
        return receiverList;
    }
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setReceiverList(List<Receiver> receiverList) {
        this.receiverList = receiverList;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
