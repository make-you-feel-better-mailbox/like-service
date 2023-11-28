package com.onetwo.likeservice.adapter.in.web.like.mapper;

import com.onetwo.likeservice.adapter.in.web.like.request.FilterLikeSliceRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.FilteredLikeResponse;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.application.port.in.response.FilteredLikeResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LikeFilterDtoMapperImpl implements LikeFilterDtoMapper {
    @Override
    public LikeFilterCommand filterRequestToCommand(FilterLikeSliceRequest filterLikeSliceRequest) {
        Pageable pageable = PageRequest.of(
                filterLikeSliceRequest.pageNumber() == null ? 0 : filterLikeSliceRequest.pageNumber(),
                filterLikeSliceRequest.pageSize() == null ? 10 : filterLikeSliceRequest.pageSize()
        );

        return new LikeFilterCommand(
                filterLikeSliceRequest.userId(),
                filterLikeSliceRequest.category(),
                filterLikeSliceRequest.targetId(),
                filterLikeSliceRequest.filterStartDate(),
                filterLikeSliceRequest.filterEndDate(),
                pageable
        );
    }

    @Override
    public Slice<FilteredLikeResponse> dtoToFilteredLikeResponse(Slice<FilteredLikeResponseDto> filteredLikeResponseDto) {
        List<FilteredLikeResponse> filteredLikeResponseList = filteredLikeResponseDto.getContent().stream()
                .map(response -> new FilteredLikeResponse(
                        response.likeId(),
                        response.userId(),
                        response.category(),
                        response.targetId(),
                        response.createdDate()
                )).toList();

        return new SliceImpl<>(filteredLikeResponseList, filteredLikeResponseDto.getPageable(), filteredLikeResponseDto.hasNext());
    }
}
