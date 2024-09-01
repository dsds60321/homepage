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
    private T result;

    public static <T> ResponseEntity<ApiResponse> SUCCESS(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .result(data)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static <T> ResponseEntity<ApiResponse> BAD_REQUEST() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.BAD_REQUEST.getCode())
                .message(ApiResponseCode.BAD_REQUEST.getMessage())
                .result(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse> BAD_REQUEST(String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.BAD_REQUEST.getCode())
                .message(message)
                .result(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }


    public static <T> ResponseEntity<ApiResponse> FORBIDEN() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.FORBIDEN.getCode())
                .message(ApiResponseCode.FORBIDEN.getMessage())
                .result(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse> EXPIRED_TOKEN() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.EXPIRED_TOKEN.getCode())
                .message(ApiResponseCode.EXPIRED_TOKEN.getMessage())
                .result(null)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    public static <T> ResponseEntity<ApiResponse> NOT_FOUND(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.NOT_FOUND.getCode())
                .message(ApiResponseCode.NOT_FOUND.getMessage())
                .result(data)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public static <T> ResponseEntity<ApiResponse> SERVER_ERROR(int code, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .result(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static <T> ResponseEntity<ApiResponse> SERVER_ERROR() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ApiResponseCode.INTERNAL_SERVER_ERROR.getMessage())
                .result(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
