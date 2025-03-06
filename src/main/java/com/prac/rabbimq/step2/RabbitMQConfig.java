package com.prac.rabbimq.step2;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 큐 네임을 설정한다.
    public static final String QUEUE_NAME = "workQueue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        container.setConcurrentConsumers(5);  // 최대 5개의 스레드로 동시에 메시지를 처리
        container.setAcknowledgeMode(AcknowledgeMode.AUTO); // 경쟁 소비자 패턴: 라운드 로빈 방식,  MANUAL: 공정 분배 ( Fair Dispatch) 방식
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(Consumer receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}