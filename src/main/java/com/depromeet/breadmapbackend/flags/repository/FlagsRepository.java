package com.depromeet.breadmapbackend.flags.repository;

import com.depromeet.breadmapbackend.members.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagsRepository extends JpaRepository<Members, Long> {
}
