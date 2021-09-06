package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanHeader;
import org.srm.purchasecooperation.cux.domain.repository.ZhnyPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.infra.mapper.ZhnyPoPlanHeaderMapper;

import java.util.List;

/**
 * 采购计划头表 资源库实现
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Component
public class ZhnyPoPlanHeaderRepositoryImpl extends BaseRepositoryImpl<ZhnyPoPlanHeader> implements ZhnyPoPlanHeaderRepository {

    @Autowired
    private ZhnyPoPlanHeaderMapper zhnyPoPlanHeaderMapper;

    @Override
    public List<ZhnyPoPlanHeader> list(ZhnyPoPlanHeader zhnyPoPlanHeader) {
        return zhnyPoPlanHeaderMapper.list(zhnyPoPlanHeader);
    }

}
