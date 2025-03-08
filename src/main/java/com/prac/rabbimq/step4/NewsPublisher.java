package com.prac.rabbimq.step4;

import com.prac.rabbimq.step4.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewsPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NewsPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private String publishMessage(String news, String messageSuffix) {
        String message = news + messageSuffix;
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_FOR_NEWS, "", message);
        System.out.println("News Published: " + message);
        return message;
    }

    public void publish(String news) {
        publishMessage(news, " 관련 새 소식이 있어요!");
    }

    public String publishAPI(String news) {
        return publishMessage(news, " - 관련 새 소식이 나왔습니다. (API)");
    }
}