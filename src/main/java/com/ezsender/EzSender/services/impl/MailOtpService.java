package com.ezsender.EzSender.services.impl;

import com.ezsender.EzSender.models.events.SendOtpMailEvent;
import com.ezsender.EzSender.models.request.OtpSendRequest;
import com.ezsender.EzSender.services.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class MailOtpService implements OtpService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendOtp(OtpSendRequest request) {
       eventPublisher.publishEvent(new SendOtpMailEvent(request.receiver(), "otp", request.otp()));
    }
}
