package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;

public interface ReadLikeUseCase {

    /**
     * Get Target's Like count use case,
     * get target's like count on persistence
     *
     * @param countLikeCommand request count target data
     * @return About target's like count
     */
    CountLikeResponseDto getLikeCount(CountLikeCommand countLikeCommand);
}
