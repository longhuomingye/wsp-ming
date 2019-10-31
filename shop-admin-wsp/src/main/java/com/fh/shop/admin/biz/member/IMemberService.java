package com.fh.shop.admin.biz.member;


import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.member.MemberSearch;

public interface IMemberService {
    ServerResponse findPage(MemberSearch memberSearch);
}
