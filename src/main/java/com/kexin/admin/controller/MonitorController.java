package com.kexin.admin.controller;

import com.kexin.admin.entity.monitor.MonitorAssignPo;
import com.kexin.admin.entity.monitor.MonitorPub;
import com.kexin.admin.entity.monitor.MonitorSearch;
import com.kexin.admin.entity.monitor.MonitorView;
import com.kexin.admin.entity.tables.Facility;
import com.kexin.admin.entity.tables.User;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.admin.service.FacilityGroupService;
import com.kexin.admin.service.FacilityService;
import com.kexin.admin.service.UserService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 现场监控controller层
 */
/**
 * @Description:现场监控controller
 * @Author: 巫恒强  @Date: 2019/10/23 12:46
 * @Param:
 * @Return: 
 */
@Controller
@RequestMapping("monitor")
public class MonitorController {

    @Autowired
    FacilityGroupService facilityGroupService;

    @Autowired
    FacilityService facilityService;

    @Autowired
    UserService userService;




    /**
     * @Description:监控初始化信息
     * @Author: 巫恒强  @Date: 2019/10/23 12:46
     * @Param: [groupId]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("init")
    @ResponseBody
    private ResponseEntity initGet(@RequestParam(required = false ,name = "groupId") Integer groupId){
        ResponseEntity responseEntity=new ResponseEntity();
        List<CheckOptionsType> facilityGroupList=facilityGroupService.getFacilityGroupMontor();//获取所有分组
        responseEntity.setAny("facilityGroupList",facilityGroupList);//设备分组

        List<MonitorView> monitorViewList=new ArrayList<>();//所有设备的列表信息
        List<MonitorAssignPo> monitorAssignList=new ArrayList<>();//获取所有设备以及人员信息,之间的关联信息
        List<User> staffList=new ArrayList<>();//所有拥有员工角色的用户
        List<Facility> facilityList=new ArrayList<>();//所有设备的列表信息
        MonitorPub monitorPub=new MonitorPub();


        monitorViewList=facilityService.selectMonitorList(groupId);

        if(monitorViewList.size()==0){
            return ResponseEntity.failure("当前分组没有设备");
        }
        responseEntity.setAny("monitorViewList",monitorViewList);//所有设备的列表信息
        MonitorView monitorView=monitorViewList.get(0);//第一个设备信息
        responseEntity.setAny("monitorView",monitorView);//第一个设备信息
//            monitorAssignList=facilityService.selectMonitorPubList();//获取所有设备以及人员信息,之间的关联信息
        staffList=userService.selectStaffList(3);//获取所有拥有员工角色的用户
        responseEntity.setAny("staffList",staffList);//所有设备的列表信息
//            QueryWrapper<Facility> wrapper = new QueryWrapper<>();
//            wrapper.eq("GROUP_ID",groupId);
        if (null==groupId){
            facilityList=facilityService.list();//获取所有设备的list
        }else{
            facilityList=facilityService.listByGroupId(groupId);//根据分组id,得到该分组的设备
        }

        responseEntity.setAny("facilityList",facilityList);//所有设备的列表信息
        int total=facilityService.getTotal(groupId);
        monitorPub.setTotal(total);//总的设备数量
        monitorPub.setOnlineNum(facilityService.getOnline(groupId));//已上线的设备数量
        int  completedNum= (int) monitorViewList.stream().filter(m -> m.getReceiveNum() == m.getRecountNum()).count();//已完成的数量
        monitorPub.setCompletedNum(completedNum);//已完成的数量
        monitorPub.setUnfinishedNum(total-completedNum);//未完成的额数量
        responseEntity.setAny("monitorPub",monitorPub);//公用的设备信息

        responseEntity.setSuccess(true);
        return responseEntity;
    }

    /**
     * @Description:监控根据分组刷新控制台信息
     * @Author: 巫恒强  @Date: 2019/10/23 12:46
     * @Param: [machineId]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("refresh")
    @ResponseBody
    public ResponseEntity refresh(@RequestParam Integer machineId){
        if (machineId==null){
            return ResponseEntity.failure("设备id为空");
        }
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setAny("isShow",0);
        MonitorView monitorView=facilityService.selectMonitorByMachineId(machineId);
        if (monitorView!=null){
            if (monitorView.getOperatorId()==null){
                responseEntity.setMessage("该设备还没有分配员工");
                responseEntity.setAny("isShow",1);
                return responseEntity;
            }else{
                responseEntity.setSuccess(true);
                responseEntity.setAny("monitorView",monitorView);

                return responseEntity;
            }

        }
        return ResponseEntity.failure("获取失败");
    }

    /**
     * @Description:为控制台(机台)分配员工功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:47
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("add")
    @ResponseBody
    public ResponseEntity add(@RequestBody  Map map){
        Integer machineId= (Integer) map.get("machineId");
        Integer operatorId= (Integer) map.get("operatorId");
        if(machineId==null){
            return ResponseEntity.failure("请选择控制台");
        }
        if(operatorId==null){
            return ResponseEntity.failure("请选择用户");
        }
        Facility ownFacility=facilityService.getFacilityByOperatorId(operatorId);
        boolean ownUpdate=false;
        if (ownFacility!=null){
            ownFacility.setOperatorId(null);
            facilityService.saveOrUpdate(ownFacility);//更新已有的控制台分配情况,一个控制台只能一个用户操作
            ownUpdate=true;
        }
        Facility facility=facilityService.getById(machineId);
        facility.setOperatorId(operatorId);
        if (facilityService.saveOrUpdate(facility)){//更新现在分配的操作台信息
            if (ownUpdate){
                return ResponseEntity.success("更新成功,"+ownFacility.getMachineName()+"的操作台用户已更新,请重新分配");
            }else{
                return ResponseEntity.success("添加成功");
            }
        }else{
            return ResponseEntity.failure("操作失败");
        }
    }


    /**
     * @Description:按条件搜索功能
     * @Author: 巫恒强  @Date: 2019/10/22 16:12
     * @Param: []
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("search")
    @ResponseBody
    private ResponseEntity search(@RequestBody MonitorSearch monitorSearch){
        if (monitorSearch==null){
            return ResponseEntity.failure("服务器错误,无法搜索");
        }
        return facilityService.searchFacility(monitorSearch);
    }
}
