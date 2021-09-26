package com.depromeet.breadmapbackend.filters.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.reviews.domain.ReviewsFiltersMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "Filters")
    private List<ReviewsFiltersMap> reviewsFiltersMapList = new ArrayList<>();
}
