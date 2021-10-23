package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.depromeet.breadmapbackend.bakeries.domain.QMenus;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.depromeet.breadmapbackend.bakeries.domain.QBakeries.bakeries;
import static com.depromeet.breadmapbackend.bakeries.domain.QMenus.menus;
import static com.depromeet.breadmapbackend.flags.domain.QFlags.flags;
import static com.depromeet.breadmapbackend.reviews.domain.QBakeryReviews.bakeryReviews;
import static com.depromeet.breadmapbackend.reviews.domain.QMenuReviews.menuReviews;

@Repository
@RequiredArgsConstructor
public class MenusQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //@Transactional(readOnly = true)
    public Menus findByMenuNameBakeryId(String menuName, Long bakeryId) {
        return jpaQueryFactory
                .selectFrom(menus)
                .where(menus.name.eq(menuName)
                        .and(menus.bakeries.id.eq(bakeryId)))
                .fetchOne();
    }
}
