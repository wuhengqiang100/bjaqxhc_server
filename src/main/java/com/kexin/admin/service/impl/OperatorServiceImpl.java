package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.service.OperatorService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 人员 配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper, Operator> implements OperatorService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public Integer operatorCountByCode(String operatorCode) {
        QueryWrapper<Operator> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",operatorCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer operatorCountByName(String operatorName) {
        QueryWrapper<Operator> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",operatorName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperator(Operator operator) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOperator(Operator operator) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperator(Operator operator) {
        baseMapper.deleteById(operator.getOperatorId());
    }

    @Override
    public void lockOperator(Operator operator) {
        operator.setUseFlag(operator.getUseFlag()?false:true);
        baseMapper.updateById(operator);
    }
}
