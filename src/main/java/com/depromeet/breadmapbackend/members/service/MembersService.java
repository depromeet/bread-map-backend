package com.depromeet.breadmapbackend.members.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeBakeryIdResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.members.dto.ProfileBakeryResponse;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembersService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final MenuReviewQuerydslRepository menuReviewQuerydslRepository;
    private final BakeriesQuerydslRepository bakeriesQuerydslRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<ProfileBakeryResponse> getProfileBakeryList(String token) {
        Long memberId = authService.getMemberId(token);
        List<ProfileBakeryResponse> profileBakeryResponseList = new ArrayList<>();

        List<FlagTypeBakeryIdResponse> flagTypeBakeryIdResponseList = flagsQuerydslRepository.findByMemberId(memberId);
        for(FlagTypeBakeryIdResponse flagTypeBakeryResponse: flagTypeBakeryIdResponseList) {
            Long bakeryId = flagTypeBakeryResponse.getBakeryId();

            BakeryInfoResponse bakeryInfoResponse = bakeriesQuerydslRepository.findByBakeryId(bakeryId);

            List<String> menuReviewContentList = menuReviewQuerydslRepository.findMenuReviewContentByBakeryId(bakeryId, 0L, 3L);

            profileBakeryResponseList.add(ProfileBakeryResponse.builder()
                    .flagType(flagTypeBakeryResponse.getFlagType())
                    .bakeryId(bakeryId)
                    .bakeryName(bakeryInfoResponse.getBakeries().getName())
                    .flagsCount(bakeryInfoResponse.getFlagsCount())
                    .menuReviewsCount(bakeryInfoResponse.getMenuReviewsCount())
                    .avgRating(bakeryInfoResponse.getAvgRating())
                    .imgPath(bakeryInfoResponse.getBakeries().getImgPath().size() != 0 ? bakeryInfoResponse.getBakeries().getImgPath().get(0) : "")
                    .menuReviewContentList(menuReviewContentList != null ? menuReviewContentList : Collections.emptyList())
                    .build());
        }
        return profileBakeryResponseList != null ? profileBakeryResponseList : Collections.emptyList();
    }
}
