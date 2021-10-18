package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.mapper.SinochemintlPoPlanLineMapper;

import java.util.HashSet;
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
    public List<SinochemintlPoPlanLineDTO> selectByHeaderId(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        return sinochemintlPoPlanLineMapper.selectByHeaderId(sinochemintlPoPlanLineDTO);
    }

    @Override
    public List<String> getPlanSharedProvince(List<String> strings) {
        return sinochemintlPoPlanLineMapper.getPlanSharedProvince(strings);
    }

    @Override
    public Integer getSerialNum(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        return sinochemintlPoPlanLineMapper.getSerialNum(sinochemintlPoPlanLineDTO);
    }

    @Override
    public Integer getDisplayLineNum(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        return sinochemintlPoPlanLineMapper.getDisplayLineNum(sinochemintlPoPlanLineDTO);
    }

    @Override
    public List<SinochemintlPoPlanLineDTO> sharedProvinceVerify(List<String> planSharedProvince) {
        return sinochemintlPoPlanLineMapper.sharedProvinceVerify(planSharedProvince);
    }

    @Override
    public void batchMaint(SinochemintlPoPlanLineDTO dto) {
        sinochemintlPoPlanLineMapper.batchMaint(dto);
    }

    @Override
    public List<SinochemintlPoPlanLineDTO> detailList(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanLineMapper.detailList(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public HashSet<Long> verifyPlanSharedProvince(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        return sinochemintlPoPlanLineMapper.verifyPlanSharedProvince(sinochemintlPoPlanLineDTO);
    }

    @Override
    public Integer spellDocProvince(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        return sinochemintlPoPlanLineMapper.spellDocProvince(sinochemintlPoPlanLineDTO);
    }

    @Override
    public List<SinochemintlPoPlanLineDTO> getPoPlanLine(SinochemintlPoPlanLineDTO sinochemintlPoPlanLine) {
        return sinochemintlPoPlanLineMapper.getPoPlanLine(sinochemintlPoPlanLine);
    }

    @Override
    public SinochemintlPoPlanLineDTO getCnyCurrency(Long tenantId) {
        return sinochemintlPoPlanLineMapper.getCnyCurrency(tenantId);
    }
}
