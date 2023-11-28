package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.FilteredLikeResponseDto;
import org.springframework.data.domain.Slice;

public interface ReadLikeUseCase {

    /**
     * Get Target's Like count use case,
     * get target's like count on persistence
     *
     * @param countLikeCommand request count target data
     * @return About target's like count
     */
    CountLikeResponseDto getLikeCount(CountLikeCommand countLikeCommand);

    /**
     * Get Filtered like use case,
     * Get Filtered slice like data
     *
     * @param likeFilterCommand filter condition and pageable
     * @return content and slice data
     */
    Slice<FilteredLikeResponseDto> filterLike(LikeFilterCommand likeFilterCommand);
}
