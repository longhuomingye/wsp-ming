package com.fh.shop.api.area.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.area.mapper.IAreaMapper;
import com.fh.shop.api.area.po.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("areaService")
@Transactional(rollbackFor = Exception.class)
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Area> buildAreaSelect(Area area) {
        QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
        areaQueryWrapper.eq("pid", area.getPid());
        List<Area> areas = areaMapper.selectList(areaQueryWrapper);
        return areas;
    }
}
