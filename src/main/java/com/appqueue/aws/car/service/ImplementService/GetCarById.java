package com.appqueue.aws.car.service.ImplementService;

import com.appqueue.aws.car.repository.model.CarModel;

public interface GetCarById {
    CarModel getCarById(Long id);
}
