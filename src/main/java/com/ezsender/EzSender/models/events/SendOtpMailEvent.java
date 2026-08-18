package com.ezsender.EzSender.models.events;

public record SendOtpMailEvent(
        String receiver,
        String subject,
        String otp
) {
}
