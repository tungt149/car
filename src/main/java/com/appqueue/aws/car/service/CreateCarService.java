package com.appqueue.aws.car.service;

import com.appqueue.aws.car.controller.model.CarControllerRequest;
import com.appqueue.aws.car.repository.CreateCarRepositoryIm;
import com.appqueue.aws.car.service.ImplementService.CreateCarServiceIm;
import com.appqueue.aws.car.service.mapper.CarServiceMapper;
import com.appqueue.aws.car.service.model.CarServiceRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CreateCarService implements CreateCarServiceIm {

    @Autowired
    private CreateCarRepositoryIm createCarRepository;

    @Override
    public void createCar(CarControllerRequest carControllerRequest) {
        CarServiceRequestModel carServiceRequestModel = new CarServiceRequestModel();
        carControllerRequest.setId(UUID.randomUUID().toString());
        carControllerRequest.setDml_date(Instant.now().toString());
          createCarRepository.createCar(CarServiceMapper.mappingRequest(carControllerRequest,carServiceRequestModel ));
    }
}
