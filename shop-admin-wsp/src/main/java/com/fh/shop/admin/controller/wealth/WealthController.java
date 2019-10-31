package com.fh.shop.admin.controller.wealth;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.biz.wealth.IWealthService;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.DistributedSession;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.vo.wealth.WealthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.List;

@Controller
@RequestMapping("/wealth")
public class WealthController {

    @Resource(name = "wealthService")
    private IWealthService wealthService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    /**
     * 跳转到查询菜单页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "/wealth/wealthList";
    }

    /**
     * 查询菜单
     * @return
     */
    @RequestMapping("/findZtreeList")
    @ResponseBody
    public ServerResponse findZtreeList(){
        List<WealthVo> listVo = wealthService.findZtreeList();
        return ServerResponse.success(ResponseEnum.SUCCESS,listVo);
    }

    /**
     * 添加菜单节点
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @Log("添加菜单")
    public ServerResponse add(Wealth wealth){
        wealthService.add(wealth);
        return ServerResponse.success(ResponseEnum.SUCCESS,wealth.getId());
    }

    /**
     * 删除节点
     * @return
     */
    @RequestMapping("/deleteZtree")
    @ResponseBody
    @Log("删除菜单")
    public ServerResponse deleteZtree(@RequestParam("ids[]")Integer[] ids){
        wealthService.deleteZtree(ids);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 修改
     * @param wealth
     * @return
     */
    @RequestMapping("/updateZtree")
    @ResponseBody
    @Log("修改菜单")
    public ServerResponse updateZtree(Wealth wealth){
        wealthService.updateZtree(wealth);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 查询当前用户对应的角色资源
     * @return
     */
    @RequestMapping("/findWealthByUserId")
    @ResponseBody
    public ServerResponse findWealthByUserId(){
        //List<WealthVo> wealthVoList = (List<WealthVo>) request.getSession().getAttribute(SystemConstant.MANU_USER);
        String sessionId = DistributedSession.getSessionId(request, response);
        String wealthVoListJson = RedisUtil.get(KeyUtil.buildUserWealth(sessionId));
        List<Wealth> wealthVoList = JSONObject.parseArray(wealthVoListJson, Wealth.class);
        return ServerResponse.success(ResponseEnum.SUCCESS,wealthVoList);
    }

}
