package com.onetwo.likeservice.application.service.adapter;

import com.onetwo.likeservice.application.port.out.LikeLockPort;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeLockAdapter implements LikeLockPort {

    private final RedissonClient redissonClient;

    @Override
    public RLock getLikeRock(Integer category, Long targetId, String userId) {

        String lockKey = "likeLock-category" + category +
                "target-id" + targetId +
                "user-id" + userId;

        return redissonClient.getLock(lockKey);
    }
}
