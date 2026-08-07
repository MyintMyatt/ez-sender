package com.ezsender.EzSender.models.documents;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "user_notification_profiles")
@CompoundIndexes({
        @CompoundIndex(name = "username_tenant_index",def = "{'username' : 1, 'tenantId' : 1}", unique = true)
})
@Data
public class UserNotificationProfiles {

    @Id
    private String id;

    private String username;

    @NotBlank(message = "Tenant id required")
    private String tenantId;

    @Indexed(unique = true)
    private String email;

    private String phone;

    private Set<DeviceInfo> deviceList = new HashSet<>();
    private NotificationPreferences preferences = new NotificationPreferences();

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class DeviceInfo{
        private String deviceId;
        private String fcmToken;
        private String deviceOs;
        private String osVersion;
        private String appVersion;
        private LocalDateTime updatedAt;
    }

    @Data
    public static class NotificationPreferences{
        private boolean emailEnabled = true;
        private boolean pushNotificationEnabled = true;
        private boolean smsEnabled;
    }

    private boolean active = false;

}
