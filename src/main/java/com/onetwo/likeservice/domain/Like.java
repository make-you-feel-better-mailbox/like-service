package com.onetwo.likeservice.domain;

import com.onetwo.likeservice.adapter.out.persistence.entity.LikeEntity;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Like extends BaseDomain {

    private Long id;
    private String userId;
    /* 1: posing 2:comment */
    private Integer category;
    private Long targetId;
    private Boolean state;

    public static Like createNewLikeByCommand(RegisterLikeCommand registerLikeCommand) {
        Like like = new Like(
                null,
                registerLikeCommand.getUserId(),
                registerLikeCommand.getCategory(),
                registerLikeCommand.getTargetId(),
                false
        );

        like.setDefaultState();
        return like;
    }

    public static Like entityToDomain(LikeEntity likeEntity) {
        Like like = new Like(
                likeEntity.getId(),
                likeEntity.getUserId(),
                likeEntity.getCategory(),
                likeEntity.getTargetId(),
                likeEntity.getState()
        );

        like.setMetaDataByEntity(likeEntity);
        return like;
    }

    private void setDefaultState() {
        setCreatedAt(Instant.now());
        setCreateUser(this.userId);
    }

    public void deleteLike() {
        this.state = true;
        setUpdatedAt(Instant.now());
        setUpdateUser(this.userId);
    }

    public boolean isDeleted() {
        return this.state;
    }

    public boolean isSameUserId(String userId) {
        return this.userId.equals(userId);
    }
}
