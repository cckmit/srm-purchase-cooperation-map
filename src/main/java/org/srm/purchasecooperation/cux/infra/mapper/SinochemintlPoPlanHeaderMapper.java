package org.srm.purchasecooperation.cux.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;

import java.util.ArrayList;
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
    List<SinochemintlPoPlanExcelDTO> excel(List<String> ids);

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
    void timedTaskAlterState(@Param("date") Date date, @Param("status") String status);

    /**
     * 根据拼单截至时间获取订单信息
     *
     * @param date 当前时间
     */
    List<SinochemintlPoPlanHeaderDTO> timedTaskHeader(Date date);

    /**
     * 根据员工id获取当前用户所在公司id和公司编码
     *
     * @param employeeId 员工id
     * @return 公司id和公司编码
     */
    List<SinochemintlPoPlanLineDTO> getDefaultCompanyId(Long employeeId);

    /**
     * 通过省区代码，获取对应人员
     *
     * @param integers
     * @return
     */
    List<Receiver> getDefaultEmployeeList(@Param("integers") List<Integer> integers, @Param("lovValues") List<LovValueDTO> lovValues);

    /**
     * 采购计划批量导出
     *
     * @param dto 查询数据
     * @return 结果
     */
    List<SinochemintlPoPlanExcelDTO> batchExcel(SinochemintlPoPlanHeaderDTO dto);

    /**
     * 获取快到期采购计划
     *
     * @param date 截至时间
     * @return 头数据
     */
    List<SinochemintlPoPlanHeaderDTO> getExpirationTime(Date date);

    /**
     * 如果公司唯一则查询业务实体
     *
     * @param sinochemintlPoPlanHeaderDTO 公司id
     * @return 查询结果
     */
    List<SinochemintlPoPlanHeaderDTO> verifyBusiness(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 如果业务实体唯一查询所在部门是否唯一
     *
     * @param sinochemintlPoPlanHeaderDTO 业务实体id
     * @return 查询结果
     */
    List<SinochemintlPoPlanHeaderDTO> verifyDepartment(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 如果业务实体唯一查询采购组织
     *
     * @param sinochemintlPoPlanHeaderDTO 业务实体id
     * @return 查询结果
     */
    List<SinochemintlPoPlanHeaderDTO> verifyPurchaseOrg(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    List<SinochemintlPoPlanExcelDTO> excelLine(List<String> list);

    List<SinochemintlPoPlanExcelDTO> batchExcelLine(SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO);

    /**
     * 根据公司编码获取公司id和公司名称
     *
     * @param planSharedProvince 公司编码
     * @return 公司id和公司名称
     */
    SinochemintlPoPlanLineDTO getCompany(String planSharedProvince);

    /**
     * 根据公司Id获取
     * @param integers
     * @return
     */
    List<String> selectCompanyById(ArrayList<Integer> integers);

    List<Receiver> getEmployees(List<String> list);

    List<String> selectCompanyNumById(ArrayList<Integer> integers);

    Long selectUomId(@Param("uomCode") String uomCode, @Param("tenantId") Long tenantId);
}
