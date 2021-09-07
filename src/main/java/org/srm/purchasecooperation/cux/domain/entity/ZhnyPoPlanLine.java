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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购计划行表
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@ApiModel("采购计划行表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_zhny_po_plan_line")
public class ZhnyPoPlanLine extends AuditDomain {

    public static final String FIELD_PO_PLAN_LINE_ID = "poPlanLineId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_PO_PLAN_HEADER_ID = "poPlanHeaderId";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_SPELL_DOC_PROVINCE = "spellDocProvince";
    public static final String FIELD_NEED_DATE = "needDate";
    public static final String FIELD_PLAN_SHARED_PROVINCE = "planSharedProvince";
    public static final String FIELD_PROVINCE_COMPANY = "provinceCompany";
    public static final String FIELD_PRODUCT_NAME = "productName";
    public static final String FIELD_SPECIFICATION = "specification";
    public static final String FIELD_PACKAGE = "pack";
    public static final String FIELD_INITIAL_SUPPLIER = "initialSupplier";
    public static final String FIELD_FINAL_SUPPLIER = "finalSupplier";
    public static final String FIELD_FACTORY_PRICE = "factoryPrice";
    public static final String FIELD_FINAL_PRICE = "finalPrice";
    public static final String FIELD_DELIVERY_ADDRESS = "deliveryAddress";
    public static final String FIELD_MATERIAL_CODING = "materialCoding";
    public static final String FIELD_MATERIAL_CLASS = "materialClass";
    public static final String FIELD_REQUIRED_QUANTITY = "requiredQuantity";
    public static final String FIELD_UNIT = "unit";
    public static final String FIELD_TOTAL_PURCHASE_PRICE = "totalPurchasePrice";
    public static final String FIELD_CURRENCY_TYPE = "currencyType";
    public static final String FIELD_FREIGHT_SUPPLIER = "freightSupplier";
    public static final String FIELD_FREIGHT_PRICE = "freightPrice";
    public static final String FIELD_PRICE_TERMS = "priceTerms";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_TAX_TYPE = "taxType";
    public static final String FIELD_TAX_RATE = "taxRate";
    public static final String FIELD_APPLICANT = "applicant";
    public static final String FIELD_APPENDIX_ID = "appendixId";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long poPlanLineId;
    @ApiModelProperty(value = "采购方租户", required = true)
    @NotNull
    private Long tenantId;
    @ApiModelProperty(value = "头表id", required = true)
    @NotNull
    private Long poPlanHeaderId;
    @ApiModelProperty(value = "状态", required = true)
    @NotBlank
    private String status;
    @ApiModelProperty(value = "已拼单省区数量", required = true)
    @NotNull
    private Long spellDocProvince;
    @ApiModelProperty(value = "需求日期", required = true)
    @NotNull
    private Date needDate;
    @ApiModelProperty(value = "计划共享省区", required = true)
    @NotBlank
    private String planSharedProvince;
    @ApiModelProperty(value = "省公司/项目", required = true)
    @NotBlank
    private String provinceCompany;
    @ApiModelProperty(value = "产品名称", required = true)
    @NotBlank
    private String productName;
    @ApiModelProperty(value = "规格", required = true)
    @NotBlank
    private String specification;
    @ApiModelProperty(value = "包装")
    private String pack;
    @ApiModelProperty(value = "初步沟通供应商", required = true)
    @NotBlank
    private String initialSupplier;
    @ApiModelProperty(value = "最终供应商")
    private String finalSupplier;
    @ApiModelProperty(value = "出厂价", required = true)
    @NotNull
    private BigDecimal factoryPrice;
    @ApiModelProperty(value = "最终价格")
    private BigDecimal finalPrice;
    @ApiModelProperty(value = "发货地址", required = true)
    @NotBlank
    private String deliveryAddress;
    @ApiModelProperty(value = "物料编码")
    private String materialCoding;
    @ApiModelProperty(value = "物料分类")
    private String materialClass;
    @ApiModelProperty(value = "需求数量", required = true)
    @NotNull
    private Long requiredQuantity;
    @ApiModelProperty(value = "单位", required = true)
    @NotBlank
    private String unit;
    @ApiModelProperty(value = "采购总价", required = true)
    @NotNull
    private BigDecimal totalPurchasePrice;
    @ApiModelProperty(value = "币种", required = true)
    @NotBlank
    private String currencyType;
    @ApiModelProperty(value = "运费供应商")
    private String freightSupplier;
    @ApiModelProperty(value = "运费价格")
    private BigDecimal freightPrice;
    @ApiModelProperty(value = "价格条款")
    private String priceTerms;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "税种")
    private String taxType;
    @ApiModelProperty(value = "税率")
    private BigDecimal taxRate;
    @ApiModelProperty(value = "申请人")
    private String applicant;
    @ApiModelProperty(value = "附件id")
    private Long appendixId;

//
// 非数据库字段
// ------------------------------------------------------------------------------

    @Transient
    @ApiModelProperty(value = "序号")
    private Integer serialNum;

