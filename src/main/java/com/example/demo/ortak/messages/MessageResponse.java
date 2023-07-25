package com.example.demo.ortak.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties("arguments")
public class MessageResponse {
    private String message;
    private MessageType type;
    private List<String> arguments;

    public MessageResponse(String message, MessageType type) {
        this.message = message;
        this.type = type;
        this.arguments = new ArrayList<String>() {};
    }

    public static MessageResponse defaultSuccessResult(){
        return new MessageResponse("Success", MessageType.INFO);
    }

    public boolean hasError(){
        return type == MessageType.ERROR;
    }


}
