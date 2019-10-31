package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

public class RedisUtil {

    public static void set(String key,String value){
        try {
            JedisCluster jedisCluster = RedisPool.getResource();
            jedisCluster.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Long del(String key){
        Long del = 0l;
        try {
            JedisCluster jedisCluster = RedisPool.getResource();
            del= jedisCluster.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return del;
    }

    public static String get(String key){
        String str = null;
        try {
            JedisCluster jedisCluster = RedisPool.getResource();
            str = jedisCluster.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return str;
    }

    public static void setEx(String key,String value,int seconds){
        Jedis resource =null;
        try {
            JedisCluster jedisCluster = RedisPool.getResource();
            jedisCluster.setex(key,seconds,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Long ttl (String key){
        JedisCluster jedisCluster = RedisPool.getResource();
        Long ttl = jedisCluster.ttl(key);
        return ttl;
    };

    public static boolean exists(String key){
        JedisCluster jedisCluster = RedisPool.getResource();
        Boolean exists = jedisCluster.exists(key);
        return exists;
    }

    public static void exprie(String key,int seconds){
        try {
            JedisCluster jedisCluster = RedisPool.getResource();
            jedisCluster.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void hset(String key,String field,String value){
        JedisCluster jedisCluster = RedisPool.getResource();
        jedisCluster.hset(key,field,value);
    }


    public static String hget(String key,String field){
        JedisCluster jedisCluster = RedisPool.getResource();
        String hget = jedisCluster.hget(key, field);
        return hget;
    }

    public static void hdel(String key,String field){
        JedisCluster resource = RedisPool.getResource();
        resource.hdel(key,field);
    }

    public static Long increment(String key,Long count){
        JedisCluster resource = RedisPool.getResource();
        Long aLong = resource.incrBy(key, count);
        return aLong;
    }

}
