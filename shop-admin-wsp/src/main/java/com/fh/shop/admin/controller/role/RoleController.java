package com.fh.shop.admin.controller.role;

import com.fh.shop.admin.biz.role.IRoleService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.role.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource(name="roleService")
    private IRoleService roleService;

    /**
     * 跳转查询角色页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "/role/roleList";
    }

    /**
     * 这是查询角色信息的方法
     * @return
     */
    @RequestMapping("/findRoleByList")
    @ResponseBody
    public ServerResponse findRoleByList(Role role){
        DataTableResult roleByList = roleService.findRoleByList(role);
        return ServerResponse.success(ResponseEnum.SUCCESS,roleByList);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public ServerResponse addRole(Role role){
        roleService.addRole(role);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 回显角色信息
     * @return
     */
    @RequestMapping("/toUpdateRole")
    @ResponseBody
    public ServerResponse toUpdateRole(Integer id){
        Role role = roleService.toUpdateRole(id);
        return ServerResponse.success(ResponseEnum.SUCCESS,role);
    }

    /**
     * 修改角色
     * @return
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public ServerResponse updateRole(Role role){
        roleService.updateRole(role);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 删除单个角色的方法
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public ServerResponse deleteRole(Integer id){
        roleService.deleteRole(id);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findRoleCheckbox")
    @ResponseBody
    public List<Role> findRoleCheckbox(){
        List<Role> list = roleService.findRoleCheckbox();
        return list;
    }
}
