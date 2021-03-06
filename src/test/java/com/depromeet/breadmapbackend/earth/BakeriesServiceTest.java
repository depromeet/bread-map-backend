package com.depromeet.breadmapbackend.earth;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.dto.*;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesBreadCategoriesMapQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.flags.repository.FlagsRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.depromeet.breadmapbackend.reviews.repository.BakeryReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.BakeryReviewRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BakeriesServiceTest {

    @InjectMocks
    private BakeriesService bakeriesService;

    @Mock
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BakeriesRepository bakeriesRepository;

    @Mock
    private FlagsQuerydslRepository flagsQuerydslRepository;

    @Mock
    private BakeriesQuerydslRepository bakeriesQuerydslRepository;

    @Mock
    private BakeryReviewQuerydslRepository bakeryReviewQuerydslRepository;

    @Mock
    private MenuReviewQuerydslRepository menuReviewQuerydslRepository;

    @Mock
    private BakeriesBreadCategoriesMapQuerydslRepository bakeriesBreadCategoriesMapQuerydslRepository;

    @Mock
    private BakeryReviewRepository bakeryReviewRepository;

    @Mock
    private FlagsRepository flagsRepository;

    private static final String TOKEN = "tokentokentoken";
    private static final Long BAKERY_ID = 1L;
    private static final Long MEMBER_ID = 1L;

    @Test
    @DisplayName("?????? ????????? ???????????? ?????? ?????? ????????? ???????????????.")
    void notExistBakery() {
        // given
        String expectedException = "?????? ???????????? ????????? ???????????? ????????????.";
        given(authService.getMemberId(TOKEN)).willReturn(MEMBER_ID);

        // when
        when(flagsQuerydslRepository.findBakeryReviewByBakeryIdMemberId(BAKERY_ID, MEMBER_ID)).thenReturn(new FlagTypeReviewRatingResponse());
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            bakeriesService.getBakeryDetail(TOKEN, BAKERY_ID);
        });

        // then
        assertEquals(responseStatusException.getReason(), expectedException);
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ?????? ?????? ????????? ???????????????. (??????, ?????? ????????? ????????? ????????? ?????? ?????? ?????? ????????? NONE????????? ?????????.)")
    public void getBakeryDetail() {
        // given
        given(authService.getMemberId(TOKEN)).willReturn(MEMBER_ID);
        Bakeries bakery = Bakeries.builder()
                .id(BAKERY_ID)
                .imgPath(new ArrayList<>())
                .build();

        BakeryInfoResponse bakeryInfoResponse = new BakeryInfoResponse(bakery, 10L, 15L, 3L, 3.5, 4L);

        // when
        when(flagsQuerydslRepository.findBakeryReviewByBakeryIdMemberId(BAKERY_ID, MEMBER_ID)).thenReturn(null);
        when(bakeriesQuerydslRepository.findByBakeryId(BAKERY_ID)).thenReturn(bakeryInfoResponse);
        when(menuReviewQuerydslRepository.findMenuReviewListByBakeryId(BAKERY_ID, 0L, 3L)).thenReturn(new ArrayList<>());
        when(menuReviewQuerydslRepository.findBakeryMenuListByBakeryId(BAKERY_ID, 0L, 3L)).thenReturn(new ArrayList<>());

        BakeryDetailResponse bakeryDetailResponse = bakeriesService.getBakeryDetail(TOKEN, BAKERY_ID);
        // then
        assertEquals(bakeryDetailResponse.getFlagType(), FlagType.NONE);
        assertEquals(bakeryDetailResponse.getPersonalRating(), 0L);
    }

    @Test
    @DisplayName("???/??????, ????????? ???????????? ?????? ?????? ????????? ???????????? ????????? ??? ???????????? ???????????????.")
    public void getEmptyBakeryList() {
        // given
        Double latitude = 1.1;
        Double longitude = 1.1;
        Long range = 4L;

        // when
        when(bakeriesRepository.findByEarthDistance(latitude, longitude, range)).thenReturn(Collections.emptyList());
        List<BakeryListResponse> bakeryListResponseList = bakeriesService.getBakeryList(latitude, longitude, range);

        // then
        assertEquals(bakeryListResponseList, Collections.emptyList());
    }

    @Test
    @DisplayName("???/??????, ????????? ???????????? ?????? ?????? ????????? ???????????? ???????????????.")
    public void getBakeryList() {
        // given
        Double latitude = 1.1;
        Double longitude = 1.1;
        Long range = 4L;

        List<Long> bakeryIdList = Arrays.asList(BAKERY_ID);

        given(bakeriesRepository.findByEarthDistance(latitude, longitude, range)).willReturn(bakeryIdList);

        for(Long bakeryId: bakeryIdList) {
            Bakeries bakery = Bakeries.builder()
                    .id(bakeryId)
                    .imgPath(new ArrayList<>())
                    .build();

            BakeryInfoResponse bakeryInfoResponse = new BakeryInfoResponse(bakery, 5L, 3L, 15L, 3.5, 5L);

            when(bakeriesQuerydslRepository.findByBakeryId(bakeryId)).thenReturn(bakeryInfoResponse);
        }

        // when
        when(bakeriesRepository.findByEarthDistance(latitude, longitude, range)).thenReturn(bakeryIdList);
        when(menuReviewQuerydslRepository.findMenuReviewListByBakeryId(BAKERY_ID, 0L, 3L)).thenReturn(new ArrayList<>());
        when(bakeriesBreadCategoriesMapQuerydslRepository.findByBakeryId(BAKERY_ID)).thenReturn(new ArrayList<>());

        List<BakeryListResponse> bakeryListResponseList = bakeriesService.getBakeryList(latitude, longitude, range);

        assertTrue(bakeryListResponseList.size() > 0);
    }

    @Test
    @DisplayName("?????? ????????? ????????? ?????? ????????? ???????????????.")
    public void alreadyRegisteredBakery() {
        // given
        String expectedException = "?????? ?????? ??? ???????????????.";
        Double latitude = 1.1;
        Double longitude = 1.1;
        CreateBakeryRequest createBakeryRequest = new CreateBakeryRequest("BAKERY_NAME", latitude, longitude, "ADDRESS", "BUSINESS_HOUR", new ArrayList<>(), "TEL", new ArrayList<>(), new ArrayList<>());

        // when
        when(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude())).thenReturn(true);
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            bakeriesService.createBakery(TOKEN, createBakeryRequest);
        });

        // then
        assertEquals(responseStatusException.getReason(), expectedException);
    }

    @Test
    @DisplayName("???????????? ????????? ?????????, ?????? ?????? ????????? ???????????????.")
    void createBakery() {
        // given
        Double latitude = 1.1;
        Double longitude = 1.1;
        CreateBakeryRequest createBakeryRequest = new CreateBakeryRequest("BAKERY_NAME", latitude, longitude, "ADDRESS", "BUSINESS_HOUR", new ArrayList<>(), "TEL", new ArrayList<>(), new ArrayList<>());

        Members member = Members.builder()
                .id(MEMBER_ID)
                .bakeryReviewsList(new ArrayList<>())
                .flagsList(new ArrayList<>())
                .build();

        given(authService.getMemberId(TOKEN)).willReturn(MEMBER_ID);
        given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.ofNullable(member));

        // when
        when(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude())).thenReturn(false);
        bakeriesService.createBakery(TOKEN, createBakeryRequest);

        // then
        verify(bakeriesRepository).save(any());

    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????? ?????? ??????, ?????? ????????? ?????? ???????????????.")
    void registerNewBakeryRating() {
        // given
        RegisterBakeryRatingRequest registerBakeryRatingRequest = new RegisterBakeryRatingRequest(5L);

        Bakeries bakery = Bakeries.builder()
                .id(BAKERY_ID)
                .bakeryReviewsList(new ArrayList<>())
                .flagsList(new ArrayList<>())
                .build();

        Members member = Members.builder()
                .id(MEMBER_ID)
                .bakeryReviewsList(new ArrayList<>())
                .flagsList(new ArrayList<>())
                .build();

        BakeryReviews bakeryReviews = BakeryReviews.builder()
                .members(member)
                .bakeries(bakery)
                .contents("")
                .rating(registerBakeryRatingRequest.getRating())
                .imgPath(Collections.emptyList())
                .build();

        given(authService.getMemberId(TOKEN)).willReturn(MEMBER_ID);
        given(bakeriesRepository.findById(BAKERY_ID)).willReturn(Optional.ofNullable(bakery));
        given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.ofNullable(member));

        // when
        when(bakeryReviewQuerydslRepository.findByBakeryIdMemberId(BAKERY_ID, MEMBER_ID)).thenReturn(null); // bakeryReview == null
        when(flagsQuerydslRepository.findByBakeryIdMemberId(BAKERY_ID, MEMBER_ID)).thenReturn(null);

        bakeriesService.registerBakeryRating(TOKEN, BAKERY_ID, registerBakeryRatingRequest);

        // then
        verify(flagsRepository).save(any());
        then(bakeryReviewRepository).should().save(refEq(bakeryReviews));

    }

    @Test
    @DisplayName("????????? ????????? ????????? ????????? ??????, ?????? ????????? ???????????? ?????????.")
    void registerBakeryRating() {
        // given
        RegisterBakeryRatingRequest registerBakeryRatingRequest = new RegisterBakeryRatingRequest(5L);

        Bakeries bakery = Bakeries.builder()
                .id(BAKERY_ID)
                .bakeryReviewsList(new ArrayList<>())
                .flagsList(new ArrayList<>())
                .build();

        Members member = Members.builder()
                .id(MEMBER_ID)
                .bakeryReviewsList(new ArrayList<>())
                .flagsList(new ArrayList<>())
                .build();

        BakeryReviews bakeryReviews = BakeryReviews.builder()
                .members(member)
                .bakeries(bakery)
                .contents("")
                .rating(4L)
                .imgPath(Collections.emptyList())
                .build();

        given(authService.getMemberId(TOKEN)).willReturn(MEMBER_ID);
        given(bakeriesRepository.findById(BAKERY_ID)).willReturn(Optional.ofNullable(bakery));
        given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.ofNullable(member));

        // when
        when(bakeryReviewQuerydslRepository.findByBakeryIdMemberId(BAKERY_ID, MEMBER_ID)).thenReturn(bakeryReviews);

        bakeriesService.registerBakeryRating(TOKEN, BAKERY_ID, registerBakeryRatingRequest);

        // then
        verify(flagsRepository, never()).save(any());
        assertEquals(bakeryReviews.getRating(), registerBakeryRatingRequest.getRating());
    }
}
