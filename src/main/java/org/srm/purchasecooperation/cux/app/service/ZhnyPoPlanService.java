package org.srm.purchasecooperation.cux.app.service;


import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.api.dto.ZhnyPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanHeader;

import java.util.List;

/**
 * 采购计划应用服务
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
public interface ZhnyPoPlanService {

    /**
     * 采购计划头表查询参数
     *
     * @param zhnyPoPlanHeader 采购计划头表
     * @param pageRequest      分页
     * @return 采购计划头表列表
     */
    Page<ZhnyPoPlanHeader> list(ZhnyPoPlanHeader zhnyPoPlanHeader, PageRequest pageRequest);

    /**
     * 新增/保存/修改采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    ZhnyPoPlanHeader addPoPlan(ZhnyPoPlanHeaderDTO dto);

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
    ZhnyPoPlanHeaderDTO getPoPlan(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest);

    /**
     * 提交采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    void submit(Long poPlanHeaderId);

    /**
     * 取消采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    void cancel(Long poPlanHeaderId);
}
