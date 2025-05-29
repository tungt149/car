package com.appqueue.aws.car.service.ImplementService;

import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

public interface GetBatchMessageSqsIm {
    List<Message> getListMessage(int amount) throws Exception;
}
