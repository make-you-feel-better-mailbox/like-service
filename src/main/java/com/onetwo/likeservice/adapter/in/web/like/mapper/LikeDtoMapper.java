package com.onetwo.likeservice.adapter.in.web.like.mapper;

import com.onetwo.likeservice.adapter.in.web.like.request.RegisterLikeRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.CountLikeResponse;
import com.onetwo.likeservice.adapter.in.web.like.response.DeleteLikeResponse;
import com.onetwo.likeservice.adapter.in.web.like.response.LikeTargetCheckResponse;
import com.onetwo.likeservice.adapter.in.web.like.response.RegisterLikeResponse;
import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.command.DeleteLikeCommand;
import com.onetwo.likeservice.application.port.in.command.LikeTargetCheckCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.LikeTargetCheckResponseDto;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;

public interface LikeDtoMapper {
    RegisterLikeCommand registerRequestToCommand(String userId, RegisterLikeRequest registerLikeRequest);

    RegisterLikeResponse dtoToRegisterResponse(RegisterLikeResponseDto registerLikeResponseDto);

    DeleteLikeCommand deleteRequestToCommand(String userId, Integer category, Long targetId);

    DeleteLikeResponse dtoToDeleteResponse(DeleteLikeResponseDto deleteLikeResponseDto);

    CountLikeCommand countRequestToCommand(Integer category, Long targetId);

    CountLikeResponse dtoToCountResponse(CountLikeResponseDto countLikeResponseDto);

    LikeTargetCheckCommand likeCheckRequestToCommand(String userId, Integer category, Long targetId);

    LikeTargetCheckResponse dtoToLikeTargetCheckResponse(LikeTargetCheckResponseDto likeTargetCheckResponseDto);
}
