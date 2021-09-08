package org.srm.purchasecooperation.cux.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购计划头表
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@ApiModel("采购计划头表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_sinochemintl_po_plan_header")
public class SinochemintlPoPlanHeader extends AuditDomain {

    public static final String FIELD_PO_PLAN_HEADER_ID = "poPlanHeaderId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_PLAN_TYPE = "planType";
    public static final String FIELD_PO_PLAN_NUMBER = "poPlanNumber";
    public static final String FIELD_APPLICATION_SUM = "applicationSum";
    public static final String FIELD_CREATE_ID = "createId";
    public static final String FIELD_CREATE_NAME = "createName";
    public static final String FIELD_DEADLINE = "deadline";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_BUSINESS_CODE = "businessCode";
    public static final String FIELD_BUSINESS_NAME = "businessName";
    public static final String FIELD_PURCHASE_ORG_CODE = "purchaseOrgCode";
    public static final String FIELD_PURCHASE_ORG_NAME = "purchaseOrgName";
    public static final String FIELD_PURCHASE_CODE = "purchaseCode";
    public static final String FIELD_PURCHASE_NAME = "purchaseName";
    public static final String FIELD_PO_SOURCE = "poSource";
    public static final String FIELD_APPLICANT = "applicant";
    public static final String FIELD_DEPARTMENT_CODE = "departmentCode";
    public static final String FIELD_DEPARTMENT_NAME = "departmentName";
    public static final String FIELD_APPLICATION_DATE = "applicationDate";
    public static final String FIELD_ORIGINAL_CURRENCY = "originalCurrency";
    public static final String FIELD_PLAN_ILLUSTRATE = "planIllustrate";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long poPlanHeaderId;
    @ApiModelProperty(value = "采购方租户", required = true)
    @NotNull
    private Long tenantId;
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank
    private String title;
    @ApiModelProperty(value = "状态(SCUX.SINOCHEMINTL.PO_PLAN_NUMBER)", required = true)
    @LovValue(lovCode = "SCUX.SINOCHEMINTL.PO_PLAN_NUMBER", meaningField = "statusName")
    @NotBlank
    private String status;
    @ApiModelProperty(value = "计划类型", required = true)
    @NotBlank
    private String planType;
    @ApiModelProperty(value = "采购计划单号", required = true)
    @NotBlank
    private String poPlanNumber;
    @ApiModelProperty(value = "申请总额", required = true)
    @NotNull
    private BigDecimal applicationSum;
    @ApiModelProperty(value = "创建人id")
    private Long createId;
    @ApiModelProperty(value = "创建人", required = true)
    @NotBlank
    private String createName;
    @ApiModelProperty(value = "拼单截至时间", required = true)
    @NotNull
    private Date deadline;
    @ApiModelProperty(value = "公司编码")
    private String companyCode;
    @ApiModelProperty(value = "公司(HPFM.COMPANY)", required = true)
    @NotBlank
    private String companyName;
    @ApiModelProperty(value = "业务实体编码")
    private String businessCode;
    @ApiModelProperty(value = "业务实体(HPFM.OU)", required = true)
    @NotBlank
    private String businessName;
    @ApiModelProperty(value = "采购组织编码")
    private String purchaseOrgCode;
    @ApiModelProperty(value = "采购组织(HPFM.PURCHASE_ORGANIZATION)", required = true)
    @NotBlank
    private String purchaseOrgName;
    @ApiModelProperty(value = "采购员编码")
    private String purchaseCode;
    @ApiModelProperty(value = "采购员(HPFM.PURCHASE_AGENT)", required = true)
    @NotBlank
    private String purchaseName;
    @ApiModelProperty(value = "单据来源", required = true)
    @NotBlank
    private String poSource;
    @ApiModelProperty(value = "申请人", required = true)
    @NotBlank
    private String applicant;
    @ApiModelProperty(value = "部门编码")
    private String departmentCode;
    @ApiModelProperty(value = "所属部门(SPRM.USER_UNIT)", required = true)
    @NotBlank
    private String departmentName;
    @ApiModelProperty(value = "申请日期", required = true)
    @NotNull
    private Date applicationDate;
    @ApiModelProperty(value = "原币币种(SPCM.CURRENCY)")
    private String originalCurrency;
    @ApiModelProperty(value = "计划说明")
    private String planIllustrate;

//
// 非数据库字段
// ------------------------------------------------------------------------------

