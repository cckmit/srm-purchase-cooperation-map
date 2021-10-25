package org.srm.purchasecooperation.cux.infra.constant;

/**
 * @author gui-tian-jing
 * @date 2021/9/6 20:34
 */
public class SinochemintlConstant {

    /**
     * 错误提醒
     */
    public interface ErrorCode {
        String ERROR_CANT_DELETE = "仅新建状态的采购计划单才可以进行删除！";
        String ERROR_LINE_NO_DATA = "行表无数据，无法提交。！";
        String ERROR_PARAMETER_ERROR = "参数错误！";
        String ERROR_NON_NEW_NOT_CANCEL = "非新建的单据不能取消！";
        String ERROR_RESULTS_NOT_ENTERED = "定点结果尚未录入，请进行录入！";
        String ERROR_SHARED_PROVINCE_CORRE = "只有共享省区对应人才可以进行拼单！";
        String ERROR_NOT_FOUNDER = "只有创建人才可以最终确认采购计划！";
        String ERROR_NOT_FOUNDER_CANCEL = "只有创建人才可以最终确认采购计划！";
    }

    /**
     * 状态
     */
    public interface StatusCode {
        //新建状态
        String STATUS_NEW = "NEW";
        //拼单中
        String STATUS_SPLICING_DOC_MIDDLE = "SPLICING_DOC_MIDDLE";
        //拼单完成
        String STATUS_SPLICING_DOC_COMPLETE = "SPLICING_DOC_COMPLETE";
        //录入完成
        String STATUS_INPUT_COMPLETE = "INPUT_COMPLETE";
        //已取消
        String STATUS_CANCELLED = "CANCELLED";
        //拼单标识
        String STATUS_MAINTAIN = "MAINTAIN";
    }

    /**
     * 状态
     */
    public interface ActionStatusCode {
        //新建
        String STATUS_PENDING = "PENDING";
        //提交
        String STATUS_SUBMITTED = "SUBMITTED";
        //更新
        String STATUS_UPDATE = "UPDATE";
        //新增行
        String STATUS_NEWLINE = "NEWLINE";
        //取消
        String STATUS_CANCEL = "CANCEL";
        //删除行
        String STATUS_DELLINE = "DELLINE";
        //确认
        String STATUS_ENABLE = "ENABLE";
    }

    /**
     * 编码
     */
    public interface CodingCode {
        //采购计划单号
        String SCUX_ZHNY_RULES_PO_PLAN = "SCUX.ZHNY.RULES.PO_PLAN";
        //定时任务 : 根据拼单截至时间修改状态
        String SPUC_ZHNY_PO_PLAN_HANDLER = "SPUC.ZHNY.PO_PLAN.HANDLER";
        //定时任务 : 根据拼单截至时间提前发起通知
        String SPUC_ZHNY_ADVANCED_PO_PLAN = "SPUC.ZHNY.ADVANCED.PO_PLAN";
        //短信提醒人配置
        String SPUC_SINOCHEMINTL_PURCHASING_AGENT = "SPUC.SINOCHEMINTL.PURCHASING_AGENT";
        //默认共享省区
        String SPUC_SINOCHEMINTL_PLAN_SHARED_PROVINCE = "SPUC.SINOCHEMINTL.PLAN_SHARED_PROVINCE";
        //计划类型
        String SINOCHEMINTL_PROJECT_TYPE = "SINOCHEMINTL.PROJECT_TYPE";
    }
}
