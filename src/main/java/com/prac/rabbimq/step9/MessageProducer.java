package com.prac.rabbimq.step9;

import com.prac.rabbimq.step9.dto.StockReqDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final StockRepository stockRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void sendMessage(StockReqDto stockReqDto, String testCase) {
        rabbitTemplate.execute(channel -> {
            try {
                channel.txSelect(); // 트랜잭션 시작
                StockEntity stockEntity = stockReqDto.toEntity();
                StockEntity savedStockEntity = stockRepository.save(stockEntity);

                log.info("Stock Saved : {}", savedStockEntity);

                // 메세지 발행
                rabbitTemplate.convertAndSend("transactionQueue", savedStockEntity);

                if ("fail".equalsIgnoreCase(testCase)) {
                    throw new RuntimeException("트랜잭션 작업 중 에러 발생");
                }

                channel.txCommit();
                log.info("message.producer:트랜잭션 정상 처리");
            } catch (Exception e) {
                log.error("트랜잭션 실패 : {}", e.getMessage());
                channel.txRollback();
                throw new RuntimeException("트랜잭션 롤백 완료 ", e);
            } finally {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        });
    }

}
