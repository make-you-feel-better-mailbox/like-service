package com.onetwo.likeservice.adapter.in.web.like.api;

import com.onetwo.likeservice.adapter.in.web.like.mapper.LikeDtoMapper;
import com.onetwo.likeservice.adapter.in.web.like.request.RegisterLikeRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.DeleteLikeResponse;
import com.onetwo.likeservice.adapter.in.web.like.response.RegisterLikeResponse;
import com.onetwo.likeservice.application.port.in.command.DeleteLikeCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;
import com.onetwo.likeservice.application.port.in.usecase.DeleteLikeUseCase;
import com.onetwo.likeservice.application.port.in.usecase.RegisterLikeUseCase;
import com.onetwo.likeservice.common.GlobalUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final RegisterLikeUseCase registerLikeUseCase;
    private final DeleteLikeUseCase deleteLikeUseCase;
    private final LikeDtoMapper likeDtoMapper;

    /**
     * Register Like inbound adapter
     *
     * @param registerLikeRequest data about register like
     * @param userId              user authentication id
     * @return Boolean about register success
     */
    @PostMapping(GlobalUrl.LIKE_ROOT)
    public ResponseEntity<RegisterLikeResponse> registerLike(@RequestBody @Valid RegisterLikeRequest registerLikeRequest,
                                                             @AuthenticationPrincipal String userId) {
        RegisterLikeCommand registerLikeCommand = likeDtoMapper.registerRequestToCommand(userId, registerLikeRequest);
        RegisterLikeResponseDto registerLikeResponseDto = registerLikeUseCase.registerLike(registerLikeCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeDtoMapper.dtoToRegisterResponse(registerLikeResponseDto));
    }

    /**
     * Delete Like inbound adapter
     *
     * @param category request delete like's category
     * @param targetId request delete like's target id
     * @param userId   user authentication id
     * @return Boolean about delete like success
     */
    @DeleteMapping(GlobalUrl.LIKE_ROOT + GlobalUrl.PATH_VARIABLE_CATEGORY_WITH_BRACE + GlobalUrl.PATH_VARIABLE_TARGET_ID_WITH_BRACE)
    public ResponseEntity<DeleteLikeResponse> deleteLike(@PathVariable(GlobalUrl.PATH_VARIABLE_CATEGORY) Integer category,
                                                         @PathVariable(GlobalUrl.PATH_VARIABLE_TARGET_ID) Long targetId,
                                                         @AuthenticationPrincipal String userId) {
        DeleteLikeCommand deleteLikeCommand = likeDtoMapper.deleteRequestToCommand(userId, category, targetId);
        DeleteLikeResponseDto deleteLikeResponseDto = deleteLikeUseCase.deleteLike(deleteLikeCommand);
        return ResponseEntity.ok().body(likeDtoMapper.dtoToDeleteResponse(deleteLikeResponseDto));
    }
}
