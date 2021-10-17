package com.depromeet.breadmapbackend.bakeries.controller;

import com.depromeet.breadmapbackend.bakeries.dto.BakeryListResponse;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
