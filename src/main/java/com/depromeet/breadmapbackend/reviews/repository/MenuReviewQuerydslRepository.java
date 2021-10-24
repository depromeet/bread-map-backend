package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.depromeet.breadmapbackend.reviews.domain.QMenuReviews.menuReviews;

@Repository
@RequiredArgsConstructor
public class MenuReviewQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 더미데이터 더 생성해서 테스트 해볼 것
    public List<MenuReviewResponse> findMenuReviewListByBakeryId(Long bakeryId, Long offset, Long limit) {
        return jpaQueryFactory
                .select(Projections.fields(MenuReviewResponse.class,
                        menuReviews.id.as("menuReviewId"),
                        menuReviews.members.id.as("memberId"),
                        menuReviews.menus.id.as("menuId"),
                        menuReviews.imgPath.as("imgPathList"),
                        menuReviews.contents,
                        menuReviews.rating,
                        menuReviews.menus.name.as("menuName"),
                        menuReviews.members.name.as("memberName"),
                        menuReviews.menus.breadCategories.id.as("breadCategoryId"),
                        menuReviews.lastModifiedDateTime))
                .from(menuReviews)
                .where(menuReviews.bakeries.id.eq(bakeryId))
                .groupBy(menuReviews.id, menuReviews.menus.name, menuReviews.members.name, menuReviews.menus.breadCategories.id)
                .orderBy(menuReviews.createdDateTime.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public List<BakeryMenuResponse> findBakeryMenuListByBakeryId(Long bakeryId, Long offset, Long limit) {
        return jpaQueryFactory
                .select(Projections.fields(BakeryMenuResponse.class,
                        menuReviews.menus.id.as("menuId"),
                        menuReviews.menus.breadCategories.id.as("breadCategoryId"),
                        menuReviews.menus.breadCategories.name.as("breadCategoryName"),
                        menuReviews.menus.name.as("menuName"),
                        menuReviews.menus.price.as("price"),
                        menuReviews.menus.imgPath.as("imgPath"),
                        menuReviews.rating.avg().as("avgRating")))
                .from(menuReviews)
                .where(menuReviews.bakeries.id.eq(bakeryId))
                .groupBy(menuReviews.menus.id,menuReviews.menus.breadCategories.id,menuReviews.menus.breadCategories.name,menuReviews.menus.name,menuReviews.menus.price,menuReviews.menus.imgPath)
                .orderBy(menuReviews.id.count().desc(), menuReviews.rating.avg().desc()) // 리뷰 많은 순(동일 리뷰 개수면 rating 높은 순)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public MenuReviews findByMenuReviewIdAndBakeryId(Long bakeryId, Long menuReviewId) {
        return jpaQueryFactory
                .selectFrom(menuReviews)
                .where(menuReviews.id.eq(menuReviewId)
                        .and(menuReviews.bakeries.id.eq(bakeryId)))
                .fetchOne();
    }
}
