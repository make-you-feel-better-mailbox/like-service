package com.onetwo.likeservice.application.service.converter;

import com.onetwo.likeservice.application.port.in.response.*;
import com.onetwo.likeservice.domain.Like;

public interface LikeUseCaseConverter {
    RegisterLikeResponseDto likeToRegisterResponseDto(Like savedLike);

    DeleteLikeResponseDto likeToDeleteResponseDto(Like like);

    CountLikeResponseDto resultToCountResponseDto(int countLike);

    FilteredLikeResponseDto likeToFilteredResponse(Like like);

    LikeTargetCheckResponseDto resultToLikeTargetCheckResponseDto(boolean isUserLikeTarget);
}
