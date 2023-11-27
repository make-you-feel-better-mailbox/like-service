package com.onetwo.likeservice.application.service.converter;

import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;
import com.onetwo.likeservice.domain.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeUseCaseConverterImpl implements LikeUseCaseConverter {
    @Override
    public RegisterLikeResponseDto likeToRegisterResponseDto(Like savedLike) {
        return new RegisterLikeResponseDto(savedLike != null && savedLike.getId() != null);
    }
}
