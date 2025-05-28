package com.appqueue.aws.car.repository.repositoryMapper;

import com.appqueue.aws.car.repository.model.CarModel;
import com.appqueue.aws.car.service.model.CarServiceRequestModel;

public class CarMapper {

    public static CarModel mapRequest(CarServiceRequestModel carRequestModel, CarModel carModel) {
        carModel.setId(carRequestModel.getId());
        carModel.setName(carRequestModel.getName());
        carModel.setType(carRequestModel.getType());
        carModel.setDml_date(carRequestModel.getDml_date());
        return carModel;
    }
}
