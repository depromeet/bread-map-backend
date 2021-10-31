package com.depromeet.breadmapbackend.earth;

import com.depromeet.breadmapbackend.bakeries.repository.BreadCategoriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.MenusQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.service.MenusService;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menusService = new MenusService(breadCategoriesQuerydslRepository, menuReviewQuerydslRepository, menusQuerydslRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("잘못된 page값 입력 시 예외 발생")
    void getBakeryMenuList() {
        // then
        assertThrows(ResponseStatusException.class, () -> {
            menusService.getBakeryMenuList(BAKERY_ID, 0, 1);
        });
    }
}