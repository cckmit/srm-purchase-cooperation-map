package org.srm.purchasecooperation.cux.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.code.constant.CodeConstants;
import org.hzero.core.base.BaseAppService;
import org.hzero.core.message.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.api.dto.UserVO;
import org.srm.purchasecooperation.cux.app.service.SinochemintlPoPlanService;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;
import org.srm.purchasecooperation.cux.infra.feign.PigicIamFeignClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 采购计划应用服务默认实现
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Service
public class SinochemintlPoPlanServiceImpl extends BaseAppService implements SinochemintlPoPlanService {

    private final SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository;

    private final SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository;

    @Autowired
    private CodeRuleBuilder codeRuleBuilder;

    @Autowired
    public SinochemintlPoPlanServiceImpl(SinochemintlPoPlanHeaderRepository sinochemintlPoPlanHeaderRepository, SinochemintlPoPlanLineRepository sinochemintlPoPlanLineRepository) {
        this.sinochemintlPoPlanHeaderRepository = sinochemintlPoPlanHeaderRepository;
        this.sinochemintlPoPlanLineRepository = sinochemintlPoPlanLineRepository;
    }

    @Autowired
    private PigicIamFeignClient pigicIamFeignClient;

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
        UserVO userVO = pigicIamFeignClient.selectSelf();
        Assert.notNull(userVO.getDefaultCompanyId(), MessageAccessor.getMessage("error.supplier.quota.company.num.not.find").desc());
        sinochemintlPoPlanHeaderDTO.setCompanyId(userVO.getDefaultCompanyId());
        sinochemintlPoPlanHeaderDTO.setCreateId(userVO.getId());
        return PageHelper.doPage(pageRequest, () -> sinochemintlPoPlanHeaderRepository.list(sinochemintlPoPlanHeaderDTO));
    }

    /**
     * 新增/保存/修改采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SinochemintlPoPlanHeaderDTO addPoPlan(SinochemintlPoPlanHeaderDTO dto) {
        //创建头表并保存
        Date date = new Date();
        CustomUserDetails user = DetailsHelper.getUserDetails();
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = new SinochemintlPoPlanHeaderDTO();
        BeanUtils.copyProperties(dto, sinochemintlPoPlanHeaderDTO);
        if (StringUtils.isEmpty(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId())) {
            //id无值 状态无值 新增操作
            if (StringUtils.isEmpty(dto.getStatus()) && StringUtils.isEmpty(dto.getCreateName())) {
                //获取采购计划单号
                String poPlanNumber = codeRuleBuilder.generateCode(dto.getTenantId(), SinochemintlConstant.CodingCode.SCUX_ZHNY_RULES_PO_PLAN,
                        CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null);
                sinochemintlPoPlanHeaderDTO.setPoPlanNumber(poPlanNumber);
//                sinochemintlPoPlanHeaderDTO.setPoPlanNumber("PO202108130001");
                sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                //获取当前登录人信息
                sinochemintlPoPlanHeaderDTO.setCreateName(user.getRealName());
                sinochemintlPoPlanHeaderDTO.setCreateId(user.getUserId());
                sinochemintlPoPlanHeaderDTO.setCreationDate(new Date());
                //系统自动带出当前申请人
                sinochemintlPoPlanHeaderDTO.setApplicant(user.getRealName());
                sinochemintlPoPlanHeaderDTO.setApplicationDate(new Date());
                //系统自动带出单据来源于哪个系统 暂时只默认SRM系统
                sinochemintlPoPlanHeaderDTO.setPoSource("SRM");
                return sinochemintlPoPlanHeaderDTO;
            }
            //id无值 保存操作
            sinochemintlPoPlanHeaderDTO.setCreationDate(date);
            sinochemintlPoPlanHeaderDTO.setCreatedBy(user.getUserId());
            sinochemintlPoPlanHeaderDTO.setLastUpdateDate(date);
            sinochemintlPoPlanHeaderDTO.setLastUpdatedBy(user.getUserId());
            sinochemintlPoPlanHeaderRepository.setOne(sinochemintlPoPlanHeaderDTO);
            Long poPlanHeaderId = sinochemintlPoPlanHeaderRepository.getPoPlanHeaderId(sinochemintlPoPlanHeaderDTO);
            sinochemintlPoPlanHeaderDTO.setPoPlanHeaderId(poPlanHeaderId);
        } else {
            //有值 修改操作
            sinochemintlPoPlanHeaderDTO.setLastUpdateDate(date);
            sinochemintlPoPlanHeaderDTO.setLastUpdatedBy(user.getUserId());
            sinochemintlPoPlanHeaderRepository.updateByKey(sinochemintlPoPlanHeaderDTO);
        }
        //获取行表数据
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = dto.getSinochemintlPoPlanLineList();
        if (sinochemintlPoPlanLineList != null && !sinochemintlPoPlanLineList.isEmpty()) {
            for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
                //拼接计划共享省区
                StringBuilder planSharedProvince = new StringBuilder("|");
                for (String planSharedProvinceName : sinochemintlPoPlanLineDTO.getPlanSharedProvinceName()) {
                    planSharedProvince.append(planSharedProvinceName).append("|");
                }
                sinochemintlPoPlanLineDTO.setPlanSharedProvince(planSharedProvince.append("|").toString());
                if (StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getPoPlanLineId())) {
                    //id无值 新增操作
                    sinochemintlPoPlanLineDTO.setPoPlanHeaderId(sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId());
                    sinochemintlPoPlanLineDTO.setTenantId(sinochemintlPoPlanHeaderDTO.getTenantId());
                    sinochemintlPoPlanLineDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                    sinochemintlPoPlanLineDTO.setApplicant(user.getRealName());
                    sinochemintlPoPlanLineDTO.setCreationDate(date);
                    sinochemintlPoPlanLineDTO.setCreatedBy(user.getUserId());
                    sinochemintlPoPlanLineDTO.setLastUpdateDate(date);
                    sinochemintlPoPlanLineDTO.setLastUpdatedBy(user.getUserId());
                    sinochemintlPoPlanLineRepository.setOne(sinochemintlPoPlanLineDTO);
                } else {
                    //有值 修改操作
                    sinochemintlPoPlanLineDTO.setLastUpdateDate(date);
                    sinochemintlPoPlanLineDTO.setLastUpdatedBy(user.getUserId());
                    sinochemintlPoPlanLineRepository.updateByKey(sinochemintlPoPlanLineDTO);
                }
            }
        }
        sinochemintlPoPlanHeaderDTO.setSinochemintlPoPlanLineList(sinochemintlPoPlanLineRepository.selectByHeaderId(sinochemintlPoPlanHeaderDTO.getTenantId(), sinochemintlPoPlanHeaderDTO.getPoPlanHeaderId()));
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
                sinochemintlPoPlanLineRepository.deleteByKey(sinochemintlPoPlanLineDTO.getPoPlanHeaderId());
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
        //获取行数据
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = sinochemintlPoPlanLineRepository.selectByHeaderId(organizationId, poPlanHeaderId);
        if (sinochemintlPoPlanLineList.get(0) == null) {
            sinochemintlPoPlanHeaderDTO.setSinochemintlPoPlanLineList(new ArrayList<>());
        } else {
            // 处理页面数据的序号
            int size = pageRequest.getSize();
            int page = pageRequest.getPage();
            int firstNumber = page * size + 1;
            for (SinochemintlPoPlanLineDTO result : sinochemintlPoPlanLineList) {
                result.setSerialNum(firstNumber);
                String[] split = result.getPlanSharedProvince().split("\\|");
                List<String> strings = Arrays.asList(split);
                result.setPlanSharedProvinceName(strings);
                firstNumber++;
            }
            sinochemintlPoPlanHeaderDTO.setSinochemintlPoPlanLineList(sinochemintlPoPlanLineList);
        }
        return sinochemintlPoPlanHeaderDTO;
    }

    /**
     * 提交采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    @Override
    public void submit(Long organizationId, Long poPlanHeaderId) {
        //提交采购计划
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(poPlanHeaderId);
        List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList = sinochemintlPoPlanLineRepository.selectByHeaderId(organizationId, poPlanHeaderId);
        for (SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO : sinochemintlPoPlanLineList) {
            if (sinochemintlPoPlanLineDTO == null) {
                throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_LINE_NO_DATA);
            }
            if (!StringUtils.isEmpty(sinochemintlPoPlanLineDTO.getSpellDocProvince())) {
                //校验共享省区数量，若均已填写，则状态更新为【拼单完成】
                String[] split = sinochemintlPoPlanLineDTO.getPlanSharedProvince().split("\\|");
                if (split.length > sinochemintlPoPlanLineDTO.getSpellDocProvince()) {
                    sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_COMPLETE);
                    if (sinochemintlPoPlanLineDTO.getSpellDocProvince() != 1) {
                        //TODO 拼单完成 发送短信
                    }
                } else {
                    sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_MIDDLE);
                }
            }
        }
        sinochemintlPoPlanHeaderRepository.updateByKey(sinochemintlPoPlanHeaderDTO);
    }

    /**
     * 取消采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    @Override
    public void cancel(Long poPlanHeaderId) {
        //取消采购计划
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderRepository.selectByKey(poPlanHeaderId);
        if (sinochemintlPoPlanHeaderDTO.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
            //非新建状态无法取消
            throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_NON_NEW_NOT_CANCEL);
        }
        sinochemintlPoPlanHeaderDTO.setStatus(SinochemintlConstant.StatusCode.STATUS_CANCELLED);
        sinochemintlPoPlanHeaderRepository.updateByKey(sinochemintlPoPlanHeaderDTO);
    }

}
