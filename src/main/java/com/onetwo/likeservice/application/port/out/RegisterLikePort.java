package com.onetwo.likeservice.application.port.out;

import com.onetwo.likeservice.domain.Like;

public interface RegisterLikePort {
    Like registerLike(Like newLike);
}
