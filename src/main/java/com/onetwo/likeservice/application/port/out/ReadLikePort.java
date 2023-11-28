package com.onetwo.likeservice.application.port.out;

import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.domain.Like;

import java.util.List;
import java.util.Optional;

public interface ReadLikePort {
    Optional<Like> findByUserIdAndCategoryAndTargetId(String userId, Integer category, Long targetId);

    int countLikeByCategoryAndTargetId(Integer category, Long targetId);

    List<Like> filterLike(LikeFilterCommand likeFilterCommand);
}
