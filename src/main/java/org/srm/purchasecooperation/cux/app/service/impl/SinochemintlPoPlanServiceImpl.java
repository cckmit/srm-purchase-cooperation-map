package org.srm.purchasecooperation.cux.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.code.constant.CodeConstants;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseAppService;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.srm.boot.platform.message.MessageHelper;
import org.srm.boot.platform.message.entity.SpfmMessageSender;
import org.srm.purchasecooperation.cux.api.dto.MessageSenderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.app.service.SinochemintlPoPlanService;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlMessageConstant;
import org.srm.purchasecooperation.cux.infra.repository.impl.SinochemintlPoPlanHeaderRepositoryImpl;
import org.srm.purchasecooperation.pr.api.dto.PrActionDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrAction;
import org.srm.purchasecooperation.pr.domain.repository.PrActionRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购计划应用服务默认实现
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Service
public class SinochemintlPoPlanServiceImpl extends BaseAppService implements SinochemintlPoPlanService {

    private static final Logger logger = LoggerFactory.getLogger(SinochemintlPoPlanServiceImpl.class);

    private final SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository;

    private final SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository;

    private final SinochemintlSendMessageService sinochemintlSendMessageService;

    @Autowired
    private CodeRuleBuilder codeRuleBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PrActionRepository prActionRepository;

    @Autowired
    public LovAdapter lovAdapter;

    @Autowired
    public MessageHelper messageHelper;

    @Autowired
    public SinochemintlPoPlanHeaderRepositoryImpl sinochemintlPoPlanHeaderRepositoryImpl;

    @Autowired
    public SinochemintlPoPlanServiceImpl(SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository, SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository, SinochemintlSendMessageService sinochemintlSendMessageService) {
        this.sinochemintlPoPlanHeaderRepository = sinochemintlPoPlanHeaderRepository;
        this.sinochemintlPoPlanLineRepository = sinochemintlPoPlanLineRepository;
        this.sinochemintlSendMessageService = sinochemintlSendMessageService;
    }

