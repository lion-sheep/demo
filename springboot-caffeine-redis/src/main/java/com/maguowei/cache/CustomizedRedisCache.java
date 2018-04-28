package com.maguowei.cache;/**
 * @author maguowei
 * @date ${DTAE} 下午6:43
 */

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisOperations;

/**
 * @author maguowei
 * @date 2018/4/13 下午6:43
 */
public class CustomizedRedisCache extends RedisCache {

    //设置key的统一前缀
    private final byte[]  prefix;
    //redis的所有操作
    private RedisOperations<?, ?> redisOperations;
    //设置key的过期时间
    private long expiratgionTime;
    //在缓存过期前，强制刷新缓存的时间。主要用于防止同一时间缓存过期导致的数据库压力问题
    private long preloadTime;
    //是否强制刷新（走数据库），默认是false
    private boolean forceRefresh = false;


    public CustomizedRedisCache(String name, byte[] prefix, RedisOperations<?, ?> redisOperations, long expirationTime) {
        super(name, prefix, redisOperations, expirationTime);
        this.prefix = prefix;
    }
}
