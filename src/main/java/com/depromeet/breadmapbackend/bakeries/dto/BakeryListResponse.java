package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.reviews.dto.MenuReviewsResponse;
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
    private Integer flagsCount; // 가본 곳 select
    private Double latitude;
    private Double longitude;
    private String address;
    private List<String> imgPathList;
    private Float rating;
    private Integer reviewsCount;
    private String businessHour;
    private List<MenuReviewsResponse> menuReviewsList; // select
    private List<String> breadCategoriesList;
}
