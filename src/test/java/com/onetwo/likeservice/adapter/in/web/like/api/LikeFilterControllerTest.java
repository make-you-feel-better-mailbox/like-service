package com.onetwo.likeservice.adapter.in.web.like.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.likeservice.adapter.in.web.config.TestConfig;
import com.onetwo.likeservice.adapter.in.web.like.mapper.LikeFilterDtoMapper;
import com.onetwo.likeservice.adapter.in.web.like.request.FilterLikeSliceRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.FilteredLikeResponse;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.application.port.in.response.FilteredLikeResponseDto;
import com.onetwo.likeservice.application.port.in.usecase.ReadLikeUseCase;
import com.onetwo.likeservice.common.GlobalUrl;
import com.onetwo.likeservice.common.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LikeFilterController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfig.class
                })
        })
@Import(TestConfig.class)
class LikeFilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReadLikeUseCase readLikeUseCase;

    @MockBean
    private LikeFilterDtoMapper likeFilterDtoMapper;

    private final int category = 1;
    private final String categoryPath = "category";
    private final long targetId = 3;
    private final String targetIdPath = "targetId";
    private final String pageNumber = "pageNumber";
    private final String pageSize = "pageSize";
    private final String userId = "testUserId";
    private final String userIdQueryStringPath = "userId";
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final String filterStartDatePath = "filterStartDate";
    private final String filterEndDatePath = "filterEndDate";
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    @DisplayName("[단위][Web Adapter] Like Filter 조회 성공 - 성공 테스트")
    void getFilteredLikeSuccessTest() throws Exception {
        //given
        LikeFilterCommand likeFilterCommand = new LikeFilterCommand(userId, category, targetId, filterStartDate, filterEndDate, pageRequest);

        List<FilteredLikeResponseDto> filteredLikeResponseDtoList = new ArrayList<>();

        for (int i = 1; i <= pageRequest.getPageSize(); i++) {
            FilteredLikeResponseDto testFilteredLike = new FilteredLikeResponseDto(i, userId, category, targetId, Instant.now());
            filteredLikeResponseDtoList.add(testFilteredLike);
        }

        Slice<FilteredLikeResponseDto> filteredPostingResponseDtoSlice = new SliceImpl<>(filteredLikeResponseDtoList, pageRequest, true);

        List<FilteredLikeResponse> filteredLikeResponseList = filteredLikeResponseDtoList.stream()
                .map(responseDto -> new FilteredLikeResponse(
                        responseDto.likeId(),
                        responseDto.userId(),
                        responseDto.category(),
                        responseDto.targetId(),
                        responseDto.createdDate()
                )).toList();

        Slice<FilteredLikeResponse> filteredLikeResponseSlice = new SliceImpl<>(filteredLikeResponseList, pageRequest, true);

        when(likeFilterDtoMapper.filterRequestToCommand(any(FilterLikeSliceRequest.class))).thenReturn(likeFilterCommand);
        when(readLikeUseCase.filterLike(any(LikeFilterCommand.class))).thenReturn(filteredPostingResponseDtoSlice);
        when(likeFilterDtoMapper.dtoToFilteredLikeResponse(any(Slice.class))).thenReturn(filteredLikeResponseSlice);

        String queryString = UriComponentsBuilder.newInstance()
                .queryParam(pageNumber, pageRequest.getPageNumber())
                .queryParam(pageSize, pageRequest.getPageSize())
                .queryParam(categoryPath, category)
                .queryParam(targetIdPath, targetId)
                .queryParam(userIdQueryStringPath, userId)
                .queryParam(filterStartDatePath, filterStartDate)
                .queryParam(filterEndDatePath, filterEndDate)
                .build()
                .encode()
                .toUriString();
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.LIKE_FILTER + queryString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("[단위][Web Adapter] Like Filter Null 조회 성공 - 성공 테스트")
    void getFilteredLikeNullSuccessTest() throws Exception {
        //given
        LikeFilterCommand likeFilterCommand = new LikeFilterCommand(userId, null, null, null, null, pageRequest);

        List<FilteredLikeResponseDto> filteredLikeResponseDtoList = new ArrayList<>();

        for (int i = 1; i <= pageRequest.getPageSize(); i++) {
            FilteredLikeResponseDto testFilteredLike = new FilteredLikeResponseDto(i, userId, category, targetId + i, Instant.now());
            filteredLikeResponseDtoList.add(testFilteredLike);
        }

        Slice<FilteredLikeResponseDto> filteredPostingResponseDtoSlice = new SliceImpl<>(filteredLikeResponseDtoList, pageRequest, true);

        List<FilteredLikeResponse> filteredLikeResponseList = filteredLikeResponseDtoList.stream()
                .map(responseDto -> new FilteredLikeResponse(
                        responseDto.likeId(),
                        responseDto.userId(),
                        responseDto.category(),
                        responseDto.targetId(),
                        responseDto.createdDate()
                )).toList();

        Slice<FilteredLikeResponse> filteredLikeResponseSlice = new SliceImpl<>(filteredLikeResponseList, pageRequest, true);

        when(likeFilterDtoMapper.filterRequestToCommand(any(FilterLikeSliceRequest.class))).thenReturn(likeFilterCommand);
        when(readLikeUseCase.filterLike(any(LikeFilterCommand.class))).thenReturn(filteredPostingResponseDtoSlice);
        when(likeFilterDtoMapper.dtoToFilteredLikeResponse(any(Slice.class))).thenReturn(filteredLikeResponseSlice);

        String queryString = UriComponentsBuilder.newInstance()
                .queryParam(pageNumber, pageRequest.getPageNumber())
                .queryParam(pageSize, pageRequest.getPageSize())
                .queryParam(userIdQueryStringPath, userId)
                .build()
                .encode()
                .toUriString();
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.LIKE_FILTER + queryString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("[단위][Web Adapter] Like Filter Null user Id 조회 성공 - 성공 테스트")
    void getFilteredLikeNullUserIdSuccessTest() throws Exception {
        //given
        LikeFilterCommand likeFilterCommand = new LikeFilterCommand(null, category, targetId, null, null, pageRequest);

        List<FilteredLikeResponseDto> filteredLikeResponseDtoList = new ArrayList<>();

        for (int i = 1; i <= pageRequest.getPageSize(); i++) {
            FilteredLikeResponseDto testFilteredLike = new FilteredLikeResponseDto(i, userId + i, category, targetId, Instant.now());
            filteredLikeResponseDtoList.add(testFilteredLike);
        }

        Slice<FilteredLikeResponseDto> filteredPostingResponseDtoSlice = new SliceImpl<>(filteredLikeResponseDtoList, pageRequest, true);

        List<FilteredLikeResponse> filteredLikeResponseList = filteredLikeResponseDtoList.stream()
                .map(responseDto -> new FilteredLikeResponse(
                        responseDto.likeId(),
                        responseDto.userId(),
                        responseDto.category(),
                        responseDto.targetId(),
                        responseDto.createdDate()
                )).toList();

        Slice<FilteredLikeResponse> filteredLikeResponseSlice = new SliceImpl<>(filteredLikeResponseList, pageRequest, true);

        when(likeFilterDtoMapper.filterRequestToCommand(any(FilterLikeSliceRequest.class))).thenReturn(likeFilterCommand);
        when(readLikeUseCase.filterLike(any(LikeFilterCommand.class))).thenReturn(filteredPostingResponseDtoSlice);
        when(likeFilterDtoMapper.dtoToFilteredLikeResponse(any(Slice.class))).thenReturn(filteredLikeResponseSlice);

        String queryString = UriComponentsBuilder.newInstance()
                .queryParam(pageNumber, pageRequest.getPageNumber())
                .queryParam(pageSize, pageRequest.getPageSize())
                .queryParam(categoryPath, category)
                .queryParam(targetIdPath, targetId)
                .build()
                .encode()
                .toUriString();
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.LIKE_FILTER + queryString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}