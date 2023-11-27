package com.onetwo.likeservice.adapter.in.web.like.mapper;

import com.onetwo.likeservice.adapter.in.web.like.request.RegisterLikeRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.CountLikeResponse;
import com.onetwo.likeservice.adapter.in.web.like.response.DeleteLikeResponse;
import com.onetwo.likeservice.adapter.in.web.like.response.RegisterLikeResponse;
import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.command.DeleteLikeCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;
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

    @Override
    public DeleteLikeCommand deleteRequestToCommand(String userId, Integer category, Long targetId) {
        return new DeleteLikeCommand(userId, category, targetId);
    }

    @Override
    public DeleteLikeResponse dtoToDeleteResponse(DeleteLikeResponseDto deleteLikeResponseDto) {
        return new DeleteLikeResponse(deleteLikeResponseDto.isDeleteSuccess());
    }

    @Override
    public CountLikeCommand countRequestToCommand(Integer category, Long targetId) {
        return new CountLikeCommand(category, targetId);
    }

    @Override
    public CountLikeResponse dtoToCountResponse(CountLikeResponseDto countLikeResponseDto) {
        return new CountLikeResponse(countLikeResponseDto.likeCount());
    }
}
