package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.DeleteLikeCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.DeleteLikeResponseDto;
import com.onetwo.likeservice.application.port.out.ReadLikePort;
import com.onetwo.likeservice.application.port.out.UpdateLikePort;
import com.onetwo.likeservice.application.service.converter.LikeUseCaseConverter;
import com.onetwo.likeservice.application.service.service.LikeService;
import com.onetwo.likeservice.domain.Like;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
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
class DeleteLikeUseCaseTest {

    @InjectMocks
    private LikeService deleteLikeUseCase;

    @Mock
    private ReadLikePort readLikePort;

    @Mock
    private UpdateLikePort updateLikePort;

    @Mock
    private LikeUseCaseConverter likeUseCaseConverter;

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 11L;

    @Test
    @DisplayName("[단위][Use Case] Like 삭제 - 성공 테스트")
    void deleteLikeUseCaseSuccessTest() {
        //given
        DeleteLikeCommand deleteLikeCommand = new DeleteLikeCommand(userId, category, targetId);
        DeleteLikeResponseDto deleteLikeResponseDto = new DeleteLikeResponseDto(true);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        given(readLikePort.findByUserIdAndCategoryAndTargetId(anyString(), anyInt(), anyLong())).willReturn(Optional.of(like));
        given(likeUseCaseConverter.likeToDeleteResponseDto(any(Like.class))).willReturn(deleteLikeResponseDto);
        //when
        DeleteLikeResponseDto result = deleteLikeUseCase.deleteLike(deleteLikeCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isDeleteSuccess());
    }

    @Test
    @DisplayName("[단위][Use Case] Like 삭제 like does not exist - 실패 테스트")
    void deleteLikeUseCaseLikeDoesNotExistFailTest() {
        //given
        DeleteLikeCommand deleteLikeCommand = new DeleteLikeCommand(userId, category, targetId);

        given(readLikePort.findByUserIdAndCategoryAndTargetId(anyString(), anyInt(), anyLong())).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> deleteLikeUseCase.deleteLike(deleteLikeCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Like 삭제 user id does not matched - 실패 테스트")
    void deleteLikeUseCaseUserIdDoesNotMatchedFailTest() {
        //given
        String wrongUserId = "wrongUserId";

        DeleteLikeCommand deleteLikeCommand = new DeleteLikeCommand(userId, category, targetId);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(wrongUserId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        given(readLikePort.findByUserIdAndCategoryAndTargetId(anyString(), anyInt(), anyLong())).willReturn(Optional.of(like));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> deleteLikeUseCase.deleteLike(deleteLikeCommand));
    }
}