package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.admin.entity.vo.CheckOptionsGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.common.base.TableEntity;

import java.util.List;

/**
 * 登录用户实体类
 */
@TableName("SYS_LOGIN_USERS")
public class LoginUser extends TableEntity<LoginUser> {
    @TableId(type = IdType.AUTO)
    @TableField(value = "LOGIN_ID")
    private Integer loginId;//人员序号

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId; //用户Id,外键

    @TableField(value = "LOGIN_NAME")
    private String loginName; //登录名称

    @TableField(value = "LOGIN_PASS")
    private String loginPass; //登录密码

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }
}
