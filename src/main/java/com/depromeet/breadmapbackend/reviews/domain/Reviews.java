package com.depromeet.breadmapbackend.reviews.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.breadShops.domain.BreadShops;
import com.depromeet.breadmapbackend.common.domain.Images;
import com.depromeet.breadmapbackend.members.domain.Members;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reviews extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_shop_id")
    private BreadShops breadShop;

    @OneToMany(mappedBy = "reviews")
    private List<ReviewsFiltersMap> reviewsFiltersMaps = new ArrayList<>();

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer rating;

    @OneToMany(mappedBy = "reviews")
    private List<Images> reviewImgPathList = new ArrayList<>();
}
