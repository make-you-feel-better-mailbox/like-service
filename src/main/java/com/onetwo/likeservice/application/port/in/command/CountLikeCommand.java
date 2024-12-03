package com.onetwo.likeservice.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import onetwo.mailboxcommonconfig.common.SelfValidating;

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
