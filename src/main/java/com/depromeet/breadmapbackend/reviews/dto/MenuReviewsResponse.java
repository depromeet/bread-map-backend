package com.depromeet.breadmapbackend.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuReviewsResponse {

    private Long menuReviewId;
    private Long memberId;
    private Long breadCategoryId;
    private String memberName;
    private String menuName;
    private Long menuId;
    private List<String> imgPathList;
    private String contents;
    private Long rating;
    private LocalDateTime lastModifiedDateTime;
}
