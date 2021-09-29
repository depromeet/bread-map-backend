package com.depromeet.breadmapbackend.reviews.dto;

import com.depromeet.breadmapbackend.common.enumerate.FilterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewsRequest {

    private Long breadShopId;
    private String contents;
    private Integer rating;
    private String filterName;
    private FilterType filterType;
    private List<String> reviewImgPathList; //Images 테이블에 따로 넣어야 함
}
