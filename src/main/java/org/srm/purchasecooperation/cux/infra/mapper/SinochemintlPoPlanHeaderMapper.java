package org.srm.purchasecooperation.cux.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlEmployeeInformationDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;

import java.util.Date;
import java.util.List;

/**
 * 采购计划头表Mapper
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
public interface SinochemintlPoPlanHeaderMapper extends BaseMapper<SinochemintlPoPlanHeaderDTO> {

    /**
     * 采购计划头表查询参数
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     * @return 采购计划头表列表
     */
    List<SinochemintlPoPlanHeaderDTO> list(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 保存头表
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     */
    void setOne(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 根据头表id修改
     *
     * @param sinochemintlPoPlanHeaderDTO 采购计划头表
     */
    void updateByKey(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 根据头表id删除
     *
     * @param poPlanHeaderId 头表id
     */
    void deleteByKey(Long poPlanHeaderId);

    /**
     * 根据头表id查询
     *
     * @param poPlanHeaderId 头表id
     * @return 头表数据
     */
    SinochemintlPoPlanHeaderDTO selectByKey(Long poPlanHeaderId);

    Long getPoPlanHeaderId(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 采购计划导出
     *
     * @param ids 勾选的头表id
     * @return 需要导出的结果
     */
    List<SinochemintlPoPlanExcelDTO> excel(List<Long> ids);

    List<SinochemintlPoPlanHeaderDTO> maintain(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 导入数据校验
     *
     * @param sinochemintlPoPlanLineDTO 导入的数据
     * @return 查询结果
     */
    SinochemintlPoPlanLineDTO importVerify(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO);

    /**
     * 根据拼单截至时间修改状态
     *
     * @param date 当前时间
     */
    void timedTaskAlterState(Date date);

    /**
     * 根据员工id获取当前用户所在公司id和公司编码
     *
     * @param employeeId 员工id
     * @return 公司id和公司编码
     */
    SinochemintlPoPlanLineDTO getDefaultCompanyId(Long employeeId);

    /**
     * 通过省区代码，获取对应人员
     * @param provinceCompanyId
     * @return
     */
    List<SinochemintlEmployeeInformationDTO> getDefaultEmployeeList(Long provinceCompanyId);
}
