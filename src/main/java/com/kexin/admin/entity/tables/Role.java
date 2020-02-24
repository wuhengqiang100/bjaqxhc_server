package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.admin.entity.vo.AllFunction;
import com.kexin.common.base.TableEntity;


import java.io.Serializable;
import java.util.List;

/**
 * 角色实体类
 */
@TableName("dic_roles")
public class Role  extends TableEntity<Role> implements Serializable {
    @TableId(type = IdType.AUTO)
    @TableField(value = "ROLE_ID")
    private int roleId;//角色id

    @TableField(value = "ROLE_CODE")
    private String roleCode; //角色编号

     @TableField(value = "ROLE_NAME")
    private String roleName; //角色名称

    @TableField(exist = false)
    private List<Functions> functionsList;//权限集合

    @TableField(value = "FUNCTION_STRING")
    private String functionString;//处理后的权限字段
    @TableField(exist = false)
    private AllFunction functions;//角色更改时的,权限判断实体

    public AllFunction getFunctions() {
        return functions;
    }

    public void setFunctions(AllFunction functions) {
        this.functions = functions;
    }

    public String getFunctionString() {
        return functionString;
    }

    public void setFunctionString(String functionString) {
        this.functionString = functionString;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Functions> getFunctionsList() {
        return functionsList;
    }

    public void setFunctionsList(List<Functions> functionsList) {
        this.functionsList = functionsList;
    }
}
