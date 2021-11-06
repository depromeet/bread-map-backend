package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.BreadCategories;
import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.depromeet.breadmapbackend.bakeries.domain.QBreadCategories;
import com.depromeet.breadmapbackend.common.enumerate.BreadCategoryType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.depromeet.breadmapbackend.bakeries.domain.QBreadCategories.breadCategories;
import static com.depromeet.breadmapbackend.bakeries.domain.QMenus.menus;

@Repository
@RequiredArgsConstructor
public class BreadCategoriesQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BreadCategories findByBreadCategoryName(String categoryName) {
        return jpaQueryFactory
                .selectFrom(breadCategories)
                .where(breadCategories.name.eq(BreadCategoryType.valueOf(categoryName)))
                .fetchOne();
    }
}
