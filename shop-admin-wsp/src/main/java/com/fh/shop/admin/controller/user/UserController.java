package com.fh.shop.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.biz.user.IUserService;
import com.fh.shop.admin.biz.wealth.IWealthService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.UserPasswordParam;
import com.fh.shop.admin.param.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.*;
import com.fh.shop.admin.vo.user.UserVo;
import com.itextpdf.text.DocumentException;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@Component
public class UserController {
    @Resource(name = "userService")
    private IUserService userService;
    @Autowired
    HttpServletRequest request;
    @Resource(name = "wealthService")
    private IWealthService wealthService;
    @Autowired
    HttpServletResponse response;

    /**
     * 这是新增用户的方法
     *
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse add(User user) {
        userService.addUser(user);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 查询用户表的所有数据/条件查询(查询条件)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/userList")
    public ServerResponse userList(UserSearchParam userSearchParam) {
        DataTableResult dataTableResult = userService.userList(userSearchParam);
        return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
    }

    /**
     * 这是跳转查询页面的方法
     *
     * @return
     */
    @RequestMapping("/toList")
    public String toUserList() {
        return "/user/userList";
    }

    /**
     * 回显
     *
     * @return
     */
    @RequestMapping(value = "/toUpdateUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse toUpdateUser(Integer id) {
        UserVo user = null;
        user = userService.toUpdateUser(id);
        return ServerResponse.success(ResponseEnum.SUCCESS, user);
    }

