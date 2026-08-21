package com.ezsender.EzSender.listeners;

import com.ezsender.EzSender.repository.EmailTemplateRepository;
import com.ezsender.EzSender.services.MailTemplateEngineProcessor;
import com.ezsender.commons.models.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqNotificationListeners {

    private final MailTemplateEngineProcessor engine;
    private final EmailTemplateRepository templateRepository;

    @RabbitListener(queues = "#{rabbitMqConfigMetadata.emailQueue}" )
    public void handleEmail(NotificationRequest request){
        var template = templateRepository.findByTemplateNameAndChannelAndLocal(request.getTemplateName(), request.getChannel().name().toLowerCase(), request.getLocale())
                .orElseGet(() -> templateRepository.findByTemplateNameAndChannelAndLocal(request.getTemplateName(), request.getChannel().name().toLowerCase(), "en").orElseThrow());

        var title = engine.render(template.getTitleTemplate(), request.getTemplateObject());
        var body = engine.render(template.getBodyTemplate(), request.getTemplateObject());
        log.info("==========email template========== {} ===== {}", title, body );
        // TODO:
    }

    @RabbitListener(queues = "#{rabbitMqConfigMetadata.emailQueue}" )
    public void handleSms(NotificationRequest request){
        // send sms
        // TODO:
    }

    @RabbitListener(queues = "#{rabbitMqConfigMetadata.emailQueue}" )
    public void handlePushNotification(NotificationRequest request){
        // send push noti
        // TODO:
    }

    private void handlePriority(String routingKey){

    }
}
