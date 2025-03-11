package com.prac.rabbimq.step10;

import com.prac.rabbimq.step10.dto.StockReqDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    public final RabbitTemplate rabbitTemplate;
    public final StockRepositoryV2 stockRepository;

    @Transactional
    public void sendMessage(StockReqDto stockReqDto, boolean testCase) {

        if (stockReqDto.getUserId() == null || stockReqDto.getUserId().isEmpty()) {
            log.error("StockReqDto.userId is required");
            throw new RuntimeException("User id is required");
        }

        StockEntityV2 stockEntity = stockReqDto.toEntity();
        StockEntityV2 savedStockEntity = stockRepository.save(stockEntity);

        System.out.println("[producer entity] : " + savedStockEntity);

        try {
            // 메세지를 rabbitmq 에 전송
            CorrelationData correlationData = new CorrelationData(savedStockEntity.getId().toString());
            rabbitTemplate.convertAndSend(
                    testCase ? "stockReqDto" : RabbitMQConfig.EXCHANGE_NAME,
                    testCase ? "invalidRoutingKey" : RabbitMQConfig.ROUTING_KEY,
                    savedStockEntity,
                    correlationData
            );

            if (correlationData.getFuture().get(5, TimeUnit.SECONDS).isAck()) {
                System.out.println("[producer correlationData] 성공" + savedStockEntity);
                savedStockEntity.setProcessed(true);
                stockRepository.save(savedStockEntity);
            } else {
                throw new RuntimeException("# confirm 실패 - 롤백");
            }

        } catch (Exception e) {
            System.out.println("[producer exception fail] : " + e);
            throw new RuntimeException(e);
        }
    }
}
