package org.srm.purchasecooperation.cux.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanHeader;

import java.util.List;

/**
 * 采购计划头表Mapper
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
public interface ZhnyPoPlanHeaderMapper extends BaseMapper<ZhnyPoPlanHeader> {

    /**
     * 采购计划头表查询参数
     *
     * @param zhnyPoPlanHeader 采购计划头表
     * @return 采购计划头表列表
     */
    List<ZhnyPoPlanHeader> list(ZhnyPoPlanHeader zhnyPoPlanHeader);

}
