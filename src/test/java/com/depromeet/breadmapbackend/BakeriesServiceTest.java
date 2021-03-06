package com.depromeet.breadmapbackend;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryListResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.bakeries.dto.CreateBakeryRequest;
import com.depromeet.breadmapbackend.bakeries.dto.RegisterBakeryRatingRequest;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesBreadCategoriesMapQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
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
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BakeriesServiceTest {

    @InjectMocks
    private BakeriesService bakeriesService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BakeriesQuerydslRepository bakeriesQuerydslRepository;

    @Mock
    private BakeriesRepository bakeriesRepository;

    @Mock
    private FlagsQuerydslRepository flagsQuerydslRepository;

    @Mock
    private FlagsRepository flagsRepository;

    @Mock
    private MenuReviewQuerydslRepository menuReviewQuerydslRepository;

    @Mock
    private BakeryReviewQuerydslRepository bakeryReviewQuerydslRepository;

    @Mock
    private BakeryReviewRepository bakeryReviewRepository;

    @Mock
    private BakeriesBreadCategoriesMapQuerydslRepository bakeriesBreadCategoriesMapQuerydslRepository;

    @Mock
    private AuthService authService;

    @ParameterizedTest
    @AutoSource
    void ??????_????????????_?????????_??????_ResponseStatusException_?????????_????????????(CreateBakeryRequest createBakeryRequest, Bakeries bakeries, String token) {
        when(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude())).thenReturn(true);

        Exception exception = assertThrows(ResponseStatusException.class, () -> bakeriesService.createBakery(token, createBakeryRequest));
        String exceptedMessage = "?????? ?????? ??? ???????????????.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));
        then(bakeriesRepository).should(never()).save(bakeries);
    }

    @ParameterizedTest
    @AutoSource
    void ??????_?????????_??????_???????????????_???_????????????_????????????(CreateBakeryRequest createBakeryRequest, Members members, String token, Long memberId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        when(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude())).thenReturn(false);
        when(memberRepository.findById(memberId)).thenReturn(Optional.ofNullable(members));

        Bakeries data = Bakeries.builder()
                .name(createBakeryRequest.getBakeryName())
                .latitude(createBakeryRequest.getLatitude())
                .longitude(createBakeryRequest.getLongitude())
                .members(members)
                .telNumber(createBakeryRequest.getTelNumber())
                .address(createBakeryRequest.getAddress())
                .businessHour(createBakeryRequest.getBusinessHour())
                .basicInfoList(createBakeryRequest.getBasicInfoList())
                .websiteUrlList(createBakeryRequest.getWebsiteUrlList())
                .imgPath(createBakeryRequest.getImgPathList())
                .build();

        bakeriesService.createBakery(token, createBakeryRequest);
        then(bakeriesRepository).should().save(refEq(data));
    }

    @ParameterizedTest
    @AutoSource
    void ????????????_????????????_????????????_????????????_??????_??????_????????????_??????????????????_????????????(Bakeries bakeries, List<MenuReviewResponse> menuReviewResponseList, List<BakeryMenuResponse> bakeryMenuResponseList, String token, Long bakeryId, Long memberId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        BakeryInfoResponse bakeryInfoResponse = new BakeryInfoResponse(bakeries, 22L, 10L, 3L, 4.5, 5L);
        FlagTypeReviewRatingResponse flagTypeReviewRatingResponse = new FlagTypeReviewRatingResponse(FlagType.NONE, 5L);

        when(flagsQuerydslRepository.findBakeryReviewByBakeryIdMemberId(bakeryId, memberId)).thenReturn(flagTypeReviewRatingResponse);
        when(bakeriesQuerydslRepository.findByBakeryId(bakeryId)).thenReturn(bakeryInfoResponse);
        when(menuReviewQuerydslRepository.findMenuReviewListByBakeryId(bakeryId, 0L, 3L)).thenReturn(menuReviewResponseList);
        when(menuReviewQuerydslRepository.findBakeryMenuListByBakeryId(bakeryId, 0L, 3L)).thenReturn(bakeryMenuResponseList);

        bakeriesService.getBakeryDetail(token, bakeryId);

        verify(flagsQuerydslRepository).findBakeryReviewByBakeryIdMemberId(bakeryId, memberId);
        verify(bakeriesQuerydslRepository).findByBakeryId(bakeryId);
        verify(menuReviewQuerydslRepository).findMenuReviewListByBakeryId(bakeryId, 0L, 3L);
        verify(menuReviewQuerydslRepository).findBakeryMenuListByBakeryId(bakeryId, 0L, 3L);
    }

    @ParameterizedTest
    @AutoSource
    void ????????????_????????????_????????????_bakeryInfoResponse???_??????_??????_ResponseStatusException???_????????????(Long bakeryId, String token, Long memberId) {
        given(bakeriesQuerydslRepository.findByBakeryId(bakeryId)).willReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> bakeriesService.getBakeryDetail(token, bakeryId));
        String exceptedMessage = "?????? ???????????? ????????? ???????????? ????????????.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));

        then(flagsQuerydslRepository).should(never()).findBakeryReviewByBakeryIdMemberId(bakeryId, memberId);
        then(menuReviewQuerydslRepository).should(never()).findMenuReviewListByBakeryId(bakeryId, 0L, 3L);
        then(menuReviewQuerydslRepository).should(never()).findBakeryMenuListByBakeryId(bakeryId, 0L, 3L);
    }

    @ParameterizedTest
    @AutoSource
    void ????????????_?????????_????????????_????????????_member????????????_null_??????_NoSuchElementException???_????????????(RegisterBakeryRatingRequest registerBakeryRatingRequest, BakeryReviews bakeryReviews, Long bakeryId, Long memberId, String token) {
        given(bakeryReviewQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).willReturn(bakeryReviews);
        given(memberRepository.findById(memberId)).willReturn(null);

        assertThrows(NoSuchElementException.class, () -> bakeriesService.registerBakeryRating(token, bakeryId, registerBakeryRatingRequest));
    }

    @ParameterizedTest
    @AutoSource
    void ??????????????????_?????????_????????????_?????????_null??????_?????????_?????????_?????????_NONE????????????_????????????_?????????_????????????(String token, Long memberId, Long bakeryId, RegisterBakeryRatingRequest registerBakeryRatingRequest, Bakeries bakeries, Members members) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(bakeriesRepository.findById(bakeryId)).willReturn(Optional.ofNullable(bakeries));
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        bakeriesService.registerBakeryRating(token, bakeryId, registerBakeryRatingRequest);

        when(bakeryReviewQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).thenReturn(null);
        when(flagsQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).thenReturn(null);

        then(flagsRepository).should().save(refEq(Flags.builder()
                .members(members)
                .bakeries(bakeries)
                .flagType(FlagType.NONE)
                .build()));

        then(bakeryReviewRepository).should().save(refEq(BakeryReviews.builder()
                .members(members)
                .bakeries(bakeries)
                .contents("")
                .rating(registerBakeryRatingRequest.getRating())
                .imgPath(Collections.emptyList())
                .build()));
    }

    @ParameterizedTest
    @AutoSource
    void ??????????????????_?????????_????????????_?????????_null??????_?????????_?????????_??????????????????_??????????????????_?????????_????????????(String token, Long memberId, Long bakeryId, Flags flags, RegisterBakeryRatingRequest registerBakeryRatingRequest, Bakeries bakeries, Members members) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(bakeriesRepository.findById(bakeryId)).willReturn(Optional.ofNullable(bakeries));
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        bakeriesService.registerBakeryRating(token, bakeryId, registerBakeryRatingRequest);

        when(bakeryReviewQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).thenReturn(null);
        when(flagsQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).thenReturn(flags);

        then(flagsRepository).should(never()).save(flags);
        then(bakeryReviewRepository).should().save(refEq(BakeryReviews.builder()
                .members(members)
                .bakeries(bakeries)
                .contents("")
                .rating(registerBakeryRatingRequest.getRating())
                .imgPath(Collections.emptyList())
                .build()));
    }

    @ParameterizedTest
    @AutoSource
    void ??????????????????_?????????_????????????_?????????_?????????_request???_??????_??????????????????_??????????????????(String token, Long memberId, Long bakeryId, BakeryReviews bakeryReviews, RegisterBakeryRatingRequest registerBakeryRatingRequest, Bakeries bakeries, Members members) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(bakeriesRepository.findById(bakeryId)).willReturn(Optional.ofNullable(bakeries));
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        bakeriesService.registerBakeryRating(token, bakeryId, registerBakeryRatingRequest);
        when(bakeryReviewQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).thenReturn(bakeryReviews);

        bakeryReviews.updateRating(registerBakeryRatingRequest.getRating());
    }

    @ParameterizedTest
    @AutoSource
    void ??????_??????_??????_????????????_?????????_??????_????????????_?????????_???_????????????_????????????(Double latitude, Double longitude, Long range) {
        List<BakeryListResponse> bakeryListResponseList = new ArrayList<>();
        given(bakeriesRepository.findByEarthDistance(latitude, longitude, range)).willReturn(Collections.emptyList());

        bakeriesService.getBakeryList(latitude, longitude, range);

        assertEquals(Collections.emptyList(), bakeryListResponseList);
    }

    @ParameterizedTest
    @AutoSource
    void ??????_??????_??????_????????????_?????????_??????_????????????_?????????_???????????????_????????????_??????????????????_????????????(List<BakeryListResponse> bakeryListResponseList, Bakeries bakeries, List<BreadCategoryType> breadCategoryList, List<MenuReviewResponse> menuReviewResponseList, Double latitude, Double longitude, Long range, Long bakeryId) {
        BakeryInfoResponse bakeryInfoResponse = new BakeryInfoResponse(bakeries, 22L, 10L, 3L, 4.5, 5L);

        List<Long> bakeryIdList = new ArrayList<>();
        bakeryIdList.add(bakeryId);
        bakeryIdList.add(bakeryId);

        when(bakeriesRepository.findByEarthDistance(latitude, longitude, range)).thenReturn(bakeryIdList);

        bakeryIdList.forEach(bakeryIdResponse -> {
            when(bakeriesQuerydslRepository.findByBakeryId(bakeryId)).thenReturn(bakeryInfoResponse);
            when(menuReviewQuerydslRepository.findMenuReviewListByBakeryId(bakeryId, 0L, 3L)).thenReturn(menuReviewResponseList);
            when(bakeriesBreadCategoriesMapQuerydslRepository.findByBakeryId(bakeryId)).thenReturn(breadCategoryList);

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
        });

        bakeriesService.getBakeryList(latitude, longitude, range);

        assertNotNull(bakeryListResponseList);
    }
}
