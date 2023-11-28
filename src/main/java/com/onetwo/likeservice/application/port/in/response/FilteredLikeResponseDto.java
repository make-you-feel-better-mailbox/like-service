package com.onetwo.likeservice.application.port.in.response;

import java.time.Instant;

public record FilteredLikeResponseDto(long likeId,
                                      String userId,
                                      int category,
                                      long targetId,
                                      Instant createdDate) {
}
