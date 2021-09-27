package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlEmployeeInformationDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.infra.mapper.SinochemintlPoPlanHeaderMapper;

import java.util.Date;
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

    @Override
    public Long getPoPlanHeaderId(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanHeaderMapper.getPoPlanHeaderId(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public List<SinochemintlPoPlanExcelDTO> excel(List<String> ids) {
        return sinochemintlPoPlanHeaderMapper.excel(ids);
    }

    @Override
    public List<SinochemintlPoPlanHeaderDTO> maintain(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanHeaderMapper.maintain(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public SinochemintlPoPlanLineDTO importVerify(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO) {
        return sinochemintlPoPlanHeaderMapper.importVerify(sinochemintlPoPlanLineDTO);
    }

    @Override
    public void timedTaskAlterState(Date date, String status) {
        sinochemintlPoPlanHeaderMapper.timedTaskAlterState(date, status);
    }

    @Override
    public List<SinochemintlPoPlanLineDTO> getDefaultCompanyId(Long employeeId) {
        return sinochemintlPoPlanHeaderMapper.getDefaultCompanyId(employeeId);
    }

    @Override
    public List<SinochemintlEmployeeInformationDTO> getDefaultEmployeeList(Long provinceCompanyId) {
        return sinochemintlPoPlanHeaderMapper.getDefaultEmployeeList(provinceCompanyId);
    }

    @Override
    public List<SinochemintlPoPlanExcelDTO> batchExcel(SinochemintlPoPlanHeaderDTO dto) {
        return sinochemintlPoPlanHeaderMapper.batchExcel(dto);
    }

    @Override
    public List<SinochemintlPoPlanHeaderDTO> getExpirationTime(Date date) {
        return sinochemintlPoPlanHeaderMapper.getExpirationTime(date);
    }
}
