package com.depromeet.breadmapbackend.members.repository;

import com.depromeet.breadmapbackend.members.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Members, Long> {

    Members findMembersById(Long memberId);
}
