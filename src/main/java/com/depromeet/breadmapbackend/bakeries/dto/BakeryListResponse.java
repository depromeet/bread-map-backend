package com.depromeet.breadmapbackend.bakeries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryListResponse {

    private Long bakeryId;
    private String bakeryName;
    private Integer flagsCount; //TODO 가본곳만 카운트인지, 가볼곳도 카운트인지 확인 필요
    private Double latitude;
    private Double longitude;
    private String address;
    // TODO 외부 사진(대표사진) 하나 넣어주는 건 어떤지? 준다고 하면 어떻게 내려줄건지
}
