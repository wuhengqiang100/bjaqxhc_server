package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Operator;
import org.apache.ibatis.annotations.Param;

/**
 * 人员管理,service接口层
 */
public interface OperatorService extends IService<Operator> {

    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param operatorCode
     * @return
     */
    Integer operatorCountByCode(@Param("operatorCode") String operatorCode);


    /**
     * 根据机器名称计算数量
     * @param operatorName
     * @return
     */
    Integer operatorCountByName(@Param("operatorName") String operatorName);

    /**
     * 保存设备
     * @param operator
     */
    void saveOperator(@Param("operator") Operator operator);


    /**
     * 修改更新设备
     * @param operator
     */
    void updateOperator(@Param("operator") Operator operator);

    /**
     * 删除设备(单个)
     * @param operator
     */
    void deleteOperator(@Param("operator") Operator operator);

    /**
     * 禁用或者启用设备
     * @param operator
     */
    void lockOperator(@Param("operator") Operator operator);
}
