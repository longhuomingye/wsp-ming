package com.fh.shop.admin.controller.area;

import com.fh.shop.admin.biz.area.IAreaService;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.area.Area;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/area")
public class AreaController {
    @Resource(name = "areaService")
    private IAreaService areaService;

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "/area/areaList";
    }

    /**
     * 三级联动
     * @param pid
     * @return
     */
    @RequestMapping("/findAllAreaByPid")
    @ResponseBody
    public List<Area> findAllAreaByPid(Integer pid){
        List<Area> list = areaService.findAllAreaByPid(pid);
        return list;
    }

    /**
     * 查询树
     * @return
     */
    @RequestMapping("/findLayTree")
    @ResponseBody
    public List<Object> findLayTree(){
        List<Area> layTreeList = areaService.findLayTree();
        // 存放子节点
        List<Object> mapList = new ArrayList<>();
        for (Area area : layTreeList) {
            System.out.println();
            Map<String, Object> map = new HashMap<>();
            map.put("id", area.getId());
            map.put("name", area.getAreaName());
            map.put("pId", area.getPid());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 新增节点
     * @param area
     */
    @RequestMapping("/saveZtree")
    @ResponseBody
    public ServerResponse saveZtree(Area area){
        areaService.saveZtree(area);
        return ServerResponse.success(ResponseEnum.SUCCESS,area.getId());
    }

    /**
     * 删除
     * @param ids
     */
    @RequestMapping("/deleteZtreeByPash")
    @ResponseBody
    public ServerResponse deleteZtreeByPash(Integer[] ids){
        areaService.deleteZtreeByPash(ids);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 修改
     * @param area
     */
    @RequestMapping("/updateZtree")
    @ResponseBody
    public ServerResponse updateZtree(Area area){
        areaService.updateZtree2(area);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }
}
