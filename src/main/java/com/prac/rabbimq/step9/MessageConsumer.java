package com.prac.rabbimq.step9;

import com.prac.rabbimq.step9.dto.StockReqDto;
import com.prac.rabbimq.step9.dto.StockResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {
    private final StockRepository stockRepository;

    @RabbitListener(queues = "transactionQueue")
    public void receiveTransaction(StockEntity stockEntity) {
        log.info("# received message  = {}", stockEntity.toString());

        try {
            stockEntity.msgReceiveProcessed();
            stockRepository.save(stockEntity);
            log.info("# StockENtity 저장 완료");

            // TODO: 클라이언트로 전송
            StockResDto stockResDto = stockEntity.toDto();
            // simplMessageTemplate.convertAndSend("클라이언트 엔드포인트", )

        } catch (Exception e) {
            log.error("MessageConsumer - stockEntity 저장 오류: {}", e.getMessage());
            throw e;
            // TODO: 메세지 데드레터 큐로 이동
        }
    }
}
