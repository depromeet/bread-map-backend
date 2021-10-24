package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.BreadCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreadCategoriesRepository extends JpaRepository<BreadCategories, Long> {
}

