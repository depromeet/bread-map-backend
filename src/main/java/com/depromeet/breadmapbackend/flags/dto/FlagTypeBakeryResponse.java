package com.depromeet.breadmapbackend.flags.dto;

import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlagTypeBakeryResponse {

    @ApiModelProperty(value = "회원 한명의 flagType")
    private FlagType flagType;
    @ApiModelProperty(value = "flagType이 설정된 bakeryId")
    private Long bakeryId;
}
