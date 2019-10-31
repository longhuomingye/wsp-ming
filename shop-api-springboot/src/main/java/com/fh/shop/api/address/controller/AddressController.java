package com.fh.shop.api.address.controller;

import com.fh.shop.api.address.biz.IAddressService;
import com.fh.shop.api.address.po.Address;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource(name = "addressService")
    private IAddressService addressService;

    //添加地址
    @PostMapping
    @Check
    public ServerResponse add(Address address, MemberVo memberVo) {
        address.setMemberId(memberVo.getId());
        return addressService.add(address);
    }

    //查看当前会员所持有的地址
    @GetMapping
    @Check
    public ServerResponse findAddressByMemberId(MemberVo memberVo) {
        return addressService.findAddressByMemberId(memberVo);
    }

    //删除地址
    @DeleteMapping(value = "/{id}")
    @Check
    public ServerResponse delAddressById(@PathVariable Long id) {
        return addressService.delAddressById(id);
    }

    //回显
    @GetMapping(value = "/toUpdate")
    @Check
    public ServerResponse toUpdateAddress(Long id) {
        return addressService.toUpdateAddress(id);
    }

    //修改地址
    @PutMapping
    @Check
    public ServerResponse updateAddress(@RequestBody Address address) {
        return addressService.updateAddress(address);
    }
}
