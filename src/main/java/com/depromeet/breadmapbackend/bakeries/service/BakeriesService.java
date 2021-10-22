package com.depromeet.breadmapbackend.bakeries.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryDetailResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BakeriesService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final BakeriesQuerydslRepository bakeriesQuerydslRepository;
    private final MenuReviewQuerydslRepository menuReviewQuerydslRepository;
    private final AuthService authService;

    public BakeryDetailResponse getBakeryDetail(String token, Long bakeryId) {
        Long memberId = authService.getMemberId(token);
        FlagTypeReviewRatingResponse flagTypeReviewRatingResponse = flagsQuerydslRepository.findByMemberIdAndBakeryId(memberId, bakeryId);
        BakeryInfoResponse bakeryInfoResponse = bakeriesQuerydslRepository.findByBakeryId(bakeryId);

        List<MenuReviewResponse> menuReviewDetailResponseList = menuReviewQuerydslRepository.findMenuReviewListByBakeryId(bakeryId, 0L, 3L);

        List<BakeryMenuResponse> bakeryMenuResponseList = menuReviewQuerydslRepository.findBakeryMenuListByBakeryId(bakeryId, 0L, 3L);
        for(BakeryMenuResponse bakeryMenuResponse: bakeryMenuResponseList) {
            log.info("menuName: " + bakeryMenuResponse.getMenuName());
            log.info("imgPath: " + bakeryMenuResponse.getImgPath());
        }


        // TODO dto null이면 어떻게 할 지 찾아볼 것 (null ? ' ' : getData())

        return BakeryDetailResponse.builder()
                .bakeryId(bakeryId)
                .bakeryName(bakeryInfoResponse.getBakeries().getName())
                .address(bakeryInfoResponse.getBakeries().getAddress())
                .flagsCount(bakeryInfoResponse.getFlagsCount())
                .menuReviewsCount(bakeryInfoResponse.getMenuReviewsCount())
                .businessHour(bakeryInfoResponse.getBakeries().getBusinessHour())
                .websiteUrlList(bakeryInfoResponse.getBakeries().getWebsiteUrlList())
                .telNumber(bakeryInfoResponse.getBakeries().getTelNumber())
                .avgRating(bakeryInfoResponse.getAvgRating())
                .ratingCount(bakeryInfoResponse.getRatingCount())
                .basicInfoList(bakeryInfoResponse.getBakeries().getBasicInfoList())
                .imgPath(bakeryInfoResponse.getBakeries().getImgPath().get(0))
                .flagType(flagTypeReviewRatingResponse.getFlagType())
                .personalRating(flagTypeReviewRatingResponse.getRating())
                .menuReviewsResponseList(menuReviewDetailResponseList)
                .bakeryMenuListResponseList(bakeryMenuResponseList)
                .build();
    }
}
