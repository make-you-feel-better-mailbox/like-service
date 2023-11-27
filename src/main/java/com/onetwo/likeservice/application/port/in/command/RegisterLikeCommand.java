package com.onetwo.likeservice.application.port.in.command;

import com.onetwo.likeservice.application.port.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class RegisterLikeCommand extends SelfValidating<RegisterLikeCommand> {

    @NotEmpty
    private final String userId;

    @NotNull
    private final Integer category;

    @NotNull
    private final Long targetId;

    public RegisterLikeCommand(String userId, Integer category, Long targetId) {
        this.userId = userId;
        this.category = category;
        this.targetId = targetId;
        this.validateSelf();
    }
}
