package com.onetwo.likeservice.adapter.in.web.like.mapper;

import com.onetwo.likeservice.adapter.in.web.like.request.RegisterLikeRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.DeleteLikeResponse;
import com.onetwo.likeservice.adapter.in.web.like.response.RegisterLikeResponse;
import com.onetwo.likeservice.application.port.in.command.DeleteLikeCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;

public interface LikeDtoMapper {
    RegisterLikeCommand registerRequestToCommand(String userId, RegisterLikeRequest registerLikeRequest);

    RegisterLikeResponse dtoToRegisterResponse(RegisterLikeResponseDto registerLikeResponseDto);

    DeleteLikeCommand deleteRequestToCommand(String userId, Integer category, Long targetId);

    DeleteLikeResponse dtoToDeleteResponse(DeleteLikeResponseDto deleteLikeResponseDto);
}
