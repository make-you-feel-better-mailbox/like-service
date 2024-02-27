package com.onetwo.likeservice.adapter.in.web.like.api;

import com.onetwo.likeservice.adapter.in.web.like.mapper.LikeDtoMapper;
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
import com.onetwo.likeservice.application.port.in.usecase.DeleteLikeUseCase;
import com.onetwo.likeservice.application.port.in.usecase.ReadLikeUseCase;
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
    private final ReadLikeUseCase readLikeUseCase;
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
     * Get Boolean about User like Target inbound adapter
     *
     * @param category request target like's category
     * @param targetId request target like's target id
     * @param userId   user authentication id
     * @return Boolean about user like target
     */
    @GetMapping(GlobalUrl.LIKE_ROOT + GlobalUrl.PATH_VARIABLE_CATEGORY_WITH_BRACE + GlobalUrl.PATH_VARIABLE_TARGET_ID_WITH_BRACE)
    public ResponseEntity<LikeTargetCheckResponse> userLikeTargetCheck(@PathVariable(GlobalUrl.PATH_VARIABLE_CATEGORY) Integer category,
                                                                       @PathVariable(GlobalUrl.PATH_VARIABLE_TARGET_ID) Long targetId,
                                                                       @AuthenticationPrincipal String userId) {
        LikeTargetCheckCommand likeTargetCheckCommand = likeDtoMapper.likeCheckRequestToCommand(userId, category, targetId);
        LikeTargetCheckResponseDto likeTargetCheckResponseDto = readLikeUseCase.userLikeTargetCheck(likeTargetCheckCommand);
        return ResponseEntity.ok().body(likeDtoMapper.dtoToLikeTargetCheckResponse(likeTargetCheckResponseDto));
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

    /**
     * Get Target's Like count inbound adapter
     *
     * @param category request count like's category
     * @param targetId request count like's target id
     * @return About target's like count
     */
    @GetMapping(GlobalUrl.LIKE_COUNT + GlobalUrl.PATH_VARIABLE_CATEGORY_WITH_BRACE + GlobalUrl.PATH_VARIABLE_TARGET_ID_WITH_BRACE)
    public ResponseEntity<CountLikeResponse> getLikeCount(@PathVariable(GlobalUrl.PATH_VARIABLE_CATEGORY) Integer category,
                                                          @PathVariable(GlobalUrl.PATH_VARIABLE_TARGET_ID) Long targetId) {
        CountLikeCommand countLikeCommand = likeDtoMapper.countRequestToCommand(category, targetId);
        CountLikeResponseDto countLikeResponseDto = readLikeUseCase.getLikeCount(countLikeCommand);
        return ResponseEntity.ok().body(likeDtoMapper.dtoToCountResponse(countLikeResponseDto));
    }
}
