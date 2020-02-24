package com.kexin.admin.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.pojo.Token;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.User;
import com.kexin.admin.mapper.UserMapper;
import com.kexin.admin.service.UserService;
import com.kexin.common.util.ResponseEntity;
import com.kexin.common.util.StringUtilSubstring;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     * 用户登陆服务
     * @param userName
     * @param password
     * @return
     */
    @Override
    public ResponseEntity userLogin(String userName, String password) {
        ResponseEntity responseEntity=new ResponseEntity();
        Token token=new Token();//用户登陆成功token
        User currentUser=new User();
        List<User> currentUserList=new ArrayList<>();
        if (StringUtils.isEmpty(userName)){
            return ResponseEntity.failure("请输入用户名");
        }
        if (StringUtils.isEmpty(password)){
            return ResponseEntity.failure("请输入密码");
        }
        Map map=new HashMap<Object,String>();
        map.put("OPERATOR_LOGIN_NAME",userName);
        currentUserList=baseMapper.selectByMap(map);
        if (currentUserList.size()!=1){
            return ResponseEntity.failure("没有这个用户,请重新输入");
        }else{
            currentUser=currentUserList.get(0);
            if (password.equals(currentUser.getOperatorLoginPass())){
                responseEntity.setSuccess(true);
                responseEntity.setMessage("登陆成功");
//                responseEntity.setAny("UserType",currentUser.getUserType());
                token.setId(currentUser.getOperatorId());
                token.setName(currentUser.getOperatorLoginName());
                token.setEmail("120@QQ.COM");
                token.setTime(new Date());
                token.setToken(String.valueOf(currentUser.getOperatorId()));
                token.setPhone(1234567789);
                responseEntity.setAny("token",token);
                return responseEntity;
            }else{
                responseEntity.setSuccess(false);
                responseEntity.setMessage("密码错误");
                return responseEntity;
            }
        }
    }
    @Override
    public ResponseEntity userLock(String userId, String password) {
//        User user=baseMapper.findUserById(Integer.parseInt(userId));
        ResponseEntity responseEntity=new ResponseEntity();
        Token token=new Token();//用户登陆成功token
        User currentUser=baseMapper.selectById(userId);
        if (password.equals(currentUser.getOperatorLoginPass())){
            responseEntity.setSuccess(true);
            responseEntity.setMessage("登陆成功");
            token.setId(currentUser.getOperatorId());
            token.setName(currentUser.getOperatorLoginName());
            token.setEmail("120@QQ.COM");
            token.setTime(new Date());
            token.setToken(String.valueOf(currentUser.getOperatorId()));
            token.setPhone(1234567789);
            responseEntity.setAny("token",token);
            return responseEntity;
        }else{
            responseEntity.setSuccess(false);
            responseEntity.setMessage("密码错误");
            return responseEntity;
        }
    }
    @Override
    public IPage<User> selectUserPage(Page<User> page, Integer useFlag, String userName) {
        return baseMapper.selectPageUser(page,useFlag,userName);
    }

    @Override
    public ResponseEntity forbiddenUser(int userId) {
        User user=baseMapper.selectById(userId);
        if (user.getUseFlag()==0){
            user.setUseFlag(1);
            Integer i=baseMapper.updateById(user);
            if (i!=0){
                return ResponseEntity.success("启用用户成功");
            }else{
                return ResponseEntity.success("启用用户失败");
            }
        }else{
            user.setUseFlag(0);
            Integer i=baseMapper.updateById(user);
            if (i!=0){
                return ResponseEntity.success("禁止用户成功");
            }else{
                return ResponseEntity.success("禁止用户失败");
            }
        }
    }

    @Override
    public Boolean updateRoleString(Role oldRole, Role newRole) {
        try {
            List<User> userList=baseMapper.selectUserByRoleId(newRole.getRoleId());
            for (User allUser:userList) {
                String finalString=StringUtilSubstring.subStringReplace(allUser.getRoleString(),oldRole.getRoleName(),newRole.getRoleName());
                allUser.setRoleString(finalString);
                baseMapper.updateById(allUser);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public User findUserById(int userId) {
        return baseMapper.findUserById(userId);
    }

    @Override
    public Integer updateUserinfo(User user) {
        return baseMapper.updateUserinfo(user);
    }

    @Override
    public User getUserAllInfoById(int userId) {
        return baseMapper.getUserAllInfoById(userId);
    }

    @Override
    public Integer selectByMapCount(User user) {
        return baseMapper.selectByMapCount(user);
    }

    @Override
    public List<User> selectStaffList(int roleId) {
        return baseMapper.selectStaffList(roleId);
    }


}
