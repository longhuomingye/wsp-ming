package com.fh.shop.admin.biz.member;


import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.member.IMemberMapper;
import com.fh.shop.admin.param.member.MemberSearch;
import com.fh.shop.admin.po.member.Member;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.vo.member.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberMapper memberMapper;


    @Override
    public ServerResponse findPage(MemberSearch memberSearch) {
        Long count = memberMapper.queryMemberCount(memberSearch);
        List<Member> memberList = memberMapper.queryMemberList(memberSearch);
        List<MemberVo> memberVoList = buildMemberVoList(memberList);
        DataTableResult dataTableRult=new DataTableResult(memberSearch.getDraw(),count,count,memberVoList);
        return ServerResponse.success(ResponseEnum.SUCCESS,dataTableRult);
    }

    private List<MemberVo> buildMemberVoList(List<Member> memberList) {
        List<MemberVo> memberVoList=new ArrayList<>();
        for (Member member : memberList) {
            MemberVo memberVo=new MemberVo();
            memberVo.setId(member.getId());
            memberVo.setMemberName(member.getMemberName());
            memberVo.setAreanames(member.getAreanames());
            memberVo.setEmail(member.getEmail());
            memberVo.setRealName(member.getRealName());
            memberVo.setPhone(member.getPhone());
            Date brithdy = member.getBrithdy();
            memberVo.setBrithdy(DateUtil.date2str(brithdy,DateUtil.Y_M_D));
            memberVoList.add(memberVo);
        }
        return memberVoList;
    }

}
