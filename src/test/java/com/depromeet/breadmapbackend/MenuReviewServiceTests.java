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
    void ??????_???????????????_?????????_?????????_????????????_ResponseStatusException???_????????????(MenuReviews menuReviews, Members members, String token, Long bakeryId, Long memberId, Long menuReviewId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        when(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(bakeryId, menuReviewId)).thenReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> menuReviewsService.deleteMenuReview(token, bakeryId, menuReviewId));
        String exceptedMessage = "????????? ???????????? ????????????.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));
        then(menuReviewRepository).should(never()).delete(menuReviews);
    }

    @ParameterizedTest
    @AutoSource
    void ?????????_?????????_?????????_?????????_??????_????????????_ResponseStatusException???_????????????(Menus menus, Bakeries bakeries, Members members, String token, Long bakeryId, Long memberId, Long menuReviewId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        MenuReviews menuReviews = new MenuReviews(menuReviewId, menus, members, bakeries, "??????", 4L, new ArrayList<>());
        when(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(bakeryId, menuReviewId)).thenReturn(menuReviews);

        if(!Objects.equals(menuReviews.getMembers().getId(), members.getId())) {
            Exception exception = assertThrows(ResponseStatusException.class, () -> menuReviewsService.deleteMenuReview(token, bakeryId, menuReviewId));
            String exceptedMessage = "?????? ?????? ???????????? ????????????.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(exceptedMessage));
        }

        then(menuReviewRepository).should(never()).delete(menuReviews);
    }

    @ParameterizedTest
    @AutoSource
    void ?????????_?????????_????????????_?????????_?????????_????????????_?????????_????????????(Menus menus, Bakeries bakeries, Members members, String token, Long bakeryId, Long memberId, Long menuReviewId) {
        given(authService.getMemberId(token)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));

        MenuReviews menuReviews = new MenuReviews(menuReviewId, menus, members, bakeries, "??????", 4L, new ArrayList<>());
        when(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(bakeryId, menuReviewId)).thenReturn(menuReviews);

        menuReviewsService.deleteMenuReview(token, bakeryId, menuReviewId);

        then(menuReviewRepository).should().delete(menuReviews);
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void ????????????_1??????_??????_??????_?????????_??????_ResponseStatusException_?????????_????????????(int page) {
        int limit = 10;
        Long bakeryId = 1L;
        Exception exception = assertThrows(ResponseStatusException.class, () -> menuReviewsService.getMenuReviewList(bakeryId, page, limit));
        String exceptedMessage = "????????? page ????????????(?????? page: 1).";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));

        then(menuReviewQuerydslRepository).should(never()).findMenuReviewPageableByBakeryId(bakeryId, PageRequest.of(page, limit));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void ????????????_1?????????_?????????_????????????_??????_limit???_?????????_?????????_????????????_????????????(int page) {
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
    void ????????????_???_???????????????_??????????????????_?????????????????????_DB???_??????_???_???????????????_????????????(BreadCategories breadCategory, Members members, Bakeries bakeries, String token, Long bakeryId, Long memberId, Menus menus) {
        List<CreateMenuReviewsRequest> createMenuReviewRequestList = new ArrayList<>();
        CreateMenuReviewsRequest createMenuReviewsRequest = new CreateMenuReviewsRequest("????????????", "?????????", 1000, 5L, "????????????", new ArrayList<>());
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
    void ????????????_???_???????????????_????????????_????????????_?????????_????????????_????????????_???????????????_????????????(List<String> imgList, BreadCategories breadCategory, Members members, Bakeries bakeries, String token, Long bakeryId, Long memberId) {
        Menus menus = new Menus(1L, "?????????", bakeries, 1000, breadCategory, imgList.get(0));
        List<CreateMenuReviewsRequest> createMenuReviewRequestList = new ArrayList<>();
        CreateMenuReviewsRequest createMenuReviewsRequest = new CreateMenuReviewsRequest("????????????", "?????????", 1000, 5L, "????????????", imgList);
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
                if(menus.getImgPath().equals("") && imgPath != null) menus.updateImgPath(imgPath);
                menuReviews.createMenuReview(createMenuReviewsRequest, menus, members, bakeries);
            }
            verify(menuReviewRepository).save(any());
        });
    }
}
