package com.onetwo.likeservice.application.port.in.command;

import com.onetwo.likeservice.application.port.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class CountLikeCommand extends SelfValidating<CountLikeCommand> {

    @NotNull
    private final Integer category;

    @NotNull
    private final Long targetId;

    public CountLikeCommand(Integer category, Long targetId) {
        this.category = category;
        this.targetId = targetId;
        this.validateSelf();
    }
}
