package org.srm.purchasecooperation.cux.app.service;


import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;

import java.util.List;

/**
 * 采购计划应用服务
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
public interface SinochemintlPoPlanService {

    /**
     * 采购计划头表查询参数
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     * @param pageRequest                 分页
     * @return 采购计划头表列表
     */
    Page<SinochemintlPoPlanHeaderDTO> list(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, PageRequest pageRequest);

    /**
     * 新增/保存/修改采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    SinochemintlPoPlanHeaderDTO addPoPlan(SinochemintlPoPlanHeaderDTO dto);

    /**
     * 删除采购计划头表
     *
     * @param ids 头表id集合
     */
    void delHeader(List<Long> ids);

    /**
     * 删除采购计划行表
     *
     * @param ids 行表id集合
     */
    void delLine(List<Long> ids);

    /**
     * 获取头行单表
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     */
    SinochemintlPoPlanHeaderDTO getPoPlan(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest);

    /**
     * 提交采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    void submit(Long organizationId, Long poPlanHeaderId);

    /**
     * 取消采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    void cancel(Long poPlanHeaderId);

    /**
     * 采购计划导出
     *
     * @param ids 勾选的头表id
     * @return 需要导出的结果
     */
    List<SinochemintlPoPlanExcelDTO> excel(List<Long> ids);

    /**
     * 采购计划确认
     *
     * @param dto 修改后的数据
     */
    void confirm(SinochemintlPoPlanHeaderDTO dto);

    /**
     * 采购计划拼单截止时间到达通知（定时任务触发）
     *
     */
    void timedTaskHeader();

}
