package com.onetwo.likeservice.application.service.adapter;

import com.onetwo.likeservice.adapter.out.persistence.entity.LikeEntity;
import com.onetwo.likeservice.adapter.out.persistence.repository.like.LikeRepository;
import com.onetwo.likeservice.application.port.out.ReadLikePort;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.application.port.out.UpdateLikePort;
import com.onetwo.likeservice.common.GlobalStatus;
import com.onetwo.likeservice.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikePersistenceAdapter implements RegisterLikePort, ReadLikePort, UpdateLikePort {

    private final LikeRepository likeRepository;

    @Override
    public Optional<Like> findByUserIdAndCategoryAndTargetId(String userId, Integer category, Long targetId) {
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByUserIdAndCategoryAndTargetIdAndState(
                userId,
                category,
                targetId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
        );

        if (optionalLikeEntity.isPresent()) {
            Like like = Like.entityToDomain(optionalLikeEntity.get());

            return Optional.of(like);
        }

        return Optional.empty();
    }

    @Override
    public int countLikeByCategoryAndTargetId(Integer category, Long targetId) {
        Integer countLike = likeRepository.countByCategoryAndTargetIdAndState(category, targetId, GlobalStatus.PERSISTENCE_NOT_DELETED);

        return countLike == null ? 0 : countLike;
    }

    @Override
    public Like registerLike(Like newLike) {
        LikeEntity likeEntity = LikeEntity.domainToEntity(newLike);

        LikeEntity savedLikeEntity = likeRepository.save(likeEntity);

        return Like.entityToDomain(savedLikeEntity);
    }

    @Override
    public void updateLike(Like like) {
        LikeEntity likeEntity = LikeEntity.domainToEntity(like);
        likeRepository.save(likeEntity);
    }
}
