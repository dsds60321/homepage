package com.gh.apt.dto;

import lombok.Getter;

public class AptRe {
    public static class Response {
        private Header header;
        private Body body;

        // Getters and setters
    }

    public static class Header {
        private String resultCode;
        private String resultMsg;

        // Getters and setters
    }

    public static class Body {
        private Item[] items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        // Getters and setters
    }

    public static class Item {
        private String 거래금액;
        private String 거래유형;
        private String 건축년도;
        private String 년;
        private String 동;
        private String 등기일자;
        private String 매도자;
        private String 매수자;
        private String 법정동;
        private String 아파트;
        private String 월;
        private String 일;
        private String 전용면적;
        private String 중개사소재지;
        private String 지번;
        private String 지역코드;
        private String 층;
        private String 해제사유발생일;
        private String 해제여부;

        // Getters and setters
    }
}
