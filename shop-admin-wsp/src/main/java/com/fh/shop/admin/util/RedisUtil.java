package com.fh.shop.admin.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void set(String key,String value){
        Jedis resource = null;
        try {
            resource = RedisPool.getResource();
            resource.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally{
            if(null != resource){
                resource.close();
            }
        }
    }

    public static void del(String... key){
        Jedis resource = null;
        try {
            resource = RedisPool.getResource();
            resource.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != resource){
                resource.close();
            }
        }
    }

    public static String get(String key){
        Jedis resource =null;
        String str = null;
        try {
            resource = RedisPool.getResource();
            str = resource.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if( null != resource){
                resource.close();
            }
        }
        return str;
    }

    public static void setEx(String key,String value,int seconds){
        Jedis resource =null;
        try {
            resource = RedisPool.getResource();
            resource.setex(key,seconds,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != resource){
                resource.close();
            }
        }
    }

    public static void exprie(String key,int seconds){
        Jedis resource = RedisPool.getResource();
        try {
            resource.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally{
            if( null != resource){
                resource.close();
            }
        }
    }
}
