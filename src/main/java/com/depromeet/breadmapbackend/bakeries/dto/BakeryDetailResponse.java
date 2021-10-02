package com.depromeet.breadmapbackend.bakeries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryDetailResponse {

    private Long bakeryId;
    private String bakeryName;
    private String address;
    private Integer flagsCount; //TODO 가본곳만 카운트인지, 가볼곳도 카운트인지 확인 필요
    private List<String> exteriorImgPathList;
    private List<String> interiorImgPathList;
}
