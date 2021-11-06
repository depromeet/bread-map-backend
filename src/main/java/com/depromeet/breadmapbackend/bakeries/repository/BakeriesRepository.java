package com.depromeet.breadmapbackend.bakeries.repository;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BakeriesRepository extends JpaRepository<Bakeries, Long> {

    @Query(value = "SELECT b.bakery_id FROM bakeries b where earth_distance(" +
                    "ll_to_earth(b.latitude, b.longitude)," +
                    "ll_to_earth(:latitude, :longitude)) < :range",
            nativeQuery = true)
    List<Long> findByEarthDistance(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("range") Long range);
}