    @Transient
    @ApiModelProperty(value = "状态名")
    private String statusName;

//
// getter/setter
// ------------------------------------------------------------------------------


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatus() {
        return status;
    }

    public SinochemintlPoPlanHeader setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getPoPlanHeaderId() {
        return poPlanHeaderId;
    }

    public SinochemintlPoPlanHeader setPoPlanHeaderId(Long poPlanHeaderId) {
        this.poPlanHeaderId = poPlanHeaderId;
        return this;
    }

    /**
     * @return 采购方租户
     */
    public Long getTenantId() {
        return tenantId;
    }

    public SinochemintlPoPlanHeader setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    /**
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    public SinochemintlPoPlanHeader setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return 计划类型
     */
    public String getPlanType() {
        return planType;
    }

    public SinochemintlPoPlanHeader setPlanType(String planType) {
        this.planType = planType;
        return this;
    }

    /**
     * @return 采购计划单号
     */
    public String getPoPlanNumber() {
        return poPlanNumber;
    }

    public SinochemintlPoPlanHeader setPoPlanNumber(String poPlanNumber) {
        this.poPlanNumber = poPlanNumber;
        return this;
    }

    /**
     * @return 申请总额
     */
    public BigDecimal getApplicationSum() {
        return applicationSum;
    }

    public SinochemintlPoPlanHeader setApplicationSum(BigDecimal applicationSum) {
        this.applicationSum = applicationSum;
        return this;
    }

    /**
     * @return 创建人id
     */
    public Long getCreateId() {
        return createId;
    }

    public SinochemintlPoPlanHeader setCreateId(Long createId) {
        this.createId = createId;
        return this;
    }

    /**
     * @return 创建人
     */
    public String getCreateName() {
        return createName;
    }

    public SinochemintlPoPlanHeader setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    /**
     * @return 拼单截至时间
     */
    public Date getDeadline() {
        return deadline;
    }

    public SinochemintlPoPlanHeader setDeadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * @return 公司编码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    public SinochemintlPoPlanHeader setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    /**
     * @return 公司
     */
    public String getCompanyName() {
        return companyName;
    }

    public SinochemintlPoPlanHeader setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * @return 业务实体编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    public SinochemintlPoPlanHeader setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
        return this;
    }

    /**
     * @return 业务实体
     */
    public String getBusinessName() {
        return businessName;
    }

    public SinochemintlPoPlanHeader setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    /**
     * @return 采购组织编码
     */
    public String getPurchaseOrgCode() {
        return purchaseOrgCode;
    }

    public SinochemintlPoPlanHeader setPurchaseOrgCode(String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
        return this;
    }

    /**
     * @return 采购组织
     */
    public String getPurchaseOrgName() {
        return purchaseOrgName;
    }

    public SinochemintlPoPlanHeader setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
        return this;
    }

    /**
     * @return 采购员编码
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    public SinochemintlPoPlanHeader setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
        return this;
    }

    /**
     * @return 采购员
     */
    public String getPurchaseName() {
        return purchaseName;
    }

    public SinochemintlPoPlanHeader setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
        return this;
    }

    /**
     * @return 单据来源
     */
    public String getPoSource() {
        return poSource;
    }

    public SinochemintlPoPlanHeader setPoSource(String poSource) {
        this.poSource = poSource;
        return this;
    }

    /**
     * @return 申请人
     */
    public String getApplicant() {
        return applicant;
    }

    public SinochemintlPoPlanHeader setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    /**
     * @return 部门编码
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    public SinochemintlPoPlanHeader setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
        return this;
    }

    /**
     * @return 所属部门
     */
    public String getDepartmentName() {
        return departmentName;
    }

    public SinochemintlPoPlanHeader setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    /**
     * @return 申请日期
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    public SinochemintlPoPlanHeader setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    /**
     * @return 原币币种
     */
    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public SinochemintlPoPlanHeader setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
        return this;
    }

    /**
     * @return 计划说明
     */
    public String getPlanIllustrate() {
        return planIllustrate;
    }

    public SinochemintlPoPlanHeader setPlanIllustrate(String planIllustrate) {
        this.planIllustrate = planIllustrate;
        return this;
    }
}
