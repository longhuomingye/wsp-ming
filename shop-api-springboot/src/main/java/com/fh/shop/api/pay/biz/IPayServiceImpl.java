package com.fh.shop.api.pay.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.paylog.mapper.IPayLogMapper;
import com.fh.shop.api.paylog.po.PayLog;
import com.fh.shop.api.util.*;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
@Transactional(rollbackFor = Exception.class)
public class IPayServiceImpl implements IPayService {

    @Autowired
    private IPayLogMapper payLogMapper;

    @Autowired
    private IOrderMapper orderMapper;

    HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                    + session.getPeerHost());
            return true;
        }
    };

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    @Override
    public ServerResponse createNative(MemberVo memberVo) {
        //从redis中取出当前用户的支付订单信息
        String payJson = RedisUtil.get(KeyUtil.buildPayLog(memberVo.getId()));
        String codeUrl = "";
        PayLog payLog = null;
        if (StringUtils.isEmpty(payJson)) {
            return ServerResponse.error(ResponseEnum.IS_ORDER_MISS);
        }
        try {
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            MyConfig config = new MyConfig();
            payLog = JSONObject.parseObject(payJson, PayLog.class);
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<>();
            data.put("body", "wsp-test");
            data.put("out_trade_no", payLog.getOrderId());
            int i = BigDecimalUtil.mul(payLog.getPayMoney().toString(), "100").intValue();
            data.put("total_fee", i + "");
            Date date = DateUtils.addMinutes(new Date(), 4);
            String dateString = DateUtil.date2str(date, DateUtil.YYYYMMDDHHMMSS);
            //设置失效时间
            data.put("time_expire", dateString);
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            Map<String, String> stringStringMap = wxpay.unifiedOrder(data);
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
            codeUrl = stringStringMap.get("code_url");
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
        Map map = new HashMap();
        map.put("orderId", payLog.getOrderId());
        map.put("payMoney", payLog.getPayMoney());
        map.put("codeUrl", codeUrl);
        return ServerResponse.success(ResponseEnum.SUCCESS, map);
    }

    @Override
    public void updatePayLog(PayLog payLog, Long id) {
        payLogMapper.updateById(payLog);
        Order order = new Order();
        order.setOid(payLog.getOrderId());
        order.setEndTime(new Date());
        order.setState(1);
        orderMapper.updateById(order);
    }


}
