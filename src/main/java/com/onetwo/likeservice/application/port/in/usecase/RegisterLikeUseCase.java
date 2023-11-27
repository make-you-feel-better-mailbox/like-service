package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;

public interface RegisterLikeUseCase {

    /**
     * Register like use case,
     * register like data to persistence
     *
     * @param registerLikeCommand data about register like with user id and target id
     * @return Boolean about register success
     */
    RegisterLikeResponseDto registerLike(RegisterLikeCommand registerLikeCommand);
}
