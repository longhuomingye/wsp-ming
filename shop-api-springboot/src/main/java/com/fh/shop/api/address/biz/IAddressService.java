package com.fh.shop.api.address.biz;

import com.fh.shop.api.address.po.Address;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;

public interface IAddressService {
    ServerResponse add(Address address);

    ServerResponse findAddressByMemberId(MemberVo memberVo);

    ServerResponse delAddressById(Long id);

    ServerResponse updateAddress(Address address);

    ServerResponse toUpdateAddress(Long id);
}
