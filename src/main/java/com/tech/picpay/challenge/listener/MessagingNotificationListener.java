package com.tech.picpay.challenge.listener;

import com.tech.picpay.challenge.client.DeviClient;
import com.tech.picpay.challenge.client.dto.SendNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.tech.picpay.challenge.config.RabbitMqConfig.MESSAGING_NOTIFICATION_QUEUE;

@Component
public class MessagingNotificationListener {
    private final Logger logger = LoggerFactory.getLogger(MessagingNotificationListener.class);
    private final DeviClient deviClient;

    public MessagingNotificationListener(DeviClient deviClient) {
        this.deviClient = deviClient;
    }

    @RabbitListener(queues = MESSAGING_NOTIFICATION_QUEUE)
    public void listen(Message<SendNotificationDTO> message) {
        try {
            var resp = deviClient.sendNotification(message.getPayload());
            logger.info(resp.toString());
        } catch (Exception e) {
            logger.error("Erro ao enviar notificação");
        }
    }
}
