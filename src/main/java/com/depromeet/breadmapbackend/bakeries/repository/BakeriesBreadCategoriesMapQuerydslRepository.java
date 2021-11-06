package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.common.enumerate.BreadCategoryType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.depromeet.breadmapbackend.bakeries.domain.QBakeriesBreadCategoriesMap.bakeriesBreadCategoriesMap;

@Repository
@RequiredArgsConstructor
public class BakeriesBreadCategoriesMapQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<BreadCategoryType> findByBakeryId(Long bakeryId) {
        return jpaQueryFactory
                .select(bakeriesBreadCategoriesMap.breadCategories.name)
                .from(bakeriesBreadCategoriesMap)
                .where(bakeriesBreadCategoriesMap.bakeries.id.eq(bakeryId))
                .fetch();
    }
    
}
