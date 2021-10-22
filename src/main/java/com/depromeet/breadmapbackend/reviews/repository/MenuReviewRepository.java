package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuReviewRepository extends JpaRepository<Bakeries, Long> {
}
