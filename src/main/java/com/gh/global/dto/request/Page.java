package com.gh.global.dto.request;

import lombok.Data;

@Data
public class Page {

    private final int MAX_PER_SIZE = 100;

    private int page = 1; // 기본 페이지 번호
    private int perSize = 10; // 기본 페이지 크기

    public int getPage() {
        return page <= 0 ? 1 : page; // 페이지 번호는 1부터 시작
    }

    public int getPerSize() {
        return Math.min(perSize, MAX_PER_SIZE); // 최대 페이지 크기를 100으로 제한
    }
}
