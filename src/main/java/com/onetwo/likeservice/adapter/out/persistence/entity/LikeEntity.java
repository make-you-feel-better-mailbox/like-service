package com.onetwo.likeservice.adapter.out.persistence.entity;

import com.onetwo.likeservice.adapter.out.persistence.repository.converter.BooleanNumberConverter;
import com.onetwo.likeservice.domain.Like;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@Table(name = "target_like")
public class LikeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false, length = 1)
    @Convert(converter = BooleanNumberConverter.class)
    private Boolean state;

    public LikeEntity(Long id, String userId, Integer category, Long targetId, Boolean state) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.targetId = targetId;
        this.state = state;
    }

    public static LikeEntity domainToEntity(Like like) {
        LikeEntity likeEntity = new LikeEntity(
                like.getId(),
                like.getUserId(),
                like.getCategory(),
                like.getTargetId(),
                like.getState()
        );

        likeEntity.setMetaDataByDomain(like);
        return likeEntity;
    }
}
