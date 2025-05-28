package com.appqueue.aws.car.controller;

import com.appqueue.aws.car.repository.model.CarResponseModel;
import com.appqueue.aws.car.service.ImplementService.CreateListCarQueueServiceIm;
import com.appqueue.aws.car.service.SqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CreateListCarQueueController {

    @Autowired
    private CreateListCarQueueServiceIm service;

    @Autowired
    private SqsService sqsService;

    @GetMapping("/createListCard")
    public ResponseEntity<String> createListCard() {
        List<CarResponseModel> carResponseModelList = service.createListCard();
        sqsService.pushMessage(carResponseModelList);
        return ResponseEntity.ok("Success");
    }

}
