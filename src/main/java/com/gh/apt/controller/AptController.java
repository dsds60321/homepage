package com.gh.apt.controller;

import com.gh.apt.dto.AptRequest;
import com.gh.apt.service.AptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apt")
public class AptController {

    private final AptService aptService;

    @PostMapping("/info")
    public String getApitDetailInfo(@RequestBody AptRequest aptRequest) {
        return aptService.getAptInfo(aptRequest);
    }
}
