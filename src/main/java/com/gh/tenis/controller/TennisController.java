package com.gh.tenis.controller;

import com.gh.global.dto.request.Page;
import com.gh.tenis.dto.TennisRespDto;
import com.gh.tenis.service.TennisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tennis")
public class TennisController {

    private final TennisService tennisService;

    @PostMapping("/reservation")
    public TennisRespDto tennis(@RequestBody Page page) {
        return tennisService.getReservationList(page.getPage(), page.getPerSize());
    }
}
