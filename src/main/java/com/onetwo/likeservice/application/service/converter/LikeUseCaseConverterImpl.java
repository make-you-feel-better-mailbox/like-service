package com.onetwo.likeservice.application.service.converter;

import com.onetwo.likeservice.application.port.in.response.*;
import com.onetwo.likeservice.domain.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeUseCaseConverterImpl implements LikeUseCaseConverter {
    @Override
    public RegisterLikeResponseDto likeToRegisterResponseDto(Like savedLike) {
        return new RegisterLikeResponseDto(savedLike != null && savedLike.getId() != null);
    }

    @Override
    public DeleteLikeResponseDto likeToDeleteResponseDto(Like like) {
        return new DeleteLikeResponseDto(like.isDeleted());
    }

    @Override
    public CountLikeResponseDto resultToCountResponseDto(int countLike) {
        return new CountLikeResponseDto(countLike);
    }

    @Override
    public FilteredLikeResponseDto likeToFilteredResponse(Like like) {
        return new FilteredLikeResponseDto(
                like.getId(),
                like.getUserId(),
                like.getCategory(),
                like.getTargetId(),
                like.getCreatedAt()
        );
    }

    @Override
    public LikeTargetCheckResponseDto resultToLikeTargetCheckResponseDto(boolean isUserLikeTarget) {
        return new LikeTargetCheckResponseDto(isUserLikeTarget);
    }
}
