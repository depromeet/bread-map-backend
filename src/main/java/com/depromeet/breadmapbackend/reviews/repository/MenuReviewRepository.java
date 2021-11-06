package com.depromeet.breadmapbackend.reviews.repository;

import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuReviewRepository extends JpaRepository<MenuReviews, Long> {

    void delete(@NonNull MenuReviews menuReviews);

}
