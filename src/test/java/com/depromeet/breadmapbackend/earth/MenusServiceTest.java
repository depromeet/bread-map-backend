package com.depromeet.breadmapbackend.earth;

import com.depromeet.breadmapbackend.bakeries.repository.BreadCategoriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.MenusQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.service.MenusService;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MenusServiceTest {

    @InjectMocks
    private MenusService menusService;

    @Mock
    private BreadCategoriesQuerydslRepository breadCategoriesQuerydslRepository;

    @Mock
    private MenuReviewQuerydslRepository menuReviewQuerydslRepository;

    @Mock
    private MenusQuerydslRepository menusQuerydslRepository;

    private static final Long BAKERY_ID = 1L;

    @AfterEach
    void tearDown() {
    }

    @DisplayName("존재하지 않는 빵 카테고리 입력 시 예외 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"BAKERY", "BREAD"})
    public void getMenuListIfNotExistCategoryInsert(String category) {
        // given
        String exception = "존재하지 않는 빵 카테고리입니다.";

        // when
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            menusService.getMenuList(BAKERY_ID, category);
        });

        // then
        assertEquals(responseStatusException.getReason(), exception);
    }

    @Test
    @DisplayName("잘못된 page값(1보다 작은 값) 입력 시 예외 발생합니다.")
    void getBakeryMenuList() {
        // given
        String exception = "잘못된 page 값입니다(시작 page: 1).";

        // when
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            menusService.getBakeryMenuList(BAKERY_ID, 0, 1);
        });

        // then
        assertEquals(responseStatusException.getReason(), exception);
    }
}