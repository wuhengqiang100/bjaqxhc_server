package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.common.base.TableEntity;

/**
 * 权限实体类
 */
@TableName("dic_functions")
public class Functions extends TableEntity<Functions> {

    @TableId(type = IdType.AUTO)
    @TableField(value = "FUNCTON_ID")
    private int functionId;//权限id

    @TableField(value = "FUNCTON_CODE")
    private String functionCode;//权限编号

    @TableField(value = "FUNCTON_NAME")
    private String functionName;//权限功能名称

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
