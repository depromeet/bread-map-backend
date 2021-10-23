package com.depromeet.breadmapbackend.bakeries.dto;

import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.common.enumerate.BasicInfoType;
import com.depromeet.breadmapbackend.members.domain.Members;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private List<BasicInfoType> basicInfoList = new ArrayList<>();
    private List<String> imgPathList = new ArrayList<>();

    public Bakeries toEntity(Members members) {
        return Bakeries.builder()
                .name(bakeryName)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .members(members)
                .businessHour(businessHour)
                .websiteUrlList(websiteUrlList)
                .telNumber(telNumber)
                .basicInfoList(basicInfoList)
                .imgPath(imgPathList)
                .build();
    }
}
