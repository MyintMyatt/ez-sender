package com.ezsender.EzSender.rest.response;

public record CommonResponse(
        boolean success,
        String message
) {
}
