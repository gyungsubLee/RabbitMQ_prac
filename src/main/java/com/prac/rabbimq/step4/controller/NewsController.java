package com.prac.rabbimq.step4.controller;

import com.prac.rabbimq.step4.NewsPublisher;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NewsController {

    private final NewsPublisher newsPublisher;

    public NewsController(NewsPublisher newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    // 발행 엔드포인트: /app/subscribe
    @MessageMapping("/subscribe")
    public void handleSubscribe(@Header("newsType") String newsType) {
        System.out.println("[#] newsType: " + newsType);

        newsPublisher.publish(newsType);
    }
}