package org.srm.purchasecooperation.cux.infra.constant;


/**
 * @author huang-jia-xing
 * @date 2021/9/16 10:50
 */
public class SinochemintlMessageConstant {

    public static String Message_Input_Template_Code = "SPUC_PURCHASE_RESULTS_INPUT_NOTIFICATION";
    public static String Message_Submit_Template_Code = "SPUC.PURCHASE_PLAN_SUBMISSION_NOTICE";
    public static String Message_ARRIVAL_Template_Code = "SPUC_PURCHASE_SCHEDULE_TIME_ARRIVAL_NOTIFICATION";
    public static String MESSAGE_INFORM_ADVANCE_CODE = "SPUC.PURCHASE.INFORM_ADVANCE";
    public static String Web_Message_Language_Chinese = "zh_CN";
    public static String Web_Message_Language_English = "en_US";
    /**
     * Server_code
     */
    public static String Email_Server_Code = "MAP";
    public static String Sms_Server_Code = "MAP_ALIYUN";

    /**
     * 消息模板参数
     */
    public interface MessageTemplateParameters {
        String Parameter_Company_Name = "companyName";
        String Parameter_Applicant = "applicant";
        String Parameter_Po_Plan_Number = "poPlanNumber";
        String Parameter_Title = "title";
        String Parameter_Deadline = "deadline";
    }

}
