package com.onetwo.likeservice.adapter.in.web.like.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.likeservice.adapter.in.web.config.TestHeader;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.in.usecase.RegisterLikeUseCase;
import com.onetwo.likeservice.common.GlobalStatus;
import com.onetwo.likeservice.common.GlobalUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(TestHeader.class)
class LikeFilterControllerBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterLikeUseCase registerLikeUseCase;

    @Autowired
    private TestHeader testHeader;

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
    private final int likeCount = 23;

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] Like Filter 조회 성공 - 성공 테스트")
    void getFilteredLikeSuccessTest() throws Exception {
        //given
        RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId);
        registerLikeUseCase.registerLike(registerLikeCommand);

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
                        .headers(testHeader.getRequestHeader())
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("filtering-like",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key")
                                ),
                                queryParameters(
                                        parameterWithName(categoryPath).description("조회할 like의 target category"),
                                        parameterWithName(targetIdPath).description("조회할 like의 target id"),
                                        parameterWithName(pageNumber).description("조회할 like slice 페이지 번호"),
                                        parameterWithName(pageSize).description("조회할 like slice size"),
                                        parameterWithName(userIdQueryStringPath).description("조회할 like의 등록 user id"),
                                        parameterWithName(filterStartDatePath).description("조회할 like의 시작 날짜"),
                                        parameterWithName(filterEndDatePath).description("조회할 like의 끝 날짜")
                                ),
                                responseFields(
                                        fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("like List"),
                                        fieldWithPath("content[].likeId").type(JsonFieldType.NUMBER).description("like의 id"),
                                        fieldWithPath("content[].category").type(JsonFieldType.NUMBER).description("like가 등록돼 있는 target의 category (1: posting, 2: comment)"),
                                        fieldWithPath("content[].targetId").type(JsonFieldType.NUMBER).description("like가 등록돼 있는 target의 target id"),
                                        fieldWithPath("content[].userId").type(JsonFieldType.STRING).description("like 작성자 user id"),
                                        fieldWithPath("content[].createdDate").type(JsonFieldType.STRING).description("like 작성 일자"),
                                        fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageable object"),
                                        fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                                        fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("조회 한 size"),
                                        fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("sort object"),
                                        fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("sort 요청 여부"),
                                        fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort 여부"),
                                        fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsort 여부"),
                                        fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("대상 시작 번호"),
                                        fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                        fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("List 크기"),
                                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                                        fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort object"),
                                        fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort 요청 여부"),
                                        fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort 여부"),
                                        fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsort 여부"),
                                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                        fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("처음인지 여부"),
                                        fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막인지 여부"),
                                        fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("비어있는지 여부")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] Like Filter user id null 조회 성공 - 성공 테스트")
    void getFilteredLikeNullSuccessTest() throws Exception {
        //given
        for (int i = 0; i <= likeCount; i++) {
            RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId + i, category, targetId);
            registerLikeUseCase.registerLike(registerLikeCommand);
        }

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
                        .headers(testHeader.getRequestHeader())
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] Like Filter category null 조회 성공 - 성공 테스트")
    void getFilteredLikeNullCategorySuccessTest() throws Exception {
        //given
        for (int i = 0; i <= likeCount; i++) {
            RegisterLikeCommand registerLikeCommand = new RegisterLikeCommand(userId, category, targetId + i);
            registerLikeUseCase.registerLike(registerLikeCommand);
        }

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
                        .headers(testHeader.getRequestHeader())
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}