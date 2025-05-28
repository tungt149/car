package com.appqueue.aws.car.service.mapper;

import com.appqueue.aws.car.controller.model.CarControllerRequest;
import com.appqueue.aws.car.service.model.CarServiceRequestModel;

public class CarServiceMapper {

    public static CarServiceRequestModel mappingRequest(CarControllerRequest carControllerRequest, CarServiceRequestModel carServiceRequestModel) {
        CarServiceRequestModel carServiceRequest = new CarServiceRequestModel();
        carServiceRequest.setId(carControllerRequest.getId());
        carServiceRequest.setName(carControllerRequest.getName());
        carServiceRequest.setType(carControllerRequest.getType());
        carServiceRequest.setDml_date(carControllerRequest.getDml_date());
        return carServiceRequest;
    }
}
