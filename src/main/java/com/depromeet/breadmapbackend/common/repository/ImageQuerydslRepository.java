package com.depromeet.breadmapbackend.common.repository;

import com.depromeet.breadmapbackend.common.domain.Images;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.depromeet.breadmapbackend.common.domain.QImages.images;

@Repository
@RequiredArgsConstructor
public class ImageQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 더미데이터 더 생성해서 테스트 해볼 것
    @Transactional(readOnly = true)
    public Images findFirstByBakeryId(Long bakeryId) {
        return jpaQueryFactory
                .selectFrom(images)
                .where(images.bakeries.id.eq(bakeryId))
                .orderBy(images.createdDateTime.asc())
                .fetchFirst();
    }

    @Transactional(readOnly = true)
    public List<Images> findByMenuReviewId(Long menuReviewId) {
        return jpaQueryFactory
                .selectFrom(images)
                .where(images.menuReviews.id.eq(menuReviewId))
                .fetch();
    }
}
