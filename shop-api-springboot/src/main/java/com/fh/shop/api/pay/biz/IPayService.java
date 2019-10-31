package com.fh.shop.api.pay.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.paylog.po.PayLog;

public interface IPayService {
    ServerResponse createNative(MemberVo memberVo);

    void updatePayLog(PayLog payLog, Long id);
}
