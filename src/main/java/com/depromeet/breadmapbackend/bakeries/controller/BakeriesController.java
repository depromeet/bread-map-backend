package com.depromeet.breadmapbackend.bakeries.controller;

import com.depromeet.breadmapbackend.auth.jwt.JwtHeaderUtil;
import com.depromeet.breadmapbackend.bakeries.dto.*;
import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
import com.depromeet.breadmapbackend.bakeries.service.MenusService;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import com.depromeet.breadmapbackend.flags.service.FlagsService;
import com.depromeet.breadmapbackend.reviews.dto.CreateMenuReviewsRequest;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import com.depromeet.breadmapbackend.reviews.service.MenuReviewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bakery")
@RequiredArgsConstructor
public class BakeriesController {

    private final BakeriesService bakeriesService;
    private final MenuReviewsService menuReviewsService;
    private final FlagsService flagsService;
    private final MenusService menusService;

    /**
     * 빵집 리스트 조회
     * @return ResponseEntity<BakeryListResponse>
     */
    @ApiOperation(value = "빵집 리스트", notes = "빵집 리스트 조회")
    @GetMapping
    public ResponseEntity<List<BakeryListResponse>> getBakeryList(
            @ApiParam(value="위도", required = true) @RequestParam Double latitude,
            @ApiParam(value="경도", required = true) @RequestParam Double longitude,
            @ApiParam(value="반지름(m)", required = true) @RequestParam Long range){
        return ApiResponse.success(bakeriesService.getBakeryList(latitude, longitude, range));
    }

    /**
     * 단일 빵집 리뷰 조회
     * @return ResponseEntity<Slice<MenuReviewResponse>>
     */
    @ApiOperation(value = "단일 빵집 리뷰 리스트", notes = "단일 빵집에 있는 메뉴에 대한 리뷰 리스트 조회")
    @GetMapping(value = "/{bakeryId}/menu-review")
    public ResponseEntity<Slice<MenuReviewResponse>> getMenuReviewList(@PathVariable Long bakeryId,
                                                                       @ApiParam(value="page index(1부터 시작)", required = true) @RequestParam Integer page,
                                                                       @ApiParam(value="page당 메뉴 최대 개수", required = true) @RequestParam Integer limit) {
        return ApiResponse.success(menuReviewsService.getMenuReviewList(bakeryId, page, limit));
    }

    /**
     * 단일 빵집 메뉴 조회
     * @return ResponseEntity<Slice<BakeryMenuResponse>>
     */
    @ApiOperation(value = "단일 빵집 메뉴 리스트", notes = "단일 빵집에 있는 메뉴 리스트 조회")
    @GetMapping(value = "/{bakeryId}/menu")
    public ResponseEntity<Slice<BakeryMenuResponse>> getBakeryMenuList(
            @PathVariable Long bakeryId,
            @ApiParam(value="page index(1부터 시작)", required = true) @RequestParam Integer page,
            @ApiParam(value="page당 메뉴 최대 개수", required = true) @RequestParam Integer limit){
        return ApiResponse.success(menusService.getBakeryMenuList(bakeryId, page, limit));
    }

    /**
     * 빵집 별점 넣기
     * @return 성공 시 201 Created
     */
    @ApiOperation(value = "빵집 별점 넣기", notes = "빵집 별점 넣기")
    @PostMapping(value = "/{bakeryId}/rating")
    public ResponseEntity<Void> registerBakeryRating(HttpServletRequest request, @PathVariable Long bakeryId, @RequestBody RegisterBakeryRatingRequest registerBakeryRatingRequest){
        String token = JwtHeaderUtil.getAccessToken(request);
        bakeriesService.registerBakeryRating(token, bakeryId, registerBakeryRatingRequest);
        return ApiResponse.created(null);
    }