    /**
     * 这是修改用户资料的方法
     *
     * @return
     */
    @RequestMapping(value = "/updateUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse updateUser(User user) {
        userService.updateUser(user);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 删除单条数据的方法
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse deleteUser(Integer id) {
        userService.deleteUser(id);
        return ServerResponse.success(ResponseEnum.SUCCESS);

    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteUserByIds")
    @ResponseBody
    public ServerResponse deleteUserByIds(Integer[] ids) {
        userService.deleteUserByIds(ids);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    public User userTimt = new User();

    /**
     * 登录验证
     *
     * @param user
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(User user) throws Exception {
        //根据用户名查询对象
        User userInfo = userService.login(user);
        //验证用户名或密码或验证码是否为空
        if (!StringUtils.isNotEmpty(user.getUserName()) || !StringUtils.isNotEmpty(user.getPassword()) || !StringUtils.isNotEmpty(user.getImgCode())) {
            return ServerResponse.error(ResponseEnum.USERNAME_PASSWORD_IS_ERROR);
        }
        //获取sesssionId
        String sessionId = CookieUtil.read(SystemConstant.SESSIONId_NAME, request);
        //获取验证码
        String checkcode = RedisUtil.get(KeyUtil.buildCodeKey(sessionId));
        //判断验证码是否正确
        if(!user.getImgCode().equalsIgnoreCase(checkcode)){
            return ServerResponse.error(ResponseEnum.CODE_ERROR);
        }
        //验证用户名是否存在
        if (userInfo == null) {
            return ServerResponse.error(ResponseEnum.USERNAME_ERROR);
        }
        //获取当天时间
        String today = DateUtil.date2str(new Date(), DateUtil.Y_M_D);
        //获取当前用户的上次错误登录时间
        String errorDate = DateUtil.date2str(userInfo.getLoginErrorTime(), DateUtil.Y_M_D);
        //连续登陆错误次数大于等于3次同时是当天 则 锁定当前用户 返回提示 当前用户已被锁定
        if (userInfo.getLoginCount() == SystemConstant.LOGINF_ERROR_COUNT && errorDate.equals(today)) {
            return ServerResponse.error(ResponseEnum.USER_ERROR);
        }
        //验证密码是否正确
        String salt = userInfo.getSalt();
        String md5Password = Md5Util.buildMd5Password(salt, user.getPassword());
        if (!userInfo.getPassword().equals(md5Password)) {
            //判断当前账户的错误登录时间是否为null 如果是 设置为1
            if (userInfo.getLoginErrorTime() == null) {
                userInfo.setLoginCount(1);
                //设置错误登录时间为当前时间
                userInfo.setLoginErrorTime(new Date());
            }else{
                //判断是否为同一天连续登录错误  如果是同一天  连续登陆次数+1
                if(errorDate.equals(today)){
                    userInfo.setLoginCount(userInfo.getLoginCount() + 1);
                    userInfo.setLoginErrorTime(new Date());
                    if(userInfo.getLoginCount()==3){
                        FileUtil.FaYouJian(userInfo.getEmail(),"您的用户已被锁定,如不是本人操作请修改密码或联系管理员",userInfo.getUserName());
                    }
                }else{
                    //如果不在同一天  重置为1
                    userInfo.setLoginCount(1);
                    userInfo.setLoginErrorTime(new Date());
                }
            }
            //修改连续登陆次数和错误登录时间
            userService.updateUserLoginCount(userInfo);
            return ServerResponse.error(ResponseEnum.PASSWORD_ERROR);
        } else {
            userInfo.setLoginCount(0);
            //密码正确  清空连续登陆次数
            userService.updateUserLoginCount(userInfo);
        }
        //获取当前用户的上次登录时间  转换成字符串  同时工具类里做了非空判断
        String newDate = DateUtil.date2str(userInfo.getLoginTime(), DateUtil.Y_M_D);
        //登录成功 保存当天登录次数
        if (!today.equals(newDate)) {
            //如果是今天第一次登录  保存为1
            userInfo.setCount(1);
        } else {
            //如果是当天登录 在原有基础上+1
            userInfo.setCount(userInfo.getCount() + 1);
        }
        //登录成功 把当前登录的用户放入Redis中
        //request.getSession().setAttribute(SystemConstant.USER, userInfo);
        //转换为json字符串
        String userInfoJson = JSONObject.toJSONString(userInfo);
        RedisUtil.setEx(KeyUtil.buildUserKey(sessionId),userInfoJson,SystemConstant.USER_EXPIRE);
        //把用户对应的资源放入session中
        List<Wealth> wealthVoList = wealthService.findWealthByUserId();
        //request.getSession().setAttribute(SystemConstant.MANU_USER,wealthVoList);
        String wealthVoListJson = JSONObject.toJSONString(wealthVoList);
        RedisUtil.set(KeyUtil.buildUserWealth(sessionId),wealthVoListJson);
        //把所有的资源放入session
        List<Wealth> wealthListUrl = wealthService.findWealthUrl();
        //request.getSession().setAttribute(SystemConstant.MANULIST,wealthListUrl);
        String wealthListUrlJson = JSONObject.toJSONString(wealthListUrl);
        RedisUtil.set(KeyUtil.buildAllWealth(sessionId),wealthListUrlJson);
        //更新数据
        userService.updateUserTime(userInfo);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }



    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        String sessionId = DistributedSession.getSessionId(request, response);
        RedisUtil.del(KeyUtil.buildUserKey(sessionId),KeyUtil.buildUserWealth(sessionId),KeyUtil.buildAllWealth(sessionId));
        return "redirect:/";
    }

    /**
     * 定时器
     */
    @Scheduled(cron = "0 0 0 * * ?")//秒、分、时、日、月、周、年（可选）
    public void time() {
        System.out.println("进了");
    }

    /**
     * 定时解锁同时刷新连续登陆失败次数
     */
    /*public void updateTime() {
        userTimt.setLoginCount(0);
        userTimt.setLockState(2);
        userService.updateUserLoginCount(userTimt);
        System.out.println("=========================");
    }*/

    /**
     * 导出pdf
     *
     * @param userSearchParam
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(UserSearchParam userSearchParam, HttpServletResponse response) throws IOException, DocumentException {
        userService.exportPdf(userSearchParam,response);
    }

    /**
     * 导出excel
     *
     * @param userSearchParam
     * @param response
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(UserSearchParam userSearchParam, HttpServletResponse response) {
        userService.exportExcel(userSearchParam,response);
    }

    /**
     * 导出word
     */
    @RequestMapping("/exportWord")
    public void exportWord(UserSearchParam userSearchParam,HttpServletResponse response) throws IOException, TemplateException {
        if(userSearchParam.getRoleIds()!=null){
            userSearchParam.setRoleIdsLengTh(userSearchParam.getRoleIds().length);
        }
        //条件查询数据源
        List<UserVo> userList = userService.findUser(userSearchParam);
        //处理性别
        for (UserVo userVo : userList) {
            userVo.setSexNmae((userVo.getSex() == 1 ? "男" : userVo.getSex() == 0 ? "女" : "未知"));
        }
        Map map = new HashMap();
        map.put("list",userList);
        //调用下载
        FileUtil.wordDownload(map,request,response);
    }

    /**
     * 验证当前用户名是否重复
     * @return
     */
    @RequestMapping("/loginUserName")
    @ResponseBody
    public Map loginUserName(User user){
        Map map = new HashMap();
        User login = userService.login(user);
        //通过id 查询当前用户名 是否为原本的名称
        UserVo userVo = userService.toUpdateUser(user.getId().intValue());
        if(null!=login && !user.getUserName().equals(userVo.getUserName())){
            map.put("valid",false);
        }else{
            map.put("valid",true);
        }
        return map;
    }

    /**
     * 解锁
     * @param id
     * @return
     */
    @RequestMapping("/updateLock")
    @ResponseBody
    public ServerResponse updateLock(Long id){
        userService.updateLock(id);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("/toUpdatepassword")
    public String toUpdatepassword(){
        return "/user/password";
    }

    /**
     * 修改密码
     * @param userPasswordParam
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    @Log("修改密码")
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam){
        return userService.updatePassword(userPasswordParam);
    }

    /**
     * 通过邮箱修改密码
     * @return
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    @Log("忘记密码")
    public ServerResponse resetPassword(UserPasswordParam userParam) throws Exception {
        ServerResponse serverResponse = userService.resetPassword(userParam);
        return serverResponse;
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @RequestMapping("/resetPasswordByUserId")
    @ResponseBody
    @Log("重置密码")
    public ServerResponse resetPasswordByUserId(Long id){
        return userService.resetPasswordByUserId(id);
    }

}
