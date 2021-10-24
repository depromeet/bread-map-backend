package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.depromeet.breadmapbackend.bakeries.domain.QBakeries.bakeries;
import static com.depromeet.breadmapbackend.flags.domain.QFlags.flags;
import static com.depromeet.breadmapbackend.reviews.domain.QBakeryReviews.bakeryReviews;
import static com.depromeet.breadmapbackend.reviews.domain.QMenuReviews.menuReviews;

@Repository
@RequiredArgsConstructor
public class BakeriesQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BakeryInfoResponse findByBakeryId(Long bakeryId) {
        return jpaQueryFactory
                .select(Projections.fields(BakeryInfoResponse.class,
                        bakeries,
                        flags.count().as("flagsCount"),
                        bakeryReviews.rating.avg().coalesce(0.0).as("avgRating"),
                        bakeryReviews.count().as("ratingCount"),
                        menuReviews.count().as("menuReviewsCount")))
                .from(bakeries)
                .leftJoin(flags)
                .on(flags.bakeries.id.eq(bakeryId))
                .leftJoin(menuReviews)
                .on(menuReviews.bakeries.id.eq(bakeryId))
                .leftJoin(bakeryReviews)
                .on(bakeryReviews.bakeries.id.eq(bakeryId))
                .where(bakeries.id.eq(bakeryId))
                .groupBy(bakeries.id)
                .fetchOne();
    }

    public Boolean isBakeryExisted(Double latitude, Double longitude) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(bakeries)
                .where(bakeries.latitude.eq(latitude)
                        .and(bakeries.longitude.eq(longitude)))
                .fetchFirst();

        return fetchOne != null;
    }
}
