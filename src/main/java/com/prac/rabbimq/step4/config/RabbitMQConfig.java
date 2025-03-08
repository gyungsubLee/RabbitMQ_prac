package com.prac.rabbimq.step4.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String FANOUT_EXCHANGE_FOR_NEWS = "newsExchange";
    public static final String TOPIC1_QUEQUE = "topic1";
    public static final String TOPIC2_QUEQUE = "topic2";
    public static final String TOPIC3_QUEQUE = "topic3";

    @Bean
    public Queue topic1Queue() {
        return new Queue(TOPIC1_QUEQUE, false);
    }

    @Bean
    public Queue topic2Queue() {
        return new Queue(TOPIC2_QUEQUE, false);
    }

    @Bean
    public Queue topic3Queue() {
        return new Queue(TOPIC3_QUEQUE, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        // 메세지를 수신하면 연결된 모든 큐로 브로드캐스트
        return new FanoutExchange(FANOUT_EXCHANGE_FOR_NEWS);
    }

    @Bean
    public Binding topic1Binding(Queue topic1Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(topic1Queue).to(fanoutExchange);
    }

    @Bean
    public Binding topic2Binding(Queue topic2Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(topic2Queue).to(fanoutExchange);
    }

    @Bean
    public Binding topic3Binding(Queue topic3Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(topic3Queue).to(fanoutExchange);
    }
}