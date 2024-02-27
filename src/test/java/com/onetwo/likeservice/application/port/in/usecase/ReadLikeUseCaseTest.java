package com.onetwo.likeservice.application.port.in.usecase;

import com.onetwo.likeservice.application.port.in.command.CountLikeCommand;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.response.CountLikeResponseDto;
import com.onetwo.likeservice.application.port.in.response.FilteredLikeResponseDto;
import com.onetwo.likeservice.application.port.out.ReadLikePort;
import com.onetwo.likeservice.application.service.converter.LikeUseCaseConverter;
import com.onetwo.likeservice.application.service.service.LikeService;
import com.onetwo.likeservice.domain.Like;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReadLikeUseCaseTest {

    @InjectMocks
    private LikeService readLikeUseCase;

    @Mock
    private ReadLikePort readLikePort;

    @Mock
    private LikeUseCaseConverter likeUseCaseConverter;

    private final String userId = "testUserId";
    private final Long likeId = 1L;
    private final int category = 1;
    private final long targetId = 11L;
    private final int likeCount = 19;
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final PageRequest pageRequest = PageRequest.of(0, 20);
    private final Instant createAt = Instant.now();

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

    @Test
    @DisplayName("[단위][Use Case] Like filter - 성공 테스트")
    void likeFilterUseCaseSuccessTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(userId, category, targetId, filterStartDate, filterEndDate, pageRequest);

        FilteredLikeResponseDto filteredLikeResponseDto = new FilteredLikeResponseDto(likeId, userId, category, targetId, createAt);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        given(readLikePort.filterLike(any(LikeFilterCommand.class))).willReturn(List.of(like));
        given(likeUseCaseConverter.likeToFilteredResponse(any(Like.class))).willReturn(filteredLikeResponseDto);
        //when
        Slice<FilteredLikeResponseDto> result = readLikeUseCase.filterLike(findLikeDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("[단위][Use Case] Like filter Null Condition - 성공 테스트")
    void likeFilterUseCaseNullConditionSuccessTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(userId, null, null, null, null, pageRequest);

        FilteredLikeResponseDto filteredLikeResponseDto = new FilteredLikeResponseDto(likeId, userId, category, targetId, createAt);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        given(readLikePort.filterLike(any(LikeFilterCommand.class))).willReturn(List.of(like));
        given(likeUseCaseConverter.likeToFilteredResponse(any(Like.class))).willReturn(filteredLikeResponseDto);
        //when
        Slice<FilteredLikeResponseDto> result = readLikeUseCase.filterLike(findLikeDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("[단위][Use Case] Like filter user id Null Condition - 성공 테스트")
    void likeFilterUseCaseUserIdNullConditionSuccessTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(null, category, targetId, null, null, pageRequest);

        FilteredLikeResponseDto filteredLikeResponseDto = new FilteredLikeResponseDto(likeId, userId, category, targetId, createAt);

        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);
        Like like = Like.createNewLikeByCommand(registerLikeCommand);

        given(readLikePort.filterLike(any(LikeFilterCommand.class))).willReturn(List.of(like));
        given(likeUseCaseConverter.likeToFilteredResponse(any(Like.class))).willReturn(filteredLikeResponseDto);
        //when
        Slice<FilteredLikeResponseDto> result = readLikeUseCase.filterLike(findLikeDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("[단위][Use Case] Like filter All Null Condition - 실패 테스트")
    void likeFilterUseCaseNullConditionFailTest() {
        //given
        LikeFilterCommand findLikeDetailCommand = new LikeFilterCommand(null, null, null, null, null, pageRequest);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> readLikeUseCase.filterLike(findLikeDetailCommand));
    }
}