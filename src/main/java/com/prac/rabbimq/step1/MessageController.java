package com.prac.rabbimq.step1;

import com.prac.rabbimq.step1.Sender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final Sender sender;

    public MessageController(Sender sender) {
        this.sender = sender;
    }

    @GetMapping("/msg")
    public String message(@RequestParam String message) {
        sender.send(message);
        return "Controller 메세지 호출: " + message;
    }
}
