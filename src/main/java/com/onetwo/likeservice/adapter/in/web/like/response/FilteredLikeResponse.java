package com.onetwo.likeservice.adapter.in.web.like.response;

import java.time.Instant;

public record FilteredLikeResponse(long likeId,
                                   String userId,
                                   int category,
                                   long targetId,
                                   Instant createdDate) {
}
