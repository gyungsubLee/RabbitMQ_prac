package com.prac.rabbimq.step10;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final StockRepositoryV2 stockRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(StockEntityV2 stock,
                               @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                               Channel channel) {
        try {
            System.out.println("[Consumer] " + stock);
            Thread.sleep(200);
            Optional<StockEntityV2> optionalStock = stockRepository.findById(stock.getId());
            if (optionalStock.isPresent()) {
                StockEntityV2 findStockEntity = optionalStock.get();
                stockRepository.save(findStockEntity);  // 업데이트
                System.out.println("[Save Entity Consumer] " + findStockEntity);
            } else {
                throw new RuntimeException("Stock not found");
            }
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            System.out.println("[Consumer Error] " + e.getMessage());
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                System.out.println("[Consumer send nack] " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    }
}
