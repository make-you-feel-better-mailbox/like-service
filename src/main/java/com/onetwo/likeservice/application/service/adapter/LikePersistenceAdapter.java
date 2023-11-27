package com.onetwo.likeservice.application.service.adapter;

import com.onetwo.likeservice.adapter.out.persistence.entity.LikeEntity;
import com.onetwo.likeservice.adapter.out.persistence.repository.like.LikeRepository;
import com.onetwo.likeservice.application.port.in.command.RegisterLikeCommand;
import com.onetwo.likeservice.application.port.out.ReadLikePort;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.common.GlobalStatus;
import com.onetwo.likeservice.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikePersistenceAdapter implements RegisterLikePort, ReadLikePort {

    private final LikeRepository likeRepository;

    @Override
    public Optional<Like> findByUserIdAndCategoryAndTargetId(RegisterLikeCommand registerLikeCommand) {
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByUserIdAndCategoryAndTargetIdAndState(
                registerLikeCommand.getUserId(),
                registerLikeCommand.getCategory(),
                registerLikeCommand.getTargetId(),
                GlobalStatus.PERSISTENCE_NOT_DELETED
        );

        if (optionalLikeEntity.isPresent()) {
            Like like = Like.entityToDomain(optionalLikeEntity.get());

            return Optional.of(like);
        }

        return Optional.empty();
    }

    @Override
    public Like registerLike(Like newLike) {
        LikeEntity likeEntity = LikeEntity.domainToEntity(newLike);

        LikeEntity savedLikeEntity = likeRepository.save(likeEntity);

        return Like.entityToDomain(savedLikeEntity);
    }
}
