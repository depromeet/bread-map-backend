package com.depromeet.breadmapbackend.reviews.dto;

import com.depromeet.breadmapbackend.common.enumerate.FilterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyReviewsRequest {

    private Long reviewId;
    private String contents;
    private Long rating;
    private List<FilterType> filterTypeList;
    private List<String> filterNameList;
    private List<String> imgPathList;

}
