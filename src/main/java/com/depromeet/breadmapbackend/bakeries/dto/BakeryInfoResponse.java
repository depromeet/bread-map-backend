package com.depromeet.breadmapbackend.bakeries.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryInfoResponse {

    private Long bakeryId;
    private String bakeryName;
    private String address;
    private Long flagsCount;
    private Long menuReviewsCount;
    private String businessHour;
    private List<String> websiteUrlList = new ArrayList<>();
    private String telNumber;
    @ApiModelProperty(value = "빵집 별점(평균 점수), 별점 없을 경우 null 반환")
    private Double avgRating;
    @ApiModelProperty(value = "빵집 별점 매긴 사람 수")
    private Long ratingCount;
    @ApiModelProperty(value = "wifi, pet, parking, takeOut")
    private List<String> basicInfoList = new ArrayList<>();
}
