package com.onetwo.likeservice.adapter.in.web.like.api;

import com.onetwo.likeservice.adapter.in.web.like.mapper.LikeFilterDtoMapper;
import com.onetwo.likeservice.adapter.in.web.like.request.FilterLikeSliceRequest;
import com.onetwo.likeservice.adapter.in.web.like.response.FilteredLikeResponse;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.application.port.in.response.FilteredLikeResponseDto;
import com.onetwo.likeservice.application.port.in.usecase.ReadLikeUseCase;
import com.onetwo.likeservice.common.GlobalUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeFilterController {

    private final ReadLikeUseCase readLikeUseCase;
    private final LikeFilterDtoMapper likeFilterDtoMapper;

    /**
     * Get Filtered like inbound adapter
     *
     * @param filterLikeSliceRequest filter condition and pageable
     * @return content and slice data
     */
    @GetMapping(GlobalUrl.LIKE_FILTER)
    public ResponseEntity<Slice<FilteredLikeResponse>> filterLike(@ModelAttribute FilterLikeSliceRequest filterLikeSliceRequest) {
        LikeFilterCommand likeFilterCommand = likeFilterDtoMapper.filterRequestToCommand(filterLikeSliceRequest);
        Slice<FilteredLikeResponseDto> filteredLikeResponseDto = readLikeUseCase.filterLike(likeFilterCommand);
        return ResponseEntity.ok().body(likeFilterDtoMapper.dtoToFilteredLikeResponse(filteredLikeResponseDto));
    }
}
