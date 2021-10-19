package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BakeriesRepository extends JpaRepository<Bakeries, Long> {
}
