package com.appqueue.aws.car.service;

import com.appqueue.aws.car.repository.model.CarResponseModel;
import com.appqueue.aws.car.service.ImplementService.CreateListCarQueueServiceIm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateListCarQueueService implements CreateListCarQueueServiceIm {
    @Override
    public List<CarResponseModel> createListCarQueue() {
        List<CarResponseModel> carResponseModelList = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            CarResponseModel carResponseModel = new CarResponseModel();
            carResponseModel.setId((long) i);
            carResponseModel.setName("Tung " + i);
            carResponseModel.setType("Car Type " + i);
            carResponseModelList.add(carResponseModel);
        }
        return carResponseModelList;
    }
}
