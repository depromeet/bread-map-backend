package com.depromeet.breadmapbackend.common.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;

/*
깃발 종류 지정
 */
public enum FlagType {
    NONE, // 안간 곳
    PICKED, // 가볼 곳
    GONE;   // 가본 곳

    @JsonCreator
    public static FlagType fromFlagType(String val) {
        for (FlagType flagType: FlagType.values()) {
            if(flagType.name().equals(val))
                return flagType;
        }
        return null;
    }
}
