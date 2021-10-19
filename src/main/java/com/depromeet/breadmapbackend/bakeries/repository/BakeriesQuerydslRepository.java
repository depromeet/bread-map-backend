package com.depromeet.breadmapbackend.bakeries.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BakeriesQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
