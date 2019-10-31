package com.fh.shop.admin.biz.role;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.role.IRoleMapper;
import com.fh.shop.admin.po.role.Role;
import com.fh.shop.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class IRoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleMapper roleMapper;


    /**
     * 这是查询当前页数据的方法
     * @param role
     * @return
     */
    @Override
    public DataTableResult findRoleByList(Role role) {
        Map map = new HashMap();
        /*查询总条数*/
        Long count  = roleMapper.findRoleByCount();
        System.out.println();
        /*查询当前页的数据*/
        List<Role> roleList = roleMapper.findRoleByList(role);
        for (Role roleInfo : roleList) {
            List<String> wealthNameList = roleMapper.toRoleOnWealthByName(roleInfo.getId());
            String join = StringUtils.join(wealthNameList, ",");
            roleInfo.setWealthNames(join);
        }
        DataTableResult dataTableResult = new DataTableResult(role.getDraw(),count,count,roleList);
        return dataTableResult;
    }

    /**
     * 添加角色
     * @param role
     */
    @Override
    public void addRole(Role role) {
        roleMapper.addRole(role);
        //添加角色的后添加角色对应的资源到角色资源表
        Integer[] ids = role.getWealthIds();
        addRoleWealth(role, ids);
    }

    private void addRoleWealth(Role role, Integer[] ids) {
        List<Map> list = new ArrayList<>();
        if(ids.length>0){
            for (Integer id : ids) {
                System.out.println();
                Map map = new HashMap();
                map.put("roleId",role.getId());
                map.put("wealthId",id);
                list.add(map);
            }
            roleMapper.addRoleOnWealth(list);
        }
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);
        //删除角色对应的 角色资源中间表的数据
        roleMapper.deleteRoleOnWealth(role.getId());
        //重新添加
        Integer[] ids = role.getWealthIds();
        addRoleWealth(role, ids);
    }

    /**
     * 回显角色信息
     * @param id
     * @return
     */
    @Override
    public Role toUpdateRole(Integer id) {
        Role role = roleMapper.toUpdateRole(id);
        Integer[] ids = roleMapper.toRoleOnWealth(id);
        role.setWealthIds(ids);
        return role;
    }

    /**
     * 删除角色的方法
     * @param id
     */
    @Override
    public void deleteRole(Integer id) {
        //查询当前角色是否被使用
        //List<String> list = roleMapper.findUserRoleByRoleId(id);
        roleMapper.deleteRole(id);
        //删除角色对应的 角色资源中间表的数据
        int i = id;
        long q = i;
        roleMapper.deleteRoleOnWealth(q);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Role> findRoleCheckbox() {
        String roleListJson = RedisUtil.get("roleList");
        if(StringUtils.isNotEmpty(roleListJson)){
            //将json字符串 转换为 java对象
            List<Role> roles = JSONObject.parseArray(roleListJson, Role.class);
            return roles;
        }
        //如果redis中不存在该数据 则查询数据库
        List<Role> roleCheckbox = roleMapper.findRoleCheckbox();
        //将java对象转换为json格式的字符串
        String jsonString = JSONObject.toJSONString(roleCheckbox);
        //保存到redis缓存服务器中
        RedisUtil.setEx("roleList",jsonString,60);
        return roleCheckbox;
    }
}
