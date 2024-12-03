package com.onetwo.likeservice.adapter.out.persistence.repository.like;

import com.onetwo.likeservice.adapter.out.persistence.entity.LikeEntity;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;

import java.util.List;

public interface QLikeRepository {
    List<LikeEntity> sliceByCommand(LikeFilterCommand likeFilterCommand);
}
