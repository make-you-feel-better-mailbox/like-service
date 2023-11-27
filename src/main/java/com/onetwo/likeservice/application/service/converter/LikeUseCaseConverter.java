package com.onetwo.likeservice.application.service.converter;

import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;
import com.onetwo.likeservice.domain.Like;

public interface LikeUseCaseConverter {
    RegisterLikeResponseDto likeToRegisterResponseDto(Like savedLike);

    DeleteLikeResponseDto likeToDeleteResponseDto(Like like);
}
