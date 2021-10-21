package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.reviews.dto.MenuReviewDetailResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.depromeet.breadmapbackend.reviews.domain.QMenuReviews.menuReviews;

@Repository
@RequiredArgsConstructor
public class MenuReviewQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 더미데이터 더 생성해서 테스트 해볼 것
    @Transactional(readOnly = true)
    public List<MenuReviewDetailResponse> findByBakeryId(Long bakeryId, Long offset, Long limit) {
        return jpaQueryFactory
                .select(Projections.fields(MenuReviewDetailResponse.class,
                        //menuReviews,
                        menuReviews.id.as("menuReviewId"),
                        menuReviews.members.id.as("memberId"),
                        menuReviews.menus.id.as("menuId"),
                        menuReviews.images.imgPath.as("imgPathList"),
                        menuReviews.contents,
                        menuReviews.rating,
                        menuReviews.menus.name.as("menuName"),
                        menuReviews.members.name.as("memberName"),
                        menuReviews.menus.breadCategories.id.as("breadCategoryId"),
                        menuReviews.lastModifiedDateTime))
                .from(menuReviews)
                .where(menuReviews.bakeries.id.eq(bakeryId))
                .groupBy(menuReviews.id, menuReviews.menus.name, menuReviews.members.name, menuReviews.menus.breadCategories.id, menuReviews.images.id)
                .orderBy(menuReviews.createdDateTime.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }
}
