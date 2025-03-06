package com.prac.rabbimq.step3;

import com.prac.rabbimq.step3.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationSubscriber {

    public static final String CLIENT_URL = "/sub/notifications";

    // WebSocket으로 메세지를 전달하기 위한 Spring 템플릿 클래스
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    // RabbitMQ Queue 에서 메세지 수신
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void subscriber(String message) {

        // @RabbitListener를 사용하지 않으면 아래와 같이 직접 큐에서 메세지를 받아와 convert 해서 사용해야 한다.
            // String message = (String) rabbitTemplate.receiveAndConvert(RabbitMQConfig.QUEUE_NAME);

        System.out.println("Received Notification: " + message);

        // 클라이언트의 구독 엔드포인트로 메세지 전송
        simpMessagingTemplate.convertAndSend(CLIENT_URL, message); // 클라이언트 브로드캐스트
    }
}