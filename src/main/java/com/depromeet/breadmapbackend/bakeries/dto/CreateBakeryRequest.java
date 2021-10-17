package com.depromeet.breadmapbackend.bakeries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBakeryRequest {

    private String bakeryName;
    private Double latitude;
    private Double longitude;
    private String address;
    private String businessHour;
    private List<String> websiteUrlList = new ArrayList<>();
    private String telNumber;
    private List<String> basicInfoList = new ArrayList<>();
    private List<String> imgPathList = new ArrayList<>();

}
