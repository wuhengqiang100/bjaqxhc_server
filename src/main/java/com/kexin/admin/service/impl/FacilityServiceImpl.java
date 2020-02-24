package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.monitor.MonitorAssignPo;
import com.kexin.admin.entity.monitor.MonitorSearch;
import com.kexin.admin.entity.monitor.MonitorView;
import com.kexin.admin.entity.tables.Facility;
import com.kexin.admin.entity.tables.FacilityGroup;
import com.kexin.admin.entity.tables.User;
import com.kexin.admin.mapper.FacilityMapper;
import com.kexin.admin.mapper.UserMapper;
import com.kexin.admin.service.FacilityService;
import com.kexin.common.util.ResponseEntity;
import com.kexin.common.util.StringUtilSubstring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 设备service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FacilityServiceImpl extends ServiceImpl<FacilityMapper, Facility> implements FacilityService {


    @Override
    public IPage<Facility> selecFacilityPage(Page<Facility> page, Integer useFlag, String facilityName) {
        return baseMapper.selectPageFacility(page,useFlag,facilityName);
    }

    @Override
    public ResponseEntity forbiddenFacility(int facilityId) {
        Facility facility=baseMapper.selectById(facilityId);
        if (facility.getUseFlag()==0){
            facility.setUseFlag(1);
            Integer i=baseMapper.updateById(facility);
            if (i!=0){
                return ResponseEntity.success("启用设备成功");
            }else{
                return ResponseEntity.success("启用设备失败");
            }
        }else{
            facility.setUseFlag(0);
            Integer i=baseMapper.updateById(facility);
            if (i!=0){
                return ResponseEntity.success("禁止设备成功");
            }else{
                return ResponseEntity.success("禁止设备失败");
            }
        }
    }

    /**
     * 更新facility中的groupString字段
     * @param oldGroup
     * @param newGroup
     * @return
     */
    @Override
    public Boolean updateGroupString(FacilityGroup oldGroup, FacilityGroup newGroup) {
        try {
            List<Facility> facilityList=baseMapper.selectFacilityByGroupId(newGroup.getGroupId());
            for (Facility allFacility: facilityList) {
                String finalString=StringUtilSubstring.subStringReplace(allFacility.getGroupString(),oldGroup.getGroupName(),newGroup.getGroupName());
                allFacility.setGroupString(finalString);
                baseMapper.updateById(allFacility);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<MonitorView> selectMonitorList(Integer groupId) {
        return baseMapper.selectMonitorList(groupId);
    }

    @Override
    public List<Facility> listByGroupId(Integer groupId) {
        return baseMapper.listByGroupId(groupId);
    }

    @Override
    public MonitorView selectMonitorByMachineId(int machineId) {
        return baseMapper.selectMonitorByMachineId(machineId);
    }

    @Override
    public List<MonitorAssignPo> selectMonitorPubList() {
        return baseMapper.selectMonitorPubList();
    }

    @Override
    public Integer selectMaxOrder() {
        return baseMapper.selectMaxOrder();
    }

    @Override
    public Integer getTotal(Integer groupId) {
        return baseMapper.getTotal(groupId);
    }

    @Override
    public Integer getOnline(Integer groupId) {
        return baseMapper.getOnline(groupId);
    }

    @Override
    public Facility getFacilityByOperatorId(int operatorId) {
        return baseMapper.getFacilityByOperatorId(operatorId);
    }

    /**
     * @Description:获取没有分配给设备的 所有用户
     * @Author: 巫恒强  @Date: 2019/10/22 15:44
     * @Param: []
     * @Return: com.kexin.common.util.ResponseEntity
     */
    @Override
    public ResponseEntity getUnallocatedUser() {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setSuccess(true);
        return responseEntity.setAny("userList",baseMapper.getUnallocatedUser());
    }

    @Override
    public List<User> getUnallocatedUserList() {
        return baseMapper.getUnallocatedUser();
    }

    @Override
    public List<User> getUnallocatedAndCurrentUser(Integer operatorId) {
        return baseMapper.getUnallocatedAndCurrentUser(operatorId);
    }


    @Override
    public ResponseEntity searchFacility(MonitorSearch monitorSearch) {
        ResponseEntity responseEntity=new ResponseEntity();
        List<MonitorView> monitorViewList= baseMapper.searchFacility(monitorSearch);


        if (monitorViewList.size()==0){
            return ResponseEntity.failure("搜索无结果");
        }
        else{
            responseEntity.setSuccess(true);
            responseEntity.setMessage("搜索成功");
            responseEntity.setAny("monitorView",monitorViewList.get(0));//取第一个设备作为右侧展示的详细信息
            responseEntity.setAny("monitorViewList",monitorViewList);//获取控制台页面左侧的所有设备列表
            return responseEntity;
        }

    }

    @Override
    public ResponseEntity getOrderMax() {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setSuccess(true);
        return responseEntity.setAny("orderMax",baseMapper.selectMaxOrder());
    }
}
