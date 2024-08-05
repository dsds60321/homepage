package com.gh.apt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AptRequest {

    // 법정동 코드 (필수)
    @JsonProperty("lawdCd")
    private String LAWD_CD;
    // 실거래 자료 계약년월 (필수)
    @JsonProperty("dealYmd")
    private String DEAL_YMD;
    // 한페이지 결과 수 ( 비필수 )
    private String numOfRows;
    // 페이지 번호   ( 비필수 )
    private String pagNo;

}
