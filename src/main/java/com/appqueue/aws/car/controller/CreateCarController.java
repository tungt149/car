package com.appqueue.aws.car.controller;

import com.appqueue.aws.car.controller.model.CarControllerRequest;
import com.appqueue.aws.car.service.CreateCarService;
import com.appqueue.aws.car.service.ImplementService.CreateCarServiceIm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
public class CreateCarController {

    @Autowired
    private CreateCarServiceIm service;


    @PostMapping("/createCar")
    public ResponseEntity<String> createCar(@RequestBody CarControllerRequest carRequestModel){

        try {
            service.createCar(carRequestModel);
            return ResponseEntity.ok().body("Car created successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
