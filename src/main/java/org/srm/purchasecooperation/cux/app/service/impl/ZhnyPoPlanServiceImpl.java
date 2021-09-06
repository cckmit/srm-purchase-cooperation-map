package org.srm.purchasecooperation.cux.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
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
        return PageHelper.doPage(pageRequest, () -> zhnyPoPlanHeaderRepository.list(zhnyPoPlanHeader));
    }

    /**
     * 新增采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    @Override
    public void addPoPlan(ZhnyPoPlanHeaderDTO dto) {
        //创建头表并保存
        ZhnyPoPlanHeader zhnyPoPlanHeader = new ZhnyPoPlanHeader();
        BeanUtils.copyProperties(dto, zhnyPoPlanHeader);
        if (StringUtils.isEmpty(zhnyPoPlanHeader.getPoPlanHeaderId())) {
            //id无值 新增操作
            zhnyPoPlanHeader.setStatus(ZhnyConstant.StatusCode.STATUS_NEW);
            zhnyPoPlanHeaderRepository.insert(zhnyPoPlanHeader);
        } else {
            //有值 修改操作
            zhnyPoPlanHeaderRepository.updateByPrimaryKey(zhnyPoPlanHeader);
        }
        //获取行表数据
        for (ZhnyPoPlanLine zhnyPoPlanLine : dto.getZhnyPoPlanLineList()) {
            if (StringUtils.isEmpty(zhnyPoPlanLine.getPoPlanLineId())) {
                //id无值 新增操作
                zhnyPoPlanLine.setPoPlanHeaderId(zhnyPoPlanHeader.getPoPlanHeaderId());
                zhnyPoPlanLine.setStatus(ZhnyConstant.StatusCode.STATUS_NEW);
                zhnyPoPlanLineRepository.insert(zhnyPoPlanLine);
            } else {
                //有值 修改操作
                zhnyPoPlanLineRepository.updateByPrimaryKey(zhnyPoPlanLine);
            }
        }
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

}
