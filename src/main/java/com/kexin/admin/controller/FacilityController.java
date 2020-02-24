package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.CheckOptionsGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.admin.service.FacilityGroupService;
import com.kexin.admin.service.FacilityService;
import com.kexin.admin.service.FacilityTypeService;
import com.kexin.common.base.PageData;
import com.kexin.common.util.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设备管理Controller层
 */
@Controller
@RequestMapping("facility")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @Autowired
    FacilityTypeService facilityTypeService;

    @Autowired
    FacilityGroupService facilityGroupService;

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    public PageData<Facility> list(@RequestParam(value = "pi",defaultValue = "1")Integer page,
                                   @RequestParam(value = "ps",defaultValue = "10")Integer limit,
                                   @RequestParam(value = "q",required = false) String q
    ){
//        Map map = WebUtils.getParametersStartingWith(request, "q");
        PageData<Facility> facilityPageData = new PageData<>();
/*        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("USE_FLAG",1);
        if(StringUtils.isNotBlank(q)){
            roleWrapper.eq("ROLE_NAME",q);
            roleWrapper.like("ROLE_NAME", q);
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                roleWrapper.like("name", keys);
//            }
        }*/
        IPage<Facility> facilityPage = facilityService.selecFacilityPage(new Page<>(page,limit),3,q);

        facilityPageData.setTotal(facilityPage.getTotal());
        facilityPageData.setResults(facilityPage.getRecords());
        return facilityPageData;
    }


    @RequestMapping(value = "/init",method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity initFacilityOption(@RequestBody Map map){
        return ResponseEntity.success("success");
    }
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("init")
    @ResponseBody
    public ResponseEntity initFacility(@RequestBody Map map){
        ResponseEntity responseEntity=new ResponseEntity();
        FacilityType ownFacilityType=new FacilityType();
        Facility ownFaclity=new Facility();
        List<FacilityGroup> ownFacilityGroupList=new ArrayList<>();
        if (map.size()>0){//编辑时获取已拥有的设备类型以及设备分组
            int machineId= (int) map.get("machineId");
            ownFaclity=facilityService.getById(machineId);//已拥有的设备类型
            responseEntity.setAny("checkMachineType",String.valueOf(ownFaclity.getMachineTypeId()));
            ownFacilityGroupList=facilityGroupService.getOwnFacilityByMachineId(machineId);//已拥有的分组类型 list
        }
        List<FacilityType> facilityTypeList=facilityTypeService.list();//所有设备类型
        List<FacilityGroup> facilityGroupList=facilityGroupService.list();//所有设备分组
        //组装,返回的设备分组
        CheckOptionsGroup [] arrayGroup=new CheckOptionsGroup[facilityGroupList.size()];
        for (int i = 0; i < facilityGroupList.size(); i++) {
            CheckOptionsGroup checkOptionsGroup=new CheckOptionsGroup();
            FacilityGroup facilityGroup=new FacilityGroup();
            facilityGroup=facilityGroupList.get(i);
            checkOptionsGroup.setLabel(facilityGroup.getGroupName());
            checkOptionsGroup.setValue(String.valueOf(facilityGroup.getGroupId()));
            if (ownFacilityGroupList.size()>0){
                FacilityGroup ownFacilityGroup=new FacilityGroup();
                for (int j = 0; j <ownFacilityGroupList.size() ; j++) {
                    ownFacilityGroup=ownFacilityGroupList.get(j);
                    if (ownFacilityGroup.getGroupId()==facilityGroup.getGroupId()){
                        checkOptionsGroup.setChecked(true);
                    }
                }
            }

//            checkOptionsGroup.setChecked();//编辑初始化的时候再判断
            arrayGroup[i]=checkOptionsGroup;

        }
        //组装,返回的设备类型
        CheckOptionsType[] arrayType=new CheckOptionsType[facilityTypeList.size()];
        for (int i = 0; i < facilityTypeList.size(); i++) {
            CheckOptionsType checkOptionsType=new CheckOptionsType();
            FacilityType facilityType=new FacilityType();
            facilityType=facilityTypeList.get(i);
            checkOptionsType.setLabel(facilityType.getMachineTypeName());
            checkOptionsType.setValue(String.valueOf(facilityType.getMachineTypeId()));
            arrayType[i]=checkOptionsType;
        }
        responseEntity.setAny("facilityTypeList",facilityTypeList);
        responseEntity.setAny("facilityGroupList",facilityGroupList);
        responseEntity.setAny("checkOptionsGroup",arrayGroup);
        responseEntity.setAny("checkOptionsType",arrayType);
        responseEntity.setAny("orderMax",facilityService.selectMaxOrder());//设备排序的最大值
        if (map.size()==2){
            Integer operatorId= (Integer) map.get("operatorId");
            responseEntity.setAny("userList",facilityService.getUnallocatedAndCurrentUser(operatorId));//设备排序的最大值
        }else{
            responseEntity.setAny("userList",facilityService.getUnallocatedUserList());
        }
        responseEntity.setSuccess(true);
        return responseEntity;
    }

    private Facility checkGroupString(Facility facility){
        int[] checkGroup=facility.getCheckMachineGroup();
        StringBuffer str=new StringBuffer();
        for (int groupId:checkGroup) {
            FacilityGroup facilityGroup=new FacilityGroup();
            facilityGroup=facilityGroupService.getById(groupId);
            str.append(facilityGroup.getGroupName());
            str.append(" ");
        }
        String groupString=new String(str);
        facility.setGroupString(groupString);
        return facility;
    }


    /**
     * @Description:保存设备操作
     * @Author: 巫恒强  @Date: 2019/10/23 11:50
     * @Param: [facility]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("save")
    @ResponseBody
    public ResponseEntity save(@RequestBody Facility facility){
        if (StringUtils.isEmpty(facility.getMachineCode())){
            return ResponseEntity.failure("设备编码不能为空");
        }if (StringUtils.isEmpty(facility.getMachineName())){
            return ResponseEntity.failure("设备名称不能为空");
        }if (facility.getMachineTypeId()==0){
            return ResponseEntity.failure("请选择一个设备类别");
        }
        ResponseEntity responseEntity=new ResponseEntity();
        if (facility.getMachineId()==0){//新增
            Boolean saveTrue=facilityService.save(this.checkGroupString(facility));
            if(saveTrue){//保存用户身份类别
                if (facilityGroupService.updateGrantGroupAndMachines(facility.getMachineId(),facility.getCheckMachineGroup())){
                    return ResponseEntity.success("保存成功");
                }else{
                    return ResponseEntity.success("分组保存失败");
                }

            }else{
                return ResponseEntity.failure("保存失败");
            }
        }else{//修改

            facility.setUseFlag(facilityService.getById(facility.getMachineId()).getUseFlag());//是否启用保留
            Boolean updateTrue=facilityService.updateById(this.checkGroupString(facility));
            if(facilityGroupService.updateGrantGroupAndMachines(facility.getMachineId(),facility.getCheckMachineGroup())){//修改设备分组
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }
    }

    /**
     * @Description:设备删除操作
     * @Author: 巫恒强  @Date: 2019/10/23 12:39
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("设备ID不能为空");
        }
        int id= (int) map.get("id");
        Integer deleteGrant=facilityGroupService.deleteGrantMachineAndGroup(id);//删除设备与分组之间的关系表数据
        if (deleteGrant>0){
            Boolean removeTrue=facilityService.removeById(id);
            if(removeTrue){//修改用户身份类别
                return ResponseEntity.success("删除成功");
            }else{
                return ResponseEntity.failure("删除失败");
            }
        }else{
            return ResponseEntity.failure("删除失败");
        }

    }
    /**
     * @Description:获取没有分配给设备的所有用户
     * @Author: 巫恒强  @Date: 2019/10/22 15:41
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("UnallocatedUser")
    @ResponseBody
    public ResponseEntity UnallocatedUser(@RequestBody Map map){
        return facilityService.getUnallocatedUser();
    }

    /**
     * @Description:禁止与启用设备操作
     * @Author: 巫恒强  @Date: 2019/10/23 11:50
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("forbidden")
    @ResponseBody
    public ResponseEntity forbidden(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("设备ID不能为空");
        }
        int id= (int) map.get("id");
        return facilityService.forbiddenFacility(id);//禁止或者启用
    }


    /**
     * @Description:获取排序序号最大的值
     * @Author: 巫恒强  @Date: 2019/10/23 11:31
     * @Param: []
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("orderMax")
    @ResponseBody
    public ResponseEntity orderMax(){

        return facilityService.getOrderMax();
    }

}
