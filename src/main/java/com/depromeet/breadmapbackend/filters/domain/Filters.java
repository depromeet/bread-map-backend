package com.depromeet.breadmapbackend.filters.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.common.enumerate.FilterType;
import com.depromeet.breadmapbackend.reviews.domain.ReviewsFiltersMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Filters extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "filter_id")
    private Long id;

    @Enumerated
    @Column(nullable = false)
    private FilterType filterType;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "filters")
    private List<ReviewsFiltersMap> reviewsFiltersMapList = new ArrayList<>();
}
