package com.ezsender.EzSender.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email_templates")
@CompoundIndexes({
        @CompoundIndex(name = "template_name_index", def = "{'templateName' : 'otp', 'channel' : 'sms', 'local': 'en'}", unique = true )
})
public class EmailTemplate {
    @Id
    private String id;
    private String templateName;
    private String channel; // sms, mail, push notification
    private String local;
    private String titleTemplate;
    private String bodyTemplate;
}
