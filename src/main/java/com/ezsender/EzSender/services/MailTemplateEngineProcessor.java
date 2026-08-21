package com.ezsender.EzSender.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailTemplateEngineProcessor {

    public String render(String templateString, Map<String, Object> variables){
        if(templateString == null) return "";
        String renderText = templateString;
        for (Map.Entry<String, Object> entry : variables.entrySet()){
            renderText = renderText.replace("[" + entry.getKey() + "]" , String.valueOf(entry.getValue()));
        }
        return renderText;
    }
}
