package com.fh.shop.api.address.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.address.mapper.IAddressMapper;
import com.fh.shop.api.address.po.Address;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("addressService")
public class IAddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressMapper addressMapper;

    @Override
    public ServerResponse add(Address address) {
        addressMapper.insert(address);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse findAddressByMemberId(MemberVo memberVo) {
        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.eq("memberId", memberVo.getId());
        List<Address> addresses = addressMapper.selectList(addressQueryWrapper);
        return ServerResponse.success(ResponseEnum.SUCCESS, addresses);
    }


    @Override
    public ServerResponse delAddressById(Long id) {
        addressMapper.deleteById(id);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse updateAddress(Address address) {
        addressMapper.updateById(address);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse toUpdateAddress(Long id) {
        Address address = addressMapper.selectById(id);
        return ServerResponse.success(ResponseEnum.SUCCESS, address);
    }
}
