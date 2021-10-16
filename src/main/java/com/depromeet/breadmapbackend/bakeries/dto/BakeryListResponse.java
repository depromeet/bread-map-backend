package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryListResponse {

    private Long bakeryId;
    private String bakeryName;
    private Integer flagsCount; // TODO 가본곳만 카운트인지, 가볼곳도 카운트인지 확인 필요(select)
    private Double latitude;
    private Double longitude;
    private String address;
    private List<String> imgPathList;
    private Float rating;
    private Integer reviewsCount;
    private List<BakeryReviews> bakeryReviewsList; // select
    // TODO 외부 사진(대표사진) 하나 넣어주는 건 어떤지? 준다고 하면 어떻게 내려줄건지
}
