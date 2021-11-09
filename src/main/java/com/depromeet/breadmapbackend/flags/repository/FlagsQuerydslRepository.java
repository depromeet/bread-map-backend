package com.depromeet.breadmapbackend.flags.repository;

import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeBakeryIdResponse;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.depromeet.breadmapbackend.bakeries.domain.QBakeries.bakeries;
import static com.depromeet.breadmapbackend.flags.domain.QFlags.flags;
import static com.depromeet.breadmapbackend.reviews.domain.QBakeryReviews.bakeryReviews;

@Repository
@RequiredArgsConstructor
public class FlagsQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FlagTypeReviewRatingResponse findBakeryReviewByBakeryIdMemberId(Long bakeryId, Long memberId) {
        return jpaQueryFactory
                .select(Projections.fields(FlagTypeReviewRatingResponse.class,
                        flags.flagType.as("flagType"),
                        bakeryReviews.rating.coalesce(0L).as("personalRating")))
                .from(flags)
                .leftJoin(bakeryReviews)
                .on(flags.members.id.eq(memberId)
                        .and(bakeryReviews.members.id.eq(memberId))
                        .and(flags.bakeries.id.eq(bakeryId))
                        .and(bakeryReviews.bakeries.id.eq(bakeryId)))
                .where(flags.bakeries.id.eq(bakeryId).and(flags.members.id.eq(memberId)))
                .fetchOne();
    }

    public Flags findByBakeryIdMemberId(Long bakeryId, Long memberId) {
        return jpaQueryFactory
                .selectFrom(flags)
                .where(flags.bakeries.id.eq(bakeryId)
                        .and(flags.members.id.eq(memberId)))
                .fetchOne();
    }

    /**
     * MemberId를 기준으로 flagType과 해당 flag가 꽂힌 bakeryId 반환 (FLAGTYPE NONE 제외하고 조회)
     */
    public List<FlagTypeBakeryIdResponse> findByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(Projections.fields(FlagTypeBakeryIdResponse.class,
                        flags.flagType.as("flagType"),
                        flags.bakeries.id.as("bakeryId")))
                .from(flags)
                .leftJoin(bakeries)
                .on(flags.bakeries.id.eq(bakeries.id))
                .where(flags.members.id.eq(memberId)
                        .and(flags.flagType.notIn(FlagType.NONE)))
                .fetch();
    }
}
