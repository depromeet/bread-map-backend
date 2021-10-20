package com.depromeet.breadmapbackend.bakeries.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryDetailResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.common.dto.ImageResponse;
import com.depromeet.breadmapbackend.common.repository.ImageQuerydslRepository;
import com.depromeet.breadmapbackend.common.repository.ImageRepository;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BakeriesService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final BakeriesQuerydslRepository bakeriesQuerydslRepository;
    private final ImageQuerydslRepository imageQuerydslRepository;
    private final AuthService authService;

    public BakeryDetailResponse getBakeryDetail(String token, Long bakeryId) {
        Long memberId = authService.getMemberId(token);
        FlagTypeReviewRatingResponse flagTypeReviewRatingResponse = flagsQuerydslRepository.findByMemberIdAndBakeryId(memberId, bakeryId);
        BakeryInfoResponse bakeryInfoResponse = bakeriesQuerydslRepository.findByBakeryId(bakeryId);
        ImageResponse imageResponse = imageQuerydslRepository.findByBakeryId(bakeryId);

        // TODO dto null이면 어떻게 할 지 찾아볼 것 (null ? ' ' : getData())
        // TODO basicInfoList (값 타입 컬렉션 쿼리 구성)

        return BakeryDetailResponse.builder()
                .bakeryId(bakeryId)
                .bakeryName(bakeryInfoResponse.getBakeryName())
                .address(bakeryInfoResponse.getAddress())
                .flagsCount(bakeryInfoResponse.getFlagsCount())
                .menuReviewsCount(bakeryInfoResponse.getMenuReviewsCount())
                .businessHour(bakeryInfoResponse.getBusinessHour())
                .websiteUrlList(bakeryInfoResponse.getWebsiteUrlList())
                .telNumber(bakeryInfoResponse.getTelNumber())
                .avgRating(bakeryInfoResponse.getAvgRating())
                .ratingCount(bakeryInfoResponse.getRatingCount())
                //.basicInfoList(bakeryInfoResponse.getBasicInfoList())
                .imgPath(imageResponse.getImgPath())
                .flagType(flagTypeReviewRatingResponse.getFlagType())
                .personalRating(flagTypeReviewRatingResponse.getRating())
                .build();
    }
}
