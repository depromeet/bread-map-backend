package com.depromeet.breadmapbackend.flags.dto;

import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlagsRequest {

    private Long bakeryId;
    private FlagType flagType;

}
