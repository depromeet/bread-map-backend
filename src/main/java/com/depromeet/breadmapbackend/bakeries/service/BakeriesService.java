package com.depromeet.breadmapbackend.bakeries.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.domain.BreadCategories;
import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.depromeet.breadmapbackend.bakeries.dto.*;
import com.depromeet.breadmapbackend.bakeries.repository.*;
import com.depromeet.breadmapbackend.common.enumerate.BreadCategoryType;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.flags.repository.FlagsRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import com.depromeet.breadmapbackend.reviews.dto.CreateMenuReviewsRequest;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.repository.BakeryReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.BakeryReviewRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BakeriesService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final BakeriesQuerydslRepository bakeriesQuerydslRepository;
    private final MenuReviewQuerydslRepository menuReviewQuerydslRepository;
    private final MenusQuerydslRepository menusQuerydslRepository;
    private final BreadCategoriesQuerydslRepository breadCategoriesQuerydslRepository;
    private final BakeriesBreadCategoriesMapQuerydslRepository bakeriesBreadCategoriesMapQuerydslRepository;
    private final BakeryReviewQuerydslRepository bakeryReviewQuerydslRepository;
    private final MemberRepository memberRepository;
    private final BakeriesRepository bakeriesRepository;
    private final MenuReviewRepository menuReviewRepository;
    private final MenusRepository menusRepository;
    private final BakeryReviewRepository bakeryReviewRepository;
    private final FlagsRepository flagsRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public BakeryDetailResponse getBakeryDetail(String token, Long bakeryId) {
        Long memberId = authService.getMemberId(token);
        FlagTypeReviewRatingResponse flagTypeReviewRatingResponse = flagsQuerydslRepository.findByMemberIdAndBakeryId(memberId, bakeryId);
        BakeryInfoResponse bakeryInfoResponse = bakeriesQuerydslRepository.findByBakeryId(bakeryId);

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
                .imgPath(bakeryInfoResponse.getBakeries().getImgPath() != null ? bakeryInfoResponse.getBakeries().getImgPath().get(0) : "")
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
            List<String> breadCategoryList = bakeriesBreadCategoriesMapQuerydslRepository.findByBakeryId(bakeryId);

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
                    .imgPath(bakeryInfoResponse.getBakeries().getImgPath() != null ? bakeryInfoResponse.getBakeries().getImgPath().get(0) : "")
                    .menuReviewList(menuReviewResponseList != null ? menuReviewResponseList : Collections.emptyList())
                    .breadCategoryList(breadCategoryList)
                    .build());
        }
        return bakeryListResponseList != null ? bakeryListResponseList : Collections.emptyList();
    }

    @Transactional
    public void createMenuReviewList(String token, Long bakeryId, List<CreateMenuReviewsRequest> createMenuReviewRequestList) {
        for(CreateMenuReviewsRequest createMenuReviewsRequest: createMenuReviewRequestList) {
            Long memberId = authService.getMemberId(token);
            String imgPath = createMenuReviewsRequest.getImgPathList().isEmpty() ? "" : createMenuReviewsRequest.getImgPathList().get(0);
            MenuReviews menuReview = new MenuReviews();

            Optional<Members> member = memberRepository.findById(memberId);
            Optional<Bakeries> bakery = bakeriesRepository.findById(bakeryId);
            Menus menu = menusQuerydslRepository.findByMenuNameBakeryId(createMenuReviewsRequest.getMenuName(), bakeryId);

            if (menu == null) {
                BreadCategories breadCategory = breadCategoriesQuerydslRepository.findByBreadCategoryName(createMenuReviewsRequest.getCategoryName().replaceAll("[ /]", ""));

                Menus newMenu = new Menus();
                newMenu.createMenu(bakery.orElseThrow(NullPointerException::new), createMenuReviewsRequest.getMenuName(), createMenuReviewsRequest.getPrice(), breadCategory, imgPath);
                menusRepository.save(newMenu);
                menuReview.createMenuReview(createMenuReviewsRequest, newMenu, member.orElseThrow(NullPointerException::new), bakery.orElseThrow(NullPointerException::new));
            } else {
                if (menu.getImgPath().equals("") && !imgPath.equals("")) {
                        menu.updateImgPath(imgPath);
                }
                menuReview.createMenuReview(createMenuReviewsRequest, menu, member.orElseThrow(NullPointerException::new), bakery.orElseThrow(NullPointerException::new));
            }
            menuReviewRepository.save(menuReview);
        }
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
                        .members(member.orElseThrow(NullPointerException::new))
                        .bakeries(bakery.orElseThrow(NullPointerException::new))
                        .flagType(FlagType.NONE)
                        .build());
            }

            bakeryReviewRepository.save(BakeryReviews.builder()
                    .members(member.orElseThrow(NullPointerException::new))
                    .bakeries(bakery.orElseThrow(NullPointerException::new))
                    .contents("")
                    .rating(registerBakeryRatingRequest.getRating())
                    .imgPath(Collections.emptyList())
                    .build());
        } else {
            bakeryReview.updateRating(registerBakeryRatingRequest.getRating());
        }
    }

    @Transactional
    public void registerFlag(String token, Long bakeryId, CreateFlagsRequest createFlagsRequest) {
        if(createFlagsRequest.getFlagType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 FlagType입니다.");
        }

        Long memberId = authService.getMemberId(token);
        Optional<Bakeries> bakery = bakeriesRepository.findById(bakeryId);
        Optional<Members> member = memberRepository.findById(memberId);
        Flags flag = flagsQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId);

        if (flag == null) {
            flagsRepository.save(Flags.builder()
                    .members(member.orElseThrow(NullPointerException::new))
                    .bakeries(bakery.orElseThrow(NullPointerException::new))
                    .flagType(createFlagsRequest.getFlagType())
                    .build());
        } else {
            flag.updateFlagType(createFlagsRequest.getFlagType());
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


    @Transactional(readOnly = true)
    public MenuListResponse getMenuList(Long bakeryId, String category) {
        BreadCategoryType breadCategoryType = Optional.ofNullable(BreadCategoryType.fromString(category))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 빵 카테고리입니다."));

        BreadCategories breadCategories = breadCategoriesQuerydslRepository.findByBreadCategoryName(breadCategoryType.toString().replaceAll("[/]", ""));
        Long categoryId = breadCategories.getId();
        List<String> menuList = menusQuerydslRepository.findByBreadCategoryIdBakeryId(categoryId, bakeryId);

        return MenuListResponse.builder()
                .menuList(menuList != null ? menuList : Collections.emptyList())
                .build();
    }
}
