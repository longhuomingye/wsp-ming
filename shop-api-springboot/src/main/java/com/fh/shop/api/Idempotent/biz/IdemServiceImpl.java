package com.fh.shop.api.Idempotent.biz;

import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("idemService")
@Transactional(rollbackFor = Exception.class)
public class IdemServiceImpl implements IdemService {


    @Transactional(readOnly = true)
    @Override
    public ServerResponse createIdem() {
        String token = UUID.randomUUID().toString();
        RedisUtil.set(token, token);
        return ServerResponse.success(ResponseEnum.SUCCESS, token);
    }
}
