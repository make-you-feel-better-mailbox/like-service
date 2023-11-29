package com.onetwo.likeservice.adapter.out.persistence.repository.like;

import com.onetwo.likeservice.adapter.out.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long>, QLikeRepository {
    Optional<LikeEntity> findByUserIdAndCategoryAndTargetIdAndState(String userId, Integer category, Long targetId, boolean persistenceNotDeleted);

    Integer countByCategoryAndTargetIdAndState(Integer category, Long targetId, boolean persistenceNotDeleted);
}
