package com.fh.shop.api.util;

import redis.clients.jedis.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class RedisPool {

    private static JedisPool jedisPool = null;

    public static JedisCluster cluster = null;

    private RedisPool(){}

    public static void RedisPoolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        System.out.println();
        // 最大连接数
        poolConfig.setMaxTotal(1000);
        // 最大空闲数
        poolConfig.setMaxIdle(100);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        //poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new LinkedHashSet<>();
        nodes.add(new HostAndPort("169.254.33.76", 30001));
        nodes.add(new HostAndPort("169.254.33.76", 30002));
        nodes.add(new HostAndPort("169.254.33.76", 30003));
        nodes.add(new HostAndPort("169.254.33.76", 30004));
        nodes.add(new HostAndPort("169.254.33.76", 30005));
        nodes.add(new HostAndPort("169.254.33.76", 30006));
        cluster = new JedisCluster(nodes, poolConfig);
    }

    static{
        RedisPoolConfig();
    }

    public static JedisCluster getResource(){
        return cluster;
    }
}
