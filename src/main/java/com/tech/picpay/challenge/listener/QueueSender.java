package com.tech.picpay.challenge.listener;

import com.tech.picpay.challenge.client.dto.SendNotificationDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(SendNotificationDTO notificationDTO) {
        rabbitTemplate.convertAndSend(this.queue.getName(), notificationDTO);
    }
}