package com.fh.shop.api.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.pay.biz.IPayService;
import com.fh.shop.api.paylog.po.PayLog;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.MyConfig;
import com.fh.shop.api.util.RedisUtil;
import com.github.wxpay.sdk.WXPay;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pays")
public class PayController {

    @Resource(name = "payService")
    private IPayService payService;

    @GetMapping
    @Check
    public ServerResponse createNative(MemberVo memberVo) {
        return payService.createNative(memberVo);
    }

    @PostMapping
    @Check
    public ServerResponse findNative(MemberVo memberVo) throws Exception {
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<>();
        //从redis中取出当前用户的支付订单信息
        String payJson = RedisUtil.get(KeyUtil.buildPayLog(memberVo.getId()));
        PayLog payLog = JSONObject.parseObject(payJson, PayLog.class);
        data.put("out_trade_no", payLog.getOrderId());
        Integer count = 0;
        while (true) {
            //必须写在循环内 每次都需要重新查询
            Map<String, String> stringStringMap = wxpay.orderQuery(data);
            count++;
            System.out.println(count);
            String returnCode = stringStringMap.get("return_code");
            String returnMsg = stringStringMap.get("return_msg");
            if (!returnCode.equalsIgnoreCase("SUCCESS")) {
                return ServerResponse.error(9999, returnMsg);
            }
            String resultCode = stringStringMap.get("result_code");
            String errCodeDes = stringStringMap.get("err_code_des");
            if (!resultCode.equalsIgnoreCase("SUCCESS")) {
                return ServerResponse.error(9999, errCodeDes);
            }
            String tradeState = stringStringMap.get("trade_state");
            if (tradeState.equalsIgnoreCase("SUCCESS")) {
                String transactionId = stringStringMap.get("transaction_id");
                payLog.setTarnsActionId(transactionId);
                payLog.setPayStatus(2);
                payLog.setPayTime(new Date());
                //修改数据库中的 支付日志表 以及  订单表的数据
                payService.updatePayLog(payLog, memberVo.getId());
                //从redis中移除支付日志表
                RedisUtil.del(KeyUtil.buildPayLog(memberVo.getId()));
                //支付金额
                BigDecimal payMoney = payLog.getPayMoney();
                //支付方式
                Integer payType = payLog.getPayType();
                Map map = new HashMap();
                map.put("payMoney", payMoney);
                map.put("payType", payType);
                return ServerResponse.success(ResponseEnum.SUCCESS, map);
            }
            if (count > 100) {
                return ServerResponse.error(9999, "支付超时");
            }
            Thread.sleep(3000);
        }
    }
}
