package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.area.IAreaMapper;
import com.fh.shop.admin.mapper.user.IUserMapper;
import com.fh.shop.admin.mapper.wealth.IWealthMapper;
import com.fh.shop.admin.param.UserPasswordParam;
import com.fh.shop.admin.param.UserSearchParam;
import com.fh.shop.admin.po.area.Area;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.Md5Util;
import com.fh.shop.admin.util.SystemConstant;
import com.fh.shop.admin.vo.user.UserVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IWealthMapper wealthMapper;
    @Autowired
    private IAreaMapper areaMapper;
    /**
     * 这是新增用户的方法
     * @param user
     */
    @Override
    public void addUser(User user) {
        //生成salt
        String salt = UUID.randomUUID().toString();
        user.setSalt(salt);
        //双重md5加密
        String md5Password = Md5Util.buildMd5Password(salt, user.getPassword());
        user.setPassword(md5Password);
        userMapper.addUser(user);
        //添加角色到用户角色中间表
        Integer[] roleIds = user.getRoleIds();
        if(roleIds!=null && roleIds.length>0){
            addUserRoleById(user, roleIds);
        }
    }

    private void addUserRoleById(User user, Integer[] roleIds) {
        List<Map> list = new ArrayList<>();
        for (int i =0;i<roleIds.length;i++){
            Map map = new HashMap();
            map.put("roleId",roleIds[i]);
            map.put("userId",user.getId());
            list.add(map);
            System.out.println(1);
        }
        userMapper.addUserOnRole(list);
    }

    /**
     * 查询数据库中的所有用户信息/条件查询
     * @return
     */
    @Override
    public DataTableResult userList(UserSearchParam userSearchParam) {
        //查询总条数
        Long count = userMapper.findUserByCount(userSearchParam);
        //查询当前页的数据
        System.out.println(1);
        List<User> userList = userMapper.userList(userSearchParam);
        //po转换vo用于展示
        List<UserVo> userVoList = buildUserVo(userList);
        //保存
        DataTableResult dataTableResult = new DataTableResult(userSearchParam.getDraw(),count,count,userVoList);
        return dataTableResult;
    }

    private List<UserVo> buildUserVo(List<User> userList) {
        List<UserVo> userVoList = new ArrayList<>();
        for (User userInfo : userList) {
            UserVo userVo = getUserVo(userInfo);
            //查询每个用户的所有角色名称集合
            List<String> roleNameList = userMapper.roleListById(userInfo.getId());
            //分割 拼接 拿到最后的展示用字符串
            if(roleNameList!=null && roleNameList.size()>0){
                //把集合里的数据用指定的分隔符给拼接成字符串
                String join = StringUtils.join(roleNameList, ",");
                //赋值给展示用的vo实体类对象
                userVo.setRoleNames(join);
            }else{
                userVo.setRoleNames("无角色");
            }
            //查询用户对应的地区
            setAreaNamebyVo(userInfo,userVo);
            //每次赋值完毕添加到集合里
            userVoList.add(userVo);
        }


        return userVoList;
    }

    private UserVo getUserVo(User userInfo) {
        System.out.println();
        UserVo userVo = new UserVo();
        userVo.setId(userInfo.getId());
        userVo.setAge(userInfo.getAge());
        userVo.setEmail(userInfo.getEmail());
        userVo.setPhone(userInfo.getPhone());
        userVo.setRealName(userInfo.getRealName());
        userVo.setSex(userInfo.getSex());
        userVo.setUserName(userInfo.getUserName());
        userVo.setPay(userInfo.getPay());
        userVo.setEntryTime(DateUtil.date2str(userInfo.getEntryTime(),DateUtil.Y_M_D));
        userVo.setHeadImagePath(userInfo.getHeadImagePath());
        userVo.setSalt(userInfo.getSalt());
        userVo.setUserLock(userInfo.getLoginCount()==SystemConstant.LOGINF_ERROR_COUNT);
        return userVo;
    }

    /**
     * 修改页面
     * @param
     * @return
     */
    @Override
    public UserVo toUpdateUser(Integer id) {
        User user = userMapper.toUpdateUser(id);
        //查询这个用户的所有角色
        Integer[] roleIdArray = userMapper.toUpdateRoleById(id);
        UserVo userVo = getUserVo(user);
        userVo.setRoleIds(roleIdArray);
        userVo.setPassword(user.getPassword());
        setAreaNamebyVo(user, userVo);
        return userVo;
    }

    void setAreaNamebyVo(User user, UserVo userVo) {
        //查询用户对应的地区
        Area area1 = areaMapper.selectById(user.getArea1());
        Area area2 = areaMapper.selectById(user.getArea2());
        Area area3 = areaMapper.selectById(user.getArea3());
        userVo.setAreaName(area1.getAreaName()+"-->"+area2.getAreaName()+"-->"+area3.getAreaName());
    }

    /**
     * 修改用户数据的方法
     * @param
     */
    @Override
    public void updateUser(User user) {
        //修改的时候加密
        if(StringUtils.isNotEmpty(user.getPassword())){
            String md5Password = Md5Util.buildMd5Password(user.getSalt(), user.getPassword());
            user.setPassword(md5Password);
        }
        userMapper.updateUser(user);
        //删除当前用户的所有角色
        System.out.println(1);
        userMapper.deleteRoleByUserId(user.getId());
        //重新保存当前用户的角色数据
        Integer[] roleIds = user.getRoleIds();
        if(roleIds!=null && roleIds.length>0){
            addUserRoleById(user, roleIds);
        }
    }

    /**
     * 这是删除单条数据的方法
     * @param id
     */
    @Override
    public void deleteUser(Integer id) {
        //根据id查询用户的图片名称
        User user = userMapper.toUpdateUser(id);
        //删除此用户对应的用户角色中间表的数据
        userMapper.deleteRoleByUserId(Long.valueOf(id));
        //删除用户
        userMapper.deleteUser(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void deleteUserByIds(Integer[] ids) {
        //删除用户
        userMapper.deleteUserByIds(ids);
        //删除用户对应的用户角色中间表数据
        userMapper.deleteRoleByIds(ids);
    }


    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public void updateUserTime(User userInfo) {
        userMapper.updateUserTime(userInfo);
    }

    @Override
    public void updateUserLoginCount(User userInfo) {
        userMapper.updateUserLoginCount(userInfo);
    }

    /**
     * 查询导出的数据
     * @param userSearchParam
     * @return
     */
    @Override
    public List<UserVo> findUser(UserSearchParam userSearchParam){
        List<User> userList = buildUserList(userSearchParam);
        //po转换vo用于展示
        List<UserVo> userVoList = buildUserVo(userList);
        return userVoList;
    }

    List<User> buildUserList(UserSearchParam userSearchParam) {
        Integer[] selectArea = userSearchParam.getSelectArea();
        if(selectArea[0]!=null && selectArea[0]>0){
            userSearchParam.setArea1(selectArea[0]);
            if(selectArea[1]!=null && selectArea[1]>0){
                userSearchParam.setArea2(selectArea[1]);
                if(selectArea[2]!=null && selectArea[2]>0){
                    userSearchParam.setArea3(selectArea[2]);
                }
            }
        }
        //查询数据
        return userMapper.userListExport(userSearchParam);
    }

    @Override
    public void exportPdf(UserSearchParam userSearchParam, HttpServletResponse response) throws IOException, DocumentException {
        if(userSearchParam.getRoleIds()!=null){
            userSearchParam.setRoleIdsLengTh(userSearchParam.getRoleIds().length);
        }
        //条件查询数据源
        List<UserVo> userList = findUser(userSearchParam);
        // 定义一个字节数组
        ByteArrayOutputStream byffer = new ByteArrayOutputStream();
        Font keyfont = FileUtil.keyFont();
        // 创建文本对象
        Document document = new Document(PageSize.A4);
        //pdf书写器
        PdfWriter.getInstance(document, byffer);
        //打开文档
        document.open();
        //表头
        String[] head = {"用户名", "真实姓名", "性别", "年龄", "电话", "邮箱", "薪资", "入职时间", "角色","头像"};
        //创建表格
        PdfPTable table = FileUtil.createTable(head.length);
        Font headfont = FileUtil.headFont();
        // 设置颜色
        headfont.setColor(BaseColor.RED);
        // 设置PDF表格标题
        PdfPCell cellTou = FileUtil.createHeadline("用户资料", headfont);
        // 设置段落之间的距离
        cellTou.setExtraParagraphSpace(20);
        table.addCell(cellTou);
        // 将表头添加到pdf文件中
        for (int i = 0; i < head.length; i++) {
            table.addCell(FileUtil.createCell(head[i], keyfont, Element.ALIGN_CENTER));
        }
        // 添加内容到pdf文件中
        for (int i = 0; i < userList.size(); i++) {
            table.addCell(FileUtil.createCell(userList.get(i).getUserName(), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell(userList.get(i).getRealName(), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell((userList.get(i).getSex() == 1 ? "男" : userList.get(i).getSex() == 0 ? "女" : "未知"), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell(userList.get(i).getAge().toString(), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell(userList.get(i).getPhone(), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell(userList.get(i).getEmail(), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell(userList.get(i).getPay().toString(), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell(userList.get(i).getEntryTime(), keyfont, Element.ALIGN_CENTER));
            table.addCell(FileUtil.createCell(userList.get(i).getRoleNames(), keyfont, Element.ALIGN_CENTER));
            //判断当前用户是否有头像
            if(StringUtils.isNotEmpty(userList.get(i).getHeadImagePath())){
                table.addCell(FileUtil.createCell("没有头像",keyfont,Element.ALIGN_CENTER));
            }else{
                table.addCell(FileUtil.createCell("没有头像",keyfont,Element.ALIGN_CENTER));
            }
        }
        document.add(table);
        document.close();
        FileUtil.pdfDownload(response, byffer);
        //下载完成后删除本地图片
        for (UserVo userVo : userList) {
            File file = new File("D:/upload/"+userVo.getHeadImagePath());
            file.delete();
        }
    }

    @Override
    public void exportExcel(UserSearchParam userSearchParam, HttpServletResponse response) {
        if(userSearchParam.getRoleIds()!=null){
            userSearchParam.setRoleIdsLengTh(userSearchParam.getRoleIds().length);
        }
        //条件查询数据源
        List<User> userList = buildUserList(userSearchParam);
        //设置表头以及导出的属性
        String[] herders = {"id","用户名","入职时间"};
        String[] propertys = {"id","userName","entryTime"};
        //转换为workbook 并且下载
        FileUtil.bulidExcel(userList,User.class,"用户信息",herders,propertys,response);
    }


    @Override
    public List<Wealth> findWealthByUserId(User user) {
        List<Wealth> wealthByUserId = wealthMapper.findWealthByUserId(user);
        return wealthByUserId;
    }

    @Override
    public void updateLock(Long id) {
        userMapper.updateLock(id);
    }

    @Override
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam) {
        //判断旧密码 新密码 确认密码 是否为空 有任意一个为空 返回提示
        if(StringUtils.isEmpty(userPasswordParam.getOldPassword())
                || StringUtils.isEmpty(userPasswordParam.getConPassword())
                || StringUtils.isEmpty(userPasswordParam.getNewPassword())){
            return ServerResponse.error(ResponseEnum.USER_PASSWORD_IS_NULL);
        }
        //判断确认密码和新密码是否一致  如果不一致 返回提示
        if(!userPasswordParam.getNewPassword().equals(userPasswordParam.getConPassword())){
            return ServerResponse.error(ResponseEnum.NEWPASSWORD_CONPASSWORD_IS_ERROR);
        }
        //通过id 查询 数据库中的 当前用户的 密码 和 盐
        User user = userMapper.toUpdateUser(userPasswordParam.getUserId().intValue());
        //获取盐
        String salt = user.getSalt();
        //给前台传过来的原密码加密
        String oldPassword = Md5Util.buildMd5Password(salt, userPasswordParam.getOldPassword());
        //判断原密码是否和数据库中的一致  如果不一致 返回提示
        if(!user.getPassword().equals(oldPassword)){
            return ServerResponse.error(ResponseEnum.OLDPASSWORD_IS_ERROR);
        }
        //全部正确 给新密码 加密
        userPasswordParam.setNewPassword(Md5Util.buildMd5Password(salt,userPasswordParam.getNewPassword()));
        //修改
        userMapper.updatePassword(userPasswordParam);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse resetPassword(UserPasswordParam userParam) throws Exception {
        //通过前台输入的邮箱 判断是否存在这个用户
        User user = userMapper.findUserByEmail(userParam);
        if(user!=null){
            if(user.getUserName().equals(userParam.getUserName())){
                //用户存在  并且用户名相同 重置密码
                String str = RandomStringUtils.randomAlphanumeric(6);
                user.setPassword(Md5Util.buildMd5Password(user.getSalt(),str));
                userMapper.resetPassword(user);
                //用户存在给此用户发送重置后的密码
                FileUtil.FaYouJian(user.getEmail(),"您的新密码为:"+str,user.getUserName());
                return ServerResponse.success(ResponseEnum.FORGET_PASSWORD);
            }else{
                return ServerResponse.error(ResponseEnum.USERNAME_ERROR);
            }
        }else{
            return ServerResponse.error(ResponseEnum.FORGET_PASSWORD_ERROR);
        }
    }

    @Override
    public ServerResponse resetPasswordByUserId(Long id) {
        //查询当前用户的信息
        User user = userMapper.toUpdateUser(id.intValue());
        if(user!=null){
            user.setPassword(Md5Util.buildMd5Password(user.getSalt(),SystemConstant.USER_PASSWORD_RESET));
            userMapper.resetPassword(user);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        }
        return ServerResponse.error(ResponseEnum.USER_IS_NULL);
    }
}
