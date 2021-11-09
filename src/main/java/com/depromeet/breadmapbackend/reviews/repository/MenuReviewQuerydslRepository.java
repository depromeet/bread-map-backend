package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.dto.SimpleMenuReviewResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.depromeet.breadmapbackend.reviews.domain.QMenuReviews.menuReviews;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MenuReviewQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 생성날짜 기준(최신순) MenuReviews 리스트 반환(contents만 반환하는 simple형)
     */
    public List<SimpleMenuReviewResponse> findSimpleMenuReviewListByBakeryId(Long bakeryId, Long offset, Long limit) {
        return jpaQueryFactory
                .select(Projections.fields(SimpleMenuReviewResponse.class,
                        menuReviews.contents))
                .from(menuReviews)
                .where(menuReviews.bakeries.id.eq(bakeryId))
                .orderBy(menuReviews.createdDateTime.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    /**
     * 생성날짜 기준(최신순) MenuReviews 리스트 반환(offset/limit 직접 사용)
     */
    public List<MenuReviewResponse> findMenuReviewListByBakeryId(Long bakeryId, Long offset, Long limit) {
        return jpaQueryFactory
                .select(Projections.fields(MenuReviewResponse.class,
                        menuReviews.id.as("menuReviewId"),
                        menuReviews.members.id.as("memberId"),
                        menuReviews.menus.id.as("menuId"),
                        menuReviews.imgPath.as("imgPathList"),
                        menuReviews.contents.as("contents"),
                        menuReviews.rating.as("rating"),
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

    /**
     * 생성날짜 기준(최신순) MenuReviews 리스트 반환(pageable 사용)
     */
    public Slice<MenuReviewResponse> findMenuReviewPageableByBakeryId(Long bakeryId, Pageable pageable) {
        List<MenuReviewResponse> menuReviewResponsesList = jpaQueryFactory
                .select(Projections.fields(MenuReviewResponse.class,
                        menuReviews.id.as("menuReviewId"),
                        menuReviews.members.id.as("memberId"),
                        menuReviews.menus.breadCategories.id.as("breadCategoryId"),
                        menuReviews.members.name.as("memberName"),
                        menuReviews.menus.name.as("menuName"),
                        menuReviews.menus.id.as("menuId"),
                        menuReviews.imgPath.as("imgPathList"),
                        menuReviews.contents.as("contents"),
                        menuReviews.rating.as("rating"),
                        menuReviews.lastModifiedDateTime.as("lastModifiedDateTime")))
                .from(menuReviews)
                .where(menuReviews.bakeries.id.eq(bakeryId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<MenuReviewResponse> content = new ArrayList<>();
        for (MenuReviewResponse menuReview: menuReviewResponsesList) {
            content.add(new MenuReviewResponse(menuReview));
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    /**
     * menuReview 기준(리뷰 많은순) BakeryMenu 리스트 반환(offset/limit 직접 사용)
     */
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

    /**
     * menuReview 기준(리뷰 많은순) BakeryMenu 리스트 반환(pageable 사용)
     */
    public Slice<BakeryMenuResponse> findBakeryMenuPageableByBakeryId(Long bakeryId, Pageable pageable) {
        List<BakeryMenuResponse> bakeryMenuResponseList = jpaQueryFactory
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
                .groupBy(menuReviews.menus.id, menuReviews.menus.breadCategories.id, menuReviews.menus.breadCategories.name, menuReviews.menus.name, menuReviews.menus.price, menuReviews.menus.imgPath)
                .orderBy(menuReviews.id.count().desc(), menuReviews.rating.avg().desc()) // 리뷰 많은 순(동일 리뷰 개수면 rating 높은 순)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<BakeryMenuResponse> content = new ArrayList<>();
         for (BakeryMenuResponse eachBakeryMenuResponse: bakeryMenuResponseList) {
            content.add(new BakeryMenuResponse(eachBakeryMenuResponse));

        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    public MenuReviews findByMenuReviewIdAndBakeryId(Long bakeryId, Long menuReviewId) {
        return jpaQueryFactory
                .selectFrom(menuReviews)
                .where(menuReviews.id.eq(menuReviewId)
                        .and(menuReviews.bakeries.id.eq(bakeryId)))
                .fetchOne();
    }


}
