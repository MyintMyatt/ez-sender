package com.ezsender.EzSender.grpc;

import com.ezsender.EzSender.model.UserNotificationProfiles;
import com.ezsender.EzSender.repository.UserNotificationRepo;
import dev.orion.grpc.notification.NotificationProfileRegisterRequest;
import dev.orion.grpc.notification.NotificationProfileRegisterResponse;
import dev.orion.grpc.notification.NotificationServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class NotificationProfileService extends NotificationServiceGrpc.NotificationServiceImplBase {

    private final UserNotificationRepo notificationRepo;
    @Override
    public void notificationProfileRegister(NotificationProfileRegisterRequest request, StreamObserver<NotificationProfileRegisterResponse> responseObserver) {
        log.info("Notification Profile Register: ==========Receive Request===========");
        var devices = request.getDeviceInfoList().stream().map(d -> {
            return new UserNotificationProfiles.DeviceInfo(
                    d.getDeviceId(),
                    d.getFcmToken(),
                    d.getDeviceOs(),
                    d.getOsVersion(),
                    d.getAppVersion(),
                    LocalDateTime.now()
            );
        }).collect(Collectors.toSet());
        var document = new UserNotificationProfiles();
        document.setUsername(request.getUsername());
        document.setEmail(request.getEmail());
        document.setPhone(request.getPhone());
        document.setTenantId(request.getTenantId());
        document.setDeviceList(devices);
        notificationRepo.save(document);

        responseObserver.onNext(NotificationProfileRegisterResponse.newBuilder().setSuccess(true).setMessage("notification profile registration successful!").build());
        responseObserver.onCompleted();
    }
}
