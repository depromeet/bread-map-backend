package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
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
                        flags.countDistinct().as("flagsCount"),
                        bakeryReviews.rating.avg().coalesce(0.0).as("avgRating"),
                        bakeryReviews.countDistinct().as("ratingCount"),
                        bakeries.menuReviewsList.size().castToNum(Long.class).as("menuReviewsCount"),
                        bakeries.menusList.size().castToNum(Long.class).as("menusCount")))
                .from(bakeries)
                .leftJoin(flags)
                .on(flags.bakeries.id.eq(bakeryId).and(flags.flagType.eq(FlagType.GONE)))
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
