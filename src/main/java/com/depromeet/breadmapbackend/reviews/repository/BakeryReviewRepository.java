package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BakeryReviewRepository extends JpaRepository<BakeryReviews, Long> {
}
