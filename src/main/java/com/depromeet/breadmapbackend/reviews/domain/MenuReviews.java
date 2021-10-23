package com.depromeet.breadmapbackend.reviews.domain;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.common.util.StringListConverter;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.reviews.dto.CreateMenuReviewsRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MenuReviews extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menus menus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Members members;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bakery_id")
    private Bakeries bakeries;

    @Column(nullable = false, length = 200)
    private String contents;

    @Column(nullable = false)
    private Long rating;

    @Column(nullable = false)
    @Convert(converter = StringListConverter.class)
    private List<String> imgPath = new ArrayList<>();

    @Builder
    public void createMenuReview(CreateMenuReviewsRequest createMenuReviewsRequest, Menus menu, Members member, Bakeries bakery) {
        this.contents = createMenuReviewsRequest.getContents();
        this.rating = createMenuReviewsRequest.getRating();
        this.imgPath = createMenuReviewsRequest.getImgPathList();
        this.menus = menu;
        this.members = member;
        this.bakeries = bakery;
        bakery.getMenuReviewsList().add(this);
    }
}
