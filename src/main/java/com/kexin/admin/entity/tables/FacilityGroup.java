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
@TableName("dic_groups")
public class FacilityGroup extends TableEntity<FacilityGroup> implements Serializable {
    @TableId(type = IdType.AUTO)
    @TableField(value = "GROUP_ID")
    private int groupId;//设备分组id

    @TableField(value = "GROUP_CODE")
    private String groupCode;//设备分组编号

    @TableField(value = "GROUP_NAME")
    private String groupName;//设备分组名称

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
