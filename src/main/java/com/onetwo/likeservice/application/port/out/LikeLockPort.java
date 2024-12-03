package com.onetwo.likeservice.application.port.out;

import org.redisson.api.RLock;

public interface LikeLockPort {
    RLock getLikeRock(Integer category, Long targetId, String userId);
}
