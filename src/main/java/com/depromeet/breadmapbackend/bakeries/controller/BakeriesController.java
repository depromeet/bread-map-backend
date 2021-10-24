package com.depromeet.breadmapbackend.bakeries.controller;

import com.depromeet.breadmapbackend.auth.jwt.JwtHeaderUtil;
import com.depromeet.breadmapbackend.bakeries.dto.*;
import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import com.depromeet.breadmapbackend.reviews.dto.CreateMenuReviewsRequest;
import com.depromeet.breadmapbackend.reviews.dto.MenuReviewResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 빵집 리스트 조회
     * @return ResponseEntity<BakeryListResponse>
     */
    @ApiOperation(value = "빵집 리스트", notes = "빵집 리스트 조회")
    @GetMapping
    public ResponseEntity<BakeryListResponse> getBakeryList(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Long range){ // TODO range 소숫점 있는지 체크 필요
        return null;
    }

    /**
     * 단일 빵집 리뷰 조회
     * @return List<MenuReviewDetailResponse>
     */
    @ApiOperation(value = "단일 빵집 리뷰 리스트", notes = "단일 빵집에 있는 메뉴에 대한 리뷰 리스트 조회")
    @GetMapping(value = "/{bakeryId}/menu-review")
    public List<MenuReviewResponse> getMenuReviewList(@PathVariable Long bakeryId){
        return null;
    }

    /**
     * 단일 빵집 메뉴 조회
     * @return ResponseEntity<BakeryMenusListResponse>
     */
    @ApiOperation(value = "단일 빵집 메뉴 리스트", notes = "단일 빵집에 있는 메뉴 리스트 조회")
    @GetMapping(value = "/{bakeryId}/menus")
    public ResponseEntity<BakeryMenuResponse> getBakeryMenuList(@PathVariable Long bakeryId){
        return null;
    }

    /**
     * 빵집 별점 넣기
     * @return 성공 시 201 Created
     */
    @ApiOperation(value = "빵집 별점 넣기", notes = "빵집 별점 넣기")
    @PostMapping(value = "/{bakeryId}/rating")
    public ResponseEntity<Void> registerBakeryRating(@PathVariable Long bakeryId, @RequestBody RegisterBakeryRatingRequest registerBakeryRatingRequest){
        RegisterBakeryRatingResponse registerBakeryRatingResponse = new RegisterBakeryRatingResponse();
        Double totalRating = registerBakeryRatingResponse.getRating();
        totalRating = totalRating/registerBakeryRatingRequest.getRating();
        return ApiResponse.created(null);
    }

     /** 선택된 카테고리에 해당하는 빵 리스트 반환
     * @param bakeryId
     * @param category
     * @return 성공 시 200 OK + menusListResponse
     */
    @ApiOperation(value = "선택된 빵 카테고리에 해당하는 빵(메뉴) 리스트 반환", notes = "리뷰 작성 시 선택된 빵 카테고리에 속하는 빵(메뉴) 리스트 반환")
    @GetMapping(value = "/{bakeryId}/menu")
    public ResponseEntity<MenuListResponse> getMenuList(@PathVariable Long bakeryId, @RequestParam String category) {
        MenuListResponse menuListResponse = bakeriesService.getMenuList(bakeryId, category);
        return ApiResponse.success(menuListResponse);
    }

    /**
     * 신규 빵집 생성
     * @param createBakeryRequest
     * @return 성공 시 201 Created
     */
    @ApiOperation(value = "빵집 생성", notes = "신규 빵집 생성")
    @PostMapping
    public ResponseEntity<Void> createBakery(@RequestBody CreateBakeryRequest createBakeryRequest) {
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
        bakeriesService.createMenuReviewList(token, bakeryId, createMenuReviewRequestList);
        return ApiResponse.created(null);
    }

    /**
     * 빵 리뷰 삭제
     * @return 삭제 성공 시 200 OK 또는 202 Accepted, 204 No Content
     */
    @ApiOperation(value = "빵 리뷰 삭제", notes = "빵 리뷰 삭제")
    @DeleteMapping(value = "/{bakeryId}/menu-review/{menuReviewId}")
    public ResponseEntity<Void> deleteMenuReview(@PathVariable Long bakeryId, @PathVariable Long menuReviewId) {
        return ApiResponse.success(null);
    }

    /**
     * 빵집 깃발 꼽기
     * @return ResponseEntity<Void>
     */
    @ApiOperation(value = "빵집 깃발 꼽기", notes = "빵집에 가볼 곳/가본 곳에 대한 깃발 꼽기 기능")
    @PostMapping("/{bakeryId}/flag")
    public ResponseEntity<Void> registerFlag(@RequestBody CreateFlagsRequest createFlagsRequest, @PathVariable Long bakeryId) {
        return null;
    }

    /**
     * 빵집에 꽂힌 깃발 해제
     * @return ResponseEntity<Void>
     */
    @ApiOperation(value = "빵집 깃발 꼽기", notes = "빵집에 가볼 곳/가본 곳에 대한 꽂힌 깃발을 해제하는 기능")
    @DeleteMapping("/{bakeryId}/flag/{flagId}")
    public ResponseEntity<Void> deleteFlags(@PathVariable Long flagId, @PathVariable Long bakeryId) {
        return null;
    }

    /**
     * 단일빵집 상세 조회
     * @return List<BakeryDetailResponse>
     */
    @ApiOperation(value = "단일빵집 상세 조회", notes = "지도에 클릭한 빵집의 상세보기 기능")
    @GetMapping("/{bakeryId}")
    public BakeryDetailResponse getBakeryDetail(HttpServletRequest request, @PathVariable Long bakeryId) {
        String token = JwtHeaderUtil.getAccessToken(request);

        return bakeriesService.getBakeryDetail(token, bakeryId);
    }
}
