package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.DeleteLikeCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.domain.Like;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DeleteLikeUseCaseBootTest {

    @Autowired
    private DeleteLikeUseCase deleteLikeUseCase;

    @Autowired
    private RegisterLikePort registerLikePort;

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 11L;

    @Test
    @DisplayName("[통합][Use Case] Like 삭제 - 성공 테스트")
    void deleteLikeUseCaseSuccessTest() {
        //given
        DeleteLikeCommand deleteLikeCommand = new DeleteLikeCommand(userId, category, targetId);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        registerLikePort.registerLike(like);

        //when
        DeleteLikeResponseDto result = deleteLikeUseCase.deleteLike(deleteLikeCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isDeleteSuccess());
    }

    @Test
    @DisplayName("[통합][Use Case] Like 삭제 like does not exist - 실패 테스트")
    void deleteLikeUseCaseLikeDoesNotExistFailTest() {
        //given
        DeleteLikeCommand deleteLikeCommand = new DeleteLikeCommand(userId, category, targetId);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> deleteLikeUseCase.deleteLike(deleteLikeCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] Like 삭제 like does not exist - 실패 테스트")
    void deleteLikeUseCaseUserIdDoesNotMatchedFailTest() {
        //given
        String wrongUserId = "wrongUserId";

        DeleteLikeCommand deleteLikeCommand = new DeleteLikeCommand(userId, category, targetId);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(wrongUserId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        registerLikePort.registerLike(like);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> deleteLikeUseCase.deleteLike(deleteLikeCommand));
    }
}