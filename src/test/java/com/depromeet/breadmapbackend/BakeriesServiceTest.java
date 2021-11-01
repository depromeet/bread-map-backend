package com.depromeet.breadmapbackend;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.bakeries.dto.CreateBakeryRequest;
import com.depromeet.breadmapbackend.bakeries.dto.RegisterBakeryRatingRequest;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.repository.BakeryReviewQuerydslRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

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
    private MenuReviewQuerydslRepository menuReviewQuerydslRepository;

    @Mock
    private BakeryReviewQuerydslRepository bakeryReviewQuerydslRepository;

    @Mock
    private AuthService authService;

    @Mock
    private BakeryReviews bakeryReviews;

    @ParameterizedTest
    @AutoSource
    void 이미_존재하는_빵집일_경우_ResponseStatusException_예외가_발생한다(CreateBakeryRequest createBakeryRequest, Bakeries bakeries, String token) {
        when(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude())).thenReturn(true);

        Exception exception = assertThrows(ResponseStatusException.class, () -> bakeriesService.createBakery(token, createBakeryRequest));
        String exceptedMessage = "이미 등록 된 빵집입니다.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));
        then(bakeriesRepository).should(never()).save(bakeries);
    }

    @ParameterizedTest
    @AutoSource
    void 신규_빵집일_경우_사용자조회_후_데이터를_저장한다(CreateBakeryRequest createBakeryRequest, Members members, String token, Long memberId) {
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
    void 베이커리_아이디에_해당하는_데이터가_있을_경우_베이커리_상세데이터를_반환한다(Bakeries bakeries, List<MenuReviewResponse> menuReviewResponseList, List<BakeryMenuResponse> bakeryMenuResponseList, String token, Long bakeryId, Long memberId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        BakeryInfoResponse bakeryInfoResponse = new BakeryInfoResponse(bakeries, 22L, 10L, 4.5, 5L);
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
    void 베이커리_아이디에_해당하는_bakeryInfoResponse가_없을_경우_ResponseStatusException이_발생한다(Long bakeryId, String token, Long memberId) {
        given(bakeriesQuerydslRepository.findByBakeryId(bakeryId)).willReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> bakeriesService.getBakeryDetail(token, bakeryId));
        String exceptedMessage = "해당 베이커리 정보가 존재하지 않습니다.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));

        then(flagsQuerydslRepository).should(never()).findBakeryReviewByBakeryIdMemberId(bakeryId, memberId);
        then(menuReviewQuerydslRepository).should(never()).findMenuReviewListByBakeryId(bakeryId, 0L, 3L);
        then(menuReviewQuerydslRepository).should(never()).findBakeryMenuListByBakeryId(bakeryId, 0L, 3L);
    }
}
