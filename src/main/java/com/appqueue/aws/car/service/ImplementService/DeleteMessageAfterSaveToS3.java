package com.appqueue.aws.car.service.ImplementService;

import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

public interface DeleteMessageAfterSaveToS3 {
    void deleteMessage(List<Message> messageList);

}
