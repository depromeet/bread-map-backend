package com.depromeet.breadmapbackend.members.repository;

import com.depromeet.breadmapbackend.members.domain.Members;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.depromeet.breadmapbackend.flags.domain.QFlags.flags;
import static com.depromeet.breadmapbackend.members.domain.QMembers.members;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public Members findBySocialId(String socialId) {
        return jpaQueryFactory
                .selectFrom(members)
                .where(members.socialId.eq(socialId))
                .fetchOne();
    }

    @Transactional(readOnly = true)
    public Members findByIdAndBakeryId(Long memberId, Long bakeryId) {
        return jpaQueryFactory
                .select(members)
                .from(members)
                .join(flags)
                .on(flags.members.id.eq(memberId)
                        .and(flags.bakeries.id.eq(bakeryId)))
                .where(members.id.eq(memberId))
                .fetchOne();
    }
}
