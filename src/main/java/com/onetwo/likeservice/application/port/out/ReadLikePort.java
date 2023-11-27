package com.onetwo.likeservice.application.port.out;

import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.domain.Like;

import java.util.Optional;

public interface ReadLikePort {
    Optional<Like> findByUserIdAndCategoryAndTargetId(RegisterLikeCommand registerLikeCommand);
}
