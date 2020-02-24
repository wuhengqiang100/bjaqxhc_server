package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.FacilityType;
import com.kexin.admin.service.FacilityTypeService;
import com.kexin.common.base.PageData;
import com.kexin.common.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Description:设备类别controller
 * @Author: 巫恒强  @Date: 2019/10/23 12:43
 * @Param:
 * @Return: 
 */
@Controller
@RequestMapping("facilityType")
public class FacilityTypeController {

    @Autowired
    FacilityTypeService facilityTypeService;


    /**
     * @Description:设备类型数据表格list
     * @Author: 巫恒强  @Date: 2019/10/23 12:42
     * @Param: [page, limit, q]
     * @Return: com.kexin.common.base.PageData<com.kexin.admin.entity.tables.FacilityType>
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    public PageData<FacilityType> list(@RequestParam(value = "pi",defaultValue = "1")Integer page,
                               @RequestParam(value = "ps",defaultValue = "10")Integer limit,
                               @RequestParam(value = "q",required = false) String q
    ){
//        Map map = WebUtils.getParametersStartingWith(request, "q");
        PageData<FacilityType> facilityPageData = new PageData<>();
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
        IPage<FacilityType> rolePage = facilityTypeService.selecFacilityTypePage(new Page<>(page,limit),3,q);

        facilityPageData.setTotal(rolePage.getTotal());
        facilityPageData.setResults(rolePage.getRecords());
        return facilityPageData;
    }

    /**
     * @Description:设备类型保存功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:42
     * @Param: [facilityType]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("save")
    @ResponseBody
    public ResponseEntity save(@RequestBody FacilityType facilityType){
        ResponseEntity responseEntity=new ResponseEntity();
        if (facilityType.getMachineTypeId()==0){//新增
            Boolean saveTrue=facilityTypeService.save(facilityType);
            if(saveTrue){//保存用户身份类别
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }else{//修改
            FacilityType facilityType1=facilityTypeService.getById(facilityType.getMachineTypeId());//修改的时候不更新是否启用
            facilityType.setUseFlag(facilityType1.getUseFlag());
            Boolean updateTrue=facilityTypeService.updateById(facilityType);
            if(updateTrue){//修改用户身份类别
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }
    }

    /**
     * @Description:设备类别删除功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:43
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("设备类别id不能为空");
        }
        int id= (int) map.get("id");
        Boolean removeTrue= null;
        try {
            removeTrue = facilityTypeService.removeById(id);
        } catch (Exception e) {
            return ResponseEntity.failure("该设备类别下有设备,不能删除");
        }
        if(removeTrue){//修改用户身份类别
            return ResponseEntity.success("删除成功");
        }else{
            return ResponseEntity.failure("删除失败");
        }
    }

    /**
     * @Description:设备禁止启用功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:43
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("forbidden")
    @ResponseBody
    public ResponseEntity forbidden(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("设备类别id不能为空");
        }
        int id= (int) map.get("id");
        return facilityTypeService.forbiddenFacilityType(id);//禁止或者启用
    }
}
