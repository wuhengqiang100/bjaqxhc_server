package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.User;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectPageUser(Page<User> page, Integer useFlag, String userName);
    List<User> selectUserByRoleId(int roleId);
    /**
     * 获取用户数据,带身份以及角色roleString字段
     * @param userId
     * @return
     */
    User findUserById(int userId);

    /**
     * 用户的基础信息更改
     * 包括用户编号
     * 用户名称
     * 账户名
     * 备注
     * 4个字段
     * @param user
     * @return
     */
    Integer updateUserinfo(User user);

    /**
     * 获取用户的所用信息,特别是角色信息
     * @param userId
     * @return
     */
    User getUserAllInfoById(int userId);

    /**
     * 根据user里面的,用户编号,用户名称,账户名称查询该字段用的数量
     * @param user
     * @return
     */
    Integer selectByMapCount(User user);

    /**
     * 根据角色id获取该类角色下的用户
     * @param roleId
     * @return
     */
    List<User> selectStaffList(int roleId);
}