package com.ezsender.EzSender.configuration;

import com.ezsender.EzSender.metadata.RabbitMqConfigMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

///
/// otp sending queue via sms, email or push notification
/// welcome mail queue via sms, email or push notification
///
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
        return new Queue(mqMetadata.getSmsQueue(), true);
    }

    @Bean
    public Queue pushNotiQueue(){
        return new Queue(mqMetadata.getPushQueue(), true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(mqMetadata.getEmailQueue(), true);
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
}
