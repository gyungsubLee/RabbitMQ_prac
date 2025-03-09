package com.prac.rabbimq.step8_2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private int retryCount;

    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED_QUEUE)
    public void processMessage(String message) {
        System.out.println("Received message: "+ message + "count : " + retryCount++);
        if ("fail".equals(message)) {
            throw new RuntimeException("- Processing failed. Retry" );
        }
        System.out.println("Message processed successfully: " + message);
        retryCount = 0;
    }
}