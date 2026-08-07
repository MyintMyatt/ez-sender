package com.ezsender.EzSender.repository;

import com.ezsender.EzSender.models.documents.UserNotificationProfiles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNotificationRepo extends MongoRepository<UserNotificationProfiles, String> {

    Optional<UserNotificationProfiles> findByUsername(String username);

    Optional<UserNotificationProfiles> findByEmail(String email);
}
