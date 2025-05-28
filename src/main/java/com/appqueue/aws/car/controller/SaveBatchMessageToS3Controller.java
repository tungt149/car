package com.appqueue.aws.car.controller;

import com.appqueue.aws.car.service.ImplementService.GetBatchMessageSqsIm;
import com.appqueue.aws.car.service.ImplementService.SaveMessageToS3Im;
import com.appqueue.aws.car.service.SaveBatchMessageToS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sqs")
public class SaveBatchMessageToS3Controller {

    @Autowired
    private SaveBatchMessageToS3Service service;


    @GetMapping("/saveBatchMessageToS3")
    public ResponseEntity<String> saveBatchMessageToS3(@RequestParam int amount) {
        try{
            service.saveMessageToS3(amount);
            return ResponseEntity.ok().body("save message successfully");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
