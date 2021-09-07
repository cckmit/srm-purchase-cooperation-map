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
import org.srm.purchasecooperation.cux.api.dto.ZhnyPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.app.service.ZhnyPoPlanService;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanHeader;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanLine;
import org.srm.purchasecooperation.cux.domain.repository.ZhnyPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.ZhnyPoPlanLineRepository;
import org.srm.purchasecooperation.cux.infra.constant.ZhnyConstant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购计划应用服务默认实现
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Service
public class ZhnyPoPlanServiceImpl extends BaseAppService implements ZhnyPoPlanService {

    private final ZhnyPoPlanHeaderRepository zhnyPoPlanHeaderRepository;

    private final ZhnyPoPlanLineRepository zhnyPoPlanLineRepository;

    @Autowired
    private CodeRuleBuilder codeRuleBuilder;

    @Autowired
    public ZhnyPoPlanServiceImpl(ZhnyPoPlanHeaderRepository zhnyPoPlanHeaderRepository, ZhnyPoPlanLineRepository zhnyPoPlanLineRepository) {
        this.zhnyPoPlanHeaderRepository = zhnyPoPlanHeaderRepository;
        this.zhnyPoPlanLineRepository = zhnyPoPlanLineRepository;
    }

    /**
     * 采购计划头表查询参数
     *
     * @param zhnyPoPlanHeader 采购计划头表
     * @param pageRequest      分页
     * @return 采购计划头表列表
     */
    @Override
    public Page<ZhnyPoPlanHeader> list(ZhnyPoPlanHeader zhnyPoPlanHeader, PageRequest pageRequest) {
        //TODO 当自己为采购创建人时，只能查询到状态为新建或拼单中的采购计划  当自己为共享省区对应人时，只能查询到状态为拼单中的采购计划数据
        return PageHelper.doPage(pageRequest, () -> zhnyPoPlanHeaderRepository.list(zhnyPoPlanHeader));
    }

