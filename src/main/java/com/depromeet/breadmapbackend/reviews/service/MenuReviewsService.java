package com.depromeet.breadmapbackend.reviews.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.domain.BreadCategories;
import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BreadCategoriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.MenusQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.MenusRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import com.depromeet.breadmapbackend.reviews.dto.CreateMenuReviewsRequest;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    private final MenusRepository menusRepository;
    private final BakeriesRepository bakeriesRepository;
    private final MenusQuerydslRepository menusQuerydslRepository;
    private final BreadCategoriesQuerydslRepository breadCategoriesQuerydslRepository;

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

    @Transactional
    public void createMenuReviewList(String token, Long bakeryId, List<CreateMenuReviewsRequest> createMenuReviewRequestList) {
        for(CreateMenuReviewsRequest createMenuReviewsRequest: createMenuReviewRequestList) {
            Long memberId = authService.getMemberId(token);
            String imgPath = createMenuReviewsRequest.getImgPathList().isEmpty() ? "" : createMenuReviewsRequest.getImgPathList().get(0);
            MenuReviews menuReview = new MenuReviews();

            Optional<Members> member = memberRepository.findById(memberId);
            Optional<Bakeries> bakery = bakeriesRepository.findById(bakeryId);
            Menus menu = menusQuerydslRepository.findByMenuNameBakeryId(createMenuReviewsRequest.getMenuName(), bakeryId);

            if (menu == null) {
                BreadCategories breadCategory = breadCategoriesQuerydslRepository.findByBreadCategoryName(createMenuReviewsRequest.getCategoryName().replaceAll("[ /]", ""));

                Menus newMenu = new Menus();
                newMenu.createMenu(bakery.orElseThrow(NullPointerException::new), createMenuReviewsRequest.getMenuName(), createMenuReviewsRequest.getPrice(), breadCategory, imgPath);
                menusRepository.save(newMenu);
                menuReview.createMenuReview(createMenuReviewsRequest, newMenu, member.orElseThrow(NullPointerException::new), bakery.orElseThrow(NullPointerException::new));
            } else {
                if (menu.getImgPath().equals("") && !imgPath.equals("")) {
                    menu.updateImgPath(imgPath);
                }
                menuReview.createMenuReview(createMenuReviewsRequest, menu, member.orElseThrow(NullPointerException::new), bakery.orElseThrow(NullPointerException::new));
            }
            menuReviewRepository.save(menuReview);
        }
    }
}
