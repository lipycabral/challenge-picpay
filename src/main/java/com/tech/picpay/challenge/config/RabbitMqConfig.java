package com.tech.picpay.challenge.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String MESSAGING_NOTIFICATION_QUEUE = "picpay-messaging-notification";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue messagingNotificationQueue() {
        return new Queue(MESSAGING_NOTIFICATION_QUEUE);
    }
}