    /**
     * 新增/保存/修改采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    @Override
    public ZhnyPoPlanHeader addPoPlan(ZhnyPoPlanHeaderDTO dto) {
        //创建头表并保存
        ZhnyPoPlanHeader zhnyPoPlanHeader = new ZhnyPoPlanHeader();
        BeanUtils.copyProperties(dto, zhnyPoPlanHeader);
        if (StringUtils.isEmpty(zhnyPoPlanHeader.getPoPlanHeaderId())) {
            //id无值 状态无值 新增操作
            if (StringUtils.isEmpty(dto.getStatus()) && StringUtils.isEmpty(dto.getCreateName())) {
                //获取采购计划单号
                String poPlanNumber = codeRuleBuilder.generateCode(dto.getTenantId(), ZhnyConstant.CodingCode.SCUX_ZHNY_RULES_PO_PLAN,
                        CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null);
                zhnyPoPlanHeader.setPoPlanNumber(poPlanNumber);
                zhnyPoPlanHeader.setStatus(ZhnyConstant.StatusCode.STATUS_NEW);
                //获取当前登录人信息
                CustomUserDetails user = DetailsHelper.getUserDetails();
                zhnyPoPlanHeader.setCreateName(user.getRealName());
                zhnyPoPlanHeader.setCompanyCode(user.getUserId().toString());
                zhnyPoPlanHeader.setCreationDate(new Date());
                //系统自动带出当前申请人
                zhnyPoPlanHeader.setApplicant(user.getRealName());
                //系统自动带出单据来源于哪个系统
                //zhnyPoPlanHeader.setPoSource();
                return zhnyPoPlanHeader;
            }
            //id无值 保存操作
            zhnyPoPlanHeaderRepository.insert(zhnyPoPlanHeader);
        } else {
            //有值 修改操作
            zhnyPoPlanHeader.setObjectVersionNumber(zhnyPoPlanHeaderRepository
                    .selectByPrimaryKey(zhnyPoPlanHeader.getPoPlanHeaderId()).getObjectVersionNumber());
            zhnyPoPlanHeaderRepository.updateByPrimaryKey(zhnyPoPlanHeader);
        }
        //获取行表数据
        List<ZhnyPoPlanLine> zhnyPoPlanLineList = dto.getZhnyPoPlanLineList();
        if (zhnyPoPlanLineList != null && !zhnyPoPlanLineList.isEmpty()) {
            for (ZhnyPoPlanLine zhnyPoPlanLine : zhnyPoPlanLineList) {
                if (StringUtils.isEmpty(zhnyPoPlanLine.getPoPlanLineId())) {
                    //id无值 新增操作
                    zhnyPoPlanLine.setPoPlanHeaderId(zhnyPoPlanHeader.getPoPlanHeaderId());
                    zhnyPoPlanLine.setStatus(ZhnyConstant.StatusCode.STATUS_NEW);
                    zhnyPoPlanLineRepository.insert(zhnyPoPlanLine);
                } else {
                    //有值 修改操作
                    zhnyPoPlanLine.setObjectVersionNumber(zhnyPoPlanLineRepository
                            .selectByPrimaryKey(zhnyPoPlanLine.getPoPlanLineId()).getObjectVersionNumber());
                    zhnyPoPlanLineRepository.updateByPrimaryKey(zhnyPoPlanLine);
                }
            }
        }
        return zhnyPoPlanHeader;
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
                ZhnyPoPlanHeader zhnyPoPlanHeader = zhnyPoPlanHeaderRepository.selectByPrimaryKey(id);
                if (zhnyPoPlanHeader == null) {
                    throw new CommonException(ZhnyConstant.ErrorCode.ERROR_PARAMETER_ERROR);
                }
                if (!zhnyPoPlanHeader.getStatus().equals(ZhnyConstant.StatusCode.STATUS_NEW)) {
                    throw new CommonException(ZhnyConstant.ErrorCode.ERROR_CANT_DELETE);
                }
                //只要新增状态才可以删除
                zhnyPoPlanHeaderRepository.deleteByPrimaryKey(zhnyPoPlanHeader);
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
                ZhnyPoPlanLine zhnyPoPlanLine = zhnyPoPlanLineRepository.selectByPrimaryKey(id);
                if (zhnyPoPlanLine == null) {
                    throw new CommonException(ZhnyConstant.ErrorCode.ERROR_PARAMETER_ERROR);
                }
                if (!zhnyPoPlanLine.getStatus().equals(ZhnyConstant.StatusCode.STATUS_NEW)) {
                    throw new CommonException(ZhnyConstant.ErrorCode.ERROR_CANT_DELETE);
                }
                //只要新增状态才可以删除
                zhnyPoPlanLineRepository.deleteByPrimaryKey(zhnyPoPlanLine);
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
    public ZhnyPoPlanHeaderDTO getPoPlan(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest) {
        ZhnyPoPlanHeaderDTO zhnyPoPlanHeaderDTO = new ZhnyPoPlanHeaderDTO();
        //获取头数据
        ZhnyPoPlanHeader zhnyPoPlanHeader = zhnyPoPlanHeaderRepository.selectByPrimaryKey(poPlanHeaderId);
        if (zhnyPoPlanHeader == null) {
            throw new CommonException(ZhnyConstant.ErrorCode.ERROR_PARAMETER_ERROR);
        }
        BeanUtils.copyProperties(zhnyPoPlanHeader, zhnyPoPlanHeaderDTO);
        //获取行数据
        Page<ZhnyPoPlanLine> zhnyPoPlanLineList = PageHelper.doPage(pageRequest, () -> zhnyPoPlanLineRepository.select(new ZhnyPoPlanLine().setPoPlanLineId(poPlanHeaderId).setTenantId(organizationId)));
        if (zhnyPoPlanLineList == null || zhnyPoPlanLineList.isEmpty()) {
            zhnyPoPlanHeaderDTO.setZhnyPoPlanLineList(new ArrayList<>());
        } else {
            // 处理页面数据的序号
            int size = pageRequest.getSize();
            int page = pageRequest.getPage();
            int firstNumber = page * size + 1;
            for (ZhnyPoPlanLine result : zhnyPoPlanLineList) {
                result.setSerialNum(firstNumber);
                firstNumber++;
            }
            zhnyPoPlanHeaderDTO.setZhnyPoPlanLineList(zhnyPoPlanLineList);
        }
        return zhnyPoPlanHeaderDTO;
    }

    /**
     * 提交采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    @Override
    public void submit(Long poPlanHeaderId) {
        //TODO 提交采购计划
    }

    /**
     * 取消采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    @Override
    public void cancel(Long poPlanHeaderId) {
        //TODO 取消采购计划
    }

}
