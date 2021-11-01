package com.depromeet.breadmapbackend;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryDetailResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.bakeries.dto.CreateBakeryRequest;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private AuthService authService;

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
        when(bakeriesQuerydslRepository.isBakeryExisted(createBakeryRequest.getLatitude(), createBakeryRequest.getLongitude())).thenReturn(false);
        given(authService.getMemberId(token)).willReturn(memberId);
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
    void 베이커리_아이디에_해당하는_데이터가_있을_경우_베이커리_상세데이터를_반환한다(BakeryInfoResponse bakeryInfoResponse, List<MenuReviewResponse> menuReviewResponseList, List<BakeryMenuResponse> bakeryMenuResponseList, String token, Long bakeryId) {
//        bakeriesService.getBakeryDetail(token, bakeryId);
        given(bakeriesQuerydslRepository.findByBakeryId(bakeryId)).willReturn(bakeryInfoResponse);
        when(menuReviewQuerydslRepository.findMenuReviewListByBakeryId(bakeryId, 0L, 3L)).thenReturn(menuReviewResponseList);
        when(menuReviewQuerydslRepository.findBakeryMenuListByBakeryId(bakeryId, 0L, 3L)).thenReturn(bakeryMenuResponseList);
//
//        verify(menuReviewQuerydslRepository).findMenuReviewListByBakeryId(bakeryId, 0L, 3L);
//        verify(menuReviewQuerydslRepository).findBakeryMenuListByBakeryId(bakeryId, 0L, 3L);
        //작성중
    }
}
