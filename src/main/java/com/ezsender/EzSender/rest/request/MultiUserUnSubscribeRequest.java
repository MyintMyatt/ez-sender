package com.ezsender.EzSender.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record MultiUserUnSubscribeRequest(
    @NotBlank(message = "topic is required") String topic,
    @NotEmpty(message = "usernames is required") List<String> usernames
) {
}
