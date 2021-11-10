package com.depromeet.breadmapbackend.members.dto;

import com.depromeet.breadmapbackend.common.enumerate.FlagType;
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
public class ProfileBakeryResponse {

    // FlagTypeBakeryResponse
    @ApiModelProperty(value = "깃발 타입으로 GONE 또는 PICKED 반환")
    private FlagType flagType;
    @ApiModelProperty(value = "빵집 별점(평균 점수), 별점 없을 경우 0.0 반환")
    private Double avgRating;
    private Long bakeryId;
    private String bakeryName;
    private Long flagsCount;
    @ApiModelProperty(value = "빵집 대표 이미지")
    private String imgPath;
    private Long menuReviewsCount;
    private List<String> menuReviewContentList = new ArrayList<>();
}