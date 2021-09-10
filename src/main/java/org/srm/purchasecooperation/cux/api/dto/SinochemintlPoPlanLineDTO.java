package org.srm.purchasecooperation.cux.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购计划行表
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@ApiModel("采购计划行表")
public class SinochemintlPoPlanLineDTO{

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    private Long poPlanLineId;
    @ApiModelProperty(value = "采购方租户", required = true)
    private Long tenantId;
    @ApiModelProperty(value = "头表id", required = true)
    private Long poPlanHeaderId;
    @ApiModelProperty(value = "状态(SCUX.SINOCHEMINTL.PO_PLAN_NUMBER)", required = true)
    @LovValue(lovCode = "SCUX.SINOCHEMINTL.PO_PLAN_NUMBER", meaningField = "statusName")
    private String status;
    @ApiModelProperty(value = "已拼单省区数量", required = true)
    private Long spellDocProvince;
    @ApiModelProperty(value = "需求日期", required = true)
    private Date needDate;
    @ApiModelProperty(value = "计划共享省区", required = true)
    private String planSharedProvince;
    @ApiModelProperty(value = "省公司/项目ID", required = true)
    private String provinceCompanyId;
    @ApiModelProperty(value = "省公司/项目", required = true)
    private String provinceCompany;
    @ApiModelProperty(value = "产品名称", required = true)
    private String productName;
    @ApiModelProperty(value = "包装规格", required = true)
    private String specification;
    @ApiModelProperty(value = "初步沟通供应商", required = true)
    private String initialSupplier;
    @ApiModelProperty(value = "出厂价", required = true)
    private BigDecimal factoryPrice;
    @ApiModelProperty(value = "发货地址", required = true)
    private String deliveryAddress;
    @ApiModelProperty(value = "物料编码(SSRC.PRICE_LIB_ITEM)")
    private String materialCoding;
    @ApiModelProperty(value = "物料分类")
    private String materialClass;
    @ApiModelProperty(value = "需求数量", required = true)
    private BigDecimal requiredQuantity;
    @ApiModelProperty(value = "单位(SPRM.UOM)", required = true)
    @LovValue(lovCode = "SPRM.UOM", meaningField = "unitName")
    private String uomCode;
    @ApiModelProperty(value = "单位名称", required = true)
    private String unitName;
    @ApiModelProperty(value = "采购总价", required = true)
    private BigDecimal totalPurchasePrice;
    @ApiModelProperty(value = "币种(SPCM.CURRENCY)", required = true)
    private String currencyType;
    @ApiModelProperty(value = "运费供应商")
    private String freightSupplier;
    @ApiModelProperty(value = "运费价格")
    private BigDecimal freightPrice;
    @ApiModelProperty(value = "价格条款")
    private String priceTerms;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "税种(SPRM.TAX)")
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

    @ApiModelProperty(value = "序号")
    private Integer serialNum;

    @Transient
    @ApiModelProperty(value = "状态名")
    private String statusName;

