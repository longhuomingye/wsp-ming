package com.fh.shop.admin.biz.wealth;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.mapper.wealth.IWealthMapper;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.CookieUtil;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.util.SystemConstant;
import com.fh.shop.admin.vo.wealth.WealthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service("wealthService")
public class IWealthServiceImpl implements IWealthService {

    @Autowired
    private IWealthMapper wealthMapper;
    @Autowired(required = false)
    private HttpServletRequest request;
    /**
     * 查询菜单
     * @return
     */
    @Override
    public List<WealthVo> findZtreeList() {
        List<Wealth> list = wealthMapper.findZtreeList();
        //po转换vo
        List<WealthVo> listVo = getWealthVos(list);
        return listVo;
    }

    private List<WealthVo> getWealthVos(List<Wealth> list) {
        List<WealthVo> listVo = new ArrayList<>();
        for (Wealth wealth : list) {
            System.out.println();
            WealthVo wealthVo = new WealthVo();
            wealthVo.setId(wealth.getId());
            wealthVo.setName(wealth.getMemuName());
            wealthVo.setpId(wealth.getFatherId());
            wealthVo.setMemuType(wealth.getMemuType());
            wealthVo.setUrl(wealth.getUrl());
            listVo.add(wealthVo);
        }
        return listVo;
    }

    @Override
    public void add(Wealth wealth) {
        wealthMapper.add(wealth);
    }

    @Override
    public void deleteZtree(Integer[] ids) {
        wealthMapper.deleteZtree(ids);
        //删除节点的同时删除对应的角色资源中间表数据
        wealthMapper.deleteWealthByIds(ids);
    }

    @Override
    public void updateZtree(Wealth wealth) {
        wealthMapper.updateZtree(wealth);
    }

    @Override
    public List<Wealth> findWealthByUserId() {
        //获取sesssionId
        String sessionId = CookieUtil.read(SystemConstant.SESSIONId_NAME, request);
        //从session中拿到当前登录的用户
        String userJson = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
        User user = JSONObject.parseObject(userJson, User.class);
        List<Wealth> list = wealthMapper.findWealthByUserId(user);
        //po转换vo
        //List<WealthVo> listVo = getWealthVos(list);
        return list;
    }


    @Override
    public List<Wealth> findWealthUrl() {
        //获取sesssionId
        String sessionId = CookieUtil.read(SystemConstant.SESSIONId_NAME, request);
        //从session中拿到当前登录的用户
        String userJson = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
        User user = JSONObject.parseObject(userJson, User.class);
        List<Wealth> list = wealthMapper.findWealthUrl();
        return list;
    }
}
