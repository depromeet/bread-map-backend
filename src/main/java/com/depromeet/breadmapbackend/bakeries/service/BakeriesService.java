package com.depromeet.breadmapbackend.bakeries.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.domain.BreadCategories;
import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryDetailResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.bakeries.dto.CreateBakeryRequest;
import com.depromeet.breadmapbackend.bakeries.repository.*;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import com.depromeet.breadmapbackend.reviews.dto.CreateMenuReviewsRequest;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    private final MemberRepository memberRepository;
    private final BakeriesRepository bakeriesRepository;
    private final MenuReviewRepository menuReviewRepository;
    private final MenusRepository menusRepository;
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
                .imgPath(bakeryInfoResponse.getBakeries().getImgPath().get(0))
                .flagType(flagTypeReviewRatingResponse != null ? flagTypeReviewRatingResponse.getFlagType() : FlagType.NONE)
                .personalRating(flagTypeReviewRatingResponse != null ? flagTypeReviewRatingResponse.getPersonalRating() : 0L)
                .menuReviewsResponseList(menuReviewResponseList != null ? menuReviewResponseList : Collections.emptyList())
                .bakeryMenuListResponseList(bakeryMenuResponseList != null ? bakeryMenuResponseList : Collections.emptyList())
                .build();
    }

    @Transactional
    public void createMenuReviewList(String token, Long bakeryId, List<CreateMenuReviewsRequest> createMenuReviewRequestList) {
        for(CreateMenuReviewsRequest createMenuReviewsRequest: createMenuReviewRequestList) {
            Long memberId = authService.getMemberId(token);
            Optional<Members> member = memberRepository.findById(memberId);
            Optional<Bakeries> bakery = bakeriesRepository.findById(bakeryId);
            MenuReviews menuReview = new MenuReviews();

            Menus menu = menusQuerydslRepository.findByMenuNameBakeryId(createMenuReviewsRequest.getMenuName(), bakeryId);
            if (menu == null) {
                BreadCategories breadCategory = breadCategoriesQuerydslRepository.findByBreadCategoryName(createMenuReviewsRequest.getCategoryName().replaceAll("[ /]", ""));
                String imgPath = createMenuReviewsRequest.getImgPathList().isEmpty() ? "" : createMenuReviewsRequest.getImgPathList().get(0);
                Menus newMenu = new Menus();
                newMenu.createMenu(bakery.orElseThrow(NullPointerException::new), createMenuReviewsRequest.getMenuName(), createMenuReviewsRequest.getPrice(), breadCategory, imgPath);
                menusRepository.save(newMenu);
                menuReview.createMenuReview(createMenuReviewsRequest, newMenu, member.orElseThrow(NullPointerException::new), bakery.orElseThrow(NullPointerException::new));
            }
            else {
                menuReview.createMenuReview(createMenuReviewsRequest, menu, member.orElseThrow(NullPointerException::new), bakery.orElseThrow(NullPointerException::new));
            }
            menuReviewRepository.save(menuReview);
        }
    }

    @Transactional
    public void createBakery(String token, CreateBakeryRequest createBakeryRequest) {
        if(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 등록 된 빵집입니다.");

        Long memberId = authService.getMemberId(token);
        Optional<Members> member = memberRepository.findById(memberId);

        Bakeries newBakery = new Bakeries();
        newBakery.createBakery(createBakeryRequest, member.orElseThrow(NullPointerException::new));
        bakeriesRepository.save(newBakery);
    }
}
