package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Device;
import com.kexin.admin.service.DeviceService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEntity;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * 设备配置管理controller层
 */
@Controller
@RequestMapping("device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("设备列表获取")
    public PageDataBase<Device> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Device> devicePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Device> deviceWrapper = new QueryWrapper<>();
//        if(!map.isEmpty()){
//            String useFlag = (String) map.get("useFlag");
//            if(StringUtils.isNotBlank(useFlag)) {
//                deviceWrapper.eq("USE_FLAG", useFlag);
//            }
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                deviceWrapper.and(wrapper -> wrapper.like("MACHINE_NAME", keys));//模糊查询拼接
//            }
//        }
        IPage<Device> devicePage = deviceService.page(new Page<>(page,limit),deviceWrapper);
        data.setTotal(devicePage.getTotal());
        data.setItems(devicePage.getRecords());
        devicePageData.setData(data);
        return devicePageData;
    }

    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用设备")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Device device=deviceService.getById(id);
        if(device == null){
            return ResponseEty.failure("设备不存在");
        }
        deviceService.lockDevice(device);
        return ResponseEty.success("操作成功");
    }


    /**
     * @Description:用户添加修改初始化信息(分组,身份等)
     * @Author: 巫恒强  @Date: 2019/10/23 12:54
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     *//*
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("init")
    @ResponseBody
    public ResponseEntity initUser(@RequestBody Map map){
        ResponseEntity responseEntity=new ResponseEntity();
        Identity ownIdentity=new Identity();
        User ownUser=new User();
        List<Role> ownRoleList=new ArrayList<>();
        if (map.size()>0){//编辑时获取已拥有的用户身份以及用户角色
            int deviceId= (int) map.get("deviceId");
            ownUser=userService.getById(deviceId);//已拥有的用户身份
            responseEntity.setAny("checkDeviceIdentity",String.valueOf(ownUser.getDeviceTypeId()));
            ownRoleList=roleService.getOwnRoleByDeviceId(deviceId);//已拥有的角色 list
        }
        List<Identity> identityList=identityService.list();//所有用户类型
        List<Role> roleList=roleService.list();//所有用户分组
        // 存放过滤结果的列表
        List<Role> resultRoleList = new ArrayList<>();
        List<Identity> resultIdentityList=new ArrayList<>();
        // 使用lambda表达式过滤出结果并放到result列表里
        resultRoleList = (List<Role>) roleList.stream()
                .filter((Role r) -> (1!=r.getRoleId()))
                .collect(Collectors.toList());    // 使用lambda表达式过滤出结果并放到result列表里，written by zhangchao
        resultIdentityList = (List<Identity>) identityList.stream()
                .filter((Identity ide) -> (1!=ide.getDeviceTypeId()))
                .collect(Collectors.toList());

        //组装,返回的用户分组
        CheckOptionsGroup[] arrayRole=new CheckOptionsGroup[resultRoleList.size()];
        for (int i = 0; i < resultRoleList.size(); i++) {
            CheckOptionsGroup checkOptionsGroup=new CheckOptionsGroup();
            Role role=new Role();
            role=resultRoleList.get(i);
            checkOptionsGroup.setLabel(role.getRoleName());
            checkOptionsGroup.setValue(String.valueOf(role.getRoleId()));
            if (ownRoleList.size()>0){
                Role ownRole=new Role();
                for (int j = 0; j <ownRoleList.size() ; j++) {
                    ownRole=ownRoleList.get(j);
                    if (ownRole.getRoleId()==role.getRoleId()){
                        checkOptionsGroup.setChecked(true);
                    }
                }
            }

//            checkOptionsGroup.setChecked();//编辑初始化的时候再判断
            arrayRole[i]=checkOptionsGroup;

        }
        //组装,返回的用户类型
        CheckOptionsType[] arrayIdentity=new CheckOptionsType[resultIdentityList.size()];
        for (int i = 0; i < resultIdentityList.size(); i++) {
            CheckOptionsType checkOptionsIdentity=new CheckOptionsType();
            Identity identity=new Identity();
            identity=resultIdentityList.get(i);
            checkOptionsIdentity.setLabel(identity.getDeviceTypeName());
            checkOptionsIdentity.setValue(String.valueOf(identity.getDeviceTypeId()));
            arrayIdentity[i]=checkOptionsIdentity;
        }
        responseEntity.setAny("identityList",resultIdentityList);
        responseEntity.setAny("roleList",resultRoleList);
        responseEntity.setAny("checkOptionsRole",arrayRole);
        responseEntity.setAny("checkOptionsIdentity",arrayIdentity);

        responseEntity.setSuccess(true);
        return responseEntity;
    }

    *//**
     * @Description:拼接roleString字段
     * @Author: 巫恒强  @Date: 2019/10/23 12:55
     * @Param: [user]
     * @Return: com.kexin.admin.entity.tables.User
     *//*
    private User checkGroupString(User user){
        int[] checkGroup=user.getCheckUserRole();
        StringBuffer str=new StringBuffer();
        for (int groupId:checkGroup) {
            Role role=new Role();
            role=roleService.getById(groupId);
            str.append(role.getRoleName());
            str.append(" ");
        }
        String groupString=new String(str);
        user.setRoleString(groupString);
        return user;
    }

    *//**
     * @Description:用户保存功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:55
     * @Param: [user]
     * @Return: com.kexin.common.util.ResponseEntity
     *//*
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("save")
    @ResponseBody
    public ResponseEntity save(@RequestBody User user){
        if (StringUtils.isEmpty(user.getDeviceCode())){
            return ResponseEntity.failure("用户编码不能为空");
        }if (StringUtils.isEmpty(user.getDeviceName())){
            return ResponseEntity.failure("用户名称不能为空");
        }if(StringUtils.isBlank(user.getDeviceLoginName())){
            return ResponseEntity.failure("账户名不能为空");
        }
//        if(StringUtils.isBlank(user.getDeviceLoginPass())){
//            return ResponseEntity.failure("账户密码不能为空");
//        }
        if (user.getDeviceTypeId()==0){
            return ResponseEntity.failure("请选择一个用户类别");
        }if(user.getCheckUserRole().length==0){
            return ResponseEntity.failure("请选择一个用户角色");
        }
        ResponseEntity responseEntity=new ResponseEntity();
        if (user.getDeviceId()==0){//新增
            Boolean saveTrue=userService.save(this.checkGroupString(user));
            if(saveTrue){//保存用户身份类别
                if (roleService.updateGrantRoleAndDevice(user.getDeviceId(),user.getCheckUserRole())){
                    return ResponseEntity.success("保存成功");
                }else{
                    return ResponseEntity.success("分组保存失败");
                }

            }else{
                return ResponseEntity.failure("保存失败");
            }
        }else{//修改
            User olduser=userService.getById(user.getDeviceId());
            user.setUseFlag(olduser.getUseFlag());//是否启用保留
            Boolean updateTrue=userService.updateById(this.checkGroupString(user));

            Boolean updateGrantRole=roleService.updateGrantRoleAndDevice(user.getDeviceId(),user.getCheckUserRole());//角色关系表修改
            if(updateTrue && updateGrantRole){
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }
    }

    *//**
     * @Description:用户删除功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:55
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     *//*
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("用户ID不能为空");
        }
        int id= (int) map.get("id");
        Integer deleteGrant=roleService.deleteGrantDeviceAndRole(id);//删除用户与角色之间的关系表数据
        if (deleteGrant>0){
            Boolean removeTrue=userService.removeById(id);
            if(removeTrue){//修改用户身份类别
                return ResponseEntity.success("删除成功");
            }else{
                return ResponseEntity.failure("删除失败");
            }
        }else{
            return ResponseEntity.failure("删除失败");
        }

    }

    *//**
     * @Description:用户禁止与启用功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:55
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     *//*
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("forbidden")
    @ResponseBody
    public ResponseEntity forbidden(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("用户ID不能为空");
        }
        int id= (int) map.get("id");
        return userService.forbiddenUser(id);//禁止或者启用
    }

    *//**
     * @Description:用户更改密码功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:56
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     *//*
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("changePassword")
    @ResponseBody
    public ResponseEntity changePassword(@RequestBody Map map){
        String originPass= (String) map.get("originPass");
        String newPass= (String) map.get("newPass");
        String checkPass= (String) map.get("checkPass");
        String userId= (String) map.get("userId");
        if(StringUtils.isBlank(userId)){
            return ResponseEntity.failure("token已失效,请重新登录");
        }
        if(StringUtils.isBlank(originPass)){
            return ResponseEntity.failure("旧密码不能为空");
        }
        if(StringUtils.isBlank(newPass)){
            return ResponseEntity.failure("新密码不能为空");
        }
        if(StringUtils.isBlank(checkPass)){
            return ResponseEntity.failure("确认密码不能为空");
        }
        if(!checkPass.equals(newPass)){
            return ResponseEntity.failure("确认密码与新密码不一致");
        }
        User user = userService.getById(userId);
        if(!user.getDeviceLoginPass().equals(originPass)){
            return ResponseEntity.failure("旧密码错误");
        }
        user.setDeviceLoginPass(newPass);
        userService.updateById(user);
        return ResponseEntity.success("更新密码成功");
    }

    *//**
     * @Description:用户基本信息查看功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:56
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     *//*
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("info")
    @ResponseBody
    public ResponseEntity getUserByToken(@RequestBody Map map){
        ResponseEntity responseEntity=new ResponseEntity();
        String userId= (String) map.get("userId");
        if(StringUtils.isEmpty(userId)){
            return ResponseEntity.failure("token已失效,请重新登录");
        }
        User user=userService.findUserById(Integer.parseInt(userId));
        responseEntity.setAny("user",user);
        responseEntity.setSuccess(true);
        return responseEntity;
    }

    *//**
     * @Description:用户基本信息修改保存功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:56
     * @Param: [user]
     * @Return: com.kexin.common.util.ResponseEntity
     *//*
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("dataSave")
    @ResponseBody
    public ResponseEntity userDataSave(@RequestBody User user){
        User qurreyUser=new User();
        if (user.getDeviceId()==0){
            return ResponseEntity.failure("token已失效,请重新登录");
        }
        if (StringUtils.isEmpty(user.getDeviceCode())){
            return ResponseEntity.failure("用户编号不能为空");
        }
        qurreyUser.setDeviceCode(user.getDeviceCode());
        if (userService.selectByMapCount(qurreyUser)>1){
            return ResponseEntity.failure("用户编号已被使用,请重新输入");
        }
        if (StringUtils.isEmpty(user.getDeviceName())){
            return ResponseEntity.failure("用户名称不能为空");
        }
        qurreyUser.setDeviceName(user.getDeviceName());
        if (userService.selectByMapCount(qurreyUser)>1){
            return ResponseEntity.failure("用户名称已被使用,请重新输入");
        }
        if (StringUtils.isEmpty(user.getDeviceLoginName())){
            return ResponseEntity.failure("账户名称不能为空");
        }
        qurreyUser.setDeviceLoginName(user.getDeviceLoginName());
        if (userService.selectByMapCount(qurreyUser)>1){
            return ResponseEntity.failure("账户名称已被使用,请重新输入");
        }
        Integer i=userService.updateUserinfo(user);
        if (i>0){
            return ResponseEntity.success("更新成功");
        }else{
            return ResponseEntity.failure("更新失败,请联系管理员修改");
        }
    }*/
}
