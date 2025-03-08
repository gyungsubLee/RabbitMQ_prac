package com.prac.rabbimq.step6;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ERROR_QUEUE = "error_queue";
    public static final String WARN_QUEUE = "warn_queue";
    public static final String INFO_QUEUE = "info_queue";
    public static final String ALL_LOG__QUEUE = "all_log_queue";

    public static final String TOPIC_EXCHANGE = "topic_exchange";

    public static final String ERROR_ROUTING_KEY = "log.error";
    public static final String WARN_ROUTING_KEY = "log.warn";
    public static final String INFO_ROUTING_KEY = "log.info";
    public static final String ALL_LOG_ROUTING_KEY = "log.*";


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue(ERROR_QUEUE, false);
    }

    @Bean
    public Queue warnQueue() {
        return new Queue(WARN_QUEUE, false);
    }

    @Bean
    public Queue infoQueue() {
        return new Queue(INFO_QUEUE, false);
    }

    @Bean
    public Queue allLogQueue() {
        return new Queue(ALL_LOG__QUEUE, false);
    }

    @Bean
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue()).to(topicExchange()).with(ERROR_ROUTING_KEY);
    }

    @Bean
    public Binding warnBinding() {
        return BindingBuilder.bind(warnQueue()).to(topicExchange()).with(WARN_ROUTING_KEY);
    }

    @Bean
    public Binding infoBinding() {
        return BindingBuilder.bind(infoQueue()).to(topicExchange()).with(INFO_ROUTING_KEY);
    }

    @Bean
    public Binding allLogBinding() {
        return BindingBuilder.bind(allLogQueue()).to(topicExchange()).with(ALL_LOG_ROUTING_KEY);
    }
}
