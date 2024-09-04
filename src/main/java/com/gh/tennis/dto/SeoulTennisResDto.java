package com.gh.tennis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@NoArgsConstructor
public class SeoulTennisResDto {

    @JsonProperty("ListPublicReservationSport")
    private ListPublicReservationSport listPublicReservationSport;

    @Getter
    @NoArgsConstructor
    static class ListPublicReservationSport {

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
        private String message;
    }

    @Getter
    @NoArgsConstructor
    public static class Row {

        // ServiceID : url 크롤링에 사용함
        @JsonProperty("SVCID")
        private String svcId;

        // 대분류 : 체육시설
        @JsonProperty("MAXCLASSNM")
        private String majorCategory;

        // 소분류 : 테니스장
        @JsonProperty("MINCLASSNM")
        private String subCategory;

        // 예약상태
        @JsonProperty("SVCSTATNM")
        private String bookingStatus;

        // 서비스명
        @JsonProperty("SVCNM")
        private String svcName;

        // 유료 or 무료
        @JsonProperty("PAYATNM")
        private String paymentType;

        // 장소
        @JsonProperty("PLACENM")
        private String place;

        // 서비스 대상
        @JsonProperty("USETGTINFO")
        private String svcTarget;

        // 서비스 url
        @JsonProperty("SVCURL")
        private String svcUrl;

        // 서비스 첫 오픈일시
        @JsonProperty("RCPTBGNDT")
        private String svcOpenDate;

        // 서비스 종료일시
        @JsonProperty("RCPTENDDT")
        private String svcCloseDate;

        // 영업 오픈 시간
        @JsonProperty("V_MIN")
        private String svcOpenTime;

        // 영업 종료 시간
        @JsonProperty("V_MAX")
        private String svcCloseTime;

        // 지역명 : 송파구
        @JsonProperty("AREANM")
        private String district;

        // 이미지 url
        @JsonProperty("IMGURL")
        private String imgUrl;

        // 상세정보
        @JsonProperty("DTLCONT")
        private String detailInfo;

        // 전화번호
        @JsonProperty("TELNO")
        private String telNo;

        // 취소기간 기준일
        @JsonProperty("REVSTDDAYNM")
        private String rfdBaseDay;

        // rfdDay
        @JsonProperty("REVSTDDAY")
        private String rfdDay;

        private List<LinkedHashMap<String, Boolean>> bookingList;

        public void setBookingList(List<LinkedHashMap<String, Boolean>> bookingList) {
            this.bookingList = bookingList;
        }

        // 예약 가능 여부
        public boolean isBooking() {
            return this.getBookingStatus().equals("접수중");
        }
    }


    /**
     * 정렬 기본 조건 예약 상태
     * @param isBookingSort : 예약 상태 정렬 여부
     * @return
     */
    public List<Row> getRows(boolean isBookingSort) {
        if (listPublicReservationSport != null) {

            List<Row> rows = listPublicReservationSport.getRows();

            if (rows != null && isBookingSort) {
                rows.sort(Comparator.comparing(Row::getBookingStatus, Comparator.comparingInt(
                        status -> status.equals("접수중") ? 0 : 1
                )));
            }

            return rows;
        }
        return null;
    }
}
