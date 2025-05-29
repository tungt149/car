package com.appqueue.aws.car.service;

import com.appqueue.aws.car.service.ImplementService.DeleteMessageAfterSaveToS3;
import com.appqueue.aws.car.service.ImplementService.GetBatchMessageSqsIm;
import com.appqueue.aws.car.service.ImplementService.SaveMessageToS3Im;
import com.appqueue.aws.car.service.model.SimpleMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.time.LocalDate;
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
        private final DynamoDbClient dynamoDbClient;

        @Autowired
        public SaveBatchMessageToS3Service(SqsClient sqsClient, S3Client s3Client, DynamoDbClient dynamoDbClient) {
            this.sqsClient = sqsClient;
            this.s3Client = s3Client;
            this.dynamoDbClient = dynamoDbClient;
        }

    @Override
    public List<Message> getListMessage(int amount) throws Exception {
        List<Message> messageList = new ArrayList<>();
            try{
                while (messageList.size() < amount) {
                    int remaining = amount - messageList.size();
                    int batchSize = Math.min(remaining, 10);
                    ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest
                            .builder()
                            .queueUrl(queueUrl)
                            .maxNumberOfMessages(batchSize)
                            .waitTimeSeconds(10)
                            .build();
                    List<Message> batch = sqsClient.receiveMessage(receiveMessageRequest).messages();
                    if (!batch.isEmpty()) {
                        messageList.addAll(batch);
                    }
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage(),e);
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
    public void saveMessageToS3(int amount) throws Exception{
        List<Message> messageList =  getListMessage(amount);
        if(!messageList.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
             for(Message message: messageList){
                String json;
                try {
                    SimpleMessage simpleMessage = new SimpleMessage(message.messageId(), message.body());
                    json = objectMapper.writeValueAsString(simpleMessage);
                    String key = "car-app/" + LocalDate.now() + "/message-" + message.messageId() + ".json";

                    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            .bucket(carBuckertName)
                            .key(key)
                            .contentType("application/json")
                            .build();
                    s3Client.putObject(putObjectRequest, RequestBody.fromString(json));
                    deleteMessage(messageList);
                } catch (Exception e) {
                    throw new Exception("loi roi" +e.getMessage(),e);
                }
             }
        }

    }

}
