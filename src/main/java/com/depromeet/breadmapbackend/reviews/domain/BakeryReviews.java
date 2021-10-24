package com.depromeet.breadmapbackend.reviews.domain;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.common.util.StringListConverter;
import com.depromeet.breadmapbackend.members.domain.Members;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class BakeryReviews extends BaseEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bakery_review_id")
    private Long id;

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

    public void updateRating(Long rating) {
        this.rating = rating;
    }

    @Builder
    public BakeryReviews(Members members, Bakeries bakeries, String contents, Long rating, List<String> imgPath) {
        this.members = members;
        this.bakeries = bakeries;
        this.contents = contents;
        this.rating = rating;
        this.imgPath = imgPath;
        members.getBakeryReviewsList().add(this);
        bakeries.getBakeryReviewsList().add(this);
    }
}
