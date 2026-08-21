package com.ezsender.EzSender.listeners;

import com.ezsender.EzSender.metadata.RabbitMqConfigMetadata;
import com.ezsender.commons.models.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqNotificationListeners {

    @RabbitListener(queues = "#{rabbitMqConfigMetadata.emailQueue}" )
    public void handleEmail(NotificationRequest request, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey){
        // send email
        // TODO:
    }

    @RabbitListener(queues = "#{rabbitMqConfigMetadata.emailQueue}" )
    public void handleSms(NotificationRequest request, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey){
        // send sms
        // TODO:
    }

    @RabbitListener(queues = "#{rabbitMqConfigMetadata.emailQueue}" )
    public void handlePushNotification(NotificationRequest request, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey){
        // send push noti
        // TODO:
    }

    private void handlePriority(String routingKey){

    }
}
