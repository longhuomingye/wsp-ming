package com.fh.shop.api.member.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;

public interface IMemberService {
    ServerResponse addMember(Member member);


    ServerResponse findMemberByName(String memberName);

    ServerResponse MemberByPhone(String phone);

    ServerResponse findMemberByEmail(String email);

    ServerResponse login(Member member, String productId);

    Member findMemberById(MemberVo memberVo);

    ServerResponse loginByNode(Member member);
}
