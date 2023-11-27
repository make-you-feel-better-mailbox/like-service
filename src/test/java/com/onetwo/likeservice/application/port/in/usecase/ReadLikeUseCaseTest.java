package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;
import com.onetwo.likeservice.application.port.out.ReadLikePort;
import com.onetwo.likeservice.application.service.converter.LikeUseCaseConverter;
import com.onetwo.likeservice.application.service.service.LikeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReadLikeUseCaseTest {

    @InjectMocks
    private LikeService readLikeUseCase;

    @Mock
    private ReadLikePort readLikePort;

    @Mock
    private LikeUseCaseConverter likeUseCaseConverter;

    private final int category = 1;
    private final long targetId = 11L;
    private final int likeCount = 19;

    @Test
    @DisplayName("[단위][Use Case] Like 갯수 조회 - 성공 테스트")
    void countLikeUseCaseSuccessTest() {
        //given
        CountLikeCommand CountLikeCommand = new CountLikeCommand(category, targetId);
        CountLikeResponseDto CountLikeResponseDto = new CountLikeResponseDto(likeCount);

        given(readLikePort.countLikeByCategoryAndTargetId(anyInt(), anyLong())).willReturn(likeCount);
        given(likeUseCaseConverter.resultToCountResponseDto(anyInt())).willReturn(CountLikeResponseDto);
        //when
        CountLikeResponseDto result = readLikeUseCase.getLikeCount(CountLikeCommand);

        //then
        Assertions.assertNotNull(result);
        assertEquals(likeCount, result.likeCount());
    }
}