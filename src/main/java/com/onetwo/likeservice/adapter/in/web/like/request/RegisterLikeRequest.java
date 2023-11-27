package com.onetwo.likeservice.adapter.in.web.like.request;

import jakarta.validation.constraints.NotNull;

public record RegisterLikeRequest(@NotNull Integer category,
                                  @NotNull Long targetId) {
}
