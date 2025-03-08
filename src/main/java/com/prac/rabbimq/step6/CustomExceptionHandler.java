package com.prac.rabbimq.step6;

import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHandler {

    private final LogPublisher logPublisher;

    public CustomExceptionHandler(LogPublisher logPublisher) {
        this.logPublisher = logPublisher;
    }

    // 에러 로그 처리
    public void handleException(Exception e) {
        String message = e.getMessage();

        String routingKey;

        if (e instanceof NullPointerException) {
            routingKey = RabbitMQConfig.ERROR_ROUTING_KEY;
        } else if (e instanceof IllegalArgumentException) {
            routingKey = RabbitMQConfig.WARN_ROUTING_KEY;
        } else {
            routingKey = RabbitMQConfig.ERROR_ROUTING_KEY;
        }

        logPublisher.publish(routingKey, "Exception 발생: " + message);
    }

    // 메세지 처린
    public void handleMessage(String message) {
        String routingKey = RabbitMQConfig.INFO_ROUTING_KEY;
        logPublisher.publish(routingKey, "Info log: " + message);
    }
}
