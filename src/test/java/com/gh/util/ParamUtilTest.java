package com.gh.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

class ParamUtilTest {


    class Dummy {

        public Dummy(int number, String name, String value) {
            this.number = number;
            this.name = name;
            this.value = value;
        }

        int number;
        String name;
        String value;
    }

    @Test
    void buildQueryString() {
        Dummy dummy = new Dummy(5, "kang", "test");
        HashMap<String, String> add = new HashMap<>();
        add.put("dd","bnnn");
        String queryString = ParamUtil.objectToQueryString(dummy, add);
        System.out.println(queryString);
    }
}