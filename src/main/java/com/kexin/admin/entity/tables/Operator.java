package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.admin.entity.vo.CheckOptionsGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.common.base.TableEntity;

import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 */
@TableName("dic_operators")
public class Operator extends TableEntity<Operator> {
    @TableId(type = IdType.AUTO)
    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;//人员序号

    @TableField(value = "OPERATOR_CODE")
    private String operatorCode; //人员编号

    @TableField(value = "OPERATOR_NAME")
    private String operatorName; //人员名称

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
