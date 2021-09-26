package org.srm.purchasecooperation.cux.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.springframework.format.annotation.DateTimeFormat;

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
@VersionAudit
@ModifyAudit
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SinochemintlPoPlanHeaderDTO extends AuditDomain {

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
    @Transient
    private String statusName;
    @ApiModelProperty(value = "计划类型", required = true)
    private String planType;
    @ApiModelProperty(value = "采购计划单号", required = true)
    private String poPlanNumber;
    @ApiModelProperty(value = "申请总额", required = true)
    private BigDecimal applicationSum;
    @ApiModelProperty(value = "申请最终总额", required = true)
    private BigDecimal applicationFinalSum;
    @ApiModelProperty(value = "创建人id")
    private Long createId;
    @ApiModelProperty(value = "创建人", required = true)
    private String createName;
    @ApiModelProperty(value = "拼单截至时间", required = true)
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(pattern = BaseConstants.Pattern.DATETIME)
    private Date deadline;
    @ApiModelProperty(value = "公司ID(SPFM.USER_AUTH.COMPANY)")
    private Long companyId;
    @ApiModelProperty(value = "公司", required = true)
    private String companyName;
    @ApiModelProperty(value = "业务实体ID(SPFM.USER_AUTH.OU)")
    private Long businessId;
    @ApiModelProperty(value = "业务实体", required = true)
    private String businessName;
    @ApiModelProperty(value = "采购组织ID(HPFM.PURCHASE_ORGANIZATION_M)")
    private Long purchaseOrgId;
    @ApiModelProperty(value = "采购组织", required = true)
    private String purchaseOrgName;
    @ApiModelProperty(value = "采购员ID(SPUC.PURCHASE_AGENT)")
    private Long purchaseId;
    @ApiModelProperty(value = "采购员", required = true)
    private String purchaseName;
    @ApiModelProperty(value = "单据来源", required = true)
    private String poSource;
    @ApiModelProperty(value = "申请人", required = true)
    private String applicant;
    @ApiModelProperty(value = "部门ID(SPUC.SD_PH_UNIT)")
    private Long departmentId;
    @ApiModelProperty(value = "所属部门", required = true)
    private String departmentName;
    @ApiModelProperty(value = "申请日期", required = true)
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(pattern = BaseConstants.Pattern.DATETIME)
    private Date applicationDate;
    @ApiModelProperty(value = "原币币种(SPRM.EXCHANGE_RATE.CURRENCY)")
    private String originalId;
    @ApiModelProperty(value = "币种名称", required = true)
    private String currencyName;
    @ApiModelProperty(value = "计划说明")
    private String planIllustrate;
    @ApiModelProperty(value = "登录用户所在公司ID")
    private Long userCompany;
    @ApiModelProperty(value = "物料分类")
    private String materialClass;
    @ApiModelProperty(value = "行号")
    private Long serialNum;
    @ApiModelProperty(value = "物料编码")
    private String materialCoding;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "需求数量")
    private BigDecimal requiredQuantity;
    @ApiModelProperty(value = "税种")
    private String taxType;
    @ApiModelProperty(value = "税率")
    private BigDecimal taxRate;
    @ApiModelProperty(value = "附件id")
    private String appendixId;
    @ApiModelProperty(value = "创建日期")
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(pattern = BaseConstants.Pattern.DATETIME)
    private Date establishDate;

    @Transient
    @ApiModelProperty(value = "行表数据")
    private List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList;

    public List<SinochemintlPoPlanLineDTO> getSinochemintlPoPlanLineList() {
        return sinochemintlPoPlanLineList;
    }

    public void setSinochemintlPoPlanLineList(List<SinochemintlPoPlanLineDTO> sinochemintlPoPlanLineList) {
        this.sinochemintlPoPlanLineList = sinochemintlPoPlanLineList;
    }

    public BigDecimal getApplicationFinalSum() {
        return applicationFinalSum;
    }

    public void setApplicationFinalSum(BigDecimal applicationFinalSum) {
        this.applicationFinalSum = applicationFinalSum;
    }

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    public String getAppendixId() {
        return appendixId;
    }

    public void setAppendixId(String appendixId) {
        this.appendixId = appendixId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getMaterialClass() {
        return materialClass;
    }

    public void setMaterialClass(String materialClass) {
        this.materialClass = materialClass;
    }

    public Long getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Long serialNum) {
        this.serialNum = serialNum;
    }

    public String getMaterialCoding() {
        return materialCoding;
    }

    public void setMaterialCoding(String materialCoding) {
        this.materialCoding = materialCoding;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public Long getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(Long userCompany) {
        this.userCompany = userCompany;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
     * @return 公司ID
     */
    public Long getCompanyId() {
        return companyId;
    }

    public SinochemintlPoPlanHeaderDTO setCompanyId(Long companyId) {
        this.companyId = companyId;
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
     * @return 业务实体ID
     */
    public Long getBusinessId() {
        return businessId;
    }

    public SinochemintlPoPlanHeaderDTO setBusinessId(Long businessId) {
        this.businessId = businessId;
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
     * @return 采购组织ID
     */
    public Long getPurchaseOrgId() {
        return purchaseOrgId;
    }

    public SinochemintlPoPlanHeaderDTO setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
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
     * @return 采购员ID
     */
    public Long getPurchaseId() {
        return purchaseId;
    }

    public SinochemintlPoPlanHeaderDTO setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
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
     * @return 部门ID
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    public SinochemintlPoPlanHeaderDTO setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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
