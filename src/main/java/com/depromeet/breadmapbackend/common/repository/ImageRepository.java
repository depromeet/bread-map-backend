package com.depromeet.breadmapbackend.common.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Bakeries, Long> {
}
