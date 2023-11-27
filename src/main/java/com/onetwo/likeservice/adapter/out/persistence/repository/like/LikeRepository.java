package com.onetwo.likeservice.adapter.out.persistence.repository.like;

import com.onetwo.likeservice.adapter.out.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserIdAndCategoryAndTargetIdAndState(String userId, Integer category, Long targetId, boolean persistenceNotDeleted);
}