     /** 선택된 카테고리에 해당하는 빵 리스트 반환
     * @param bakeryId
     * @param category
     * @return 성공 시 200 OK + menusListResponse
     */
    @ApiOperation(value = "선택된 빵 카테고리에 해당하는 빵(메뉴) 리스트 반환", notes = "리뷰 작성 시 선택된 빵 카테고리에 속하는 빵(메뉴) 리스트 반환")
    @GetMapping(value = "/{bakeryId}/category/menu")
    public ResponseEntity<MenuListResponse> getMenuList(@PathVariable Long bakeryId, @RequestParam String category) {
        MenuListResponse menuListResponse = menusService.getMenuList(bakeryId, category);
        return ApiResponse.success(menuListResponse);
    }

    /**
     * 신규 빵집 생성
     * @param createBakeryRequest
     * @return 성공 시 201 Created
     */
    @ApiOperation(value = "빵집 생성", notes = "신규 빵집 생성")
    @PostMapping
    public ResponseEntity<Void> createBakery(HttpServletRequest request, @RequestBody CreateBakeryRequest createBakeryRequest) {
        String token = JwtHeaderUtil.getAccessToken(request);
        bakeriesService.createBakery(token, createBakeryRequest);
        return ApiResponse.created(null);
    }

    /**
     * 빵(들)에 대한 리뷰 작성
     * @param bakeryId
     * @param createMenuReviewRequestList
     * @return 성공 시 201 Created
     */
    @ApiOperation(value = "빵 리뷰(들) 작성", notes = "빵(들)에 대한 리뷰 작성")
    @PostMapping(value = "/{bakeryId}/menu-review")
    public ResponseEntity<Void> createMenuReviewList(HttpServletRequest request, @PathVariable Long bakeryId, @RequestBody List<CreateMenuReviewsRequest> createMenuReviewRequestList) {
        String token = JwtHeaderUtil.getAccessToken(request);
        menuReviewsService.createMenuReviewList(token, bakeryId, createMenuReviewRequestList);
        return ApiResponse.created(null);
    }

    /**
     * 빵 리뷰 삭제
     * @return 삭제 성공 시 200 OK 또는 202 Accepted, 204 No Content
     */
    @ApiOperation(value = "빵 리뷰 삭제", notes = "빵 리뷰 삭제")
    @DeleteMapping(value = "/{bakeryId}/menu-review/{menuReviewId}")
    public ResponseEntity<Void> deleteMenuReview(HttpServletRequest request, @PathVariable Long bakeryId, @PathVariable Long menuReviewId) {
        String token = JwtHeaderUtil.getAccessToken(request);
        menuReviewsService.deleteMenuReview(token, bakeryId, menuReviewId);
        return ApiResponse.success(null);
    }

    /**
     * 빵집 깃발 꼽기
     * @return ResponseEntity<Void> 잘못된 깃발 타입 보내줄 경우 400 error
     */
    @ApiOperation(value = "빵집 깃발 꼽기/해제", notes = "빵집에 가볼 곳/가본 곳에 대한 깃발 꼽기/해제 기능(NONE/PICKED/GONE) - NONE일 경우 해제")
    @PostMapping("/{bakeryId}/flag")
    public ResponseEntity<Void> registerFlag(HttpServletRequest request, @RequestBody CreateFlagsRequest createFlagsRequest, @PathVariable Long bakeryId) {
        String token = JwtHeaderUtil.getAccessToken(request);
        flagsService.registerFlag(token, bakeryId, createFlagsRequest);
        return ApiResponse.success(null);
    }

    /**
     * 단일빵집 상세 조회
     * @return List<BakeryDetailResponse>
     */
    @ApiOperation(value = "단일빵집 상세 조회", notes = "지도에 클릭한 빵집의 상세보기 기능")
    @GetMapping("/{bakeryId}")
    public ResponseEntity<BakeryDetailResponse> getBakeryDetail(HttpServletRequest request, @PathVariable Long bakeryId) {
        String token = JwtHeaderUtil.getAccessToken(request);

        return ApiResponse.success(bakeriesService.getBakeryDetail(token, bakeryId));
    }
}
