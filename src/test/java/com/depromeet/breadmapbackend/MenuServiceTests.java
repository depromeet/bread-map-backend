package com.depromeet.breadmapbackend;

import com.depromeet.breadmapbackend.bakeries.domain.BreadCategories;
import com.depromeet.breadmapbackend.bakeries.dto.BakeryMenuResponse;
import com.depromeet.breadmapbackend.bakeries.repository.BreadCategoriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.MenusQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.service.MenusService;
import com.depromeet.breadmapbackend.common.enumerate.BreadCategoryType;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTests {

    @InjectMocks
    private MenusService menusService;

    @Mock
    private BreadCategoriesQuerydslRepository breadCategoriesQuerydslRepository;

    @Mock
    private MenusQuerydslRepository menusQuerydslRepository;

    @Mock
    private MenuReviewQuerydslRepository menuReviewQuerydslRepository;

    private static final Long BAKERY_ID = 1L;

    @ParameterizedTest
    @ValueSource(strings = {"쿠키류"})
    void 존재하지_않는_카테고리일_경우_ResponseStatusException_예외가_발생한다(String category) {
        BreadCategoryType.fromString(category);

        Exception exception = assertThrows(ResponseStatusException.class, () -> menusService.getMenuList(BAKERY_ID, category));
        String exceptedMessage = "존재하지 않는 빵 카테고리입니다.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));
        then(breadCategoriesQuerydslRepository).should(never()).findByBreadCategoryName(category);
    }

    @ParameterizedTest
    @ValueSource(strings = {"과자류"})
    void 메뉴리스트_조회시_카테고리명은_존재하나_해당_베이커리에_데이터가_없을_경우_빈_리스트를_반환한다(String category) {
        BreadCategories categories = new BreadCategories();
        given(breadCategoriesQuerydslRepository.findByBreadCategoryName(category)).willReturn(categories);
        given(menusQuerydslRepository.findByBreadCategoryIdBakeryId(categories.getId(), BAKERY_ID)).willReturn(Collections.emptyList());

        menusService.getMenuList(BAKERY_ID, category);

        assertEquals(Collections.emptyList(), menusQuerydslRepository.findByBreadCategoryIdBakeryId(categories.getId(), BAKERY_ID));
    }

    @ParameterizedTest
    @AutoSource
    void 베이커리_아이디와_카테고리_아이디의_데이터를_가지는_메뉴들이_있으면_메뉴리스트를_반환한다(BreadCategories breadCategories, List<String> menuList) {
        String category = "도넛";
        BreadCategoryType.fromString(category);
        given(breadCategoriesQuerydslRepository.findByBreadCategoryName(category)).willReturn(breadCategories);
        Long categoryId = breadCategories.getId();

        when(menusQuerydslRepository.findByBreadCategoryIdBakeryId(categoryId, BAKERY_ID)).thenReturn(menuList);

        menusService.getMenuList(BAKERY_ID, category);

        verify(menusQuerydslRepository).findByBreadCategoryIdBakeryId(categoryId, BAKERY_ID);
    }

    @ParameterizedTest
    @AutoSource
    void 메뉴리스트_조회시_해당_카테고리_아이디의_데이터가_없을_경우_빈_리스트를_반환한다(BreadCategories breadCategories) {
        Long categoryId = breadCategories.getId();

        when(menusQuerydslRepository.findByBreadCategoryIdBakeryId(categoryId, BAKERY_ID)).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), menusQuerydslRepository.findByBreadCategoryIdBakeryId(categoryId, BAKERY_ID));
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void 페이지가_1보다_작은_수로_들어올_경우_ResponseStatusException_예외가_발생한다(int page) {
        int limit = 10;
        Exception exception = assertThrows(ResponseStatusException.class, () -> menusService.getBakeryMenuList(BAKERY_ID, page, limit));
        String exceptedMessage = "잘못된 page 값입니다(시작 page: 1).";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));

        then(menuReviewQuerydslRepository).should(never()).findBakeryMenuPageableByBakeryId(BAKERY_ID, PageRequest.of(page, limit));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void 페이지가_1이상의_숫자로_들어왔을_경우_limit값_만큼의_데이터_리스트를_반환한다(int page) {
        int limit = 10;
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<BakeryMenuResponse> menuResponseList = Arrays.asList(new BakeryMenuResponse(), new BakeryMenuResponse());
        Slice<BakeryMenuResponse> menuResponsesSlice = new PageImpl<>(menuResponseList, pageable, menuResponseList.size());

        when(menuReviewQuerydslRepository.findBakeryMenuPageableByBakeryId(BAKERY_ID, pageable)).thenReturn(menuResponsesSlice);

        menusService.getBakeryMenuList(BAKERY_ID, page, limit);

        verify(menuReviewQuerydslRepository).findBakeryMenuPageableByBakeryId(BAKERY_ID, pageable);
    }
}
