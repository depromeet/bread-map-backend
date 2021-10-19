package com.depromeet.breadmapbackend.bakeries.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryDetailResponse;
import com.depromeet.breadmapbackend.flags.dto.FlagTypeReviewRatingResponse;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BakeriesService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final AuthService authService;

    public BakeryDetailResponse getBakeryDetail(String token, Long bakeryId) {
        Long memberId = authService.getMemberId(token);
        FlagTypeReviewRatingResponse flagTypeReviewRatingResponse = flagsQuerydslRepository.findByMemberIdAndBakeryId(memberId, bakeryId);


        return null;
    }
}
