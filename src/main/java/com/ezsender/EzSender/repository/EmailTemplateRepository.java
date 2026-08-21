package com.ezsender.EzSender.repository;

import com.ezsender.EzSender.models.documents.EmailTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {
    Optional<EmailTemplate> findByTemplateNameAndChannelAndLocal(String templateName, String channel, String local);
}
