package com.appqueue.aws.car.service;

import com.appqueue.aws.car.repository.model.CarModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import javax.print.attribute.Attribute;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class updateDmlDateService {
    private final DynamoDbTable<CarModel> carTable;
    private final String SEVEN_DAYS_AGO = Instant.now().minus(7,ChronoUnit.DAYS).toString();
    private final String FIVE_MINUTES_AGO = Instant.now().minus(5, ChronoUnit.MINUTES).toString();


    public updateDmlDateService(DynamoDbEnhancedClient enhancedClient) {
        this.carTable = enhancedClient.table("car", TableSchema.fromBean(CarModel.class));

    }

//    @Scheduled(cron = "0 */5 * * * *") // phút chia hết cho 5
//    public void updateDmlDate(){
//        System.out.println("Update dml date");
//        Map<String, AttributeValue> expression = new HashMap<>();
//        expression.put(":oldDay", AttributeValue.builder().s(FIVE_MINUTES_AGO).build());
//        Expression fillterExpression = Expression
//                .builder()
//                .expression("dml_date < :oldDay")
//                .expressionValues(expression)
//                .build();
//
//        PageIterable<CarModel> pages = carTable.scan(c ->c.filterExpression(fillterExpression));
//        pages.stream().forEach(page -> {
//            List<CarModel> items = page.items();
//                for(CarModel carModelNeedUpdate : items) {
//                    carModelNeedUpdate.setDml_date(Instant.now().toString());
//                    carModelNeedUpdate.setName("tung sau khi update ne");
//                    carTable.putItem(carModelNeedUpdate);
//                }
//        });
//        System.out.println("End Update dml date");
//    }

}
