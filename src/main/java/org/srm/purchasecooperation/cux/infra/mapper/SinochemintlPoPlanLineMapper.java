package org.srm.purchasecooperation.cux.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;

import java.util.List;
import java.util.Map;

/**
 * 采购计划行表Mapper
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
public interface SinochemintlPoPlanLineMapper extends BaseMapper<SinochemintlPoPlanLineDTO> {

    /**
     * 保存行表
     *
     * @param sinochemintlPoPlanLineDTO 采购计划行表
     */
    void setOne(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO);

    /**
     * 根据行表id修改
     *
     * @param sinochemintlPoPlanLineDTO 采购计划行表
     */
    void updateByKey(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO);

    /**
     * 根据头表id删除
     *
     * @param poPlanHeaderId 采购计划行表
     */
    void deleteByKey(Long poPlanHeaderId);

    /**
     * 根据行表id查询
     *
     * @param poPlanLineId 行表id
     * @return 行表数据
     */
    SinochemintlPoPlanLineDTO selectByKey(Long poPlanLineId);

    /**
     * 根据头表id查询
     *
     * @param organizationId 租户id
     * @param poPlanHeaderId 头表id
     * @return 行表数据
     */
    List<SinochemintlPoPlanLineDTO> selectByHeaderId(@Param("organizationId") Long organizationId, @Param("poPlanHeaderId") Long poPlanHeaderId);

    /**
     * 获取共享计划省区
     */
    List<String> getPlanSharedProvince(List<String> strings);

    /**
     * 获取当前排序
     */
    Integer getSerialNum(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO);

    Integer getDisplayLineNum(SinochemintlPoPlanLineDTO sinochemintlPoPlanLineDTO);

    /**
     * 共享计划省区校验
     *
     * @param planSharedProvince 共享计划省区
     * @return 省区
     */
    List<SinochemintlPoPlanLineDTO> sharedProvinceVerify(List<String> planSharedProvince);

}
