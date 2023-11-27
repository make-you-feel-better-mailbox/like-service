package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.domain.Like;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
}