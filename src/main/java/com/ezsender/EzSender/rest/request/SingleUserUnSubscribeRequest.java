package com.ezsender.EzSender.rest.request;

import jakarta.validation.constraints.NotBlank;

public record SingleUserUnSubscribeRequest(
    @NotBlank(message = "topic is required") String topic,
    @NotBlank(message = "username is required") String username
) {
}
