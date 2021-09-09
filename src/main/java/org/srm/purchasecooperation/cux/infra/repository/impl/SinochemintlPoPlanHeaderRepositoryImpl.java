package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.infra.mapper.SinochemintlPoPlanHeaderMapper;

import java.util.List;

/**
 * 采购计划头表 资源库实现
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Component
public class SinochemintlPoPlanHeaderRepositoryImpl extends BaseRepositoryImpl<SinochemintlPoPlanHeaderDTO> implements SinochemintlPoPlanHeaderRepository {

    @Autowired
    private SinochemintlPoPlanHeaderMapper sinochemintlPoPlanHeaderMapper;

    @Override
    public List<SinochemintlPoPlanHeaderDTO> list(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanHeaderMapper.list(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public void setOne(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        sinochemintlPoPlanHeaderMapper.setOne(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public void updateByKey(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        sinochemintlPoPlanHeaderMapper.updateByKey(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public void deleteByKey(Long poPlanHeaderId) {
        sinochemintlPoPlanHeaderMapper.deleteByKey(poPlanHeaderId);
    }

    @Override
    public SinochemintlPoPlanHeaderDTO selectByKey(Long poPlanHeaderId) {
        return sinochemintlPoPlanHeaderMapper.selectByKey(poPlanHeaderId);
    }
}
