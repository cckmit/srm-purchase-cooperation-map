package org.srm.purchasecooperation.cux.infra.repository.impl;

import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.app.service.impl.SinochemintlPoPlanServiceImpl;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;
import org.srm.purchasecooperation.cux.infra.mapper.SinochemintlPoPlanHeaderMapper;

import java.util.*;

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
        List<SinochemintlPoPlanHeaderDTO> sinochemintlPoPlanHeaderDTOList = sinochemintlPoPlanHeaderMapper.list(sinochemintlPoPlanHeaderDTO);
        for(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeader : sinochemintlPoPlanHeaderDTOList){
            sinochemintlPoPlanHeader.setProvinseNames(this.getDefaultCompany());
        }
        return sinochemintlPoPlanHeaderDTOList;
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
        List<SinochemintlPoPlanHeaderDTO> sinochemintlPoPlanHeaderDTOList = sinochemintlPoPlanHeaderMapper.maintain(sinochemintlPoPlanHeaderDTO);
        for(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeader : sinochemintlPoPlanHeaderDTOList){
            sinochemintlPoPlanHeader.setProvinseNames(this.getDefaultCompany());
        }
        return sinochemintlPoPlanHeaderDTOList;
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
    public List<SinochemintlPoPlanHeaderDTO> timedTaskHeader(Date date) {
        return sinochemintlPoPlanHeaderMapper.timedTaskHeader(date);
    }

    @Override
    public List<SinochemintlPoPlanLineDTO> getDefaultCompanyId(Long employeeId) {
        return sinochemintlPoPlanHeaderMapper.getDefaultCompanyId(employeeId);
    }

    @Autowired
    public LovAdapter lovAdapter;

    @Override
    public List<Receiver> getDefaultEmployeeList(List<Integer> integers) {
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<LovValueDTO> lovValues = lovAdapter.queryLovValue(SinochemintlConstant.CodingCode.SPUC_SINOCHEMINTL_PURCHASING_AGENT, user.getTenantId());
        return sinochemintlPoPlanHeaderMapper.getDefaultEmployeeList(integers, lovValues);
    }

    @Override
    public List<SinochemintlPoPlanExcelDTO> batchExcel(SinochemintlPoPlanHeaderDTO dto) {
        return sinochemintlPoPlanHeaderMapper.batchExcel(dto);
    }

    @Override
    public List<SinochemintlPoPlanHeaderDTO> getExpirationTime(Date date) {
        return sinochemintlPoPlanHeaderMapper.getExpirationTime(date);
    }

    @Override
    public List<SinochemintlPoPlanHeaderDTO> verifyBusiness(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanHeaderMapper.verifyBusiness(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public List<SinochemintlPoPlanHeaderDTO> verifyDepartment(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanHeaderMapper.verifyDepartment(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public List<SinochemintlPoPlanHeaderDTO> verifyPurchaseOrg(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanHeaderMapper.verifyPurchaseOrg(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public List<SinochemintlPoPlanExcelDTO> excelLine(List<String> list) {
        return sinochemintlPoPlanHeaderMapper.excelLine(list);
    }

    @Override
    public List<SinochemintlPoPlanExcelDTO> batchExcelLine(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        return sinochemintlPoPlanHeaderMapper.batchExcelLine(sinochemintlPoPlanHeaderDTO);
    }

    @Override
    public SinochemintlPoPlanLineDTO getCompany(String planSharedProvince) {
        return sinochemintlPoPlanHeaderMapper.getCompany(planSharedProvince);
    }

    @Override
    public List<String> getCompanies(ArrayList<Integer> integers) {
        return sinochemintlPoPlanHeaderMapper.selectCompanyById(integers);
    }

    @Override
    public List<Receiver> getLovEmployeeList(List<String> list) {
        return sinochemintlPoPlanHeaderMapper.getEmployees(list);
    }

    @Override
    public List<String> getCompanyNumById(ArrayList<Integer> integers) {
        return sinochemintlPoPlanHeaderMapper.selectCompanyNumById(integers);
    }
    /**
     * 获取共享省区
     * @return 共享省区
     */
    public String getDefaultCompany() {
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<LovValueDTO> lovValues = lovAdapter.queryLovValue(SinochemintlConstant.CodingCode.SPUC_SINOCHEMINTL_PLAN_SHARED_PROVINCE, user.getTenantId());
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = this.getDefaultCompanyId(user.getUserId());
        StringBuffer provinses= new StringBuffer();
        StringBuffer provinseNames= new StringBuffer();
        if (!sinochemintlPoPlanLineDTOS.isEmpty()) {
            String companyNum = sinochemintlPoPlanLineDTOS.get(0).getPlanSharedProvince();
            for(LovValueDTO lovValueDTO : lovValues){
                String meaning = lovValueDTO.getMeaning();
                if(meaning.contains(companyNum)){
                    provinses.append(meaning).append(",");
                }
            }
        }
        //去重
        String[] province = provinses.toString().replaceAll(" ","").trim().split(",");
        List<String> list = Arrays.asList(province);
        Set<String> set = new HashSet(list);
        boolean flag = (set.size() == 1 && set.contains("")) || (set.size() == 0);
        if(!flag){
            for (String string : set) {
                SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = this.getCompany(string);
                provinseNames.append(sinochemintlPoPlanLine.getProvinceCompany()).append(",");
            }
            return provinseNames.deleteCharAt(provinseNames.length() - 1).toString().replaceAll(" ","").trim();
        } else {
            return "";
        }
    }

}