//
// getter/setter
// ------------------------------------------------------------------------------

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public String getStatus() {
        return status;
    }

    public ZhnyPoPlanLine setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getPoPlanLineId() {
        return poPlanLineId;
    }

    public ZhnyPoPlanLine setPoPlanLineId(Long poPlanLineId) {
        this.poPlanLineId = poPlanLineId;
        return this;
    }

    /**
     * @return 采购方租户
     */
    public Long getTenantId() {
        return tenantId;
    }

    public ZhnyPoPlanLine setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    /**
     * @return 头表id
     */
    public Long getPoPlanHeaderId() {
        return poPlanHeaderId;
    }

    public ZhnyPoPlanLine setPoPlanHeaderId(Long poPlanHeaderId) {
        this.poPlanHeaderId = poPlanHeaderId;
        return this;
    }

    /**
     * @return 已拼单省区数量
     */
    public Long getSpellDocProvince() {
        return spellDocProvince;
    }

    public ZhnyPoPlanLine setSpellDocProvince(Long spellDocProvince) {
        this.spellDocProvince = spellDocProvince;
        return this;
    }

    /**
     * @return 需求日期
     */
    public Date getNeedDate() {
        return needDate;
    }

    public ZhnyPoPlanLine setNeedDate(Date needDate) {
        this.needDate = needDate;
        return this;
    }

    /**
     * @return 计划共享省区
     */
    public String getPlanSharedProvince() {
        return planSharedProvince;
    }

    public ZhnyPoPlanLine setPlanSharedProvince(String planSharedProvince) {
        this.planSharedProvince = planSharedProvince;
        return this;
    }

    /**
     * @return 省公司/项目
     */
    public String getProvinceCompany() {
        return provinceCompany;
    }

    public ZhnyPoPlanLine setProvinceCompany(String provinceCompany) {
        this.provinceCompany = provinceCompany;
        return this;
    }

    /**
     * @return 产品名称
     */
    public String getProductName() {
        return productName;
    }

    public ZhnyPoPlanLine setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    /**
     * @return 规格
     */
    public String getSpecification() {
        return specification;
    }

    public ZhnyPoPlanLine setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    /**
     * @return 包装
     */
    public String getPack() {
        return pack;
    }

    public ZhnyPoPlanLine setPack(String pack) {
        this.pack = pack;
        return this;
    }

    /**
     * @return 初步沟通供应商
     */
    public String getInitialSupplier() {
        return initialSupplier;
    }

    public ZhnyPoPlanLine setInitialSupplier(String initialSupplier) {
        this.initialSupplier = initialSupplier;
        return this;
    }

    /**
     * @return 最终供应商
     */
    public String getFinalSupplier() {
        return finalSupplier;
    }

    public ZhnyPoPlanLine setFinalSupplier(String finalSupplier) {
        this.finalSupplier = finalSupplier;
        return this;
    }

    /**
     * @return 出厂价
     */
    public BigDecimal getFactoryPrice() {
        return factoryPrice;
    }

    public ZhnyPoPlanLine setFactoryPrice(BigDecimal factoryPrice) {
        this.factoryPrice = factoryPrice;
        return this;
    }

    /**
     * @return 最终价格
     */
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public ZhnyPoPlanLine setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }

    /**
     * @return 发货地址
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public ZhnyPoPlanLine setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    /**
     * @return 物料编码
     */
    public String getMaterialCoding() {
        return materialCoding;
    }

    public ZhnyPoPlanLine setMaterialCoding(String materialCoding) {
        this.materialCoding = materialCoding;
        return this;
    }

    /**
     * @return 物料分类
     */
    public String getMaterialClass() {
        return materialClass;
    }

    public ZhnyPoPlanLine setMaterialClass(String materialClass) {
        this.materialClass = materialClass;
        return this;
    }

    /**
     * @return 需求数量
     */
    public Long getRequiredQuantity() {
        return requiredQuantity;
    }

    public ZhnyPoPlanLine setRequiredQuantity(Long requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
        return this;
    }

    /**
     * @return 单位
     */
    public String getUnit() {
        return unit;
    }

    public ZhnyPoPlanLine setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    /**
     * @return 采购总价
     */
    public BigDecimal getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public ZhnyPoPlanLine setTotalPurchasePrice(BigDecimal totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
        return this;
    }

    /**
     * @return 币种
     */
    public String getCurrencyType() {
        return currencyType;
    }

    public ZhnyPoPlanLine setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
        return this;
    }

    /**
     * @return 运费供应商
     */
    public String getFreightSupplier() {
        return freightSupplier;
    }

    public ZhnyPoPlanLine setFreightSupplier(String freightSupplier) {
        this.freightSupplier = freightSupplier;
        return this;
    }

    /**
     * @return 运费价格
     */
    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    public ZhnyPoPlanLine setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
        return this;
    }

    /**
     * @return 价格条款
     */
    public String getPriceTerms() {
        return priceTerms;
    }

    public ZhnyPoPlanLine setPriceTerms(String priceTerms) {
        this.priceTerms = priceTerms;
        return this;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    public ZhnyPoPlanLine setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * @return 税种
     */
    public String getTaxType() {
        return taxType;
    }

    public ZhnyPoPlanLine setTaxType(String taxType) {
        this.taxType = taxType;
        return this;
    }

    /**
     * @return 税率
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public ZhnyPoPlanLine setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    /**
     * @return 申请人
     */
    public String getApplicant() {
        return applicant;
    }

    public ZhnyPoPlanLine setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    /**
     * @return 附件id
     */
    public Long getAppendixId() {
        return appendixId;
    }

    public ZhnyPoPlanLine setAppendixId(Long appendixId) {
        this.appendixId = appendixId;
        return this;
    }
}
