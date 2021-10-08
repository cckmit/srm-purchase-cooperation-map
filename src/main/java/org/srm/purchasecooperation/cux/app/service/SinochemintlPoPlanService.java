package org.srm.purchasecooperation.cux.app.service;


import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.pr.api.dto.PrActionDTO;

import java.util.List;
import java.util.Map;

/**
 * 采购计划应用服务
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
public interface SinochemintlPoPlanService {

    /**
     * 采购计划头表查询参数
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     * @param pageRequest                 分页
     * @return 采购计划头表列表
     */
    Page<SinochemintlPoPlanHeaderDTO> list(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, PageRequest pageRequest);

    /**
     * 新增/保存/修改采购计划
     *
     * @param dto 采购计划头表和行表数据
     */
    SinochemintlPoPlanHeaderDTO addPoPlan(SinochemintlPoPlanHeaderDTO dto, PageRequest pageRequest);

    /**
     * 删除采购计划头表
     *
     * @param ids 头表id集合
     */
    void delHeader(List<Long> ids);

    /**
     * 删除采购计划行表
     *
     * @param ids 行表id集合
     */
    void delLine(List<Long> ids);

    /**
     * 获取头行单表
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     */
    SinochemintlPoPlanHeaderDTO getPoPlan(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest);

    /**
     * 提交采购计划
     *
     * @param ids 头表id
     */
    void submit(Long organizationId, List<Long> ids);

    /**
     * 取消采购计划
     *
     * @param poPlanHeaderId 头表id
     */
    void cancel(Long poPlanHeaderId);

    /**
     * 采购计划导出
     *
     * @param ids 勾选的头表id
     * @return 需要导出的结果
     */
    List<SinochemintlPoPlanExcelDTO> excel(String ids);

    /**
     * 采购计划确认
     *
     * @param dto 修改后的数据
     */
    void confirm(SinochemintlPoPlanHeaderDTO dto);

    /**
     * 获取行表列表
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     * @param pageRequest    分页参数
     * @return 行表列表
     */
    Page<SinochemintlPoPlanLineDTO> getPoPlanLine(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest);

    /**
     * 批量采购计划确认
     *
     * @param ids 修改后的数据
     */
    void batchConfirm(Long organizationId, List<Long> ids);

    /**
     * 操作记录
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     * @param pageRequest    分页参数
     * @return 操作记录
     */
    Page<PrActionDTO> operatingRecord(Long organizationId, Long poPlanHeaderId, PageRequest pageRequest);

    /**
     * 批量维护
     *
     * @param dto 批量维护信息
     */
    void batchMaint(SinochemintlPoPlanLineDTO dto);

    /**
     * 拼单
     *
     * @param dto 拼单数据
     * @return 结果
     */
    SinochemintlPoPlanLineDTO shareTheBill(SinochemintlPoPlanLineDTO dto);

    /**
     * 采购计划批量导出
     *
     * @param dto 查询数据
     * @return 结果
     */
    List<SinochemintlPoPlanExcelDTO> batchExcel(SinochemintlPoPlanHeaderDTO dto);

    /**
     * 采购计划明细查询
     *
     * @param sinochemintlPoPlanHeaderDTO 查询条件
     * @param pageRequest                 分页参数
     * @return 查询结果
     */
    Page<SinochemintlPoPlanLineDTO> detailList(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, PageRequest pageRequest);
    /**
     * 采购计划拼单截止时间到达通知（定时任务触发）
     *
     */
    void timedTaskHeader();

    List<Map<String, Object>> province(Long organizationId, Long companyId, Long applicantId);

    List<SinochemintlPoPlanExcelDTO> excelLine(String poPlanLineIds);

    List<SinochemintlPoPlanExcelDTO> batchExcelLine(SinochemintlPoPlanHeaderDTO dto);
}
