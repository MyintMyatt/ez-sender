package com.ezsender.EzSender.services;

import com.ezsender.EzSender.models.request.OtpSendRequest;

public interface OtpService {

    void sendOtp(OtpSendRequest request);

}
