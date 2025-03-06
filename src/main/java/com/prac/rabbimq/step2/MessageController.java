package com.prac.rabbimq.step2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("messageControllerStep2")
@RequestMapping("/api")
public class MessageController {

    private final Producer producer;

    public MessageController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/workqueue")
    public String workQueue(@RequestParam String message, @RequestParam int duration) {
        producer.sendWorkQueue(message, duration);
        return "Work queue sent = " + message + ", (" + duration + ")";
        /**
         * curl -X POST "http://localhost:8080/api/workqueue?message=Task1&duration=2000"
         * curl -X POST "http://localhost:8080/api/workqueue?message=Task2&duration=4000"
         * curl -X POST "http://localhost:8080/api/workqueue?message=Task3&duration=5000"
         */
    }
}