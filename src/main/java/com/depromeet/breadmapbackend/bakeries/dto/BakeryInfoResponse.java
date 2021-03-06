package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryInfoResponse {

    private Bakeries bakeries;
    @ApiModelProperty(value = "FlagType: GONE에 대해 counting")
    private Long flagsCount;
    private Long menuReviewsCount;
    private Long menusCount;
    @ApiModelProperty(value = "빵집 별점(평균 점수), 별점 없을 경우 0.0 반환")
    private Double avgRating;
    @ApiModelProperty(value = "빵집 별점 매긴 사람 수")
    private Long ratingCount;

}
