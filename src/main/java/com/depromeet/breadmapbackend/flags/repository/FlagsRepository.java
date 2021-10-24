package com.depromeet.breadmapbackend.flags.repository;

import com.depromeet.breadmapbackend.flags.domain.Flags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagsRepository extends JpaRepository<Flags, Long> {
}
