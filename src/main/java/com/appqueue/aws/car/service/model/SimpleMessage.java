package com.appqueue.aws.car.service.model;

public class SimpleMessage {
    private String messageId;
    private String body;

    public SimpleMessage(String messageId, String body) {
        this.messageId = messageId;
        this.body = body;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
