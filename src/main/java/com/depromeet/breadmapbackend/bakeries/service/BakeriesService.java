package com.depromeet.breadmapbackend.bakeries.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.dto.*;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesBreadCategoriesMapQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.common.enumerate.BreadCategoryType;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.flags.repository.FlagsRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.repository.BakeryReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.BakeryReviewRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BakeriesService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final BakeriesQuerydslRepository bakeriesQuerydslRepository;
    private final MenuReviewQuerydslRepository menuReviewQuerydslRepository;
    private final BakeriesBreadCategoriesMapQuerydslRepository bakeriesBreadCategoriesMapQuerydslRepository;
    private final BakeryReviewQuerydslRepository bakeryReviewQuerydslRepository;
    private final MemberRepository memberRepository;
    private final BakeriesRepository bakeriesRepository;
    private final BakeryReviewRepository bakeryReviewRepository;
    private final FlagsRepository flagsRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public BakeryDetailResponse getBakeryDetail(String token, Long bakeryId) {
        Long memberId = authService.getMemberId(token);
        FlagTypeReviewRatingResponse flagTypeReviewRatingResponse = flagsQuerydslRepository.findBakeryReviewByBakeryIdMemberId(bakeryId, memberId);
        BakeryInfoResponse bakeryInfoResponse = Optional.ofNullable(bakeriesQuerydslRepository.findByBakeryId(bakeryId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 베이커리 정보가 존재하지 않습니다."));

        List<MenuReviewResponse> menuReviewResponseList = menuReviewQuerydslRepository.findMenuReviewListByBakeryId(bakeryId, 0L, 3L);
        List<BakeryMenuResponse> bakeryMenuResponseList = menuReviewQuerydslRepository.findBakeryMenuListByBakeryId(bakeryId, 0L, 3L);

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
                .imgPath(bakeryInfoResponse.getBakeries().getImgPath().size() != 0 ? bakeryInfoResponse.getBakeries().getImgPath().get(0) : "")
                .flagType(flagTypeReviewRatingResponse != null ? flagTypeReviewRatingResponse.getFlagType() : FlagType.NONE)
                .personalRating(flagTypeReviewRatingResponse != null ? flagTypeReviewRatingResponse.getPersonalRating() : 0L)
                .menuReviewsResponseList(menuReviewResponseList != null ? menuReviewResponseList : Collections.emptyList())
                .bakeryMenuListResponseList(bakeryMenuResponseList != null ? bakeryMenuResponseList : Collections.emptyList())
                .build();
    }

    @Transactional(readOnly = true)
    public List<BakeryListResponse> getBakeryList(Double latitude, Double longitude, Long range) {
        List<BakeryListResponse> bakeryListResponseList = new ArrayList<>();

        List<Long> bakeryIdList = bakeriesRepository.findByEarthDistance(latitude, longitude, range);
        for(Long bakeryId: bakeryIdList) {
            BakeryInfoResponse bakeryInfoResponse = bakeriesQuerydslRepository.findByBakeryId(bakeryId);
            List<MenuReviewResponse> menuReviewResponseList = menuReviewQuerydslRepository.findMenuReviewListByBakeryId(bakeryId, 0L, 3L);
            List<BreadCategoryType> breadCategoryList = bakeriesBreadCategoriesMapQuerydslRepository.findByBakeryId(bakeryId);

            bakeryListResponseList.add(BakeryListResponse.builder()
                    .bakeryId(bakeryId)
                    .bakeryName(bakeryInfoResponse.getBakeries().getName())
                    .latitude(bakeryInfoResponse.getBakeries().getLatitude())
                    .longitude(bakeryInfoResponse.getBakeries().getLongitude())
                    .address(bakeryInfoResponse.getBakeries().getAddress())
                    .flagsCount(bakeryInfoResponse.getFlagsCount())
                    .menuReviewsCount(bakeryInfoResponse.getMenuReviewsCount())
                    .avgRating(bakeryInfoResponse.getAvgRating())
                    .ratingCount(bakeryInfoResponse.getRatingCount())
                    .imgPath(bakeryInfoResponse.getBakeries().getImgPath().size() != 0 ? bakeryInfoResponse.getBakeries().getImgPath().get(0) : "")
                    .menuReviewList(menuReviewResponseList != null ? menuReviewResponseList : Collections.emptyList())
                    .breadCategoryList(breadCategoryList.stream().map(BreadCategoryType::getName).collect(Collectors.toList()))
                    .build());
        }
        return bakeryListResponseList != null ? bakeryListResponseList : Collections.emptyList();
    }

    @Transactional
    public void registerBakeryRating(String token, Long bakeryId, RegisterBakeryRatingRequest registerBakeryRatingRequest) {
        Long memberId = authService.getMemberId(token);
        BakeryReviews bakeryReview = bakeryReviewQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId);
        Optional<Bakeries> bakery = bakeriesRepository.findById(bakeryId);
        Optional<Members> member = memberRepository.findById(memberId);

        if (bakeryReview == null) {
            Flags flag = flagsQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId);
            if (flag == null) {
                flagsRepository.save(Flags.builder()
                        .members(member.orElseThrow(NoSuchElementException::new))
                        .bakeries(bakery.orElseThrow(NoSuchElementException::new))
                        .flagType(FlagType.NONE)
                        .build());
            }

            bakeryReviewRepository.save(BakeryReviews.builder()
                    .members(member.orElseThrow(NoSuchElementException::new))
                    .bakeries(bakery.orElseThrow(NoSuchElementException::new))
                    .contents("")
                    .rating(registerBakeryRatingRequest.getRating())
                    .imgPath(Collections.emptyList())
                    .build());
        } else {
            bakeryReview.updateRating(registerBakeryRatingRequest.getRating());
        }
    }

    @Transactional
    public void createBakery(String token, CreateBakeryRequest createBakeryRequest) {
        if(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 등록 된 빵집입니다.");

        Long memberId = authService.getMemberId(token);
        Optional<Members> member = memberRepository.findById(memberId);

        bakeriesRepository.save(Bakeries.builder()
                .name(createBakeryRequest.getBakeryName())
                .latitude(createBakeryRequest.getLatitude())
                .longitude(createBakeryRequest.getLongitude())
                .members(member.orElseThrow(NullPointerException::new))
                .telNumber(createBakeryRequest.getTelNumber())
                .address(createBakeryRequest.getAddress())
                .businessHour(createBakeryRequest.getBusinessHour())
                .basicInfoList(createBakeryRequest.getBasicInfoList())
                .websiteUrlList(createBakeryRequest.getWebsiteUrlList())
                .imgPath(createBakeryRequest.getImgPathList())
                .build());
    }
}