//
// getter/setter
// ------------------------------------------------------------------------------


    public String getProvinceCompanyId() {
        return provinceCompanyId;
    }

    public void setProvinceCompanyId(String provinceCompanyId) {
        this.provinceCompanyId = provinceCompanyId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public String getStatus() {
        return status;
    }

    public SinochemintlPoPlanLineDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getPoPlanLineId() {
        return poPlanLineId;
    }

    public SinochemintlPoPlanLineDTO setPoPlanLineId(Long poPlanLineId) {
        this.poPlanLineId = poPlanLineId;
        return this;
    }

    /**
     * @return 采购方租户
     */
    public Long getTenantId() {
        return tenantId;
    }

    public SinochemintlPoPlanLineDTO setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    /**
     * @return 头表id
     */
    public Long getPoPlanHeaderId() {
        return poPlanHeaderId;
    }

    public SinochemintlPoPlanLineDTO setPoPlanHeaderId(Long poPlanHeaderId) {
        this.poPlanHeaderId = poPlanHeaderId;
        return this;
    }

    /**
     * @return 已拼单省区数量
     */
    public Long getSpellDocProvince() {
        return spellDocProvince;
    }

    public SinochemintlPoPlanLineDTO setSpellDocProvince(Long spellDocProvince) {
        this.spellDocProvince = spellDocProvince;
        return this;
    }

    /**
     * @return 需求日期
     */
    public Date getNeedDate() {
        return needDate;
    }

    public SinochemintlPoPlanLineDTO setNeedDate(Date needDate) {
        this.needDate = needDate;
        return this;
    }

    /**
     * @return 计划共享省区
     */
    public String getPlanSharedProvince() {
        return planSharedProvince;
    }

    public SinochemintlPoPlanLineDTO setPlanSharedProvince(String planSharedProvince) {
        this.planSharedProvince = planSharedProvince;
        return this;
    }

    /**
     * @return 省公司/项目
     */
    public String getProvinceCompany() {
        return provinceCompany;
    }

    public SinochemintlPoPlanLineDTO setProvinceCompany(String provinceCompany) {
        this.provinceCompany = provinceCompany;
        return this;
    }

    /**
     * @return 产品名称
     */
    public String getProductName() {
        return productName;
    }

    public SinochemintlPoPlanLineDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    /**
     * @return 规格
     */
    public String getSpecification() {
        return specification;
    }

    public SinochemintlPoPlanLineDTO setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    /**
     * @return 初步沟通供应商
     */
    public String getInitialSupplier() {
        return initialSupplier;
    }

    public SinochemintlPoPlanLineDTO setInitialSupplier(String initialSupplier) {
        this.initialSupplier = initialSupplier;
        return this;
    }

    /**
     * @return 出厂价
     */
    public BigDecimal getFactoryPrice() {
        return factoryPrice;
    }

    public SinochemintlPoPlanLineDTO setFactoryPrice(BigDecimal factoryPrice) {
        this.factoryPrice = factoryPrice;
        return this;
    }

    /**
     * @return 发货地址
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public SinochemintlPoPlanLineDTO setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    /**
     * @return 物料编码
     */
    public String getMaterialCoding() {
        return materialCoding;
    }

    public SinochemintlPoPlanLineDTO setMaterialCoding(String materialCoding) {
        this.materialCoding = materialCoding;
        return this;
    }

    /**
     * @return 物料分类
     */
    public String getMaterialClass() {
        return materialClass;
    }

    public SinochemintlPoPlanLineDTO setMaterialClass(String materialClass) {
        this.materialClass = materialClass;
        return this;
    }

    /**
     * @return 需求数量
     */
    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public SinochemintlPoPlanLineDTO setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
        return this;
    }

    /**
     * @return 单位
     */
    public String getUomCode() {
        return uomCode;
    }

    public SinochemintlPoPlanLineDTO setUomCode(String uomCode) {
        this.uomCode = uomCode;
        return this;
    }

    /**
     * @return 采购总价
     */
    public BigDecimal getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public SinochemintlPoPlanLineDTO setTotalPurchasePrice(BigDecimal totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
        return this;
    }

    /**
     * @return 币种
     */
    public String getCurrencyType() {
        return currencyType;
    }

    public SinochemintlPoPlanLineDTO setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
        return this;
    }

    /**
     * @return 运费供应商
     */
    public String getFreightSupplier() {
        return freightSupplier;
    }

    public SinochemintlPoPlanLineDTO setFreightSupplier(String freightSupplier) {
        this.freightSupplier = freightSupplier;
        return this;
    }

    /**
     * @return 运费价格
     */
    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    public SinochemintlPoPlanLineDTO setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
        return this;
    }

    /**
     * @return 价格条款
     */
    public String getPriceTerms() {
        return priceTerms;
    }

    public SinochemintlPoPlanLineDTO setPriceTerms(String priceTerms) {
        this.priceTerms = priceTerms;
        return this;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    public SinochemintlPoPlanLineDTO setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * @return 税种
     */
    public String getTaxType() {
        return taxType;
    }

    public SinochemintlPoPlanLineDTO setTaxType(String taxType) {
        this.taxType = taxType;
        return this;
    }

    /**
     * @return 税率
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public SinochemintlPoPlanLineDTO setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    /**
     * @return 申请人
     */
    public String getApplicant() {
        return applicant;
    }

    public SinochemintlPoPlanLineDTO setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    /**
     * @return 附件id
     */
    public Long getAppendixId() {
        return appendixId;
    }

    public SinochemintlPoPlanLineDTO setAppendixId(Long appendixId) {
        this.appendixId = appendixId;
        return this;
    }
}
