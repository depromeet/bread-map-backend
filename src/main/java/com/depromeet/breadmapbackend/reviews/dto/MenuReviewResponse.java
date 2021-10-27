package com.depromeet.breadmapbackend.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuReviewResponse {

    private Long menuReviewId;
    private Long memberId;
    private Long breadCategoryId;
    private String memberName;
    private String menuName;
    private Long menuId;
    private List<String> imgPathList = new ArrayList<>();
    private String contents;
    private Long rating;
    private LocalDateTime lastModifiedDateTime;

    @Builder
    public MenuReviewResponse(MenuReviewResponse menuReviewResponse) {
        this.menuReviewId = menuReviewResponse.getMenuReviewId();
        this.memberId = menuReviewResponse.getMemberId();
        this.breadCategoryId = menuReviewResponse.getBreadCategoryId();
        this.memberName = menuReviewResponse.getMemberName();
        this.menuName = menuReviewResponse.getMenuName();
        this.menuId = menuReviewResponse.getMenuId();
        this.imgPathList = menuReviewResponse.getImgPathList();
        this.contents = menuReviewResponse.getContents();
        this.rating = menuReviewResponse.getRating();
        this.lastModifiedDateTime = menuReviewResponse.getLastModifiedDateTime();
    }
}
