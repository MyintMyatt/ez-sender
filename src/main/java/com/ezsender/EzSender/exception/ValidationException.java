package com.ezsender.EzSender.exception;

import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException{
    private final List<String> messages;

    public ValidationException(BindingResult result){
       this.messages = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    }
}
