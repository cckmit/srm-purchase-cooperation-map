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
        String ERROR_PARAMETER_ERROR = "参数错误！";
        String ERROR_NON_NEW_NOT_CANCEL = "非新建的单据不能取消！";
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
    }

    /**
     * 编码
     */
    public interface CodingCode {
        //采购计划单号
        String SCUX_ZHNY_RULES_PO_PLAN = "SCUX.ZHNY.RULES.PO_PLAN！";
    }
}
