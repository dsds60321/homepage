package com.gh.global.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private static final int MAX_MEMORY_SIZE = 10 * 1024 * 1024;

    public <T> T getRequestNoneAccept(String uri, Class<T> returnClass) {
        WebClient.RequestHeadersSpec<?> request = WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                .baseUrl(uri)
                .build()
                .get();

        String tmpResponse = request.exchangeToMono(response -> response.bodyToMono(String.class)).block();

        if (!StringUtils.hasText(tmpResponse)) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(tmpResponse, returnClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T getRequest(String uri, Class<T> returnClass) {
        WebClient.RequestHeadersSpec<?> request = WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                .baseUrl(uri)
                .build()
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(UTF_8);

        return request.exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }

    public <T> T getRequest(String uri, Map<String,String> headers , Class<T> returnClass) {
        WebClient.RequestHeadersSpec<?> request = WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                .baseUrl(uri)
                .build().get()
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(UTF_8);

        return request.exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }


    public <T> T getRequest(String uri, MultiValueMap<String,String> params , Map<String,String> headers , Class<T> returnClass) {
        WebClient.RequestHeadersSpec<?> request = WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                .baseUrl(uri)
                .build().get()
                .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(UTF_8);

        return request.exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }

    public <T> T sendFormPostWithParams(String uri, Map<String,String> headers, MultiValueMap<String,String> params, Class<T> returnClass) {
        return WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                .baseUrl(uri)
                .build().post()
                .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }

    public <T> T sendFormPostWithBearer(String uri, String bearer, Object body, Class<T> returnClass) {
        MultiValueMap<String, String> formData = convertToMultiValueMap(new ObjectMapper(), body);

        return WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                .baseUrl(uri)
                .build().post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(bearer))
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }


    public <T> T sendFormPostRequest(String uri, Object body, Class<T> returnClass) {
        return WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                .baseUrl(uri)
                .build().post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }

    public static MultiValueMap<String, String> convertToMultiValueMap(ObjectMapper objectMapper, Object dto) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, Object> map = objectMapper.convertValue(dto, new TypeReference<Map<String, Object>>() {});

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof List) {
                    List<?> list = (List<?>) entry.getValue();
                    for (Object obj : list) {
                        params.add(entry.getKey(), obj.toString());
                    }
                } else if (entry.getValue() instanceof Map) {
                    // 복잡한 객체를 JSON 문자열로 변환하여 처리
                    String json = objectMapper.writeValueAsString(entry.getValue());
                    params.add(entry.getKey(), json);
                } else {
                    params.add(entry.getKey(), entry.getValue().toString());
                }
            }

            return params;
        } catch (Exception e) {
            throw new IllegalStateException("오류가 발생했습니다.-------------- \n" + e.getMessage());
        }
    }

}