    /**
     * 采购计划头表查询参数
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     * @param pageRequest                 分页
     * @return 采购计划头表列表
     */
    @Override
    public Page<SinochemintlPoPlanHeaderDTO> list(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, PageRequest pageRequest) {
        //当自己为采购创建人时，只能查询到状态为新建或拼单中的采购计划  当自己为共享省区对应人时，只能查询到状态为拼单中的采购计划数据
        //获取用户当前登录用户所在公司
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        //采购计划维护分页查询逻辑重写
        if (StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getCreateId())) {
            sinochemintlPoPlanHeaderDTO.setStandbyCreateId(user.getUserId());
        }
        HashSet<Long> poPlanLineIds = new HashSet<>();
        if (!sinochemintlPoPlanLineDTOS.isEmpty()) {
            if (!SinochemintlConstant.StatusCode.STATUS_NEW.equals(sinochemintlPoPlanHeaderDTO.getStatus())) {
                if (SinochemintlConstant.StatusCode.STATUS_MAINTAIN.equals(sinochemintlPoPlanHeaderDTO.getStatusName()) || !"1510".equals(sinochemintlPoPlanLineDTOS.get(0).getPlanSharedProvince())) {
                    for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineDTOS) {
                        poPlanLineIds.addAll(sinochemintlPoPlanLineRepository.verifyPlanSharedProvince(sinochemintlPoPlanLineDTO));
                    }
                    if (poPlanLineIds.size() == 0) {
                        poPlanLineIds.add(0L);
                    }
                }
            } else {
                poPlanLineIds.add(0L);
            }
        } else {
            poPlanLineIds.add(0L);
        }
        sinochemintlPoPlanHeaderDTO.setPoPlanLineIds(poPlanLineIds);
        if (SinochemintlConstant.StatusCode.STATUS_MAINTAIN.equals(sinochemintlPoPlanHeaderDTO.getStatusName())) {
            return PageHelper.doPage(pageRequest, () -> sinochemintlPoPlanHeaderRepository.maintain(sinochemintlPoPlanHeaderDTO));
        } else {
            //非总部人员只可查看和自己有关的数据
            return PageHelper.doPage(pageRequest, () -> sinochemintlPoPlanHeaderRepository.list(sinochemintlPoPlanHeaderDTO));
        }
    }

    /**
     * 采购计划明细查询
     *
     * @param sinochemintlPoPlanHeaderDTO 查询条件
     * @param pageRequest                 分页参数
     * @return 查询结果
     */
    @Override
    public Page<SinochemintlPoPlanLineDTO> detailList(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, PageRequest pageRequest) {
        //当自己为采购创建人时，只能查询到状态为新建或拼单中的采购计划  当自己为共享省区对应人时，只能查询到状态为拼单中的采购计划数据
        //获取用户当前登录用户所在公司
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        //非总部人员只可查看和自己有关的数据
        HashSet<Long> poPlanLineIds = new HashSet<>();
        if (!sinochemintlPoPlanLineDTOS.isEmpty()) {
            if (!"1510".equals(sinochemintlPoPlanLineDTOS.get(0).getPlanSharedProvince())) {
                for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineDTOS) {
                    poPlanLineIds.addAll(sinochemintlPoPlanLineRepository.verifyPlanSharedProvince(sinochemintlPoPlanLineDTO));
                }
                if (poPlanLineIds.size() == 0) {
                    poPlanLineIds.add(0L);
                }
            }
        } else {
            poPlanLineIds.add(0L);
        }
        sinochemintlPoPlanHeaderDTO.setPoPlanLineIds(poPlanLineIds);
        if (StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getCreateId())) {
            sinochemintlPoPlanHeaderDTO.setStandbyCreateId(user.getUserId());
        }
        return PageHelper.doPage(pageRequest, () -> sinochemintlPoPlanLineRepository.detailList(sinochemintlPoPlanHeaderDTO));
    }

    /**
     * 新增/保存/修改采购计划
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表和行表数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SinochemintlPoPlanHeaderDTO addPoPlan(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, PageRequest pageRequest) {
        //创建头表并保存
        Date date = new Date();
        CustomUserDetails user = DetailsHelper.getUserDetails();
        if (StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId())) {
            //id无值 状态无值 新增操作
            if (StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getStatus()) && StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getCreateName())) {
                //获取采购计划单号
                sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                //获取当前登录人信息
                sinochemintlPoPlanHeaderDTO.setCreateId(user.getUserId());
                sinochemintlPoPlanHeaderDTO.setCreateName(user.getRealName());
                //系统自动带出单据来源于哪个系统 暂时只默认SRM系统
                sinochemintlPoPlanHeaderDTO.setPoSource("SRM");
                //默认使用人民币
                SinochemintlPoPlanLineDTO cnyCurrency = sinochemintlPoPlanLineRepository.getCnyCurrency(sinochemintlPoPlanHeaderDTO.getTenantId());
                sinochemintlPoPlanHeaderDTO.setCurrencyName(cnyCurrency.getCurrencyName());
                sinochemintlPoPlanHeaderDTO.setOriginalId(cnyCurrency.getCurrencyId());
                //默认种子
                List<LovValueDTO> lovValues = lovAdapter.queryLovValue(SinochemintlConstant.CodingCode.SINOCHEMINTL_PROJECT_TYPE, user.getTenantId());
                for(LovValueDTO lovValueDTO : lovValues){
                    String value = lovValueDTO.getValue();
                    if(value.equals("01")){
                        sinochemintlPoPlanHeaderDTO.setPlanType(lovValueDTO.getMeaning());
                    }
                }
                //判断用户当前公司是否唯一
                List<SinochemintlPoPlanLineDTO> defaultCompanyId = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
                if (defaultCompanyId != null && defaultCompanyId.size() > 0) {
                    sinochemintlPoPlanHeaderDTO.setCompanyId(defaultCompanyId.get(0).getProvinceCompanyId());
                    sinochemintlPoPlanHeaderDTO.setCompanyName(defaultCompanyId.get(0).getProvinceCompany());
                    //如果公司唯一则查询业务实体
                    List<SinochemintlPoPlanHeaderDTO> verifyBusiness = sinochemintlPoPlanHeaderRepository.verifyBusiness(sinochemintlPoPlanHeaderDTO);
                    if (verifyBusiness != null && verifyBusiness.size() == 1) {
                        sinochemintlPoPlanHeaderDTO.setBusinessId(verifyBusiness.get(0).getBusinessId());
                        sinochemintlPoPlanHeaderDTO.setBusinessName(verifyBusiness.get(0).getBusinessName());
                    }
                }
                //如果业务实体唯一查询采购组织
                List<SinochemintlPoPlanHeaderDTO> verifyPurchaseOrg = sinochemintlPoPlanHeaderRepository.verifyPurchaseOrg(sinochemintlPoPlanHeaderDTO);
                if (verifyPurchaseOrg != null && verifyPurchaseOrg.size() > 0) {
                    sinochemintlPoPlanHeaderDTO.setPurchaseOrgId(verifyPurchaseOrg.get(0).getPurchaseOrgId());
                    sinochemintlPoPlanHeaderDTO.setPurchaseOrgName(verifyPurchaseOrg.get(0).getPurchaseOrgName());
                    //采购人默认使用当前登陆人
                    sinochemintlPoPlanHeaderDTO.setPurchaseId(verifyPurchaseOrg.get(0).getPurchaseId());
                    sinochemintlPoPlanHeaderDTO.setPurchaseName(verifyPurchaseOrg.get(0).getPurchaseName());
                }
                //如果业务实体唯一查询所在部门是否唯一
                List<SinochemintlPoPlanHeaderDTO> verifyDepartment = sinochemintlPoPlanHeaderRepository.verifyDepartment(sinochemintlPoPlanHeaderDTO);
                if (verifyDepartment != null && verifyDepartment.size() > 0) {
                    sinochemintlPoPlanHeaderDTO.setDepartmentId(verifyDepartment.get(0).getDepartmentId());
                    sinochemintlPoPlanHeaderDTO.setDepartmentName(verifyDepartment.get(0).getDepartmentName());
                }
                return sinochemintlPoPlanHeaderDTO;
            }
            //id无值 保存操作
            String poPlanNumber = codeRuleBuilder.generateCode(sinochemintlPoPlanHeaderDTO.getTenantId(), SinochemintlConstant.CodingCode.SCUX_ZHNY_RULES_PO_PLAN,
                    CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null);
            sinochemintlPoPlanHeaderDTO.setPoPlanNumber(poPlanNumber);
            sinochemintlPoPlanHeaderDTO.setApplicationDate(date);
            sinochemintlPoPlanHeaderDTO.setEstablishDate(date);
            sinochemintlPoPlanHeaderDTO.setCreationDate(date);
            sinochemintlPoPlanHeaderDTO.setCreatedBy(user.getUserId());
            sinochemintlPoPlanHeaderDTO.setLastUpdateDate(date);
            sinochemintlPoPlanHeaderDTO.setLastUpdatedBy(user.getUserId());
            //申请日期可以为空
            if (sinochemintlPoPlanHeaderDTO.getApplicationDate() == null) {
                sinochemintlPoPlanHeaderDTO.setApplicationDate(new Date(0));
            }
            sinochemintlPoPlanHeaderRepository.setOne(sinochemintlPoPlanHeaderDTO);
            Long poPlanHeaderId = sinochemintlPoPlanHeaderRepository.getPoPlanHeaderId(sinochemintlPoPlanHeaderDTO);
            sinochemintlPoPlanHeaderDTO.setPoPlanHeaderId(poPlanHeaderId);
            //保存新建操作记录
            PrAction prAction = new PrAction();
            prAction.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
            prAction.setPrHeaderId(poPlanHeaderId);
            prAction.setDisplayPrNum(sinochemintlPoPlanHeaderDTO.getPoPlanNumber());
            prAction.setProcessTypeCode(SinochemintlConstant.ActionStatusCode.STATUS_PENDING);
            prAction.setProcessRemark("采购计划创建");
            prAction.setProcessedDate(date);
            prAction.setProcessUserId(user.getUserId());
            prAction.setProcessUserName(user.getRealName());
            prActionRepository.insert(prAction);
        } else {
            //有值 修改操作
            sinochemintlPoPlanHeaderDTO.setLastUpdateDate(date);
            sinochemintlPoPlanHeaderDTO.setLastUpdatedBy(user.getUserId());
            sinochemintlPoPlanHeaderRepository.updateByKey(sinochemintlPoPlanHeaderDTO);
        }
        //获取行表数据
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = sinochemintlPoPlanHeaderDTO.getSinochemintlPoPlanLineList();
        if (sinochemintlPoPlanLineList != null && !sinochemintlPoPlanLineList.isEmpty()) {
            for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
                //拼接计划共享省区
                List<Map<String, Object>> planSharedProvinceName = sinochemintlPoPlanLineDTO.getPlanSharedProvinceName();
                try {
                    sinochemintlPoPlanLineDTO.setPlanSharedProvince(objectMapper.writeValueAsString(planSharedProvinceName));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                if (StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getPoPlanLineId())) {
                    //id无值 新增操作
                    sinochemintlPoPlanLineDTO.setPoPlanHeaderId(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
                    sinochemintlPoPlanLineDTO.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
                    sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                    sinochemintlPoPlanLineDTO.setApplicant(user.getRealName());
                    sinochemintlPoPlanLineDTO.setApplicantId(user.getUserId());
                    sinochemintlPoPlanLineDTO.setCreationDate(date);
                    sinochemintlPoPlanLineDTO.setCreatedBy(user.getUserId());
                    sinochemintlPoPlanLineDTO.setLastUpdateDate(date);
                    sinochemintlPoPlanLineDTO.setLastUpdatedBy(user.getUserId());
                    if (StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getSharedProvinceId()) || sinochemintlPoPlanLineDTO.getSharedProvinceId() == 0) {
                        sinochemintlPoPlanLineDTO.setSharedProvinceId(0L);
                        Integer serialNum = sinochemintlPoPlanLineRepository.getSerialNum(sinochemintlPoPlanLineDTO);
                        sinochemintlPoPlanLineDTO.setSerialNum(serialNum == null ? 1 : serialNum + 1);
                        sinochemintlPoPlanLineDTO.setDisplayLineNum(sinochemintlPoPlanLineDTO.getSerialNum());
                        sinochemintlPoPlanLineDTO.setSpellDocProvince(0L);
                        //新增数据 保存行保存操作记录
                        PrAction prAction = new PrAction();
                        prAction.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
                        prAction.setPrHeaderId(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
                        prAction.setDisplayPrNum(sinochemintlPoPlanHeaderDTO.getPoPlanNumber());
                        prAction.setProcessTypeCode(SinochemintlConstant.ActionStatusCode.STATUS_NEWLINE);
                        prAction.setDisplayLineNum(String.valueOf(sinochemintlPoPlanLineDTO.getDisplayLineNum()));
                        prAction.setProcessRemark("采购计划行创建");
                        prAction.setProcessedDate(date);
                        prAction.setProcessUserId(user.getUserId());
                        prAction.setProcessUserName(user.getRealName());
                        prActionRepository.insert(prAction);
                    } else {
                        Integer serialNum = sinochemintlPoPlanLineRepository.getDisplayLineNum(sinochemintlPoPlanLineDTO);
                        if (serialNum < 100) {
                            serialNum = Integer.valueOf(serialNum.toString() + "01");
                        } else {
                            serialNum++;
                        }
                        sinochemintlPoPlanLineDTO.setDisplayLineNum(serialNum);
                    }
                    sinochemintlPoPlanLineRepository.setOne(sinochemintlPoPlanLineDTO);
                } else {
                    //有值 修改操作
                    sinochemintlPoPlanLineDTO.setLastUpdateDate(date);
                    sinochemintlPoPlanLineDTO.setLastUpdatedBy(user.getUserId());
                    sinochemintlPoPlanLineRepository.updateByKey(sinochemintlPoPlanLineDTO);
                }
            }
        }
        return sinochemintlPoPlanHeaderDTO;
    }

    /**
     * 删除采购计划头表
     *
     * @param ids 头表id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delHeader(List<Long> ids) {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(id);
                if (sinochemintlPoPlanHeaderDTO == null) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_PARAMETER_ERROR);
                }
                if (!sinochemintlPoPlanHeaderDTO.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_CANT_DELETE);
                }
                //只要新增状态才可以删除
                sinochemintlPoPlanHeaderRepository.deleteByKey(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
            }
        }
    }

    /**
     * 删除采购计划行表
     *
     * @param ids 行表id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delLine(List<Long> ids) {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO = sinochemintlPoPlanLineRepository.selectByKey(id);
                if (sinochemintlPoPlanLineDTO == null) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_PARAMETER_ERROR);
                }
                if (!sinochemintlPoPlanLineDTO.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_CANT_DELETE);
                }
                //只有新增状态才可以删除
                sinochemintlPoPlanLineRepository.deleteByKey(sinochemintlPoPlanLineDTO.getPoPlanLineId());
                //保存行删除操作记录
                SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(sinochemintlPoPlanLineDTO.getPoPlanHeaderId());
                Date date = new Date();
                CustomUserDetails user = DetailsHelper.getUserDetails();
                PrAction prAction = new PrAction();
                prAction.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
                prAction.setPrHeaderId(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
                prAction.setPrLineId(sinochemintlPoPlanLineDTO.getPoPlanLineId());
                prAction.setDisplayPrNum(sinochemintlPoPlanHeaderDTO.getPoPlanNumber());
                prAction.setProcessTypeCode(SinochemintlConstant.ActionStatusCode.STATUS_DELLINE);
                prAction.setDisplayLineNum(String.valueOf(sinochemintlPoPlanLineDTO.getDisplayLineNum()));
                prAction.setProcessRemark("采购计划行删除");
                prAction.setProcessedDate(date);
                prAction.setProcessUserId(user.getUserId());
                prAction.setProcessUserName(user.getRealName());
                prActionRepository.insert(prAction);
            }
        }
    }

    /**
     * 获取头行单表
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     */
    @Override
    public SinochemintlPoPlanHeaderDTO getPoPlan(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest) {
        //获取头数据
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(poPlanHeaderId);
        if (sinochemintlPoPlanHeaderDTO == null) {
            throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_PARAMETER_ERROR);
        }
        //非共享省区成员通过分享链接不能看订单信息
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        String provinces = sinochemintlPoPlanHeaderRepositoryImpl.getDefaultCompany(sinochemintlPoPlanHeaderDTO.getCreateId());
        String companyName = sinochemintlPoPlanLineDTOS.get(0).getProvinceCompany();
        if(!provinces.contains(companyName) && !"中化现代农业有限公司".equals(companyName)){
            throw new CommonException("仅有邀请拼单省区内的人员才可以拼单");
        }
        if (sinochemintlPoPlanHeaderDTO.getApplicationDate().equals(new Date(0))) {
            sinochemintlPoPlanHeaderDTO.setApplicationDate(null);
        }
        return sinochemintlPoPlanHeaderDTO;
    }

    /**
     * 获取行表列表
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     * @param pageRequest    分页参数
     * @return 行表列表
     */
    @Override
    public Page<SinochemintlPoPlanLineDTO> getPoPlanLine(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest) {
        //获取行数据
        SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = new SinochemintlPoPlanLineDTO();
        CustomUserDetails user = DetailsHelper.getUserDetails();
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(poPlanHeaderId);
        sinochemintlPoPlanLine.setPoPlanHeaderId(poPlanHeaderId);
        sinochemintlPoPlanLine.setTenantId(organizationId);
        if (sinochemintlPoPlanHeaderDTO.getCreateId().equals(user.getUserId())) {
            sinochemintlPoPlanLine.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
        }
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        //非共享省区成员通过分享链接不能看订单信息
        String provinces = sinochemintlPoPlanHeaderRepositoryImpl.getDefaultCompany(sinochemintlPoPlanHeaderDTO.getCreateId());
        String companyName = sinochemintlPoPlanLineDTOS.get(0).getProvinceCompany();
        if(!provinces.contains(companyName) && !"中化现代农业有限公司".equals(companyName)){
            throw new CommonException("仅有邀请拼单省区内的人员才可以拼单");
        }
        HashSet<Long> poPlanLineIds = new HashSet<>();
        if (!sinochemintlPoPlanLineDTOS.isEmpty()) {
            if (!"1510".equals(sinochemintlPoPlanLineDTOS.get(0).getPlanSharedProvince())) {
                for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineDTOS) {
                    sinochemintlPoPlanLineDTO.setPoPlanHeaderId(poPlanHeaderId);
                    poPlanLineIds.addAll(sinochemintlPoPlanLineRepository.verifyPlanSharedProvince(sinochemintlPoPlanLineDTO));
                }
            } else {
                sinochemintlPoPlanLine.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
            }
        }
        if (poPlanLineIds.size() > 0) {
            sinochemintlPoPlanLine.setPoPlanLineIds(poPlanLineIds);
        }
        sinochemintlPoPlanLine.setApplicantId(user.getUserId());
        Page<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = PageHelper.doPage(pageRequest, () -> sinochemintlPoPlanLineRepository.getPoPlanLine(sinochemintlPoPlanLine));
        if (!sinochemintlPoPlanLineList.isEmpty() && !StringUtils.isEmpty(sinochemintlPoPlanLineList.get(0).getPoPlanLineId())) {
            int serialNum = 1;
            for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
                sinochemintlPoPlanLineDTO.setSerialNum(serialNum++);
                if (!StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getPlanSharedProvince())) {
                    String planSharedProvince = sinochemintlPoPlanLineDTO.getPlanSharedProvince();
                    try {
                        sinochemintlPoPlanLineDTO.setPlanSharedProvinceName(objectMapper.readValue(planSharedProvince, ArrayList.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sinochemintlPoPlanLineList;
        } else {
            return new Page<>();
        }
    }

    /**
     * 提交采购计划
     *
     * @param ids 头表id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(Long organizationId, List<Long> ids) {
        for (Long poPlanHeaderId : ids) {
            //提交采购计划
            CustomUserDetails user = DetailsHelper.getUserDetails();
            SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(poPlanHeaderId);
            SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = new SinochemintlPoPlanLineDTO();
            sinochemintlPoPlanLine.setPoPlanHeaderId(poPlanHeaderId);
            sinochemintlPoPlanLine.setTenantId(organizationId);
            List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = sinochemintlPoPlanLineRepository.selectByHeaderId(sinochemintlPoPlanLine);
            if (sinochemintlPoPlanHeaderDTO.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
                sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_MIDDLE);
                Set<Integer> longs = new HashSet<>();
                for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
                    String planSharedProvince = sinochemintlPoPlanLineDTO.getPlanSharedProvince();
                    ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                    try {
                        arrayList = objectMapper.readValue(planSharedProvince, ArrayList.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (Map<String, Object> stringMap : arrayList) {
                        longs.add((Integer) stringMap.get("companyId"));
                    }
                }
                List<Receiver> receiverList = new ArrayList<>(sinochemintlSendMessageService.getReceiverList(longs));
                receiverList = receiverList.stream().distinct().collect(Collectors.toList());
                try {
                    Map<String, String> paramMap = new HashMap<>(BaseConstants.Digital.SIXTEEN);
                    paramMap.putAll(sinochemintlSendMessageService.getCommonParam(sinochemintlPoPlanHeaderDTO));
                    List<String> type = new ArrayList<>();
                    type.add(SinochemintlMessageConstant.MessageType.EMAIL);
                    type.add(SinochemintlMessageConstant.MessageType.WEB);
                    type.add(SinochemintlMessageConstant.MessageType.SMS);
                    messageHelper.sendMessage(new SpfmMessageSender(organizationId, SinochemintlMessageConstant.Message_Submit_Template_Code,SinochemintlMessageConstant.Web_Message_Language_Chinese,type,receiverList,paramMap));
                } catch (IllegalArgumentException e) {
                    logger.error("Message sending failure:{}", receiverList);
                }
            }
            if (sinochemintlPoPlanLineList.isEmpty() || sinochemintlPoPlanLineList.get(0) == null) {
                throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_LINE_NO_DATA);
            }
            int province = 0;
            for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
                if (SinochemintlConstant.StatusCode.STATUS_NEW.equals(sinochemintlPoPlanLineDTO.getStatus()) &&
                        sinochemintlPoPlanLineDTO.getSharedProvinceId() != 0 &&
                        sinochemintlPoPlanLineDTO.getApplicantId().equals(user.getUserId())) {
                    Integer spellDocProvinceCount = sinochemintlPoPlanLineRepository.spellDocProvince(sinochemintlPoPlanLineDTO);
                    if (spellDocProvinceCount == 0) {
                        SinochemintlPoPlanLineDTO spellDocProvince = sinochemintlPoPlanLineRepository.selectByKey(sinochemintlPoPlanLineDTO.getSharedProvinceId());
                        sinochemintlPoPlanLineRepository.updateByKey(spellDocProvince.setSpellDocProvince(spellDocProvince.getSpellDocProvince() + 1));
                    }
                    sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_COMPLETE);
                    sinochemintlPoPlanLineRepository.updateByKey(sinochemintlPoPlanLineDTO);
                }
            }
            for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineRepository.selectByHeaderId(sinochemintlPoPlanLine)) {
                if (StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getSharedProvinceId())) {
                    sinochemintlPoPlanLineDTO.setSharedProvinceId(0L);
                }
                if (sinochemintlPoPlanLineDTO.getSharedProvinceId() == 0
                        && !sinochemintlPoPlanLineDTO.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_COMPLETE)
                        && !StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getSpellDocProvince())) {
                    province++;
                    sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_MIDDLE);
                    //校验共享省区数量，若均已填写，则状态更新为【拼单完成】
                    String planSharedProvince = sinochemintlPoPlanLineDTO.getPlanSharedProvince();
                    ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                    try {
                        arrayList = objectMapper.readValue(planSharedProvince, ArrayList.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (arrayList.size() <= sinochemintlPoPlanLineDTO.getSpellDocProvince()) {
                        province--;
                        sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_COMPLETE);
                    }
                    sinochemintlPoPlanLineRepository.updateByKey(sinochemintlPoPlanLineDTO);
                }
            }
            if (province == 0) {
                sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_COMPLETE);
                Set<Integer> longs = new HashSet<>();
                for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
                    String planSharedProvince = sinochemintlPoPlanLineDTO.getPlanSharedProvince();
                    ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                    try {
                        arrayList = objectMapper.readValue(planSharedProvince, ArrayList.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (Map<String, Object> stringMap : arrayList) {
                        longs.add((Integer) stringMap.get("companyId"));
                    }
                }
                List<Receiver> receiverList = new ArrayList<>(sinochemintlSendMessageService.getReceiverList(longs));
                receiverList = receiverList.stream().distinct().collect(Collectors.toList());
                try {
                    Map<String, String> paramMap = new HashMap<>(BaseConstants.Digital.SIXTEEN);
                    paramMap.putAll(sinochemintlSendMessageService.getCommonParam(sinochemintlPoPlanHeaderDTO));
                    List<String> type = new ArrayList<>();
                    type.add(SinochemintlMessageConstant.MessageType.EMAIL);
                    type.add(SinochemintlMessageConstant.MessageType.WEB);
                    type.add(SinochemintlMessageConstant.MessageType.SMS);
                    messageHelper.sendMessage(new SpfmMessageSender(organizationId, SinochemintlMessageConstant.Message_ARRIVAL_Template_Code,SinochemintlMessageConstant.Web_Message_Language_Chinese,type,receiverList,paramMap));
                } catch (IllegalArgumentException e) {
                    logger.error("Message sending failure:{}", receiverList);
                }
            }
            sinochemintlPoPlanHeaderDTO.setLastUpdateDate(new Date());
            sinochemintlPoPlanHeaderDTO.setLastUpdatedBy(DetailsHelper.getUserDetails().getUserId());
            if (sinochemintlPoPlanHeaderDTO.getApplicationDate().equals(new Date(0))) {
                sinochemintlPoPlanHeaderDTO.setApplicationDate(new Date());
            }
            sinochemintlPoPlanHeaderRepository.updateByKey(sinochemintlPoPlanHeaderDTO);
            //保存提交操作记录
            Date date = new Date();
            PrAction prAction = new PrAction();
            prAction.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
            prAction.setPrHeaderId(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
            prAction.setDisplayPrNum(sinochemintlPoPlanHeaderDTO.getPoPlanNumber());
            prAction.setProcessTypeCode(SinochemintlConstant.ActionStatusCode.STATUS_SUBMITTED);
            prAction.setProcessRemark("采购计划提交");
            prAction.setProcessedDate(date);
            prAction.setProcessUserId(user.getUserId());
            prAction.setProcessUserName(user.getRealName());
            prActionRepository.insert(prAction);
        }
    }

    /**
     * 取消采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long poPlanHeaderId) {
        //取消采购计划
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(poPlanHeaderId);
        if (!sinochemintlPoPlanHeaderDTO.getCreateId().equals(DetailsHelper.getUserDetails().getUserId())) {
            //只有创建人才可以取消
            throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_NOT_FOUNDER_CANCEL);
        }
        if (!sinochemintlPoPlanHeaderDTO.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
            //非新建状态无法取消
            throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_NON_NEW_NOT_CANCEL);
        }
        sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_CANCELLED);
        sinochemintlPoPlanHeaderDTO.setLastUpdateDate(new Date());
        sinochemintlPoPlanHeaderDTO.setLastUpdatedBy(DetailsHelper.getUserDetails().getUserId());
        sinochemintlPoPlanHeaderRepository.updateByKey(sinochemintlPoPlanHeaderDTO);
        //保存取消操作记录
        Date date = new Date();
        CustomUserDetails user = DetailsHelper.getUserDetails();
        PrAction prAction = new PrAction();
        prAction.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
        prAction.setPrHeaderId(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
        prAction.setDisplayPrNum(sinochemintlPoPlanHeaderDTO.getPoPlanNumber());
        prAction.setProcessTypeCode(SinochemintlConstant.ActionStatusCode.STATUS_CANCEL);
        prAction.setProcessRemark("采购计划取消");
        prAction.setProcessedDate(date);
        prAction.setProcessUserId(user.getUserId());
        prAction.setProcessUserName(user.getRealName());
        prActionRepository.insert(prAction);
    }

    @Autowired
    private LovValueHandle lovValueHandle;

    /**
     * 采购计划导出
     *
     * @param ids 勾选的头表id
     * @return 需要导出的结果
     */
    @Override
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public List<SinochemintlPoPlanExcelDTO> excel(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        List<SinochemintlPoPlanExcelDTO> excel = sinochemintlPoPlanHeaderRepository.excel(list);
        //转换共享省区
        for (SinochemintlPoPlanExcelDTO sinochemintlPoPlanExcelDTO : excel) {
            if (!StringUtils.isEmpty(sinochemintlPoPlanExcelDTO.getPlanSharedProvince())) {
                ArrayList<Map<String, String>> arrayList = new ArrayList<>();
                try {
                    arrayList = objectMapper.readValue(sinochemintlPoPlanExcelDTO.getPlanSharedProvince(), ArrayList.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取共享计划省区
                StringBuilder planSharedProvince = new StringBuilder();
                for (Map<String, String> stringStringMap : arrayList) {
                    planSharedProvince.append(stringStringMap.get("companyName")).append("、");
                }
                sinochemintlPoPlanExcelDTO.setPlanSharedProvince(planSharedProvince.toString());
            }
        }
        lovValueHandle.process(null, excel);
        return excel;
    }

    @Override
    public List<SinochemintlPoPlanExcelDTO> excelLine(String poPlanLineIds) {
        List<String> list = Arrays.asList(poPlanLineIds.split(","));
        List<SinochemintlPoPlanExcelDTO> excelLine = sinochemintlPoPlanHeaderRepository.excelLine(list);
        //转换共享省区
        for (SinochemintlPoPlanExcelDTO sinochemintlPoPlanExcelDTO : excelLine) {
            if (!StringUtils.isEmpty(sinochemintlPoPlanExcelDTO.getPlanSharedProvince())) {
                ArrayList<Map<String, String>> arrayList = new ArrayList<>();
                try {
                    arrayList = objectMapper.readValue(sinochemintlPoPlanExcelDTO.getPlanSharedProvince(), ArrayList.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取共享计划省区
                StringBuilder planSharedProvince = new StringBuilder();
                for (Map<String, String> stringStringMap : arrayList) {
                    planSharedProvince.append(stringStringMap.get("companyName")).append("、");
                }
                sinochemintlPoPlanExcelDTO.setPlanSharedProvince(planSharedProvince.toString());
            }
        }
        lovValueHandle.process(null, excelLine);
        return excelLine;
    }

    @Override
    public List<SinochemintlPoPlanExcelDTO> batchExcelLine(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        //获取用户当前登录用户所在公司
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        //非总部人员只可查看和自己有关的数据
        HashSet<Long> poPlanLineIds = new HashSet<>();
        if (!sinochemintlPoPlanLineDTOS.isEmpty()) {
            if (!"1510".equals(sinochemintlPoPlanLineDTOS.get(0).getPlanSharedProvince())) {
                for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineDTOS) {
                    poPlanLineIds.addAll(sinochemintlPoPlanLineRepository.verifyPlanSharedProvince(sinochemintlPoPlanLineDTO));
                }
                sinochemintlPoPlanHeaderDTO.setPoPlanLineIds(poPlanLineIds);
            }
        } else {
            poPlanLineIds.add(0L);
            sinochemintlPoPlanHeaderDTO.setPoPlanLineIds(poPlanLineIds);
        }
        if (StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getCreateId())) {
            sinochemintlPoPlanHeaderDTO.setStandbyCreateId(user.getUserId());
        }
        List<SinochemintlPoPlanExcelDTO> batchExcel = sinochemintlPoPlanHeaderRepository.batchExcelLine(sinochemintlPoPlanHeaderDTO);
        //转换共享省区
        for (SinochemintlPoPlanExcelDTO sinochemintlPoPlanExcelDTO : batchExcel) {
            if (!StringUtils.isEmpty(sinochemintlPoPlanExcelDTO.getPlanSharedProvince())) {
                ArrayList<Map<String, String>> arrayList = new ArrayList<>();
                try {
                    arrayList = objectMapper.readValue(sinochemintlPoPlanExcelDTO.getPlanSharedProvince(), ArrayList.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取共享计划省区
                StringBuilder planSharedProvince = new StringBuilder();
                for (Map<String, String> stringStringMap : arrayList) {
                    planSharedProvince.append(stringStringMap.get("companyName")).append("、");
                }
                sinochemintlPoPlanExcelDTO.setPlanSharedProvince(planSharedProvince.toString());
            }
        }
        lovValueHandle.process(null, batchExcel);
        return batchExcel;
    }

    /**
     * 采购计划批量导出
     *
     * @param sinochemintlPoPlanHeaderDTO 查询数据
     * @return 结果
     */
    @Override
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public List<SinochemintlPoPlanExcelDTO> batchExcel(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO) {
        //获取用户当前登录用户所在公司
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        //采购计划维护分页查询逻辑重写
        if (StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getCreateId())) {
            sinochemintlPoPlanHeaderDTO.setStandbyCreateId(user.getUserId());
        }
        HashSet<Long> poPlanLineIds = new HashSet<>();
        if (!sinochemintlPoPlanLineDTOS.isEmpty()) {
            if (!"1510".equals(sinochemintlPoPlanLineDTOS.get(0).getPlanSharedProvince())) {
                if (!SinochemintlConstant.StatusCode.STATUS_NEW.equals(sinochemintlPoPlanHeaderDTO.getStatus()) || !SinochemintlConstant.StatusCode.STATUS_MAINTAIN.equals(sinochemintlPoPlanHeaderDTO.getStatusName())) {
                    for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineDTOS) {
                        poPlanLineIds.addAll(sinochemintlPoPlanLineRepository.verifyPlanSharedProvince(sinochemintlPoPlanLineDTO));
                    }
                } else {
                    poPlanLineIds.add(0L);
                }
                sinochemintlPoPlanHeaderDTO.setPoPlanLineIds(poPlanLineIds);
            } else {
                if (SinochemintlConstant.StatusCode.STATUS_NEW.equals(sinochemintlPoPlanHeaderDTO.getStatus())) {
                    poPlanLineIds.add(0L);
                    sinochemintlPoPlanHeaderDTO.setPoPlanLineIds(poPlanLineIds);
                }
            }
        } else {
            poPlanLineIds.add(0L);
            sinochemintlPoPlanHeaderDTO.setPoPlanLineIds(poPlanLineIds);
        }
        List<SinochemintlPoPlanExcelDTO> batchExcel = sinochemintlPoPlanHeaderRepository.batchExcel(sinochemintlPoPlanHeaderDTO);
        //转换共享省区
        for (SinochemintlPoPlanExcelDTO sinochemintlPoPlanExcelDTO : batchExcel) {
            if (!StringUtils.isEmpty(sinochemintlPoPlanExcelDTO.getPlanSharedProvince())) {
                ArrayList<Map<String, String>> arrayList = new ArrayList<>();
                try {
                    arrayList = objectMapper.readValue(sinochemintlPoPlanExcelDTO.getPlanSharedProvince(), ArrayList.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取共享计划省区
                StringBuilder planSharedProvince = new StringBuilder();
                for (Map<String, String> stringStringMap : arrayList) {
                    planSharedProvince.append(stringStringMap.get("companyName")).append("、");
                }
                sinochemintlPoPlanExcelDTO.setPlanSharedProvince(planSharedProvince.toString());
            }
        }
        lovValueHandle.process(null, batchExcel);
        return batchExcel;
    }

    /**
     * 采购计划确认
     *
     * @param dto 修改后的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirm(SinochemintlPoPlanHeaderDTO dto) {
        CustomUserDetails user = DetailsHelper.getUserDetails();
        if (!user.getUserId().equals(dto.getCreateId())) {
            throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_NOT_FOUNDER);
        }
        SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = new SinochemintlPoPlanLineDTO();
        sinochemintlPoPlanLine.setPoPlanHeaderId(dto.getPoPlanHeaderId());
        sinochemintlPoPlanLine.setTenantId(dto.getTenantId());
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = sinochemintlPoPlanLineRepository.selectByHeaderId(sinochemintlPoPlanLine);
        Set<Integer> longs = new HashSet<>();
        for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
            if (!sinochemintlPoPlanLineDTO.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
                if (StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getEndSupplier()) || StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getEndPrice())) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_RESULTS_NOT_ENTERED);
                }
                sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_INPUT_COMPLETE);
                sinochemintlPoPlanLineRepository.updateByKey(sinochemintlPoPlanLineDTO);
                if (StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getSpellDocProvince())) {
                    longs.add(Math.toIntExact(sinochemintlPoPlanLineDTO.getProvinceCompanyId()));
                }
            }
        }
        if (longs.size() > 0) {
            Long organizationId = dto.getTenantId();
            List<Receiver> receiverList = new ArrayList<>(sinochemintlSendMessageService.getReceiverList(longs));
            receiverList = receiverList.stream().distinct().collect(Collectors.toList());
            try {
                Map<String, String> paramMap = new HashMap<>(BaseConstants.Digital.SIXTEEN);
                paramMap.putAll(sinochemintlSendMessageService.getCommonParam(dto));
                List<String> type = new ArrayList<>();
                type.add(SinochemintlMessageConstant.MessageType.EMAIL);
                type.add(SinochemintlMessageConstant.MessageType.WEB);
                type.add(SinochemintlMessageConstant.MessageType.SMS);
                messageHelper.sendMessage(new SpfmMessageSender(organizationId, SinochemintlMessageConstant.Message_Input_Template_Code,SinochemintlMessageConstant.Web_Message_Language_Chinese,type,receiverList,paramMap));

            } catch (IllegalArgumentException e) {
                logger.error("Message sending failure:{}", receiverList);
            }
        }
        dto.setStatus(SinochemintlConstant.StatusCode.STATUS_INPUT_COMPLETE);
        dto.setLastUpdateDate(new Date());
        dto.setLastUpdatedBy(DetailsHelper.getUserDetails().getUserId());
        sinochemintlPoPlanHeaderRepository.updateByKey(dto);
        //保存确认操作记录
        Date date = new Date();
        PrAction prAction = new PrAction();
        prAction.setTenantId(dto.getTenantId());
        prAction.setPrHeaderId(dto.getPoPlanHeaderId());
        prAction.setDisplayPrNum(dto.getPoPlanNumber());
        prAction.setProcessTypeCode(SinochemintlConstant.ActionStatusCode.STATUS_ENABLE);
        prAction.setProcessRemark("采购计划确认");
        prAction.setProcessedDate(date);
        prAction.setProcessUserId(user.getUserId());
        prAction.setProcessUserName(user.getRealName());
        prActionRepository.insert(prAction);
    }

    /**
     * 批量采购计划确认
     *
     * @param ids 修改后的数据
     */
    @Override
    public void batchConfirm(Long organizationId, List<Long> ids) {
        for (Long id : ids) {
            SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(id);
            this.confirm(sinochemintlPoPlanHeaderDTO);
        }
    }

    /**
     * 操作记录
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     * @param pageRequest    分页参数
     * @return 操作记录
     */
    @Override
    @ProcessLovValue
    public Page<PrActionDTO> operatingRecord(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest) {
        PrAction prAction = new PrAction();
        prAction.setTenantId(organizationId);
        prAction.setPrHeaderId(poPlanHeaderId);
        return PageHelper.doPage(pageRequest, () -> prActionRepository.select(prAction));
    }

    /**
     * 批量维护
     *
     * @param dto 批量维护信息
     */
    @Override
    public void batchMaint(SinochemintlPoPlanLineDTO dto) {
        sinochemintlPoPlanLineRepository.batchMaint(dto);
    }

    /**
     * 拼单
     *
     * @param dto 拼单数据
     * @return 结果
     */
    @Override
    public SinochemintlPoPlanLineDTO shareTheBill(SinochemintlPoPlanLineDTO dto) {
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO = null;
        if (sinochemintlPoPlanLineDTOS.isEmpty()) {
            sinochemintlPoPlanLineDTO = new SinochemintlPoPlanLineDTO();
        } else {
            for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLine : sinochemintlPoPlanLineDTOS) {
                if (dto.getPlanSharedProvince().contains(":" + sinochemintlPoPlanLine.getProvinceCompanyId() + ",")) {
                    sinochemintlPoPlanLineDTO = sinochemintlPoPlanLine;
                }
            }
            if (sinochemintlPoPlanLineDTO == null) {
                throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_SHARED_PROVINCE_CORRE);
            }
        }
        sinochemintlPoPlanLineDTO.setSharedProvinceId(dto.getPoPlanLineId());
        sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
        //默认使用人民币
        SinochemintlPoPlanLineDTO cnyCurrency = sinochemintlPoPlanLineRepository.getCnyCurrency(dto.getTenantId());
        sinochemintlPoPlanLineDTO.setCurrencyName(cnyCurrency.getCurrencyName());
        sinochemintlPoPlanLineDTO.setCurrencyId(cnyCurrency.getCurrencyId());
        //判断公司是否唯一
        List<Map<String, Object>> province = this.province(dto.getTenantId(), user.getUserId(), dto.getPoPlanLineId());
        sinochemintlPoPlanLineDTO.setProvinceCompanyId((Long) province.get(0).get("companyId"));
        sinochemintlPoPlanLineDTO.setProvinceCompany((String) province.get(0).get("companyName"));
        return sinochemintlPoPlanLineDTO;
    }

    @Override
    public List<Map<String, Object>> province(Long organizationId, Long companyId, Long applicantId) {
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(companyId);
        List<Map<String, Object>> maps = new ArrayList<>();
        if (applicantId != 0) {
            if (!sinochemintlPoPlanLineDTOS.isEmpty()) {
                SinochemintlPoPlanLineDTO dto = sinochemintlPoPlanLineRepository.selectByKey(applicantId);
                for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLine : sinochemintlPoPlanLineDTOS) {
                    if (dto.getPlanSharedProvince().contains(":" + sinochemintlPoPlanLine.getProvinceCompanyId() + ",")) {
                        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                        stringObjectHashMap.put("companyId", sinochemintlPoPlanLine.getProvinceCompanyId());
                        stringObjectHashMap.put("companyName", sinochemintlPoPlanLine.getProvinceCompany());
                        maps.add(stringObjectHashMap);
                    }
                }
            }
        } else {
            for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLine : sinochemintlPoPlanLineDTOS) {
                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                stringObjectHashMap.put("companyId", sinochemintlPoPlanLine.getProvinceCompanyId());
                stringObjectHashMap.put("companyName", sinochemintlPoPlanLine.getProvinceCompany());
                maps.add(stringObjectHashMap);
            }
        }
        return maps;
    }

    @Override
    public void timedTaskHeader() {
        CustomUserDetails self = DetailsHelper.getUserDetails();
        Long organizationId = self.getOrganizationId();
        List<SinochemintlPoPlanHeaderDTO> poPlanHeaders = sinochemintlPoPlanHeaderRepository.timedTaskHeader(new Date());
        for (SinochemintlPoPlanHeaderDTO poPlanHeader : poPlanHeaders) {
            SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = new SinochemintlPoPlanLineDTO();
            sinochemintlPoPlanLine.setPoPlanHeaderId(poPlanHeader.getPoPlanHeaderId());
            sinochemintlPoPlanLine.setTenantId(organizationId);
            List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = sinochemintlPoPlanLineRepository.selectByHeaderId(sinochemintlPoPlanLine);
            Set<Integer> longs = new HashSet<>();
            for (SinochemintlPoPlanLineDTO poPlanLine : sinochemintlPoPlanLineList) {
                if (poPlanLine == null) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_LINE_NO_DATA);
                }
                ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                String planSharedProvince = poPlanLine.getPlanSharedProvince();
                try {
                    arrayList = objectMapper.readValue(planSharedProvince, ArrayList.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Map<String, Object> stringMap : arrayList) {
                    longs.add((Integer) stringMap.get("companyId"));
                }
            }
            List<Receiver> receiverList = new ArrayList<>(sinochemintlSendMessageService.getReceiverList(longs));
            receiverList = receiverList.stream().distinct().collect(Collectors.toList());
            try {
                Map<String, String> paramMap = new HashMap<>(BaseConstants.Digital.SIXTEEN);
                paramMap.putAll(sinochemintlSendMessageService.getCommonParam(poPlanHeader));
                List<String> type = new ArrayList<>();
                type.add(SinochemintlMessageConstant.MessageType.EMAIL);
                type.add(SinochemintlMessageConstant.MessageType.WEB);
                type.add(SinochemintlMessageConstant.MessageType.SMS);
                messageHelper.sendMessage(new SpfmMessageSender(organizationId, SinochemintlMessageConstant.Message_ARRIVAL_Template_Code,SinochemintlMessageConstant.Web_Message_Language_Chinese,type,receiverList,paramMap));
            } catch (IllegalArgumentException e) {
                logger.error("Message sending failure:{}", receiverList);
            }
        }
    }


    /**
     * 新建拼单计划行信息时返回默认共享省区
     * @param dto
     * @return 行信息
     */
    @Override
    public SinochemintlPoPlanLineDTO addPoPlanLine(SinochemintlPoPlanHeaderDTO dto) {
        CustomUserDetails user = DetailsHelper.getUserDetails();
        List<LovValueDTO> lovValues = lovAdapter.queryLovValue(SinochemintlConstant.CodingCode.SPUC_SINOCHEMINTL_PLAN_SHARED_PROVINCE, user.getTenantId());
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineDTOS = sinochemintlPoPlanHeaderRepository.getDefaultCompanyId(user.getUserId());
        StringBuffer provinses= new StringBuffer();
        List<Map<String, Object>> planSharedProvinceName = new ArrayList<>();;
        Set<String> self = new HashSet<>();
        for(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineDTOS){
            for(LovValueDTO lovValueDTO : lovValues){
                String meaning = lovValueDTO.getMeaning();
                if(meaning.contains(sinochemintlPoPlanLineDTO.getPlanSharedProvince())){
                    provinses.append(meaning).append(",");
                }
            }
        }
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(dto.getCompanyId().intValue());
        self.addAll(sinochemintlPoPlanHeaderRepository.getCompanyNumById(integers));
        //去重
        String[] province = provinses.toString().replaceAll(" ","").trim().split(",");
        List<String> list = Arrays.asList(province);
        Set<String> set = new HashSet(list);
        //过滤掉自己
        set.removeAll(self);
        //判空
        boolean flag = (set.size() == 1 && set.contains("")) || (set.size() == 0);
        if(!flag){
            for (String string : set) {
                SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = sinochemintlPoPlanHeaderRepository.getCompany(string);
                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                stringObjectHashMap.put("companyId", sinochemintlPoPlanLine.getProvinceCompanyId());
                stringObjectHashMap.put("companyName", sinochemintlPoPlanLine.getProvinceCompany());
                stringObjectHashMap.put("companyNum", sinochemintlPoPlanLine.getPlanSharedProvince());
                planSharedProvinceName.add(stringObjectHashMap);
            }
        }
        SinochemintlPoPlanLineDTO sinochemintlPoPlanLine = new SinochemintlPoPlanLineDTO();
        sinochemintlPoPlanLine.setPlanSharedProvinceName(planSharedProvinceName);
        Long organizationId = dto.getTenantId();
        long uomId = sinochemintlPoPlanHeaderRepository.selectUomId("MT", organizationId);
        sinochemintlPoPlanLine.setUomId(uomId);
        sinochemintlPoPlanLine.setUomCode("MT");
        sinochemintlPoPlanLine.setUnitName("吨");
        return sinochemintlPoPlanLine;
    }

}
