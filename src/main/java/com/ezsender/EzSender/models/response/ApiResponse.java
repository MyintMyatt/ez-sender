package com.ezsender.EzSender.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T payload;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issueAt;

    public static <T> ApiResponse<T> success(T payload){
        return new ApiResponse<>(true, payload, LocalDateTime.now());
    }

    public static ApiResponse<ErrorResponse> error(ErrorResponse payload){
        return new ApiResponse<>(false, payload, LocalDateTime.now());
    }
}
