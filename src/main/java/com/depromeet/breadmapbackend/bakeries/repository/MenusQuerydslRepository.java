package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.depromeet.breadmapbackend.bakeries.domain.QMenus.menus;

@Repository
@RequiredArgsConstructor
public class MenusQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Menus findByMenuNameBakeryId(String menuName, Long bakeryId) {
        return jpaQueryFactory
                .selectFrom(menus)
                .where(menus.name.eq(menuName)
                        .and(menus.bakeries.id.eq(bakeryId)))
                .fetchOne();
    }

    public List<String> findByBreadCategoryIdBakeryId(Long categoryId, Long bakeryId) {
        return jpaQueryFactory
                .select(Projections.fields(String.class, menus.name))
                .from(menus)
                .where(menus.breadCategories.id.eq(categoryId)
                        .and(menus.bakeries.id.eq(bakeryId)))
                .fetch();
    }
}
