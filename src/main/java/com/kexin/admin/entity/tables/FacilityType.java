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
@TableName("dic_machine_types")
public class FacilityType extends TableEntity<FacilityType> implements Serializable {
    @TableId(type = IdType.AUTO)
    @TableField(value = "MACHINE_TYPE_ID")
    private int machineTypeId;//设备类型id

    @TableField(value = "MACHINE_TYPE_CODE")
    private String machineTypeCode;//设备类型编号

    @TableField(value = "MACHINE_TYPE_NAME")
    private String machineTypeName;//设备类型名称

    public int getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(int machineTypeId) {
        this.machineTypeId = machineTypeId;
    }


    public String getMachineTypeCode() {
        return machineTypeCode;
    }

    public void setMachineTypeCode(String machineTypeCode) {
        this.machineTypeCode = machineTypeCode;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }
}
