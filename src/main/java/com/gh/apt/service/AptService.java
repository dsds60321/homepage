package com.gh.apt.service;

import com.gh.apt.dto.AptRequest;
import com.gh.global.util.ParamUtil;
import com.gh.global.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AptService {

    // datago 통신
    @Value("${api.dataGo.apt.encKey}")
    private String apiKey;

    @Value("${api.dataGo.apt.uri.common}")
    private String uri;

    private final WebClientUtil webClientUtil;

    public String getAptInfo(AptRequest aptRequest) {
        uri = uri + ParamUtil.objectToQueryString(aptRequest, Map.of("serviceKey", apiKey));
//        AptRe.Response response = webClientUtil.get(uri, AptRe.Response.class);


        return "";
    }
}
