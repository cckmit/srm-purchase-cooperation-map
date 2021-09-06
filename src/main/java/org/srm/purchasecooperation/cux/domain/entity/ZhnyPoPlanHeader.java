package org.srm.purchasecooperation.cux.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Table(name = "scux_zhny_po_plan_header")
public class ZhnyPoPlanHeader extends AuditDomain {

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
    @ApiModelProperty(value = "状态", required = true)
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
    private Long applicationSum;
    @ApiModelProperty(value = "创建人id")
    private Long createId;
    @ApiModelProperty(value = "创建人", required = true)
    @NotBlank
    private String createName;
    @ApiModelProperty(value = "拼单截至时间", required = true)
    @NotNull
    private Date deadline;
    @ApiModelProperty(value = "公司代码")
    private String companyCode;
    @ApiModelProperty(value = "公司", required = true)
    @NotBlank
    private String companyName;
    @ApiModelProperty(value = "业务实体代码")
    private String businessCode;
    @ApiModelProperty(value = "业务实体", required = true)
    @NotBlank
    private String businessName;
    @ApiModelProperty(value = "采购组织代码")
    private String purchaseOrgCode;
    @ApiModelProperty(value = "采购组织", required = true)
    @NotBlank
    private String purchaseOrgName;
    @ApiModelProperty(value = "采购员代码")
    private String purchaseCode;
    @ApiModelProperty(value = "采购员", required = true)
    @NotBlank
    private String purchaseName;
    @ApiModelProperty(value = "单据来源", required = true)
    @NotBlank
    private String poSource;
    @ApiModelProperty(value = "申请人", required = true)
    @NotBlank
    private String applicant;
    @ApiModelProperty(value = "部门代码")
    private String departmentCode;
    @ApiModelProperty(value = "所属部门", required = true)
    @NotBlank
    private String departmentName;
    @ApiModelProperty(value = "申请日期", required = true)
    @NotNull
    private Date applicationDate;
    @ApiModelProperty(value = "原币币种")
    private String originalCurrency;
    @ApiModelProperty(value = "计划说明")
    private String planIllustrate;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    public String getStatus() {
        return status;
    }

    public ZhnyPoPlanHeader setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getPoPlanHeaderId() {
        return poPlanHeaderId;
    }

    public ZhnyPoPlanHeader setPoPlanHeaderId(Long poPlanHeaderId) {
        this.poPlanHeaderId = poPlanHeaderId;
        return this;
    }

    /**
     * @return 采购方租户
     */
    public Long getTenantId() {
        return tenantId;
    }

    public ZhnyPoPlanHeader setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    /**
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    public ZhnyPoPlanHeader setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return 计划类型
     */
    public String getPlanType() {
        return planType;
    }

    public ZhnyPoPlanHeader setPlanType(String planType) {
        this.planType = planType;
        return this;
    }

    /**
     * @return 采购计划单号
     */
    public String getPoPlanNumber() {
        return poPlanNumber;
    }

    public ZhnyPoPlanHeader setPoPlanNumber(String poPlanNumber) {
        this.poPlanNumber = poPlanNumber;
        return this;
    }

    /**
     * @return 申请总额
     */
    public Long getApplicationSum() {
        return applicationSum;
    }

    public ZhnyPoPlanHeader setApplicationSum(Long applicationSum) {
        this.applicationSum = applicationSum;
        return this;
    }

    /**
     * @return 创建人id
     */
    public Long getCreateId() {
        return createId;
    }

    public ZhnyPoPlanHeader setCreateId(Long createId) {
        this.createId = createId;
        return this;
    }

    /**
     * @return 创建人
     */
    public String getCreateName() {
        return createName;
    }

    public ZhnyPoPlanHeader setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    /**
     * @return 拼单截至时间
     */
    public Date getDeadline() {
        return deadline;
    }

    public ZhnyPoPlanHeader setDeadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * @return 公司代码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    public ZhnyPoPlanHeader setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    /**
     * @return 公司
     */
    public String getCompanyName() {
        return companyName;
    }

    public ZhnyPoPlanHeader setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * @return 业务实体代码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    public ZhnyPoPlanHeader setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
        return this;
    }

    /**
     * @return 业务实体
     */
    public String getBusinessName() {
        return businessName;
    }

    public ZhnyPoPlanHeader setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    /**
     * @return 采购组织代码
     */
    public String getPurchaseOrgCode() {
        return purchaseOrgCode;
    }

    public ZhnyPoPlanHeader setPurchaseOrgCode(String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
        return this;
    }

    /**
     * @return 采购组织
     */
    public String getPurchaseOrgName() {
        return purchaseOrgName;
    }

    public ZhnyPoPlanHeader setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
        return this;
    }

    /**
     * @return 采购员代码
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    public ZhnyPoPlanHeader setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
        return this;
    }

    /**
     * @return 采购员
     */
    public String getPurchaseName() {
        return purchaseName;
    }

    public ZhnyPoPlanHeader setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
        return this;
    }

    /**
     * @return 单据来源
     */
    public String getPoSource() {
        return poSource;
    }

    public ZhnyPoPlanHeader setPoSource(String poSource) {
        this.poSource = poSource;
        return this;
    }

    /**
     * @return 申请人
     */
    public String getApplicant() {
        return applicant;
    }

    public ZhnyPoPlanHeader setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    /**
     * @return 部门代码
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    public ZhnyPoPlanHeader setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
        return this;
    }

    /**
     * @return 所属部门
     */
    public String getDepartmentName() {
        return departmentName;
    }

    public ZhnyPoPlanHeader setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    /**
     * @return 申请日期
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    public ZhnyPoPlanHeader setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    /**
     * @return 原币币种
     */
    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public ZhnyPoPlanHeader setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
        return this;
    }

    /**
     * @return 计划说明
     */
    public String getPlanIllustrate() {
        return planIllustrate;
    }

    public ZhnyPoPlanHeader setPlanIllustrate(String planIllustrate) {
        this.planIllustrate = planIllustrate;
        return this;
    }
}
