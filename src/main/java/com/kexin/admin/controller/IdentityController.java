package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Identity;
import com.kexin.admin.service.IdentityService;
import com.kexin.common.base.PageData;
import com.kexin.common.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Description:用户身份(类型)controller
 * @Author: 巫恒强  @Date: 2019/10/23 12:44
 * @Param:
 * @Return: 
 */
@Controller
@RequestMapping("identity")
public class IdentityController {

    @Autowired
    IdentityService identityService;

    /**
     * @Description:用户身份数据表格list
     * @Author: 巫恒强  @Date: 2019/10/23 12:44
     * @Param: [page, limit, q]
     * @Return: com.kexin.common.base.PageData<com.kexin.admin.entity.tables.Identity>
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    public PageData<Identity> list(@RequestParam(value = "pi",defaultValue = "1")Integer page,
                               @RequestParam(value = "ps",defaultValue = "10")Integer limit,
                               @RequestParam(value = "q",required = false) String q
    ){
//        Map map = WebUtils.getParametersStartingWith(request, "q");
        PageData<Identity> identityPageData = new PageData<>();
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
        IPage<Identity> rolePage = identityService.selecIdentityPage(new Page<>(page,limit),3,q);

        identityPageData.setTotal(rolePage.getTotal());
        identityPageData.setResults(rolePage.getRecords());
        return identityPageData;
    }

    /**
     * @Description:用户身份保存功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:44
     * @Param: [identity]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("save")
    @ResponseBody
    public ResponseEntity save(@RequestBody Identity identity){
        ResponseEntity responseEntity=new ResponseEntity();
        if (identity.getOperatorTypeId()==0){//新增
            Boolean saveTrue=identityService.save(identity);
            if(saveTrue){//保存用户身份类别
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }else{//修改
            Identity identity1=identityService.getById(identity.getOperatorTypeId());//修改的时候不更新是否启用
            identity.setUseFlag(identity1.getUseFlag());
            Boolean updateTrue=identityService.updateById(identity);
            if(updateTrue){//修改用户身份类别
                return ResponseEntity.success("保存成功");
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }
    }

    /**
     * @Description:用户身份删除功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:45
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("身份类别id不能为空");
        }
        int id= (int) map.get("id");
        Boolean removeTrue=identityService.removeById(id);
        if(removeTrue){//修改用户身份类别
            return ResponseEntity.success("删除成功");
        }else{
            return ResponseEntity.failure("删除失败");
        }
    }

    /**
     * @Description:用户身份禁止启用功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:45
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("forbidden")
    @ResponseBody
    public ResponseEntity forbidden(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("身份类别id不能为空");
        }
        int id= (int) map.get("id");
        return identityService.forbiddenIdentity(id);//禁止或者启用
    }



}
