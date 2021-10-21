package com.depromeet.breadmapbackend.reviews.dto;

import com.depromeet.breadmapbackend.common.domain.Images;
import com.depromeet.breadmapbackend.common.util.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuReviewDetailResponse {

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
}
