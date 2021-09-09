package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.mapper.SinochemintlPoPlanLineMapper;

import java.util.List;

/**
 * 采购计划行表 资源库实现
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Component
public class SinochemintlPoPlanLineRepositoryImpl extends BaseRepositoryImpl<SinochemintlPoPlanLineDTO> implements SinochemintlPoPlanLineRepository {

    @Autowired
    private SinochemintlPoPlanLineMapper sinochemintlPoPlanLineMapper;

    @Override
    public void setOne(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        sinochemintlPoPlanLineMapper.setOne(sinochemintlPoPlanLineDTO);
    }

    @Override
    public void updateByKey(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        sinochemintlPoPlanLineMapper.updateByKey(sinochemintlPoPlanLineDTO);
    }

    @Override
    public void deleteByKey(Long poPlanHeaderId) {
        sinochemintlPoPlanLineMapper.deleteByKey(poPlanHeaderId);
    }

    @Override
    public SinochemintlPoPlanLineDTO selectByKey(Long poPlanLineId) {
        return sinochemintlPoPlanLineMapper.selectByKey(poPlanLineId);
    }

    @Override
    public List<SinochemintlPoPlanLineDTO> selectByHeaderId(Long organizationId, Long poPlanHeaderId) {
        return sinochemintlPoPlanLineMapper.selectByHeaderId(organizationId, poPlanHeaderId);
    }
}
