package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.common.base.TableEntity;

/**
 * 登录用户实体类
 */
@TableName("DIC_MACHINES")
public class Device extends TableEntity<Device> {
    @TableId(type = IdType.AUTO)
    @TableField(value = "MACHINE_ID")
    private Integer machineId;//设备id

    @TableField(value = "MACHINE_CODE")
    private String machineCode; //设备代码

    @TableField(value = "MACHINE_NAME")
    private String machineName; //设备名称

    @TableField(value = "MACHINE_IP")
    private String machineIp; //设备ip

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }
}
