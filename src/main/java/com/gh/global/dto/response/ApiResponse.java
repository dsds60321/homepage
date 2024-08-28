package com.gh.global.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseEntity<ApiResponse> SUCCESS(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static <T> ResponseEntity<ApiResponse> BAD_REQUEST() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.BAD_REQUEST.getCode())
                .message(ApiResponseCode.BAD_REQUEST.getMessage())
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse> FORBIDEN() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.FORBIDEN.getCode())
                .message(ApiResponseCode.FORBIDEN.getMessage())
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse> EXPIRED_TOKEN() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.EXPIRED_TOKEN.getCode())
                .message(ApiResponseCode.EXPIRED_TOKEN.getMessage())
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse> NOT_FOUND(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.NOT_FOUND.getCode())
                .message(ApiResponseCode.NOT_FOUND.getMessage())
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    public static <T> ApiResponse<T> ERROR(ApiResponseCode responseCode) {
        return ApiResponse.<T>builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .data(null)
                .build();
    }
}
