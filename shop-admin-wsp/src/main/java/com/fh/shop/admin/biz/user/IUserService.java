package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.UserPasswordParam;
import com.fh.shop.admin.param.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.vo.user.UserVo;
import com.itextpdf.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface IUserService {
    public void addUser(User user);

    DataTableResult userList(UserSearchParam userSearchParam);

    UserVo toUpdateUser(Integer id);

    void updateUser(User user);

    void deleteUser(Integer id);


    void deleteUserByIds(Integer[] ids);

    User login(User user);

    void updateUserTime(User userInfo);

    void updateUserLoginCount(User userInfo);

    List<UserVo> findUser(UserSearchParam userSearchParam);

    void exportPdf(UserSearchParam userSearchParam, HttpServletResponse response) throws IOException, DocumentException;

    void exportExcel(UserSearchParam userSearchParam, HttpServletResponse response);

    List<Wealth> findWealthByUserId(User user);

    void updateLock(Long id);

    ServerResponse updatePassword(UserPasswordParam userPasswordParam);

    ServerResponse resetPassword(UserPasswordParam userParam) throws Exception;

    ServerResponse resetPasswordByUserId(Long id);
}
