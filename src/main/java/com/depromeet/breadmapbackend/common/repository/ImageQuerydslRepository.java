package com.depromeet.breadmapbackend.common.repository;

import com.depromeet.breadmapbackend.common.dto.ImageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.depromeet.breadmapbackend.common.domain.QImages.images;

@Repository
@RequiredArgsConstructor
public class ImageQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 더미데이터 더 생성해서 테스트 해볼 것
    @Transactional(readOnly = true)
    public ImageResponse findFirstByBakeryId(Long bakeryId) {
        return jpaQueryFactory
                .select(Projections.fields(ImageResponse.class,
                        images.imgPath))
                .from(images)
                .where(images.bakeries.id.eq(bakeryId))
                .orderBy(images.createdDateTime.asc())
                .fetchFirst();
    }
}
