package org.srm.purchasecooperation.cux.domain.repository;


import org.hzero.mybatis.base.BaseRepository;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;

import java.util.List;

/**
 * 采购计划头表资源库
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Component
public interface SinochemintlPoPlanHeaderRepository extends BaseRepository<SinochemintlPoPlanHeaderDTO> {

    /**
     * 采购计划头表查询参数
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     * @return 采购计划头表列表
     */
    List<SinochemintlPoPlanHeaderDTO> list(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 保存单头表
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     */
    void setOne(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 根据头表id修改
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     */
    void updateByKey(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 根据头表id删除
     *
     * @param poPlanHeaderId 头表id
     */
    void deleteByKey(Long poPlanHeaderId);

    /**
     * 根据头表id查询
     *
     * @param poPlanHeaderId 头表id
     * @return 头表数据
     */
    SinochemintlPoPlanHeaderDTO selectByKey(Long poPlanHeaderId);
}
