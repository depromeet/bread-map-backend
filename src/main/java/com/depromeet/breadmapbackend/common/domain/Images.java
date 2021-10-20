package com.depromeet.breadmapbackend.common.domain;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.common.enumerate.ImageType;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Images extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType imageType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private BakeryReviews bakeryReviews;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_review_id")
    private MenuReviews menuReviews;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bakery_id")
    private Bakeries bakeries;

    @Column(nullable = false)
    private String imgPath;
}
