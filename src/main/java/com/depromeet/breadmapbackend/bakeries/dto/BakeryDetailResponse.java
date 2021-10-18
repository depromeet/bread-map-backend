package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewsResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryDetailResponse {

    private Long bakeryId;
    private String bakeryName;
    private String address;
    private Integer flagsCount;
    private Integer reviewsCount;
    private FlagType flagType;
    private String businessHour;
    private List<String> websiteUrlList = new ArrayList<>();
    private String telNumber;
    @ApiModelProperty(value = "wifi, pet, parking, takeOut")
    private List<String> basicInfoList = new ArrayList<>();
    private List<String> imgPathList;
    @ApiModelProperty(value = "빵집 별점(평균 점수)")
    private Float avgRating;
    @ApiModelProperty(value = "빵집 별점(로그인한 회원)")
    private Float personalRating;
    @ApiModelProperty(value = "빵집 별점 매긴 사람 수")
    private Integer ratingCount;
    private List<MenuReviewsResponse> menuReviewsResponseList = new ArrayList<>();
    private List<BakeryMenuListResponse> bakeryMenuListResponseList = new ArrayList<>();

}
