package pfu.common.redis;

import org.redisson.api.RedissonClient;

import javax.annotation.Resource;

public abstract class AbstractCache {

    @Resource
    protected RedissonClient redissonClient;

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

}
