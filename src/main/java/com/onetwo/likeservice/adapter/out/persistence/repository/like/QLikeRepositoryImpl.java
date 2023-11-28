package com.onetwo.likeservice.adapter.out.persistence.repository.like;

import com.onetwo.likeservice.adapter.out.persistence.entity.LikeEntity;
import com.onetwo.likeservice.application.port.in.command.LikeFilterCommand;
import com.onetwo.likeservice.common.GlobalStatus;
import com.onetwo.likeservice.common.utils.QueryDslUtil;
import com.onetwo.likeservice.common.utils.SliceUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.onetwo.likeservice.adapter.out.persistence.entity.QLikeEntity.likeEntity;

public class QLikeRepositoryImpl extends QuerydslRepositorySupport implements QLikeRepository {

    private final JPAQueryFactory factory;

    public QLikeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(LikeEntity.class);
        this.factory = jpaQueryFactory;
    }

    @Override
    public List<LikeEntity> sliceByCommand(LikeFilterCommand likeFilterCommand) {
        return factory.select(likeEntity)
                .from(likeEntity)
                .where(filterCondition(likeFilterCommand),
                        likeEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED))
                .limit(SliceUtil.getSliceLimit(likeFilterCommand.getPageable().getPageSize()))
                .offset(likeFilterCommand.getPageable().getOffset())
                .orderBy(likeEntity.id.desc())
                .fetch();
    }

    private Predicate filterCondition(LikeFilterCommand likeFilterCommand) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QueryDslUtil.ifConditionExistAddEqualPredicate(likeFilterCommand.getCategory(), likeEntity.category, booleanBuilder);
        QueryDslUtil.ifConditionExistAddEqualPredicate(likeFilterCommand.getTargetId(), likeEntity.targetId, booleanBuilder);
        QueryDslUtil.ifConditionExistAddEqualPredicate(likeFilterCommand.getUserId(), likeEntity.userId, booleanBuilder);
        QueryDslUtil.ifConditionExistAddGoePredicate(likeFilterCommand.getFilterStartDate(), likeEntity.createdAt, booleanBuilder);
        QueryDslUtil.ifConditionExistAddLoePredicate(likeFilterCommand.getFilterEndDate(), likeEntity.createdAt, booleanBuilder);

        return booleanBuilder;
    }
}
