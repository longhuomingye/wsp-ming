package com.fh.shop.admin.controller.member;


import com.fh.shop.admin.biz.member.IMemberService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.member.MemberSearch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("members")

public class MemberController {
    @Resource(name = "memberService")
    private IMemberService memberService;

    @RequestMapping("totable")
    public String totable(){
        return "/member/membertable";
    }

    @RequestMapping("queryMemberJson")
    @ResponseBody
    public ServerResponse queryMember(MemberSearch memberSearch){
        return memberService.findPage(memberSearch);
    }



}
