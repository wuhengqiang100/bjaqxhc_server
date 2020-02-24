package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.FacilityGroup;
import com.kexin.admin.service.FacilityGroupService;
import com.kexin.admin.service.FacilityService;
import com.kexin.common.base.PageData;
import com.kexin.common.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 设备类别 controller
 */
@Controller
@RequestMapping("facilityGroup")
public class FacilityGroupController {

    @Autowired
    FacilityService facilityService;

    @Autowired
    FacilityGroupService facilityGroupService;


    /**
     * @Description:设备分组数据表格list
     * @Author: 巫恒强  @Date: 2019/10/23 12:40
     * @Param: [page, limit, q]
     * @Return: com.kexin.common.base.PageData<com.kexin.admin.entity.tables.FacilityGroup>
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    public PageData<FacilityGroup> list(@RequestParam(value = "pi",defaultValue = "1")Integer page,
                               @RequestParam(value = "ps",defaultValue = "10")Integer limit,
                               @RequestParam(value = "q",required = false) String q
    ){
//        Map map = WebUtils.getParametersStartingWith(request, "q");
        PageData<FacilityGroup> facilityGroupPageData = new PageData<>();
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
        IPage<FacilityGroup> rolePage = facilityGroupService.selecFacilityGroupPage(new Page<>(page,limit),3,q);

        facilityGroupPageData.setTotal(rolePage.getTotal());
        facilityGroupPageData.setResults(rolePage.getRecords());
        return facilityGroupPageData;
    }

    /**
     * @Description:设备分组保存功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:40
     * @Param: [facilityGroup]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("save")
    @ResponseBody
    public ResponseEntity save(@RequestBody FacilityGroup facilityGroup){
        ResponseEntity responseEntity=new ResponseEntity();
        if (facilityGroup.getGroupId()==0){//新增
            Boolean saveTrue=facilityGroupService.save(facilityGroup);
            if(saveTrue){//保存
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }else{//修改

            FacilityGroup oldFacilityGroup1=facilityGroupService.getById(facilityGroup.getGroupId());
            facilityGroup.setUseFlag(oldFacilityGroup1.getUseFlag());
            Boolean updateGroupString=facilityService.updateGroupString(oldFacilityGroup1,facilityGroup);//更新groupString字段
            Boolean updateTrue=facilityGroupService.updateById(facilityGroup);
            if(updateTrue && updateGroupString){//修改
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }
    }

    /**
     * @Description:设备分组删除功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:41
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("设备分组id不能为空");
        }
        int id= (int) map.get("id");
        Boolean removeTrue= null;
        try {
            removeTrue = facilityGroupService.removeById(id);
        } catch (Exception e) {
            return ResponseEntity.failure("该分组下有设备不能删除");
        }
        if(removeTrue){//修改用户身份类别
            return ResponseEntity.success("删除成功");
        }else{
            return ResponseEntity.failure("删除失败");
        }
    }

    /**
     * @Description:设备禁止启用功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:41
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("forbidden")
    @ResponseBody
    public ResponseEntity forbidden(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("设备分组id不能为空");
        }
        int id= (int) map.get("id");
        return facilityGroupService.forbiddenFacilityGroup(id);//禁止或者启用
    }
}
