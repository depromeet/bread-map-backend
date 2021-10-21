package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.depromeet.breadmapbackend.bakeries.domain.QBakeries.bakeries;
import static com.depromeet.breadmapbackend.flags.domain.QFlags.flags;
import static com.depromeet.breadmapbackend.reviews.domain.QBakeryReviews.bakeryReviews;
import static com.depromeet.breadmapbackend.reviews.domain.QMenuReviews.menuReviews;

@Repository
@RequiredArgsConstructor
public class BakeriesQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 더미데이터 더 생성해서 테스트 해볼 것
    @Transactional(readOnly = true)
    public BakeryInfoResponse findByBakeryId(Long bakeryId) {
        return jpaQueryFactory
                .select(Projections.fields(BakeryInfoResponse.class,
                        bakeries,
                        flags.count().as("flagsCount"),
                        bakeryReviews.rating.avg().as("avgRating"),
                        bakeryReviews.count().as("ratingCount"),
                        menuReviews.count().as("menuReviewsCount")))
                .from(bakeries)
                .join(flags)
                .on(flags.bakeries.id.eq(bakeryId))
                .join(menuReviews)
                .on(menuReviews.bakeries.id.eq(bakeryId))
                .join(bakeryReviews)
                .on(bakeryReviews.bakeries.id.eq(bakeryId))
                .where(bakeries.id.eq(bakeryId))
                .groupBy(bakeries.id)
                .fetchOne();
    }
}
