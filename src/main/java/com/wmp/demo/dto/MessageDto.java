package com.wmp.demo.dto;

import org.springframework.stereotype.Component;

@Component
public class MessageDto {
    private String message;

    public MessageDto() {}

    public MessageDto(String msg) {
        message = msg;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

}
