package com.ezsender.EzSender.services;

import com.ezsender.EzSender.models.documents.UserNotificationProfiles;
import com.ezsender.EzSender.repository.UserNotificationRepo;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSubscribeService {

    private final UserNotificationRepo notificationRepo;

    public void subscribeSingleUser(String topic, String username) throws FirebaseMessagingException {
        var profile = notificationRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Notification profile not found with username"));

        var tokens = profile.getDeviceList().stream()
                .map(UserNotificationProfiles.DeviceInfo::getFcmToken)
                .toList();

        FirebaseMessaging.getInstance()
                .subscribeToTopic(
                        tokens,
                        topic
        );
    }

    public void  unSubscribeSingleUser(String topic, String username) throws FirebaseMessagingException {
        var profile = notificationRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Notification profile not found with username"));

        var tokens = profile.getDeviceList().stream()
                .map(UserNotificationProfiles.DeviceInfo::getFcmToken)
                .toList();

        FirebaseMessaging.getInstance()
                .unsubscribeFromTopic(
                        tokens,
                        topic
                );
    }

    public void subscribeMultiUser(String topic, List<String> userList) throws FirebaseMessagingException {
        for (String username : userList) {
            try {
                var profile = notificationRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Notification profile not found for user: " + username));

                var tokens = profile.getDeviceList().stream()
                        .map(UserNotificationProfiles.DeviceInfo::getFcmToken)
                        .toList();

                FirebaseMessaging.getInstance().subscribeToTopic(tokens, topic);

            } catch (RuntimeException | FirebaseMessagingException e) {
                log.error("Failed to subscribe user {} : {}", username, e.getMessage());
            }
        }
    }

    public void unSubscribeMultiUser(String topic, List<String> userList) throws FirebaseMessagingException {
        for (String username : userList) {
            try {
                var profile = notificationRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Notification profile not found for user: " + username));

                var tokens = profile.getDeviceList().stream()
                        .map(UserNotificationProfiles.DeviceInfo::getFcmToken)
                        .toList();

                FirebaseMessaging.getInstance().unsubscribeFromTopic(tokens, topic);

            } catch (RuntimeException | FirebaseMessagingException e) {
                log.error("Failed to subscribe user {} : {}", username, e.getMessage());
            }
        }
    }
}
