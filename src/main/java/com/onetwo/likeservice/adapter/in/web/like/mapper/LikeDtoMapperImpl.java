package com.onetwo.likeservice.adapter.in.web.like.mapper;

import com.onetwo.likeservice.adapter.in.web.like.request.RegisterLikeRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.RegisterLikeResponse;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;
import org.springframework.stereotype.Component;

@Component
public class LikeDtoMapperImpl implements LikeDtoMapper {
    @Override
    public RegisterLikeCommand registerRequestToCommand(String userId, RegisterLikeRequest registerLikeRequest) {
        return new RegisterLikeCommand(userId, registerLikeRequest.category(), registerLikeRequest.targetId());
    }

    @Override
    public RegisterLikeResponse dtoToRegisterResponse(RegisterLikeResponseDto registerLikeResponseDto) {
        return new RegisterLikeResponse(registerLikeResponseDto.isRegisterSuccess());
    }
}
