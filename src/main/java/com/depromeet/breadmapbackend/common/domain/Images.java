package com.depromeet.breadmapbackend.common.domain;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.common.util.StringListConverter;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "bakery_review_id")
    private BakeryReviews bakeryReviews;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "menu_review_id")
    private MenuReviews menuReviews;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "bakery_id")
    @ApiModelProperty(value = "빵집리뷰, 메뉴리뷰, 빵집 개척 시 bakeryId 무조건 가지고 간다")
    private Bakeries bakeries;

    @Column(nullable = false)
    @Convert(converter = StringListConverter.class)
    private List<String> imgPath = new ArrayList<>();
}
