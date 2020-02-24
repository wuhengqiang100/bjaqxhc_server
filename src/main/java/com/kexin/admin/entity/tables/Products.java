package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.common.base.TableEntity;

import java.io.Serializable;

/**
 * 券别基础信息表
 */
@TableName(value = "dic_products")
public class Products extends TableEntity<Products> implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField(value = "PRODUCT_ID")
    private Integer productId;//券别主键序号

    @TableField(value = "PRODUCT_SERIES_CODE")
    private String productSeriesCode;//券别主键序号

    @TableField(value = "PRODUCT_CODE")
    private String productCode;//券别编号

    @TableField(value = "PRODUCT_NAME")
    private String productName;//劵别名称

    @TableField(value = "PRODUCT_AMOUNT")
    private Integer productAmount;//券别面值（元）

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductSeriesCode() {
        return productSeriesCode;
    }

    public void setProductSeriesCode(String productSeriesCode) {
        this.productSeriesCode = productSeriesCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }
}
