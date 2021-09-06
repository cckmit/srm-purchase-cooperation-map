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
     * 新增采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    void addPoPlan(ZhnyPoPlanHeaderDTO dto);

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
}
