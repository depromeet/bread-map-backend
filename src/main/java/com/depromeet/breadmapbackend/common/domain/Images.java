package com.depromeet.breadmapbackend.common.domain;

import com.depromeet.breadmapbackend.breadShops.domain.BreadShops;
import com.depromeet.breadmapbackend.common.enumerate.ImageType;
import com.depromeet.breadmapbackend.reviews.domain.Reviews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @Enumerated
    @Column(nullable = false)
    private ImageType imageType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Reviews reviews;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bread_shop_id")
    private BreadShops breadShops;

    @Column(nullable = false)
    private String imgPath;
}
