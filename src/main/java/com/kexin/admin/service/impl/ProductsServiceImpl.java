package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Products;
import com.kexin.admin.mapper.ProductsMapper;
import com.kexin.admin.service.ProductsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @Description:
 * @Author: 巫恒强  @Date: 2019/10/11 14:15
 * @Param: 产品类型service层
 * @Return: 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements ProductsService {


}
