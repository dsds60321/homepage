package com.gh.global.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> SUCCESS(T data) {
        return ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> BAD_REQUEST() {
        return ApiResponse.<T>builder()
                .code(ApiResponseCode.BAD_REQUEST.getCode())
                .message(ApiResponseCode.BAD_REQUEST.getMessage())
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> NOT_FOUND(T data) {
        return ApiResponse.<T>builder()
                .code(ApiResponseCode.NOT_FOUND.getCode())
                .message(ApiResponseCode.NOT_FOUND.getMessage())
                .data(data)
                .build();
    }


    public static <T> ApiResponse<T> ERROR(ApiResponseCode responseCode) {
        return ApiResponse.<T>builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .data(null)
                .build();
    }
}
