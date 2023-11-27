package com.onetwo.likeservice.adapter.in.web.like.api;

import com.onetwo.likeservice.adapter.in.web.like.mapper.LikeDtoMapper;
import com.onetwo.likeservice.adapter.in.web.like.request.RegisterLikeRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.RegisterLikeResponse;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;
import com.onetwo.likeservice.application.port.in.usecase.RegisterLikeUseCase;
import com.onetwo.likeservice.common.GlobalUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final RegisterLikeUseCase registerLikeUseCase;
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
}
