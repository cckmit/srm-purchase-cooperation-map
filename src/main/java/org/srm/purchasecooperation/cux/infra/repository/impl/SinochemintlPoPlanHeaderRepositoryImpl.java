package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.domain.entity.SinochemintlPoPlanHeader;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.infra.mapper.SinochemintlPoPlanHeaderMapper;

import java.util.List;

/**
 * 采购计划头表 资源库实现
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Component
public class SinochemintlPoPlanHeaderRepositoryImpl extends BaseRepositoryImpl<SinochemintlPoPlanHeader> implements SinochemintlPoPlanHeaderRepository {

    @Autowired
    private SinochemintlPoPlanHeaderMapper sinochemintlPoPlanHeaderMapper;

    @Override
    public List<SinochemintlPoPlanHeader> list(SinochemintlPoPlanHeader sinochemintlPoPlanHeader) {
        return sinochemintlPoPlanHeaderMapper.list(sinochemintlPoPlanHeader);
    }

}
