package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;
import com.onetwo.likeservice.application.port.out.ReadLikePort;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.application.service.converter.LikeUseCaseConverter;
import com.onetwo.likeservice.application.service.service.LikeService;
import com.onetwo.likeservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.likeservice.domain.Like;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisterLikeUseCaseTest {

    @InjectMocks
    private LikeService registerLikeUseCase;

    @Mock
    private ReadLikePort readLikePort;

    @Mock
    private RegisterLikePort registerLikePort;

    @Mock
    private LikeUseCaseConverter likeUseCaseConverter;

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 11L;

    @Test
    @DisplayName("[단위][Use Case] Like 등록 - 성공 테스트")
    void registerLikeUseCaseSuccessTest() {
        //given
        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);

        RegisterLikeResponseDto registerLikeResponseDto = new RegisterLikeResponseDto(true);

        Like savedLike = Like.createNewLikeByCommand(registerLikeCommand);

        given(readLikePort.findByUserIdAndCategoryAndTargetId(anyString(), anyInt(), anyLong())).willReturn(Optional.empty());
        given(registerLikePort.registerLike(any(Like.class))).willReturn(savedLike);
        given(likeUseCaseConverter.likeToRegisterResponseDto(any(Like.class))).willReturn(registerLikeResponseDto);
        //when
        RegisterLikeResponseDto result = registerLikeUseCase.registerLike(registerLikeCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isRegisterSuccess());
    }

    @Test
    @DisplayName("[단위][Use Case] Like 등록 like 기등록 - 실패 테스트")
    void registerLikeUseCaseLikeAlreadyExistFailTest() {
        //given
        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);

        Like savedLike = Like.createNewLikeByCommand(registerLikeCommand);

        given(readLikePort.findByUserIdAndCategoryAndTargetId(anyString(), anyInt(), anyLong())).willReturn(Optional.of(savedLike));
        //when then
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> registerLikeUseCase.registerLike(registerLikeCommand));
    }
}