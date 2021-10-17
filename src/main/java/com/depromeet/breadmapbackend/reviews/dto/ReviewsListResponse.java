package com.depromeet.breadmapbackend.reviews.dto;

import com.depromeet.breadmapbackend.common.enumerate.FilterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsListResponse {

    private Long reviewId;
    private Long bakeryId;
    private Long memberId;
    private String contents;
    private Integer rating;
    private List<String> reviewImgPathList;
}
