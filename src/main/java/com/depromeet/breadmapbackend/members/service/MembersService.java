package com.depromeet.breadmapbackend.members.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryInfoResponse;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesQuerydslRepository;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeBakeryIdResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.dto.ProfileBakeryResponse;
import com.depromeet.breadmapbackend.members.dto.UserInfo;
import com.depromeet.breadmapbackend.members.dto.UserInfoResponse;
import com.depromeet.breadmapbackend.members.repository.MemberQuerydslRepository;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
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
@Transactional
public class MembersService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final MenuReviewQuerydslRepository menuReviewQuerydslRepository;
    private final BakeriesQuerydslRepository bakeriesQuerydslRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<ProfileBakeryResponse> getProfileBakeryList(String token) {
        Long memberId = authService.getMemberId(token);
        List<ProfileBakeryResponse> profileBakeryResponseList = new ArrayList<>();

        List<FlagTypeBakeryIdResponse> flagTypeBakeryIdResponseList = flagsQuerydslRepository.findByMemberId(memberId);
        for (FlagTypeBakeryIdResponse flagTypeBakeryResponse : flagTypeBakeryIdResponseList) {
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

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(String token) {
        Long memberId = authService.getMemberId(token);

        return Optional.ofNullable(memberQuerydslRepository.findByMemberId(memberId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저 정보가 존재하지 않습니다."));
    }

    public UserInfo updateUserInfo(String token, UserInfo userInfo) {
        Long memberId = authService.getMemberId(token);

        Members members = Optional.ofNullable(memberRepository.findMemberById(memberId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저 정보가 존재하지 않습니다."));

        // TODO 추후 닉네임 지정 기능 생기면 그때는 닉네임 빈칸 막을지 고려 필요
        if(userInfo.getNickName() == null || userInfo.getProfileImage() == null || userInfo.getNickName().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지나 닉네임이 누락되어 수정이 불가능합니다.");
        }

        members.updateName(userInfo.getNickName());
        members.updateProfileImagePath(userInfo.getProfileImage());

        return UserInfo.builder()
                .nickName(members.getName())
                .profileImage(members.getProfileImagePath())
                .build();
    }
}
