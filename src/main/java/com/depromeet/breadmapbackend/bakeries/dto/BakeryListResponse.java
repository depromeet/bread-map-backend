package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BakeryListResponse {

    // BakeryInfoResponse
    private Long bakeryId;
    private String bakeryName;
    private Double latitude;
    private Double longitude;
    private String address;
    private Long flagsCount;
    private Long menuReviewsCount;
    @ApiModelProperty(value = "빵집 별점(평균 점수), 별점 없을 경우 0.0 반환")
    private Double avgRating;
    @ApiModelProperty(value = "빵집 별점 매긴 사람 수")
    private Long ratingCount;
    @ApiModelProperty(value = "빵집 대표 이미지")
    private String imgPath;

    private List<MenuReviewResponse> menuReviewList = new ArrayList<>();
    @ApiModelProperty(value = "빵집에 존재하는 breadCategoryList")
    private List<String> breadCategoryList;
}
