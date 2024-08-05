package com.gh.tenis.controller;

import com.gh.global.dto.request.Page;
import com.gh.global.dto.response.ApiResponse;
import com.gh.tenis.dto.TennisRespDto;
import com.gh.tenis.service.TennisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tennis")
public class TennisController {

    private final TennisService tennisService;

    // 테니스 예약현황 조회
    @PostMapping("/reservation")
    public ResponseEntity<ApiResponse> tennis() {
        TennisRespDto reservationList = tennisService.getReservationList();

        if (reservationList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.NOT_FOUND("예약 목록이 없습니다."));
        }

        return ResponseEntity.ok(ApiResponse.SUCCESS(reservationList));
    }
}
