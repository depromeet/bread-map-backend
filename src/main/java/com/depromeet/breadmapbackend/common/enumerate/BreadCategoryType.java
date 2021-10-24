package com.depromeet.breadmapbackend.common.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum BreadCategoryType {

    식사빵("식사빵"),
    구움과자류("구움과자류"),
    마카롱("마카롱"),
    케이크("케이크"),
    크림빵("크림빵"),
    도넛("도넛"),
    추억의빵("추억의 빵"),
    과자류("과자류"),
    크로와상("크로와상"),
    쿠키("쿠키"),
    파이디저트("파이/디저트"),
    기타("기타")
    ;

    private final String name;

    public static List<String> breadCategoriesList = Arrays.stream(BreadCategoryType.values())
            .map(BreadCategoryType::getName).collect(Collectors.toList());

    public static BreadCategoryType fromString(String name) {
        for (BreadCategoryType breadCategoryType: BreadCategoryType.values()) {
            if(breadCategoryType.name.equals(name))
                return breadCategoryType;
        }
        return null;
    }
}
