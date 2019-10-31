package com.fh.shop.admin.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.UserPasswordParam;
import com.fh.shop.admin.param.UserSearchParam;
import com.fh.shop.admin.po.user.User;

import java.util.List;
import java.util.Map;

public interface IUserMapper extends BaseMapper<User> {
    void addUser(User user);

    List<User> userList(UserSearchParam userSearchParam);

    User toUpdateUser(Integer id);

    void updateUser(User user);

    void deleteUser(Integer id);

    Long findUserByCount(UserSearchParam userSearchParam);

    void deleteUserByIds(Integer[] ids);

    void addUserOnRole(List<Map> list);

    Integer[] toUpdateRoleById(Integer id);

    List<String> roleListById(Long id);

    void deleteRoleByUserId(Long id);

    void deleteRoleByIds(Integer[] ids);

    User login(User user);

    void updateUserTime(User userInfo);

    void updateUserLoginCount(User userInfo);

    List<User> userListExport(UserSearchParam userSearchParam);

    void updateLock(Long id);

    User findUserByEmail(UserPasswordParam userParam);

    void resetPassword(User user);

    void updatePassword(UserPasswordParam userPasswordParam);
}
