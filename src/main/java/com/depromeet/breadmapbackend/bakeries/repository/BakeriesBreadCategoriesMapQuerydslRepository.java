package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.BakeriesBreadCategoriesMap;
import com.depromeet.breadmapbackend.bakeries.domain.QBakeriesBreadCategoriesMap;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.common.enumerate.BreadCategoryType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.depromeet.breadmapbackend.bakeries.domain.QBakeries.bakeries;
import static com.depromeet.breadmapbackend.bakeries.domain.QBakeriesBreadCategoriesMap.bakeriesBreadCategoriesMap;
import static com.depromeet.breadmapbackend.flags.domain.QFlags.flags;
import static com.depromeet.breadmapbackend.reviews.domain.QBakeryReviews.bakeryReviews;
import static com.depromeet.breadmapbackend.reviews.domain.QMenuReviews.menuReviews;

@Repository
@RequiredArgsConstructor
public class BakeriesBreadCategoriesMapQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> findByBakeryId(Long bakeryId) {
        return jpaQueryFactory
                .select(Projections.fields(String.class,
                        bakeriesBreadCategoriesMap.breadCategories.name))
                .from(bakeriesBreadCategoriesMap)
                .where(bakeriesBreadCategoriesMap.bakeries.id.eq(bakeryId))
                .fetch();
    }
    
}
