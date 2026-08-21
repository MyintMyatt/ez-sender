package com.ezsender.EzSender.configuration;

import com.ezsender.EzSender.metadata.RabbitMqConfigMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMqNotificationConfiguration {
    private final RabbitMqConfigMetadata mqMetadata;

    @Bean
    public TopicExchange notificationExchange(){
        return new TopicExchange(mqMetadata.getExchange(), true, false);
    }

    @Bean
    public Queue smsQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", mqMetadata.getDlxExchange());
        args.put("x-dead-letter-routing-key", "dead.sms");
        return new Queue(mqMetadata.getSmsQueue(), true, false, false, args);
    }

    @Bean
    public Queue pushNotiQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", mqMetadata.getDlxExchange());
        args.put("x-dead-letter-routing-key", "dead.push");
        return new Queue(mqMetadata.getPushQueue(), true, false, false, args);
    }

    @Bean
    public Queue emailQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", mqMetadata.getDlxExchange());
        args.put("x-dead-letter-routing-key", "dead.email");
        return new Queue(mqMetadata.getEmailQueue(), true, false, false, args);
    }

    @Bean
    public Binding smsBinding(){
        return BindingBuilder.bind(smsQueue()).to(notificationExchange()).with(mqMetadata.getSmsRoutingKey());
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(notificationExchange()).with(mqMetadata.getEmailRoutingKey());
    }

    @Bean
    public Binding pushNotiBinding(){
        return BindingBuilder.bind(pushNotiQueue()).to(notificationExchange()).with(mqMetadata.getPushRoutingKey());
    }

    @Bean
    public MessageConverter rabbitMessageConverter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Queue smsDlq() { return new Queue(mqMetadata.getSmsDlq(), true); }

    @Bean
    public Queue pushDlq() { return new Queue(mqMetadata.getPushDlq(), true); }

    @Bean
    public Queue emailDlq() { return new Queue(mqMetadata.getEmailDlq(), true); }

    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(mqMetadata.getDlxExchange());
    }
}
