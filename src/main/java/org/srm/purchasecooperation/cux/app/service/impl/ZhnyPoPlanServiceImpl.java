package org.srm.purchasecooperation.cux.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.BaseAppService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.api.dto.ZhnyPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.app.service.ZhnyPoPlanService;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanHeader;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanLine;
import org.srm.purchasecooperation.cux.domain.repository.ZhnyPoPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.ZhnyPoPlanLineRepository;

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
        zhnyPoPlanHeaderRepository.insert(zhnyPoPlanHeader);
        //获取行表数据
        List<ZhnyPoPlanLine> zhnyPoPlanLineList = dto.getZhnyPoPlanLineList();
        zhnyPoPlanLineRepository.batchInsert(zhnyPoPlanLineList);
    }

}
