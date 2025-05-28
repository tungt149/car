package com.appqueue.aws.car.service.ImplementService;

import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

public interface SaveMessageToS3Im {
    void saveMessageToS3(int amount);

}
