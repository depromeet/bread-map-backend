package com.depromeet.breadmapbackend;

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
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewRepository;
import com.depromeet.breadmapbackend.reviews.service.MenuReviewsService;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MenuReviewServiceTests {

    @InjectMocks
    private MenuReviewsService menuReviewsService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BakeriesRepository bakeriesRepository;

    @Mock
    private MenusQuerydslRepository menusQuerydslRepository;

    @Mock
    private MenuReviewRepository menuReviewRepository;

    @Mock
    private MenuReviewQuerydslRepository menuReviewQuerydslRepository;

    @Mock
    private BreadCategoriesQuerydslRepository breadCategoriesQuerydslRepository;

    @Mock
    private MenusRepository menusRepository;
    @Mock
    private AuthService authService;

    @ParameterizedTest
    @AutoSource
    void 해당_베이커리에_리뷰를_남기지_않았다면_ResponseStatusException이_발생한다(MenuReviews menuReviews, Members members, String token, Long bakeryId, Long memberId, Long menuReviewId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        when(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(bakeryId, menuReviewId)).thenReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> menuReviewsService.deleteMenuReview(token, bakeryId, menuReviewId));
        String exceptedMessage = "리뷰가 존재하지 않습니다.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));
        then(menuReviewRepository).should(never()).delete(menuReviews);
    }

    @ParameterizedTest
    @AutoSource
    void 조회한_리뷰가_자신이_작성한_것이_아니라면_ResponseStatusException이_발생한다(Menus menus, Bakeries bakeries, Members members, String token, Long bakeryId, Long memberId, Long menuReviewId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        MenuReviews menuReviews = new MenuReviews(menuReviewId, menus, members, bakeries, "리뷰", 4L, new ArrayList<>());
        when(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(bakeryId, menuReviewId)).thenReturn(menuReviews);

        if(!Objects.equals(menuReviews.getMembers().getId(), members.getId())) {
            Exception exception = assertThrows(ResponseStatusException.class, () -> menuReviewsService.deleteMenuReview(token, bakeryId, menuReviewId));
            String exceptedMessage = "해당 리뷰 작성자가 아닙니다.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(exceptedMessage));
        }

        then(menuReviewRepository).should(never()).delete(menuReviews);
    }

    @ParameterizedTest
    @AutoSource
    void 조회한_리뷰가_존재하고_자신이_작성한_것이라면_리뷰를_삭제한다(Menus menus, Bakeries bakeries, Members members, String token, Long bakeryId, Long memberId, Long menuReviewId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        MenuReviews menuReviews = new MenuReviews(menuReviewId, menus, members, bakeries, "리뷰", 4L, new ArrayList<>());
        when(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(bakeryId, menuReviewId)).thenReturn(menuReviews);

        menuReviewsService.deleteMenuReview(token, bakeryId, menuReviewId);

        then(menuReviewRepository).should().delete(menuReviews);
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void 페이지가_1보다_작은_수로_들어올_경우_ResponseStatusException_예외가_발생한다(int page) {
        int limit = 10;
        Long bakeryId = 1L;
        Exception exception = assertThrows(ResponseStatusException.class, () -> menuReviewsService.getMenuReviewList(bakeryId, page, limit));
        String exceptedMessage = "잘못된 page 값입니다(시작 page: 1).";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));

        then(menuReviewQuerydslRepository).should(never()).findMenuReviewPageableByBakeryId(bakeryId, PageRequest.of(page, limit));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void 페이지가_1이상의_숫자로_들어왔을_경우_limit값_만큼의_데이터_리스트를_반환한다(int page) {
        int limit = 10;
        Long bakeryId = 1L;
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<MenuReviewResponse> menuReviewResponseList = Arrays.asList(new MenuReviewResponse(), new MenuReviewResponse());
        Slice<MenuReviewResponse> menuReviewResponseSlice = new PageImpl<>(menuReviewResponseList, pageable, menuReviewResponseList.size());

        when(menuReviewQuerydslRepository.findMenuReviewPageableByBakeryId(bakeryId, pageable)).thenReturn(menuReviewResponseSlice);

        menuReviewsService.getMenuReviewList(bakeryId, page, limit);

        verify(menuReviewQuerydslRepository).findMenuReviewPageableByBakeryId(bakeryId, pageable);
    }

    @ParameterizedTest
    @AutoSource
    void 요청으로_온_메뉴정보가_존재하지않는_신규데이터라면_DB에_저장_후_메뉴리뷰를_저장한다(BreadCategories breadCategory, Members members, Bakeries bakeries, String token, Long bakeryId, Long memberId, Menus menus) {
        List<CreateMenuReviewsRequest> createMenuReviewRequestList = new ArrayList<>();
        CreateMenuReviewsRequest createMenuReviewsRequest = new CreateMenuReviewsRequest("카테고리", "메뉴명", 1000, 5L, "리뷰내용", new ArrayList<>());
        createMenuReviewRequestList.add(createMenuReviewsRequest);

        createMenuReviewRequestList.forEach(createMenuReviews -> {
            MenuReviews menuReviews = new MenuReviews();
            given(authService.getMemberId(token)).willReturn(memberId);
            given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));
            given(bakeriesRepository.findById(bakeryId)).willReturn(Optional.ofNullable(bakeries));

            String imgPath = createMenuReviews.getImgPathList().isEmpty() || createMenuReviews.getImgPathList() == null ? "" : createMenuReviews.getImgPathList().get(0);

            when(menusQuerydslRepository.findByMenuNameBakeryId(createMenuReviews.getMenuName(), bakeryId)).thenReturn(null);
            when(breadCategoriesQuerydslRepository.findByBreadCategoryName(createMenuReviewsRequest.getCategoryName().replaceAll("[ /]", ""))).thenReturn(breadCategory);

            menuReviewsService.createMenuReviewList(token, bakeryId, createMenuReviewRequestList);

            if (menus.getId() == null) {
                Menus newMenu = new Menus();
                newMenu.createMenu(bakeries, createMenuReviewsRequest.getMenuName(), createMenuReviewsRequest.getPrice(), breadCategory, imgPath);

                menusRepository.save(newMenu);
                menuReviews.createMenuReview(createMenuReviewsRequest, newMenu, members, bakeries);
            }
            verify(menuReviewRepository).save(any());
        });
    }

    @ParameterizedTest
    @AutoSource
    void 요청으로_온_메뉴정보가_존재하고_이미지가_없다면_이미지를_저장하고_메뉴리뷰를_저장한다(List<String> imgList, BreadCategories breadCategory, Members members, Bakeries bakeries, String token, Long bakeryId, Long memberId) {
        Menus menus = new Menus(1L, "메뉴명", bakeries, 1000, breadCategory, imgList.get(0));
        List<CreateMenuReviewsRequest> createMenuReviewRequestList = new ArrayList<>();
        CreateMenuReviewsRequest createMenuReviewsRequest = new CreateMenuReviewsRequest("카테고리", "메뉴명", 1000, 5L, "리뷰내용", imgList);
        createMenuReviewRequestList.add(createMenuReviewsRequest);

        createMenuReviewRequestList.forEach(createMenuReviews -> {
            MenuReviews menuReviews = new MenuReviews();
            given(authService.getMemberId(token)).willReturn(memberId);
            given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));
            given(bakeriesRepository.findById(bakeryId)).willReturn(Optional.ofNullable(bakeries));

            String imgPath = createMenuReviews.getImgPathList().isEmpty() || createMenuReviews.getImgPathList() == null ? "" : createMenuReviews.getImgPathList().get(0);

            when(menusQuerydslRepository.findByMenuNameBakeryId(createMenuReviews.getMenuName(), bakeryId)).thenReturn(menus);
            when(breadCategoriesQuerydslRepository.findByBreadCategoryName(createMenuReviewsRequest.getCategoryName().replaceAll("[ /]", ""))).thenReturn(breadCategory);

            menuReviewsService.createMenuReviewList(token, bakeryId, createMenuReviewRequestList);

            if (menus != null) {
                if(menus.getImgPath() != null && imgPath != null) menus.updateImgPath(imgPath);
                menuReviews.createMenuReview(createMenuReviewsRequest, menus, members, bakeries);
            }
            verify(menuReviewRepository).save(any());
        });
    }
}
