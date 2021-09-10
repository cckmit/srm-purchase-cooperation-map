package org.srm.purchasecooperation.cux.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购计划头表
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@ApiModel("采购计划头表")
public class SinochemintlPoPlanHeaderDTO {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    private Long poPlanHeaderId;
    @ApiModelProperty(value = "采购方租户", required = true)
    private Long tenantId;
    @ApiModelProperty(value = "标题", required = true)
    private String title;
    @ApiModelProperty(value = "状态(SCUX.SINOCHEMINTL.PO_PLAN_NUMBER)", required = true)
    @LovValue(lovCode = "SCUX.SINOCHEMINTL.PO_PLAN_NUMBER", meaningField = "statusName")
    private String status;
    @ApiModelProperty(value = "状态名")
    private String statusName;
    @ApiModelProperty(value = "计划类型", required = true)
    private String planType;
    @ApiModelProperty(value = "采购计划单号", required = true)
    private String poPlanNumber;
    @ApiModelProperty(value = "申请总额", required = true)
    private BigDecimal applicationSum;
    @ApiModelProperty(value = "创建人id")
    private Long createId;
    @ApiModelProperty(value = "创建人", required = true)
    private String createName;
    @ApiModelProperty(value = "拼单截至时间", required = true)
    private Date deadline;
    @ApiModelProperty(value = "公司编码(SPFM.USER_AUTH.COMPANY)")
    private String companyCode;
    @ApiModelProperty(value = "公司", required = true)
    private String companyName;
    @ApiModelProperty(value = "业务实体编码(SPFM.USER_AUTH.OU)")
    private String businessCode;
    @ApiModelProperty(value = "业务实体", required = true)
    private String businessName;
    @ApiModelProperty(value = "采购组织编码(SPCM.UNIT_PUR_ORGANIZATION)")
    private String purchaseOrgCode;
    @ApiModelProperty(value = "采购组织", required = true)
    private String purchaseOrgName;
    @ApiModelProperty(value = "采购员编码")
    private String purchaseCode;
    @ApiModelProperty(value = "采购员(SPUC.PURCHASE_AGENT)", required = true)
    private String purchaseName;
    @ApiModelProperty(value = "单据来源", required = true)
    private String poSource;
    @ApiModelProperty(value = "申请人", required = true)
    private String applicant;
    @ApiModelProperty(value = "部门编码")
    private String departmentCode;
    @ApiModelProperty(value = "所属部门(SPUC.SD_PH_UNIT)", required = true)
    private String departmentName;
    @ApiModelProperty(value = "申请日期", required = true)
    private Date applicationDate;
    @ApiModelProperty(value = "原币币种(SPCM.CURRENCY)")
    private String originalCurrency;
    @ApiModelProperty(value = "计划说明")
    private String planIllustrate;
    @ApiModelProperty(value = "创建日期")
    private Date creationDate;
    @Transient
    @ApiModelProperty(value = "行表数据")
    private List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList;

    public List<SinochemintlPoPlanLineDTO> getSinochemintlPoPlanLineList() {
        return sinochemintlPoPlanLineList;
    }

    public void setSinochemintlPoPlanLineList(List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList) {
        this.sinochemintlPoPlanLineList = sinochemintlPoPlanLineList;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public SinochemintlPoPlanHeaderDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getPoPlanHeaderId() {
        return poPlanHeaderId;
    }

    public SinochemintlPoPlanHeaderDTO setPoPlanHeaderId(Long poPlanHeaderId) {
        this.poPlanHeaderId = poPlanHeaderId;
        return this;
    }

    /**
     * @return 采购方租户
     */
    public Long getTenantId() {
        return tenantId;
    }

    public SinochemintlPoPlanHeaderDTO setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    /**
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    public SinochemintlPoPlanHeaderDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return 计划类型
     */
    public String getPlanType() {
        return planType;
    }

    public SinochemintlPoPlanHeaderDTO setPlanType(String planType) {
        this.planType = planType;
        return this;
    }

    /**
     * @return 采购计划单号
     */
    public String getPoPlanNumber() {
        return poPlanNumber;
    }

    public SinochemintlPoPlanHeaderDTO setPoPlanNumber(String poPlanNumber) {
        this.poPlanNumber = poPlanNumber;
        return this;
    }

    /**
     * @return 申请总额
     */
    public BigDecimal getApplicationSum() {
        return applicationSum;
    }

    public SinochemintlPoPlanHeaderDTO setApplicationSum(BigDecimal applicationSum) {
        this.applicationSum = applicationSum;
        return this;
    }

    /**
     * @return 创建人id
     */
    public Long getCreateId() {
        return createId;
    }

    public SinochemintlPoPlanHeaderDTO setCreateId(Long createId) {
        this.createId = createId;
        return this;
    }

    /**
     * @return 创建人
     */
    public String getCreateName() {
        return createName;
    }

    public SinochemintlPoPlanHeaderDTO setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    /**
     * @return 拼单截至时间
     */
    public Date getDeadline() {
        return deadline;
    }

    public SinochemintlPoPlanHeaderDTO setDeadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * @return 公司编码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    public SinochemintlPoPlanHeaderDTO setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    /**
     * @return 公司
     */
    public String getCompanyName() {
        return companyName;
    }

    public SinochemintlPoPlanHeaderDTO setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * @return 业务实体编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    public SinochemintlPoPlanHeaderDTO setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
        return this;
    }

    /**
     * @return 业务实体
     */
    public String getBusinessName() {
        return businessName;
    }

    public SinochemintlPoPlanHeaderDTO setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    /**
     * @return 采购组织编码
     */
    public String getPurchaseOrgCode() {
        return purchaseOrgCode;
    }

    public SinochemintlPoPlanHeaderDTO setPurchaseOrgCode(String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
        return this;
    }

    /**
     * @return 采购组织
     */
    public String getPurchaseOrgName() {
        return purchaseOrgName;
    }

    public SinochemintlPoPlanHeaderDTO setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
        return this;
    }

    /**
     * @return 采购员编码
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    public SinochemintlPoPlanHeaderDTO setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
        return this;
    }

    /**
     * @return 采购员
     */
    public String getPurchaseName() {
        return purchaseName;
    }

    public SinochemintlPoPlanHeaderDTO setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
        return this;
    }

    /**
     * @return 单据来源
     */
    public String getPoSource() {
        return poSource;
    }

    public SinochemintlPoPlanHeaderDTO setPoSource(String poSource) {
        this.poSource = poSource;
        return this;
    }

    /**
     * @return 申请人
     */
    public String getApplicant() {
        return applicant;
    }

    public SinochemintlPoPlanHeaderDTO setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    /**
     * @return 部门编码
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    public SinochemintlPoPlanHeaderDTO setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
        return this;
    }

    /**
     * @return 所属部门
     */
    public String getDepartmentName() {
        return departmentName;
    }

    public SinochemintlPoPlanHeaderDTO setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    /**
     * @return 申请日期
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    public SinochemintlPoPlanHeaderDTO setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    /**
     * @return 原币币种
     */
    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public SinochemintlPoPlanHeaderDTO setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
        return this;
    }

    /**
     * @return 计划说明
     */
    public String getPlanIllustrate() {
        return planIllustrate;
    }

    public SinochemintlPoPlanHeaderDTO setPlanIllustrate(String planIllustrate) {
        this.planIllustrate = planIllustrate;
        return this;
    }
}
