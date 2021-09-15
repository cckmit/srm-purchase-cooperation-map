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
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.springframework.format.annotation.DateTimeFormat;

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
@ExcelSheet(zh = "采购计划单", en = "Purchase plan")
public class SinochemintlPoPlanExcelDTO extends AuditDomain {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long poPlanHeaderId;
    @ApiModelProperty(value = "采购方租户", required = true)
    private Long tenantId;
    @ApiModelProperty(value = "标题", required = true)
    @ExcelColumn(title = "标题", zh = "标题", en = "title", order = 1)
    private String title;
    @ApiModelProperty(value = "状态(SCUX.SINOCHEMINTL.PO_PLAN_NUMBER)", required = true)
    @LovValue(lovCode = "SCUX.SINOCHEMINTL.PO_PLAN_NUMBER", meaningField = "statusName")
    private String status;
    @ApiModelProperty(value = "状态名")
    @ExcelColumn(title = "状态", zh = "状态", en = "status", order = 2)
    private String statusName;
    @ApiModelProperty(value = "计划类型", required = true)
    @ExcelColumn(title = "计划类型", zh = "计划类型", en = "plan type", order = 3)
    private String planType;
    @ApiModelProperty(value = "采购计划单号", required = true)
    @ExcelColumn(title = "采购计划单号", zh = "采购计划单号", en = "Purchase plan number", order = 4)
    private String poPlanNumber;
    @ApiModelProperty(value = "申请总额", required = true)
    @ExcelColumn(title = "申请总额", zh = "申请总额", en = "Total application", order = 5)
    private BigDecimal applicationSum;
    @ApiModelProperty(value = "创建人id")
    private Long createId;
    @ApiModelProperty(value = "创建人", required = true)
    @ExcelColumn(title = "创建人", zh = "创建人", en = "founder", order = 6)
    private String createName;
    @ApiModelProperty(value = "拼单截至时间", required = true)
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(pattern = BaseConstants.Pattern.DATETIME)
    @ExcelColumn(title = "拼单截至时间", zh = "拼单截至时间", en = "Order deadline", order = 7)
    private Date deadline;
    @ApiModelProperty(value = "公司ID(SPFM.USER_AUTH.COMPANY)")
    private Long companyId;
    @ApiModelProperty(value = "公司", required = true)
    @ExcelColumn(title = "公司", zh = "公司", en = "company", order = 8)
    private String companyName;
    @ApiModelProperty(value = "业务实体ID(SPFM.USER_AUTH.OU)")
    private Long businessId;
    @ApiModelProperty(value = "业务实体", required = true)
    @ExcelColumn(title = "业务实体", zh = "业务实体", en = "Business entity", order = 9)
    private String businessName;
    @ApiModelProperty(value = "采购组织ID(HPFM.PURCHASE_ORGANIZATION)")
    private Long purchaseOrgId;
    @ApiModelProperty(value = "采购组织", required = true)
    @ExcelColumn(title = "采购组织", zh = "采购组织", en = "Purchasing organization", order = 10)
    private String purchaseOrgName;
    @ApiModelProperty(value = "采购员ID(SPUC.PURCHASE_AGENT)")
    private Long purchaseId;
    @ApiModelProperty(value = "采购员", required = true)
    @ExcelColumn(title = "采购员", zh = "采购员", en = "buyer", order = 11)
    private String purchaseName;
    @ApiModelProperty(value = "单据来源", required = true)
    private String poSource;
    @ApiModelProperty(value = "申请人", required = true)
    @ExcelColumn(title = "申请人", zh = "申请人", en = "applicant", order = 12)
    private String applicant;
    @ApiModelProperty(value = "部门ID(SPUC.SD_PH_UNIT)")
    private Long departmentId;
    @ApiModelProperty(value = "所属部门", required = true)
    @ExcelColumn(title = "所属部门", zh = "所属部门", en = "Department", order = 13)
    private String departmentName;
    @ApiModelProperty(value = "申请日期", required = true)
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(pattern = BaseConstants.Pattern.DATETIME)
    @ExcelColumn(title = "申请日期", zh = "申请日期", en = "Date of Application", order = 14)
    private Date applicationDate;
    @ApiModelProperty(value = "原币币种(SPCM.CURRENCY)")
    private String originalId;
    @ApiModelProperty(value = "币种名称", required = true)
    @ExcelColumn(title = "币种名称", zh = "币种名称", en = "Original currency", order = 15)
    private String currencyName;
    @ApiModelProperty(value = "计划说明")
    @ExcelColumn(title = "计划说明", zh = "计划说明", en = "Plan description", order = 16)
    private String planIllustrate;
    @ApiModelProperty(value = "已拼单省区数量", required = true)
    @ExcelColumn(title = "已拼单省区数量", zh = "已拼单省区数量", en = "Number of provinces and regions that have been consolidated", order = 17)
    private Long spellDocProvince;
    @ApiModelProperty(value = "需求日期", required = true)
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(pattern = BaseConstants.Pattern.DATETIME)
    @ExcelColumn(title = "需求日期", zh = "需求日期", en = "Demand date", order = 18)
    private Date needDate;
    @ApiModelProperty(value = "计划共享省区(SPFM.USER_AUTH.COMPANY)", required = true)
    @ExcelColumn(title = "计划共享省区", zh = "计划共享省区", en = "Plan to share provinces", order = 19)
    private String planSharedProvince;
    @ApiModelProperty(value = "计划共享省区名称", required = true)
    @Transient
    private List<String> planSharedProvinceName;
    @ApiModelProperty(value = "省公司/项目ID(SPFM.USER_AUTH.COMPANY)", required = true)
    private Long provinceCompanyId;
    @ApiModelProperty(value = "省公司/项目", required = true)
    @ExcelColumn(title = "省公司/项目", zh = "省公司/项目", en = "company", order = 20)
    private String provinceCompany;
    @ApiModelProperty(value = "产品名称", required = true)
    @ExcelColumn(title = "产品名称", zh = "产品名称", en = "product name", order = 21)
    private String productName;
    @ApiModelProperty(value = "包装规格", required = true)
    @ExcelColumn(title = "包装规格", zh = "包装规格", en = "Packing specification", order = 22)
    private String specification;
    @ApiModelProperty(value = "初步沟通供应商Id(SMAL.TENANT_SUPPLIER_ALL)", required = true)
    private String initialSupplierId;
    @ApiModelProperty(value = "初步沟通供应商", required = true)
    @ExcelColumn(title = "初步沟通供应商", zh = "初步沟通供应商", en = "Initial communication with suppliers", order = 23)
    private String initialSupplier;
    @ApiModelProperty(value = "出厂价", required = true)
    @ExcelColumn(title = "出厂价", zh = "出厂价", en = "Ex-factory price", order = 24)
    private BigDecimal factoryPrice;
    @ApiModelProperty(value = "最终供应商", required = true)
    @ExcelColumn(title = "最终供应商", zh = "最终供应商", en = "Ultimate supplier", order = 25)
    private String endSupplier;
    @ApiModelProperty(value = "最终价格", required = true)
    @ExcelColumn(title = "最终价格", zh = "最终价格", en = "final price", order = 26)
    private BigDecimal endPrice;
    @ApiModelProperty(value = "发货地址", required = true)
    @ExcelColumn(title = "发货地址", zh = "发货地址", en = "delivery address", order = 27)
    private String deliveryAddress;
    @ApiModelProperty(value = "物料id(SSRC.PRICE_LIB_ITEM)")
    private Long materialId;
    @ApiModelProperty(value = "物料编码")
    @ExcelColumn(title = "物料编码", zh = "物料编码", en = "Material coding", order = 28)
    private String materialCoding;
    @ApiModelProperty(value = "物料分类")
    @ExcelColumn(title = "物料分类", zh = "物料分类", en = "Material classification", order = 29)
    private String materialClass;
    @ApiModelProperty(value = "需求数量", required = true)
    @ExcelColumn(title = "需求数量", zh = "需求数量", en = "required quantity", order = 30)
    private BigDecimal requiredQuantity;
    @ApiModelProperty(value = "单位(SPRM.UOM)", required = true)
    private Long uomId;
    @ApiModelProperty(value = "单位名称", required = true)
    @ExcelColumn(title = "单位", zh = "单位", en = "company name", order = 31)
    private String unitName;
    @ApiModelProperty(value = "采购总价", required = true)
    @ExcelColumn(title = "采购总价", zh = "采购总价", en = "Total purchase price", order = 32)
    private BigDecimal totalPurchasePrice;
    @ApiModelProperty(value = "运费供应商")
    @ExcelColumn(title = "运费供应商", zh = "运费供应商", en = "Freight supplier", order = 33)
    private String freightSupplier;
    @ApiModelProperty(value = "运费价格")
    @ExcelColumn(title = "运费价格", zh = "运费价格", en = "Shipping price", order = 34)
    private BigDecimal freightPrice;
    @ApiModelProperty(value = "价格条款")
    @ExcelColumn(title = "价格条款", zh = "价格条款", en = "pricing term", order = 35)
    private String priceTerms;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "税种ID(SPRM.TAX)")
    private Long taxId;
    @ApiModelProperty(value = "税种")
    @ExcelColumn(title = "税种", zh = "税种", en = "Tax", order = 36)
    private String taxType;
    @ApiModelProperty(value = "税率")
    @ExcelColumn(title = "税率", zh = "税率", en = "tax rate", order = 37)
    private BigDecimal taxRate;
    @ApiModelProperty(value = "行申请人")
    @ExcelColumn(title = "行申请人", zh = "行申请人", en = "Applicant", order = 38)
    private String lineApplicant;

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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public BigDecimal getApplicationSum() {
        return applicationSum;
    }

    public void setApplicationSum(BigDecimal applicationSum) {
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getPurchaseOrgId() {
        return purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public String getPurchaseOrgName() {
        return purchaseOrgName;
    }

    public void setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getPlanIllustrate() {
        return planIllustrate;
    }

    public void setPlanIllustrate(String planIllustrate) {
        this.planIllustrate = planIllustrate;
    }

    public Long getSpellDocProvince() {
        return spellDocProvince;
    }

    public void setSpellDocProvince(Long spellDocProvince) {
        this.spellDocProvince = spellDocProvince;
    }

    public Date getNeedDate() {
        return needDate;
    }

    public void setNeedDate(Date needDate) {
        this.needDate = needDate;
    }

    public String getPlanSharedProvince() {
        return planSharedProvince;
    }

    public void setPlanSharedProvince(String planSharedProvince) {
        this.planSharedProvince = planSharedProvince;
    }

    public List<String> getPlanSharedProvinceName() {
        return planSharedProvinceName;
    }

    public void setPlanSharedProvinceName(List<String> planSharedProvinceName) {
        this.planSharedProvinceName = planSharedProvinceName;
    }

    public Long getProvinceCompanyId() {
        return provinceCompanyId;
    }

    public void setProvinceCompanyId(Long provinceCompanyId) {
        this.provinceCompanyId = provinceCompanyId;
    }

    public String getProvinceCompany() {
        return provinceCompany;
    }

    public void setProvinceCompany(String provinceCompany) {
        this.provinceCompany = provinceCompany;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getInitialSupplierId() {
        return initialSupplierId;
    }

    public void setInitialSupplierId(String initialSupplierId) {
        this.initialSupplierId = initialSupplierId;
    }

    public String getInitialSupplier() {
        return initialSupplier;
    }

    public void setInitialSupplier(String initialSupplier) {
        this.initialSupplier = initialSupplier;
    }

    public BigDecimal getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(BigDecimal factoryPrice) {
        this.factoryPrice = factoryPrice;
    }

    public String getEndSupplier() {
        return endSupplier;
    }

    public void setEndSupplier(String endSupplier) {
        this.endSupplier = endSupplier;
    }

    public BigDecimal getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(BigDecimal endPrice) {
        this.endPrice = endPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCoding() {
        return materialCoding;
    }

    public void setMaterialCoding(String materialCoding) {
        this.materialCoding = materialCoding;
    }

    public String getMaterialClass() {
        return materialClass;
    }

    public void setMaterialClass(String materialClass) {
        this.materialClass = materialClass;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(BigDecimal totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public String getFreightSupplier() {
        return freightSupplier;
    }

    public void setFreightSupplier(String freightSupplier) {
        this.freightSupplier = freightSupplier;
    }

    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getPriceTerms() {
        return priceTerms;
    }

    public void setPriceTerms(String priceTerms) {
        this.priceTerms = priceTerms;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
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

    public String getLineApplicant() {
        return lineApplicant;
    }

    public void setLineApplicant(String lineApplicant) {
        this.lineApplicant = lineApplicant;
    }
}
