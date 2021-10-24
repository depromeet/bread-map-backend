package com.depromeet.breadmapbackend.reviews.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuReviewsService {

    private final MenuReviewRepository menuReviewRepository;
    private final MenuReviewQuerydslRepository menuReviewQuerydslRepository;
    private final AuthService authService;
    private final MemberRepository memberRepository;

    @Transactional
    public void deleteMenuReview(String token, Long bakeryId, Long menuReviewId) {
        Long memberId = authService.getMemberId(token);
        Optional<Members> member = memberRepository.findById(memberId);

        MenuReviews menuReviews = Optional.ofNullable(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(bakeryId, menuReviewId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "리뷰가 존재하지 않습니다."));

        if(!Objects.equals(menuReviews.getMembers().getId(), member.orElseThrow(NullPointerException::new).getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 리뷰 작성자가 아닙니다.");
        }

        menuReviewRepository.delete(menuReviews);
    }
}
