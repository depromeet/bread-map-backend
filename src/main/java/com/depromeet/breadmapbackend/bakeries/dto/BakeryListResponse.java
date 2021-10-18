package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.reviews.dto.MenuReviewsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryListResponse {

    private Long bakeryId;
    private String bakeryName;
    private Integer flagsCount; // 가본 곳 select
    private Double latitude;
    private Double longitude;
    private String address;
    private List<String> imgPathList;
    private Float rating;
    private Integer reviewsCount;
    private String businessHour;
    private List<MenuReviewsResponse> menuReviewList; // select
    private List<String> breadCategoryList;
    private List<String> websiteUrlList = new ArrayList<>();
    private String telNumber;
    private List<String> basicInfoList = new ArrayList<>();
}
