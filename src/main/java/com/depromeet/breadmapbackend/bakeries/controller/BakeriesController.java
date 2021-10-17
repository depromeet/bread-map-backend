package com.depromeet.breadmapbackend.bakeries.controller;

import com.depromeet.breadmapbackend.bakeries.dto.BakeryListResponse;
import com.depromeet.breadmapbackend.bakeries.dto.MenusListResponse;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.bakeries.dto.CreateBakeryRequest;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import com.depromeet.breadmapbackend.reviews.dto.CreateMenuReviewsRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bakery")
@RequiredArgsConstructor
public class BakeriesController {

    /**
     * 빵집 리스트 조회
     * @return ResponseEntity<BakeryListResponse>
     */
    @ApiOperation(value = "빵집 리스트", notes = "빵집 리스트 조회")
    @GetMapping(value = "")
    public ResponseEntity<BakeryListResponse> getBakeryList(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Long range){ // TODO range 소숫점 있는지 체크 필요
        return null;
    }

    /**
     * 선택된 카테고리에 해댕하는 빵 리스트 반환
     * @param bakeryId
     * @param category
     * @return 성공 시 200 OK + menusListResponse
     */
    @ApiOperation(value = "선택된 빵 카테고리에 해당하는 빵(메뉴) 리스트 반환", notes = "리뷰 작성 시 선택된 빵 카테고리에 속하는 빵(메뉴) 리스트 반환")
    @GetMapping(value = "/{bakeryId}")
    public ResponseEntity getMenusList(@PathVariable Long bakeryId, @RequestParam String category) {
        MenusListResponse menusListResponse = new MenusListResponse();
        return ApiResponse.success(menusListResponse);
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
     * @param createMenuReviewsRequestList
     * @return 성공 시 201 Created
     */
    @ApiOperation(value = "빵 리뷰(들) 작성", notes = "빵(들)에 대한 리뷰 작성")
    @PostMapping(value = "/{bakeryId}/menu-review")
    public ResponseEntity<Void> createMenuReviewsList(@PathVariable Long bakeryId, @RequestBody List<CreateMenuReviewsRequest> createMenuReviewsRequestList) {
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
}
