package com.depromeet.breadmapbackend.flags.repository;

import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.depromeet.breadmapbackend.flags.domain.QFlags.flags;
import static com.depromeet.breadmapbackend.reviews.domain.QBakeryReviews.bakeryReviews;

@Repository
@RequiredArgsConstructor
public class FlagsQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 더미데이터 더 생성해서 테스트 해볼 것
    @Transactional(readOnly = true)
    public FlagTypeReviewRatingResponse findByMemberIdAndBakeryId(Long memberId, Long bakeryId) {
        return jpaQueryFactory
                .select(Projections.fields(FlagTypeReviewRatingResponse.class,
                        flags.flagType,
                        bakeryReviews.rating.coalesce(0L).as("personalRating")))
                .from(flags)
                .leftJoin(bakeryReviews)
                .on(flags.members.id.eq(memberId)
                        .and(bakeryReviews.members.id.eq(memberId))
                        .and(flags.bakeries.id.eq(bakeryId))
                        .and(bakeryReviews.bakeries.id.eq(bakeryId)))
                .fetchOne();
    }
}
