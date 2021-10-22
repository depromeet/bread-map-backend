package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.common.enumerate.BasicInfoType;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
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
public class BakeryDetailResponse {

    // BakeryInfoResponse
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
    @ApiModelProperty(value = "WIFI, PET, PARKING, TAKEOUT")
    private List<BasicInfoType> basicInfoList = new ArrayList<>();
    // Images
    @ApiModelProperty(value = "빵집 대표 이미지")
    private String imgPath;
    // FlagTypeReviewRatingResponse
    @ApiModelProperty(value = "빵집 별점(로그인한 회원), 별점 없을 경우 null 반환")
    private Long personalRating;
    private FlagType flagType;
    private List<MenuReviewResponse> menuReviewsResponseList = new ArrayList<>();
    private List<BakeryMenuResponse> bakeryMenuListResponseList = new ArrayList<>();

}
