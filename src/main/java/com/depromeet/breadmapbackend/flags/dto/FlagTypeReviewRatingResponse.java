package com.depromeet.breadmapbackend.flags.dto;

import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlagTypeReviewRatingResponse {

    @ApiModelProperty(value = "회원 한명의 FlagType")
    private FlagType flagType;
    @ApiModelProperty(value = "회원 한명의 빵집 별점, 별점 없을 경우 0.0 반환")
    private Long personalRating;
}
