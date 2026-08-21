package com.ezsender.EzSender.grpc;

import com.ez_sender.grpc.notification.MultiUserSubscribeRequest;
import com.ez_sender.grpc.notification.MultiUserUnSubscribeRequest;
import com.ez_sender.grpc.notification.SingleUserSubscribeRequest;
import com.ez_sender.grpc.notification.SingleUserUnSubscribeRequest;
import com.ezsender.EzSender.models.documents.UserNotificationProfiles;
import com.ezsender.EzSender.repository.UserNotificationRepo;
import com.ezsender.EzSender.services.NotificationSubscribeService;
import com.google.firebase.messaging.FirebaseMessagingException;
import dev.orion.grpc.notification.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class NotificationGrpcService extends NotificationServiceGrpc.NotificationServiceImplBase {

    private final UserNotificationRepo notificationRepo;
    private final NotificationSubscribeService subscribeService;

    @Override
    public void notificationProfileRegister(NotificationProfileRegisterRequest request, StreamObserver<NotificationProfileRegisterResponse> responseObserver) {
        log.info("Notification Profile Register: ==========Receive Request===========");
        var devices = request.getDeviceInfoList().stream().map(d -> new UserNotificationProfiles.DeviceInfo(
                d.getDeviceId(),
                d.getFcmToken(),
                d.getDeviceOs(),
                d.getOsVersion(),
                d.getAppVersion(),
                LocalDateTime.now()
        )).collect(Collectors.toSet());
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

    @Override
    public void subscribeSingleUser(SingleUserSubscribeRequest request, StreamObserver<NotificationCommonResponse> responseObserver) {
        try {
            subscribeService.subscribeSingleUser(request.getTopic(), request.getUsername());
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(true).setMessage("successfully subscribed!!!!").build());
            responseObserver.onCompleted();
            log.info("{} subscribe topic {}.", request.getUsername(), request.getTopic());
        } catch (FirebaseMessagingException e){
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(false).setMessage(e.getMessage()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void unSubscribeSingleUser(SingleUserUnSubscribeRequest request, StreamObserver<NotificationCommonResponse> responseObserver) {
        try {
            subscribeService.unSubscribeSingleUser(request.getTopic(), request.getUsername());
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(true).setMessage("successfully unsubscribed!!!!").build());
            responseObserver.onCompleted();
            log.info("{} unsubscribe topic {}.", request.getUsername(), request.getTopic());
        } catch (FirebaseMessagingException e){
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(false).setMessage(e.getMessage()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void subscribeMultiUser(MultiUserSubscribeRequest request, StreamObserver<NotificationCommonResponse> responseObserver) {
        try {
            subscribeService.subscribeMultiUser(request.getTopic(), request.getUsernameList());
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(true).setMessage("successfully subscribed!!!!").build());
            responseObserver.onCompleted();
            log.info("{} multi user subscribe topic {}", request.getUsernameList(), request.getTopic());
        } catch (FirebaseMessagingException e){
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(false).setMessage(e.getMessage()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void unSubscribeMultiUser(MultiUserUnSubscribeRequest request, StreamObserver<NotificationCommonResponse> responseObserver) {
        try {
            subscribeService.unSubscribeMultiUser(request.getTopic(), request.getUsernameList());
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(true).setMessage("successfully unsubscribed!!!!").build());
            responseObserver.onCompleted();
            log.info("{} multi user unsubscribe topic {}",request.getUsernameList() , request.getTopic());
        } catch (FirebaseMessagingException e){
            responseObserver.onNext(NotificationCommonResponse.newBuilder().setSuccess(false).setMessage(e.getMessage()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void sendOtpMail(OtpMailRequest request, StreamObserver<NotificationCommonResponse> responseObserver) {
        super.sendOtpMail(request, responseObserver);
    }


}
