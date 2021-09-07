package org.srm.purchasecooperation.cux.infra.constant;

/**
 * @author gui-tian-jing
 * @date 2021/9/6 20:34
 */
public class ZhnyConstant {

    /**
     * 错误提醒
     */
    public interface ErrorCode {
        String ERROR_CANT_DELETE = "仅新建状态的采购计划单才可以进行删除！";
        String ERROR_PARAMETER_ERROR = "参数错误！";
    }

    /**
     * 状态
     */
    public interface StatusCode {
        //新建状态
        String STATUS_NEW = "NEW！";
    }

    /**
     * 编码
     */
    public interface CodingCode {
        //采购计划单号
        String SCUX_ZHNY_RULES_PO_PLAN = "SCUX.ZHNY.RULES.PO_PLAN！";
    }
}
