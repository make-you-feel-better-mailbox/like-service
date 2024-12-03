package com.onetwo.likeservice.adapter.in.web.like.request;

import java.time.Instant;

public record FilterLikeSliceRequest(String userId,
                                     Integer category,
                                     Long targetId,
                                     Instant filterStartDate,
                                     Instant filterEndDate,
                                     Integer pageNumber,
                                     Integer pageSize,
                                     String sort) {
}
