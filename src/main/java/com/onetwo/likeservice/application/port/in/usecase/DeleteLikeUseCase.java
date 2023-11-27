package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.DeleteLikeCommand;
import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;

public interface DeleteLikeUseCase {

    /**
     * Delete like use case,
     * delete like data to persistence
     *
     * @param deleteLikeCommand request delete data about like's userid, category, target id
     * @return Boolean about delete like success
     */
    DeleteLikeResponseDto deleteLike(DeleteLikeCommand deleteLikeCommand);
}
