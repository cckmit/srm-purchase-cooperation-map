package org.srm.purchasecooperation.cux.api.dto;

import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.cux.domain.entity.SinochemintlPoPlanLine;

import java.util.Date;
import java.util.List;

/**
 * @author gui-tian-jing
 * @date 2021/9/6 16:45
 */
public class SinochemintlPoPlanHeaderDTO {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long poPlanHeaderId;
    @ApiModelProperty(value = "采购方租户", required = true)
    private Long tenantId;
    @ApiModelProperty(value = "标题", required = true)
    private String title;
    @ApiModelProperty(value = "状态", required = true)
    private String status;
    @ApiModelProperty(value = "计划类型", required = true)
    private String planType;
    @ApiModelProperty(value = "采购计划单号", required = true)
    private String poPlanNumber;
    @ApiModelProperty(value = "申请总额", required = true)
    private Long applicationSum;
    @ApiModelProperty(value = "创建人id")
    private Long createId;
    @ApiModelProperty(value = "创建人", required = true)
    private String createName;
    @ApiModelProperty(value = "拼单截至时间", required = true)
    private Date deadline;
    @ApiModelProperty(value = "公司代码")
    private String companyCode;
    @ApiModelProperty(value = "公司", required = true)
    private String companyName;
    @ApiModelProperty(value = "业务实体代码")
    private String businessCode;
    @ApiModelProperty(value = "业务实体", required = true)
    private String businessName;
    @ApiModelProperty(value = "采购组织代码")
    private String purchaseOrgCode;
    @ApiModelProperty(value = "采购组织", required = true)
    private String purchaseOrgName;
    @ApiModelProperty(value = "采购员代码")
    private String purchaseCode;
    @ApiModelProperty(value = "采购员", required = true)
    private String purchaseName;
    @ApiModelProperty(value = "单据来源", required = true)
    private String poSource;
    @ApiModelProperty(value = "申请人", required = true)
    private String applicant;
    @ApiModelProperty(value = "部门代码")
    private String departmentCode;
    @ApiModelProperty(value = "所属部门", required = true)
    private String departmentName;
    @ApiModelProperty(value = "申请日期", required = true)
    private Date applicationDate;
    @ApiModelProperty(value = "原币币种")
    private String originalCurrency;
    @ApiModelProperty(value = "计划说明")
    private String planIllustrate;
    @ApiModelProperty(value = "创建日期")
    private String creationDate;
    @ApiModelProperty(value = "行表数据")
    private List<SinochemintlPoPlanLine> sinochemintlPoPlanLineList;

    public List<SinochemintlPoPlanLine> getSinochemintlPoPlanLineList() {
        return sinochemintlPoPlanLineList;
    }

    public void setSinochemintlPoPlanLineList(List<SinochemintlPoPlanLine> sinochemintlPoPlanLineList) {
        this.sinochemintlPoPlanLineList = sinochemintlPoPlanLineList;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getPoPlanHeaderId() {
        return poPlanHeaderId;
    }

    public void setPoPlanHeaderId(Long poPlanHeaderId) {
        this.poPlanHeaderId = poPlanHeaderId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPoPlanNumber() {
        return poPlanNumber;
    }

    public void setPoPlanNumber(String poPlanNumber) {
        this.poPlanNumber = poPlanNumber;
    }

    public Long getApplicationSum() {
        return applicationSum;
    }

    public void setApplicationSum(Long applicationSum) {
        this.applicationSum = applicationSum;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPurchaseOrgCode() {
        return purchaseOrgCode;
    }

    public void setPurchaseOrgCode(String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
    }

    public String getPurchaseOrgName() {
        return purchaseOrgName;
    }

    public void setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public String getPoSource() {
        return poSource;
    }

    public void setPoSource(String poSource) {
        this.poSource = poSource;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getPlanIllustrate() {
        return planIllustrate;
    }

    public void setPlanIllustrate(String planIllustrate) {
        this.planIllustrate = planIllustrate;
    }
}
