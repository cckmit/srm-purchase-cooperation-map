package org.srm.purchasecooperation.cux.domain.repository;


import org.hzero.mybatis.base.BaseRepository;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;

import java.util.List;

/**
 * 采购计划行表资源库
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Component
public interface SinochemintlPoPlanLineRepository extends BaseRepository<SinochemintlPoPlanLineDTO> {

    /**
     * 保存行表
     *
     * @param sinochemintlPoPlanLineDTO 采购计划行表
     */
    void setOne(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO);

    /**
     * 根据行表id修改
     *
     * @param sinochemintlPoPlanLineDTO 采购计划行表
     */
    void updateByKey(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO);

    /**
     * 根据头表id删除
     *
     * @param poPlanHeaderId 采购计划行表
     */
    void deleteByKey(Long poPlanHeaderId);

    /**
     * 根据行表id查询
     *
     * @param poPlanLineId 行表id
     * @return 行表数据
     */
    SinochemintlPoPlanLineDTO selectByKey(Long poPlanLineId);

    /**
     * 根据头表id查询
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     * @return 行表数据
     */
    List<SinochemintlPoPlanLineDTO> selectByHeaderId(Long organizationId, Long poPlanHeaderId);
}
