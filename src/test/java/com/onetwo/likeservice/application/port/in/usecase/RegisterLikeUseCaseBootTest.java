package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.RegisterLikeResponseDto;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.application.service.service.LikeService;
import com.onetwo.likeservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.likeservice.domain.Like;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RegisterLikeUseCaseBootTest {

    @Autowired
    private LikeService registerLikeUseCase;

    @Autowired
    private RegisterLikePort registerLikePort;

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 11L;

    @Test
    @DisplayName("[통합][Use Case] Like 등록 - 성공 테스트")
    void registerLikeUseCaseSuccessTest() {
        //given
        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);

        //when
        RegisterLikeResponseDto result = registerLikeUseCase.registerLike(registerLikeCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isRegisterSuccess());
    }

    @Test
    @DisplayName("[통합][Use Case] Like 등록 like 기등록 - 실패 테스트")
    void registerLikeUseCaseLikeAlreadyExistFailTest() {
        //given
        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);

        Like savedLike = Like.createNewLikeByCommand(registerLikeCommand);

        registerLikePort.registerLike(savedLike);

        //when then
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> registerLikeUseCase.registerLike(registerLikeCommand));
    }
}