package com.onetwo.likeservice.application.port.in.command;

import com.onetwo.likeservice.application.port.SliceRequest;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

@Getter
public final class LikeFilterCommand extends SliceRequest<LikeFilterCommand> {

    private final String userId;

    private final Integer category;

    private final Long targetId;

    private final Instant filterStartDate;

    private final Instant filterEndDate;

    public LikeFilterCommand(String userId, Integer category, Long targetId, Instant filterStartDate, Instant filterEndDate, Pageable pageable) {
        super(pageable);
        this.userId = userId;
        this.category = category;
        this.targetId = targetId;
        this.filterStartDate = filterStartDate;
        this.filterEndDate = filterEndDate;
        this.validateSelf();
    }
}
