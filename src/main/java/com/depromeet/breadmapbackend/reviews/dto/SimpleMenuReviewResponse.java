package com.depromeet.breadmapbackend.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class SimpleMenuReviewResponse {

    private String contents;

    @Builder
    public SimpleMenuReviewResponse(String contents) {
        this.contents = contents;
    }
}
