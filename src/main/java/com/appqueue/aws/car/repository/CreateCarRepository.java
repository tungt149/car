package com.appqueue.aws.car.repository;

import com.appqueue.aws.car.repository.model.CarModel;
import com.appqueue.aws.car.repository.repositoryMapper.CarMapper;
import com.appqueue.aws.car.service.model.CarServiceRequestModel;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class CreateCarRepository implements CreateCarRepositoryIm {


    private final DynamoDbTable<CarModel> carTable;

    public CreateCarRepository(DynamoDbEnhancedClient enhancedClient) {
        this.carTable = enhancedClient.table("car", TableSchema.fromBean(CarModel.class));
    }

    @Override
    public void createCar(CarServiceRequestModel carRequestModel) {
        CarModel carModel = new CarModel();
        carTable.putItem(CarMapper.mapRequest(carRequestModel,carModel));
    }

}
