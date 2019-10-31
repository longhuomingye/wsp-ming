package com.fh.shop.api.member.controller;

import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 注册会员
     *
     * @param member
     * @return
     */
    @PostMapping
    public ServerResponse addMember(Member member) {
        return memberService.addMember(member);
    }

    /**
     * 验证用户是否存在
     *
     * @param memberName
     * @return
     */
    @GetMapping
    public ServerResponse findMemberByName(String memberName) {
        return memberService.findMemberByName(memberName);
    }

    /**
     * 验证手机号是否被使用
     *
     * @param phone
     * @return
     */
    @GetMapping(value = "/phone")
    public ServerResponse MemberByPhone(String phone) {
        return memberService.MemberByPhone(phone);
    }


    /**
     * 验证邮箱是否被使用
     *
     * @param email
     * @return
     */
    @GetMapping(value = "/email")
    public ServerResponse findMemberByEmail(String email) {
        return memberService.findMemberByEmail(email);
    }

    /**
     * 登录验证
     *
     * @param member
     * @return
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "这是登录的接口",httpMethod = "post",notes = "使用的是token登录")
    @ApiParam(name = "需要的参数：",value = "这是描述参数")
    public ServerResponse login(Member member, String productId) {
        return memberService.login(member, productId);
    }

    @PostMapping(value = "/loginByNode")
    public ServerResponse loginByNode(Member member) {
        return memberService.loginByNode(member);
    }

    /**
     * 查询当前登录的用户信息
     */
    @GetMapping(value = "/findMember")
    @Check
    public ServerResponse findMember() {
        MemberVo memberVo = (MemberVo) request.getAttribute("member");
        //通过用户名查询当前用户信息
        Member member = memberService.findMemberById(memberVo);
        return ServerResponse.success(ResponseEnum.SUCCESS, member);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping(value = "/logout")
    @Check
    public ServerResponse logout() {
        MemberVo memberVo = (MemberVo) request.getAttribute("member");
        RedisUtil.del(KeyUtil.buildMember(memberVo.getId(), memberVo.getMemberName(), memberVo.getUuid()));
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }
}
