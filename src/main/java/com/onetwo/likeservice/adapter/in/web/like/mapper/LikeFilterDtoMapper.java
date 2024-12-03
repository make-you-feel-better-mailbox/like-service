package com.onetwo.likeservice.adapter.in.web.like.mapper;

import com.onetwo.likeservice.adapter.in.web.like.request.FilterLikeSliceRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.FilteredLikeResponse;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.application.port.in.response.FilteredLikeResponseDto;
import org.springframework.data.domain.Slice;

public interface LikeFilterDtoMapper {
    LikeFilterCommand filterRequestToCommand(FilterLikeSliceRequest filterLikeSliceRequest);

    Slice<FilteredLikeResponse> dtoToFilteredLikeResponse(Slice<FilteredLikeResponseDto> filteredLikeResponseDto);
}
