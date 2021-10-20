package com.depromeet.breadmapbackend.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    @ApiModelProperty(value = "빵집 대표 이미지")
    private String imgPath;
}
