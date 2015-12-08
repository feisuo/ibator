package com.wy.vas.domain;

import com.wy.domain.persistence.BaseInfo;

public class couponProduct extends BaseInfo {
    /**
     * 该字段对应数据库列 coupon_product.PRODUCT_TYPE
     *
     * @ibatorgenerated
     */
    private Integer productType;

    /**
     * 该字段对应数据库列 coupon_product.PRODUCT_NAME
     *
     * @ibatorgenerated
     */
    private String productName;

    /**
     * 该字段对应数据库列 coupon_product.WORTH
     *
     * @ibatorgenerated
     */
    private Double worth;

    /**
     * 该字段对应数据库列 coupon_product.VALID_DAY
     *
     * @ibatorgenerated
     */
    private Integer validDay;

    /**
     * 该字段对应数据库列 coupon_product.PRODUCR_NUMBER
     *
     * @ibatorgenerated
     */
    private Integer producrNumber;

    /**
     * 该字段对应数据库列 coupon_product.productType_RANGE
     *
     * @ibatorgenerated
     */
    private String producttypeRange;

    /**
     * 该字段对应数据库列 coupon_product.AMOUNT_MIN
     *
     * @ibatorgenerated
     */
    private Double amountMin;

    /**
     * 该字段对应数据库列 coupon_product.AMOUNT_MAX
     *
     * @ibatorgenerated
     */
    private Double amountMax;

    /**
     * 该字段对应数据库列 coupon_product.IS_OPEN
     *
     * @ibatorgenerated
     */
    private Integer isOpen;

    /**
     * 该字段对应数据库列 coupon_product.IS_CREATE
     *
     * @ibatorgenerated
     */
    private Integer isCreate;

    /**
     * 该字段对应数据库列 coupon_product.MODIFY_USERCODE
     *
     * @ibatorgenerated
     */
    private String modifyUsercode;

    /**
     * 该字段对应数据库列 coupon_product.MODIFY_USERNAME
     *
     * @ibatorgenerated
     */
    private String modifyUsername;

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.PRODUCT_TYPE 的值
     *
     * @return 列  coupon_product.PRODUCT_TYPE 的值
     *
     * @ibatorgenerated
     */
    public Integer getProductType() {
        return productType;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.PRODUCT_TYPE 的值
     *
     * @param productType the value for coupon_product.PRODUCT_TYPE
     *
     * @ibatorgenerated
     */
    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.PRODUCT_NAME 的值
     *
     * @return 列  coupon_product.PRODUCT_NAME 的值
     *
     * @ibatorgenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.PRODUCT_NAME 的值
     *
     * @param productName the value for coupon_product.PRODUCT_NAME
     *
     * @ibatorgenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.WORTH 的值
     *
     * @return 列  coupon_product.WORTH 的值
     *
     * @ibatorgenerated
     */
    public Double getWorth() {
        return worth;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.WORTH 的值
     *
     * @param worth the value for coupon_product.WORTH
     *
     * @ibatorgenerated
     */
    public void setWorth(Double worth) {
        this.worth = worth;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.VALID_DAY 的值
     *
     * @return 列  coupon_product.VALID_DAY 的值
     *
     * @ibatorgenerated
     */
    public Integer getValidDay() {
        return validDay;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.VALID_DAY 的值
     *
     * @param validDay the value for coupon_product.VALID_DAY
     *
     * @ibatorgenerated
     */
    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.PRODUCR_NUMBER 的值
     *
     * @return 列  coupon_product.PRODUCR_NUMBER 的值
     *
     * @ibatorgenerated
     */
    public Integer getProducrNumber() {
        return producrNumber;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.PRODUCR_NUMBER 的值
     *
     * @param producrNumber the value for coupon_product.PRODUCR_NUMBER
     *
     * @ibatorgenerated
     */
    public void setProducrNumber(Integer producrNumber) {
        this.producrNumber = producrNumber;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.productType_RANGE 的值
     *
     * @return 列  coupon_product.productType_RANGE 的值
     *
     * @ibatorgenerated
     */
    public String getProducttypeRange() {
        return producttypeRange;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.productType_RANGE 的值
     *
     * @param producttypeRange the value for coupon_product.productType_RANGE
     *
     * @ibatorgenerated
     */
    public void setProducttypeRange(String producttypeRange) {
        this.producttypeRange = producttypeRange == null ? null : producttypeRange.trim();
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.AMOUNT_MIN 的值
     *
     * @return 列  coupon_product.AMOUNT_MIN 的值
     *
     * @ibatorgenerated
     */
    public Double getAmountMin() {
        return amountMin;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.AMOUNT_MIN 的值
     *
     * @param amountMin the value for coupon_product.AMOUNT_MIN
     *
     * @ibatorgenerated
     */
    public void setAmountMin(Double amountMin) {
        this.amountMin = amountMin;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.AMOUNT_MAX 的值
     *
     * @return 列  coupon_product.AMOUNT_MAX 的值
     *
     * @ibatorgenerated
     */
    public Double getAmountMax() {
        return amountMax;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.AMOUNT_MAX 的值
     *
     * @param amountMax the value for coupon_product.AMOUNT_MAX
     *
     * @ibatorgenerated
     */
    public void setAmountMax(Double amountMax) {
        this.amountMax = amountMax;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.IS_OPEN 的值
     *
     * @return 列  coupon_product.IS_OPEN 的值
     *
     * @ibatorgenerated
     */
    public Integer getIsOpen() {
        return isOpen;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.IS_OPEN 的值
     *
     * @param isOpen the value for coupon_product.IS_OPEN
     *
     * @ibatorgenerated
     */
    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.IS_CREATE 的值
     *
     * @return 列  coupon_product.IS_CREATE 的值
     *
     * @ibatorgenerated
     */
    public Integer getIsCreate() {
        return isCreate;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.IS_CREATE 的值
     *
     * @param isCreate the value for coupon_product.IS_CREATE
     *
     * @ibatorgenerated
     */
    public void setIsCreate(Integer isCreate) {
        this.isCreate = isCreate;
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.MODIFY_USERCODE 的值
     *
     * @return 列  coupon_product.MODIFY_USERCODE 的值
     *
     * @ibatorgenerated
     */
    public String getModifyUsercode() {
        return modifyUsercode;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.MODIFY_USERCODE 的值
     *
     * @param modifyUsercode the value for coupon_product.MODIFY_USERCODE
     *
     * @ibatorgenerated
     */
    public void setModifyUsercode(String modifyUsercode) {
        this.modifyUsercode = modifyUsercode == null ? null : modifyUsercode.trim();
    }

    /**
     * This method was generated by yrtz.
     * 返回数据库列 coupon_product.MODIFY_USERNAME 的值
     *
     * @return 列  coupon_product.MODIFY_USERNAME 的值
     *
     * @ibatorgenerated
     */
    public String getModifyUsername() {
        return modifyUsername;
    }

    /**
     * This method was generated by yrtz.
     * 设置数据库列 coupon_product.MODIFY_USERNAME 的值
     *
     * @param modifyUsername the value for coupon_product.MODIFY_USERNAME
     *
     * @ibatorgenerated
     */
    public void setModifyUsername(String modifyUsername) {
        this.modifyUsername = modifyUsername == null ? null : modifyUsername.trim();
    }
}