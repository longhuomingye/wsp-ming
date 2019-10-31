package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.UUID;

@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

    @Override
    public ServerResponse addMember(Member member) {
        if (StringUtils.isEmpty(member.getMemberName()) || StringUtils.isEmpty(member.getRealName()) || StringUtils.isEmpty(member.getPassword()) || StringUtils.isEmpty(member.getNode())) {
            return ServerResponse.error(ResponseEnum.MEMBER_ERROR);
        }
        //查看当前验证码是否已过期
        Long ttl = RedisUtil.ttl("node:" + member.getPhone());
        if (ttl == -2) {
            return ServerResponse.error(ResponseEnum.SMS_NODE_ERROR);
        }
        //从redis中取出短信验证码
        String node = RedisUtil.get("node:" + member.getPhone());
        //判断验证码是否正确
        if (!member.getNode().equalsIgnoreCase(node)) {
            return ServerResponse.error(ResponseEnum.NODE_ERROR);
        }
        //验证当前手机是否已经被注册了
        Member memberInfo = findMemberByPhone(member.getPhone());
        if (memberInfo != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_PHONE_ERROR);
        }
        //验证当前用户是否存在
        Member memberName = findMemberName(member.getMemberName());
        if (memberName != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_NAME_EXIST);
        }
        //验证当前邮箱是否被使用
        Member member1 = findMember(member.getEmail());
        if (member1 != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_EMAIL_EXIST);
        }
        //验证码正确 注册 保存数据库
        member.setPassword(Md5Util.md5(Md5Util.md5(member.getPassword())));
        memberMapper.insert(member);
        RedisUtil.del(member.getPhone());
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    public Member findMemberByPhone(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("phone", phone);
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    public Member findMemberName(String memberName) {
        QueryWrapper queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("memberName", memberName);
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    public Member findMember(String email) {
        QueryWrapper queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("email", email);
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse findMemberByName(String memberName) {
        if (StringUtils.isNotEmpty(memberName)) {
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("memberName", memberName);
            Member member = memberMapper.selectOne(memberQueryWrapper);
            if (member != null) {
                return ServerResponse.error(ResponseEnum.MEMBER_IS_NAME_EXIST);
            } else {
                return ServerResponse.success(ResponseEnum.SUCCESS);
            }
        } else {
            return ServerResponse.success(ResponseEnum.SUCCESS);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse MemberByPhone(String phone) {
        if (StringUtils.isNotEmpty(phone)) {
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("phone", phone);
            Member member = memberMapper.selectOne(memberQueryWrapper);
            if (member != null) {
                return ServerResponse.error(ResponseEnum.MEMBER_IS_PHONE_EXIST);
            } else {
                return ServerResponse.success(ResponseEnum.SUCCESS);
            }
        } else {
            return ServerResponse.success(ResponseEnum.SUCCESS);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse findMemberByEmail(String email) {
        if (StringUtils.isNotEmpty(email)) {
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("email", email);
            Member member = memberMapper.selectOne(memberQueryWrapper);
            if (member != null) {
                return ServerResponse.error(ResponseEnum.MEMBER_IS_EMAIL_EXIST);
            } else {
                return ServerResponse.success(ResponseEnum.SUCCESS);
            }
        } else {
            return ServerResponse.success(ResponseEnum.SUCCESS);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse login(Member member, String productId) {
        String memberName = member.getMemberName();
        String password = member.getPassword();
        //判断用户名是否为空
        if (StringUtils.isEmpty(memberName)) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_NAME_NULL);
        }
        //判断密码是否为空
        if (StringUtils.isEmpty(password)) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_PASSWORD_NULL);
        }
        //判断当前用户名是否存在
        Member memberInfo = findMemberName(memberName);
        if (memberInfo == null) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_NAME_ERROR);
        }
        //判断密码是否正确
        String md5Password = Md5Util.md5(Md5Util.md5(member.getPassword()));
        if (!memberInfo.getPassword().equalsIgnoreCase(md5Password)) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_PASSWORD_ERROR);
        }
        //转换为加密格式
        String str = buildBase64(memberInfo);
        return ServerResponse.success(ResponseEnum.SUCCESS, str);
    }

    String buildBase64(Member memberInfo) {
        MemberVo memberVo = new MemberVo();
        memberVo.setMemberName(memberInfo.getMemberName());
        String uuid = UUID.randomUUID().toString();
        memberVo.setUuid(uuid);
        Long id = memberInfo.getId();
        memberVo.setId(id);
        //转换为json字符串
        String memberJson = JSONObject.toJSONString(memberVo);
        //通过base64编码
        byte[] bytes = memberJson.getBytes();
        String memberBase = Base64.getEncoder().encodeToString(bytes);
        //通过散列算法加密
        String sign = Md5Util.sign(memberBase, SystemConstant.SECRET);
        //通过base64编码
        String md5Base = Base64.getEncoder().encodeToString(sign.getBytes());
        //保存到redis服务器
        RedisUtil.setEx(KeyUtil.buildMember(id, memberInfo.getMemberName(), uuid), "1", SystemConstant.MEMBER_EXPIRE);
        Base64.getDecoder().decode(memberBase);
        return memberBase + "." + md5Base;
    }

    @Transactional(readOnly = true)
    @Override
    public Member findMemberById(MemberVo memberVo) {
        QueryWrapper queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("id", memberVo.getId());
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse loginByNode(Member member) {
        String phone = member.getPhone();
        String node = member.getNode();
        //判断手机号是否为空
        if (StringUtils.isEmpty(phone)) {
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        //判断验证码是否为空
        if (StringUtils.isEmpty(node)) {
            return ServerResponse.error(ResponseEnum.NODE_IS_NULL);
        }
        //判断当前用户是否存在
        Member memberInfo = findMemberByPhone(phone);
        if (memberInfo == null) {
            return ServerResponse.error(ResponseEnum.MEMBER_IS_NAME_ERROR);
        }
        //从redis中取出短信验证码
        String nodeInfo = RedisUtil.get("node:" + member.getPhone());
        //判断验证码是否正确
        if (!node.equalsIgnoreCase(nodeInfo)) {
            return ServerResponse.error(ResponseEnum.NODE_ERROR);
        }
        //全部正确
        //转换为加密格式
        String str = buildBase64(memberInfo);
        return ServerResponse.success(ResponseEnum.SUCCESS, str);
    }
}
