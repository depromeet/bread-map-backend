package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenusRepository extends JpaRepository<Menus, Long> {
}
