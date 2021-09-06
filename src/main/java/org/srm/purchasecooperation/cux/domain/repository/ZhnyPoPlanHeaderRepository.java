package org.srm.purchasecooperation.cux.domain.repository;


import org.hzero.mybatis.base.BaseRepository;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanHeader;

import java.util.List;

/**
 * 采购计划头表资源库
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Component
public interface ZhnyPoPlanHeaderRepository extends BaseRepository<ZhnyPoPlanHeader> {

    /**
     * 采购计划头表查询参数
     *
     * @param zhnyPoPlanHeader 采购计划头表
     * @return 采购计划头表列表
     */
    List<ZhnyPoPlanHeader> list(ZhnyPoPlanHeader zhnyPoPlanHeader);
}
