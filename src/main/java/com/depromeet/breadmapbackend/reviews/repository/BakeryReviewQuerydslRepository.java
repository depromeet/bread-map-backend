package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.depromeet.breadmapbackend.reviews.domain.QBakeryReviews.bakeryReviews;

@Repository
@RequiredArgsConstructor
public class BakeryReviewQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BakeryReviews findByBakeryIdMemberId(Long bakeryId, Long memberId) {
        return jpaQueryFactory
                .selectFrom(bakeryReviews)
                .where(bakeryReviews.bakeries.id.eq(bakeryId)
                        .and(bakeryReviews.members.id.eq(memberId)))
                .fetchOne();
    }

}
