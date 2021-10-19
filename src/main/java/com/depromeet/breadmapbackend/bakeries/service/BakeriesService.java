package com.depromeet.breadmapbackend.bakeries.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryDetailResponse;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BakeriesService {

    private final MemberQuerydslRepository memberQuerydslRepository;
    private final AuthService authService;

    public List<BakeryDetailResponse> getBakeryDetail(String token, Long bakeryId) {
        Long memberId = authService.getMemberId(token);
        Members member = memberQuerydslRepository.findByIdAndBakeryId(memberId, bakeryId);

        return null;
    }
}
