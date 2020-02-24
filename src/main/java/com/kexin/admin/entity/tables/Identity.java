package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.common.base.TableEntity;

import java.io.Serializable;

/**
 * 用户身份实体,人员类型
 */
@TableName("dic_operator_types")
public class Identity  extends TableEntity<Identity> implements Serializable {
    @TableId(type = IdType.AUTO)
    @TableField(value = "OPERATOR_TYPE_ID")
    private int operatorTypeId;//身份id

    @TableField(value = "OPERATOR_TYPE_CODE")
    private String operatorTypeCode;//身份编号

    @TableField(value = "OPERATOR_TYPE_NAME")
    private String operatorTypeName;//身份名称

    public int getOperatorTypeId() {
        return operatorTypeId;
    }

    public void setOperatorTypeId(int operatorTypeId) {
        this.operatorTypeId = operatorTypeId;
    }

    public String getOperatorTypeCode() {
        return operatorTypeCode;
    }

    public void setOperatorTypeCode(String operatorTypeCode) {
        this.operatorTypeCode = operatorTypeCode;
    }

    public String getOperatorTypeName() {
        return operatorTypeName;
    }

    public void setOperatorTypeName(String operatorTypeName) {
        this.operatorTypeName = operatorTypeName;
    }
}
