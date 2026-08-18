package com.ezsender.EzSender.rest;

import com.ezsender.EzSender.constant.ErrorType;
import com.ezsender.EzSender.models.response.ApiResponse;
import com.ezsender.EzSender.models.response.ErrorResponse;
import com.ezsender.EzSender.rest.request.MultiUserSubscribeRequest;
import com.ezsender.EzSender.rest.request.MultiUserUnSubscribeRequest;
import com.ezsender.EzSender.rest.request.SingleUserSubscribeRequest;
import com.ezsender.EzSender.rest.request.SingleUserUnSubscribeRequest;
import com.ezsender.EzSender.rest.response.CommonResponse;
import com.ezsender.EzSender.services.NotificationSubscribeService;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationSubscribeService subscribeService;

    @PostMapping("single-subscribe")
    public ApiResponse<?> singleSubscribe(@Valid @RequestBody SingleUserSubscribeRequest request, BindingResult result) {
        try {
            subscribeService.subscribeSingleUser(request.topic(), request.username());
            return ApiResponse.success(new CommonResponse(true, "Successfully subscribed!!!"));
        }catch (FirebaseMessagingException e){
            return ApiResponse.error(new ErrorResponse(ErrorType.InternalServer, e.getMessage()));
        }
    }

    @PostMapping("single-unsubscribe")
    public ApiResponse<?> singleUnsubscribe(@Valid @RequestBody SingleUserUnSubscribeRequest request, BindingResult result) {
        try {
            subscribeService.unSubscribeSingleUser(request.topic(), request.username());
            return ApiResponse.success(new CommonResponse(true, "Successfully unsubscribed!!!"));
        }catch (FirebaseMessagingException e){
            return ApiResponse.error(new ErrorResponse(ErrorType.InternalServer, e.getMessage()));
        }
    }

    @PostMapping("multi-subscribe")
    public ApiResponse<?> singleUnsubscribe(@Valid @RequestBody MultiUserSubscribeRequest request, BindingResult result) {
        try {
            subscribeService.subscribeMultiUser(request.topic(), request.usernames());
            return ApiResponse.success(new CommonResponse(true, "Successfully subscribed!!!"));
        }catch (FirebaseMessagingException e){
            return ApiResponse.error(new ErrorResponse(ErrorType.InternalServer, e.getMessage()));
        }
    }

    @PostMapping("multi-unsubscribe")
    public ApiResponse<?> singleUnsubscribe(@Valid @RequestBody MultiUserUnSubscribeRequest request, BindingResult result) {
        try {
            subscribeService.unSubscribeMultiUser(request.topic(), request.usernames());
            return ApiResponse.success(new CommonResponse(true, "Successfully unsubscribed!!!"));
        }catch (FirebaseMessagingException e){
            return ApiResponse.error(new ErrorResponse(ErrorType.InternalServer, e.getMessage()));
        }
    }
}
