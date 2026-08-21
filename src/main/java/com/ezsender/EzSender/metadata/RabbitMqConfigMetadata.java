package com.ezsender.EzSender.metadata;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ez-sender.rabbitmq")
public class RabbitMqConfigMetadata {
    private String exchange;
    private String emailQueue;
    private String pushQueue;
    private String smsQueue;
    private String emailRoutingKey;
    private String smsRoutingKey;
    private String pushRoutingKey;
}
