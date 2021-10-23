package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuReviewRepository extends JpaRepository<MenuReviews, Long> {
}
