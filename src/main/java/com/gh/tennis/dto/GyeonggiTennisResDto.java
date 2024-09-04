package com.gh.tennis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GyeonggiTennisResDto {

    @JsonProperty("PublicTennis")
    private List<PublicTennis> publicTennis;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PublicTennis {

        @JsonProperty("head")
        private List<Head> heads;

        @JsonProperty("row")
        private List<Row> rows;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class Head {
        @JsonProperty("list_total_count")
        private int listTotalCount;

        @JsonProperty("RESULT")
        private Result result;

        @JsonProperty("api_version")
        private String apiVersion;
    }

    @Getter
    @NoArgsConstructor
    static class Result {
        @JsonProperty("CODE")
        private String code;

        @JsonProperty("MESSAGE")
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class Row {

        // 서비스명
        @JsonProperty("FACLT_NM")
        private String svcName;

        // 안산시
        @JsonProperty("district")
        private String posesnInstNm;

        // 구주소
        @JsonProperty("REFINE_LOTNO_ADDR")
        private String legacyAddr;

        // 도로명 주소
        @JsonProperty("REFINE_ROADNM_ADDR")
        private String roadAddr;

        // 홈페이지 주소
        @JsonProperty("HMPG_ADDR")
        private String svcUrl;

        @JsonProperty("SUM_YY")
        private String sumYy;

        @JsonProperty("SIGUN_NM")
        private String sigunNm;

        @JsonProperty("RM_MATR")
        private String rmMatr;

        @JsonProperty("CONSTR_BIZ_EXPN")
        private String constrBizExpn;

        @JsonProperty("COMPLTN_YY")
        private String compltnYy;

        @JsonProperty("SEAT_FORM_NM")
        private String seatFormNm;

        @JsonProperty("SIGUN_CD")
        private String sigunCd;

        @JsonProperty("OPERT_ORGNZT_NM")
        private String opertOrgnztNm;

        @JsonProperty("CONTCT_NO")
        private String contctNo;

        @JsonProperty("MANAGE_MAINBD_NM")
        private String manageMainbdNm;

        @JsonProperty("PLOT_AR")
        private int plotAr;

        @JsonProperty("BUILD_AR")
        private String buildAr;

        @JsonProperty("TOT_AR")
        private String totAr;

        @JsonProperty("BOTM_MATRL_NM")
        private String botmMatrlNm;

        @JsonProperty("AR")
        private int ar;

        @JsonProperty("COURT_PLANE_CNT")
        private int courtPlaneCnt;

        @JsonProperty("AUDTRM_SEAT_CNT")
        private String audtrmSeatCnt;

        @JsonProperty("ACEPTNC_PSN_CNT")
        private String aceptncPsnCnt;

        @JsonProperty("REFINE_ZIP_CD")
        private String refineZipCd;

        @JsonProperty("REFINE_WGS84_LOGT")
        private String refineWgs84Logt;

        @JsonProperty("REFINE_WGS84_LAT")
        private String refineWgs84Lat;
    }

    // 헤더와 행을 합치는 메서드
    public PublicTennis mergePublicTennis() {
        PublicTennis merged = new PublicTennis();
        List<Head> mergedHeads = new ArrayList<>();
        List<Row> mergedRows = new ArrayList<>();

        for (PublicTennis tennis : publicTennis) {
            if (tennis.getHeads() != null) {
                mergedHeads.addAll(tennis.getHeads());
            }
            if (tennis.getRows() != null) {
                mergedRows.addAll(tennis.getRows());
            }
        }

        merged.setHeads(mergedHeads);
        merged.setRows(mergedRows);
        return merged;
    }

    public List<Row> getRows() {
        return publicTennis != null && !publicTennis.isEmpty() ? publicTennis.get(0).getRows() : null;
    }
}
