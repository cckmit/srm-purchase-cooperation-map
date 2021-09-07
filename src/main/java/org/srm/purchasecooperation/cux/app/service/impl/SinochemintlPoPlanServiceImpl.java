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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.app.service.SinochemintlPoPlanService;
import org.srm.purchasecooperation.cux.domain.entity.SinochemintlPoPlanHeader;
import org.srm.purchasecooperation.cux.domain.entity.SinochemintlPoPlanLine;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.SinochemintlPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.constant.SinochemintlConstant;

import java.util.ArrayList;
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

    /**
     * 采购计划头表查询参数
     *
     * @param sinochemintlPoPlanHeader 采购计划头表
     * @param pageRequest              分页
     * @return 采购计划头表列表
     */
    @Override
    public Page<SinochemintlPoPlanHeader> list(SinochemintlPoPlanHeader sinochemintlPoPlanHeader, PageRequest pageRequest) {
        //TODO 当自己为采购创建人时，只能查询到状态为新建或拼单中的采购计划  当自己为共享省区对应人时，只能查询到状态为拼单中的采购计划数据
        return PageHelper.doPage(pageRequest, () -> sinochemintlPoPlanHeaderRepository.list(sinochemintlPoPlanHeader));
    }

    /**
     * 新增/保存/修改采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    @Override
    public SinochemintlPoPlanHeader addPoPlan(SinochemintlPoPlanHeaderDTO dto) {
        //创建头表并保存
        SinochemintlPoPlanHeader sinochemintlPoPlanHeader = new SinochemintlPoPlanHeader();
        BeanUtils.copyProperties(dto, sinochemintlPoPlanHeader);
        if (StringUtils.isEmpty(sinochemintlPoPlanHeader.getPoPlanHeaderId())) {
            //id无值 状态无值 新增操作
            if (StringUtils.isEmpty(dto.getStatus()) && StringUtils.isEmpty(dto.getCreateName())) {
                //获取采购计划单号
                String poPlanNumber = codeRuleBuilder.generateCode(dto.getTenantId(), SinochemintlConstant.CodingCode.SCUX_ZHNY_RULES_PO_PLAN,
                        CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null);
                sinochemintlPoPlanHeader.setPoPlanNumber(poPlanNumber);
                sinochemintlPoPlanHeader.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                //获取当前登录人信息
                CustomUserDetails user = DetailsHelper.getUserDetails();
                sinochemintlPoPlanHeader.setCreateName(user.getRealName());
                sinochemintlPoPlanHeader.setCompanyCode(user.getUserId().toString());
                sinochemintlPoPlanHeader.setCreationDate(new Date());
                //系统自动带出当前申请人
                sinochemintlPoPlanHeader.setApplicant(user.getRealName());
                //系统自动带出单据来源于哪个系统
                //sinochemintlPoPlanHeader.setPoSource();
                return sinochemintlPoPlanHeader;
            }
            //id无值 保存操作
            sinochemintlPoPlanHeaderRepository.insert(sinochemintlPoPlanHeader);
        } else {
            //有值 修改操作
            sinochemintlPoPlanHeader.setObjectVersionNumber(sinochemintlPoPlanHeaderRepository
                    .selectByPrimaryKey(sinochemintlPoPlanHeader.getPoPlanHeaderId()).getObjectVersionNumber());
            sinochemintlPoPlanHeaderRepository.updateByPrimaryKey(sinochemintlPoPlanHeader);
        }
        //获取行表数据
        List<SinochemintlPoPlanLine> sinochemintlPoPlanLineList = dto.getSinochemintlPoPlanLineList();
        if (sinochemintlPoPlanLineList != null && !sinochemintlPoPlanLineList.isEmpty()) {
            for (SinochemintlPoPlanLine sinochemintlPoPlanLine : sinochemintlPoPlanLineList) {
                if (StringUtils.isEmpty(sinochemintlPoPlanLine.getPoPlanLineId())) {
                    //id无值 新增操作
                    sinochemintlPoPlanLine.setPoPlanHeaderId(sinochemintlPoPlanHeader.getPoPlanHeaderId());
                    sinochemintlPoPlanLine.setStatus(SinochemintlConstant.StatusCode.STATUS_NEW);
                    sinochemintlPoPlanLineRepository.insert(sinochemintlPoPlanLine);
                } else {
                    //有值 修改操作
                    sinochemintlPoPlanLine.setObjectVersionNumber(sinochemintlPoPlanLineRepository
                            .selectByPrimaryKey(sinochemintlPoPlanLine.getPoPlanLineId()).getObjectVersionNumber());
                    sinochemintlPoPlanLineRepository.updateByPrimaryKey(sinochemintlPoPlanLine);
                }
            }
        }
        return sinochemintlPoPlanHeader;
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
                SinochemintlPoPlanHeader sinochemintlPoPlanHeader = sinochemintlPoPlanHeaderRepository.selectByPrimaryKey(id);
                if (sinochemintlPoPlanHeader == null) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_PARAMETER_ERROR);
                }
                if (!sinochemintlPoPlanHeader.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_CANT_DELETE);
                }
                //只要新增状态才可以删除
                sinochemintlPoPlanHeaderRepository.deleteByPrimaryKey(sinochemintlPoPlanHeader);
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
                SinochemintlPoPlanLine sinochemintlPoPlanLine = sinochemintlPoPlanLineRepository.selectByPrimaryKey(id);
                if (sinochemintlPoPlanLine == null) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_PARAMETER_ERROR);
                }
                if (!sinochemintlPoPlanLine.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
                    throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_CANT_DELETE);
                }
                //只要新增状态才可以删除
                sinochemintlPoPlanLineRepository.deleteByPrimaryKey(sinochemintlPoPlanLine);
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
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = new SinochemintlPoPlanHeaderDTO();
        //获取头数据
        SinochemintlPoPlanHeader sinochemintlPoPlanHeader = sinochemintlPoPlanHeaderRepository.selectByPrimaryKey(poPlanHeaderId);
        if (sinochemintlPoPlanHeader == null) {
            throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_PARAMETER_ERROR);
        }
        BeanUtils.copyProperties(sinochemintlPoPlanHeader, sinochemintlPoPlanHeaderDTO);
        //获取行数据
        Page<SinochemintlPoPlanLine> sinochemintlPoPlanLineList = PageHelper.doPage(pageRequest, () -> sinochemintlPoPlanLineRepository.select(new SinochemintlPoPlanLine().setPoPlanLineId(poPlanHeaderId).setTenantId(organizationId)));
        if (sinochemintlPoPlanLineList == null || sinochemintlPoPlanLineList.isEmpty()) {
            sinochemintlPoPlanHeaderDTO.setSinochemintlPoPlanLineList(new ArrayList<>());
        } else {
            // 处理页面数据的序号
            int size = pageRequest.getSize();
            int page = pageRequest.getPage();
            int firstNumber = page * size + 1;
            for (SinochemintlPoPlanLine result : sinochemintlPoPlanLineList) {
                result.setSerialNum(firstNumber);
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
    public void submit(Long poPlanHeaderId) {
        //TODO 提交采购计划
        SinochemintlPoPlanHeader sinochemintlPoPlanHeader = sinochemintlPoPlanHeaderRepository.selectByPrimaryKey(poPlanHeaderId);
        sinochemintlPoPlanHeader.setStatus(SinochemintlConstant.StatusCode.STATUS_SPLICING_DOC_MIDDLE);
        sinochemintlPoPlanHeaderRepository.updateByPrimaryKey(sinochemintlPoPlanHeader);
    }

    /**
     * 取消采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    @Override
    public void cancel(Long poPlanHeaderId) {
        //TODO 取消采购计划
        SinochemintlPoPlanHeader sinochemintlPoPlanHeader = sinochemintlPoPlanHeaderRepository.selectByPrimaryKey(poPlanHeaderId);
        if (sinochemintlPoPlanHeader.getStatus().equals(SinochemintlConstant.StatusCode.STATUS_NEW)) {
            //非新建状态无法取消
            throw new CommonException(SinochemintlConstant.ErrorCode.ERROR_NON_NEW_NOT_CANCEL);
        }
        sinochemintlPoPlanHeader.setStatus(SinochemintlConstant.StatusCode.STATUS_CANCELLED);
        sinochemintlPoPlanHeaderRepository.updateByPrimaryKey(sinochemintlPoPlanHeader);
    }

}
