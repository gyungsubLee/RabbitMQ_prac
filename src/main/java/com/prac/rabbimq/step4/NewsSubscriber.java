package com.prac.rabbimq.step4;

import com.prac.rabbimq.step4.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewsSubscriber {

    private final SimpMessagingTemplate messagingTemplate;

    public NewsSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.TOPIC1_QUEQUE)
    public void topic1News(String message) {
        System.out.println("Topic1 Queue Received: " + message);
        messagingTemplate.convertAndSend("/topic/topic1", message);  // topic1 뉴스 브로드캐스트
    }

    @RabbitListener(queues = RabbitMQConfig.TOPIC2_QUEQUE)
    public void topic2News(String message) {
        System.out.println("Topic2 Queue Received: " + message);
        messagingTemplate.convertAndSend("/topic/topic2", message);  // topic2 뉴스 브로드캐스트
    }

    @RabbitListener(queues = RabbitMQConfig.TOPIC3_QUEQUE)
    public void topic3News(String message) {
        System.out.println("Topic3 Queue Received: " + message);
        messagingTemplate.convertAndSend("/topic/topic3", message);  // topic3 뉴스 브로드캐스트
    }

    // 중복 코드 추출: @RabbitListener ->  rabbitTemplate.receiveAndConvert()
    //    private void sendToTopic(String QueueName, String url) {
    //        String message = (String) rabbitTemplate.receiveAndConvert(QueueName);
    //        messagingTemplate.convertAndSend(url, message);
    //    }
}