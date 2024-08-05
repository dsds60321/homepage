package com.gh.tenis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class TennisRespDto {

    public TennisRespDto() {}


    @JsonProperty("ListPublicReservationSport")
    private ListPublicReservationSport listPublicReservationSport;

    @Getter
    @NoArgsConstructor
    static class ListPublicReservationSport{

        @JsonProperty("list_total_count")
        private int listTotalCount;

        @JsonProperty("RESULT")
        private Result result;

        @JsonProperty("row")
        private List<Row> rows;
    }



    @Getter
    @NoArgsConstructor
    static class Result {

        @JsonProperty("CODE")
        private String code;

        @JsonProperty("MESSAGE")
        public String message;
    }

    @Getter
    @NoArgsConstructor
    static class Row {
        @JsonProperty("GUBUN")
        private String gubun;

        @JsonProperty("SVCID")
        private String svcId;

        @JsonProperty("MAXCLASSNM")
        private String maxClassNm;

        @JsonProperty("MINCLASSNM")
        private String minClassNm;

        @JsonProperty("SVCSTATNM")
        private String svcStatNm;

        @JsonProperty("SVCNM")
        private String svcNm;

        @JsonProperty("PAYATNM")
        private String payAtNm;

        @JsonProperty("PLACENM")
        private String placeNm;

        @JsonProperty("USETGTINFO")
        private String useTgtInfo;

        @JsonProperty("SVCURL")
        private String svcUrl;

        @JsonProperty("X")
        private double x;

        @JsonProperty("Y")
        private double y;

        @JsonProperty("SVCOPNBGNDT")
        private String svcOpnBgnDt;

        @JsonProperty("SVCOPNENDDT")
        private String svcOpnEndDt;

        @JsonProperty("RCPTBGNDT")
        private String rcptBgnDt;

        @JsonProperty("RCPTENDDT")
        private String rcptEndDt;

        @JsonProperty("AREANM")
        private String areaNm;

        @JsonProperty("IMGURL")
        private String imgUrl;

        @JsonProperty("DTLCONT")
        private String dtlCont;
    }
}
