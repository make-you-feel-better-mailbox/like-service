package com.onetwo.likeservice.application.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import onetwo.mailboxcommonconfig.common.SelfValidating;

@Getter
public final class LikeTargetCheckCommand extends SelfValidating<LikeTargetCheckCommand> {

    @NotEmpty
    private final String userId;

    @NotNull
    private final Integer category;

    @NotNull
    private final Long targetId;

    public LikeTargetCheckCommand(String userId, Integer category, Long targetId) {
        this.userId = userId;
        this.category = category;
        this.targetId = targetId;
        this.validateSelf();
    }
}
