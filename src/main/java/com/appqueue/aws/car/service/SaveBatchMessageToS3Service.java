package com.appqueue.aws.car.service;

import com.appqueue.aws.car.service.ImplementService.DeleteMessageAfterSaveToS3;
import com.appqueue.aws.car.service.ImplementService.GetBatchMessageSqsIm;
import com.appqueue.aws.car.service.ImplementService.SaveMessageToS3Im;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveBatchMessageToS3Service implements GetBatchMessageSqsIm, SaveMessageToS3Im, DeleteMessageAfterSaveToS3 {

        @Value("${aws.sqs.queue-url}")
        private String queueUrl;

        @Value("${aws.s3.bucket}")
        private String carBuckertName;


        private final SqsClient sqsClient;
        private final S3Client s3Client;

        @Autowired
        public SaveBatchMessageToS3Service(SqsClient sqsClient, S3Client s3Client) {
            this.sqsClient = sqsClient;
            this.s3Client = s3Client;
        }

    @Override
    public List<Message> getListMessage(int amount) {
        List<Message> messageList = new ArrayList<>();
        if(amount>0){
            for(int i=0; i<amount;i++){
                ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest
                        .builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(100)
                        .waitTimeSeconds(20)
                        .build();
                List<Message> batch = sqsClient.receiveMessage(receiveMessageRequest).messages();
                if(!batch.isEmpty()){
                    messageList.addAll(batch);
                }
            }
        }
        return messageList;
    }

    @Override
    public void deleteMessage(List<Message> messageList) {
        for(Message mssg : messageList ){
            sqsClient.deleteMessage(DeleteMessageRequest.
                    builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(mssg.receiptHandle())
                    .build());
        }
    }

    @Override
    public void saveMessageToS3(int amount) {
        List<Message> messageList =  getListMessage(amount);
        if(!messageList.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            String prefix = "sqs-backup/" + System.currentTimeMillis() + "/";
            for(Message message: messageList){
                String json;
                try {
                    json = objectMapper.writeValueAsString(message);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                String key = prefix + "/messgae - "+ message.messageId() + ".json";
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(carBuckertName)
                        .key(key)
                        .contentType("application/json")
                        .build();
                s3Client.putObject(putObjectRequest, RequestBody.fromString(json));
            }
            deleteMessage(messageList);
        }
    }
}
