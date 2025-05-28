package com.appqueue.aws.car.service;

import com.appqueue.aws.car.repository.model.CarResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SqsService {
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final int RETRY = 2;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    private final Cache<String, Boolean> cache = Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();


    @Autowired
    public SqsService(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }
    public void pushMessage(List<CarResponseModel> carResponseModelList) {
        ObjectMapper objectMapper = new ObjectMapper();
        for(CarResponseModel carResponseModel : carResponseModelList) {
            try {
                String json = objectMapper.writeValueAsString(carResponseModel);
                String hash = Integer.toHexString(json.hashCode());
                if(isDuplicate(hash)) {
                    continue;
                }
                SendMessageRequest request = SendMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .messageBody(json)
                        .build();
                sqsClient.sendMessage(request);
                markAsSent(hash);
                System.out.println(json);
            } catch (Exception e) {
                retry(carResponseModel,RETRY);
                e.printStackTrace();
            }
        }
    }

    private void retry(CarResponseModel carResponseModel, int retry) {
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i = 0; i < retry; i++) {
            try {
                String json = objectMapper.writeValueAsString(carResponseModel);
                SendMessageRequest request = SendMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .messageBody(json)
                        .build();
                sqsClient.sendMessage(request);
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isDuplicate(String hash){
        return cache.getIfPresent(hash) != null;
    }

    private void markAsSent(String hash) {
        cache.put(hash, true);
    }

}
