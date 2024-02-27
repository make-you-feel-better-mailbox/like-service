package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.FilteredLikeResponseDto;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.domain.Like;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@SpringBootTest
@Transactional
class ReadLikeUseCaseBootTest {

    @Autowired
    private ReadLikeUseCase readLikeUseCase;

    @Autowired
    private RegisterLikePort registerLikePort;

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 11L;
    private final int likeCount = 12;
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    @DisplayName("[통합][Use Case] Like 갯수 조회 - 성공 테스트")
    void countLikeUseCaseSuccessTest() {
        //given
        CountLikeCommand CountLikeCommand = new CountLikeCommand(category, targetId);

        for (int i = 0; i <= likeCount; i++) {
            RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId + i, category, targetId);
            Like like = Like.createNewLikeByCommand(registerLikeCommand);

            registerLikePort.registerLike(like);
        }

        //when
        CountLikeResponseDto result = readLikeUseCase.getLikeCount(CountLikeCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.likeCount() >= likeCount);
    }

    @Test
    @DisplayName("[통합][Use Case] Like filter - 성공 테스트")
    void likeFilterUseCaseSuccessTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(userId, category, targetId, filterStartDate, filterEndDate, pageRequest);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        registerLikePort.registerLike(like);

        //when
        Slice<FilteredLikeResponseDto> result = readLikeUseCase.filterLike(findLikeDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("[통합][Use Case] Like filter Null Condition - 성공 테스트")
    void likeFilterUseCaseNullConditionSuccessTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(userId, null, null, null, null, pageRequest);

        for (int i = 0; i <= likeCount; i++) {
            RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId + i);
            Like like = Like.createNewLikeByCommand(registerLikeCommand);

            registerLikePort.registerLike(like);
        }

        //when
        Slice<FilteredLikeResponseDto> result = readLikeUseCase.filterLike(findLikeDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("[통합][Use Case] Like filter user id Null Condition - 성공 테스트")
    void likeFilterUseCaseUserIdNullConditionSuccessTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(null, category, targetId, null, null, pageRequest);

        for (int i = 0; i <= likeCount; i++) {
            RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId + i, category, targetId);
            Like like = Like.createNewLikeByCommand(registerLikeCommand);

            registerLikePort.registerLike(like);
        }

        //when
        Slice<FilteredLikeResponseDto> result = readLikeUseCase.filterLike(findLikeDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("[통합][Use Case] Like filter All Null Condition - 실패 테스트")
    void likeFilterUseCaseNullConditionFailTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(null, null, null, null, null, pageRequest);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> readLikeUseCase.filterLike(findLikeDetailCommand));
    }
}