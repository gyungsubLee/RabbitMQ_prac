package com.prac.rabbimq.step10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLTransientConnectionException;

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
//            Thread.sleep(200);
            StockEntityV2 findStock = stockRepository.findByIdOrElseThrow(stock.getId());

            // 업데이트 로직
            stockRepository.save(findStock);
            System.out.println("[Save Entity Consumer] " + findStock);

            // 메시지 처리 성공 -> ACK 전송
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            System.err.println("[Error] 메시지 처리 중 오류 발생: " + e.getMessage());

            try {
                // 예외 발생 시 메시지를 다시 시도하거나 버릴지 결정
                boolean shouldRequeue = shouldRequeue(e);
                channel.basicNack(deliveryTag, false, shouldRequeue);
            } catch (IOException ioException) {
                System.err.println("[Error] ACK/NACK 처리 중 오류 발생: " + ioException.getMessage());
            }
        }
    }

    /**
     * 특정 예외 유형에 따라 재처리 여부를 결정하는 메서드
     */
    private boolean shouldRequeue(Exception e) {
        /**
         *  재시도할 예외 목록
         *  - SocketTimeoutException: 네트워크 연결 시간 초과
         *  - ConnectException: 서버 연결 오류
         *  - SQLTransientConnectionException: 일시적인 DB 연결 실패
         *  - ResourceAccessException: 외부 API 또는 DB 접근 실패
         */
        if (e instanceof SocketTimeoutException ||
                e instanceof ConnectException ||
                e instanceof SQLTransientConnectionException ||
                e instanceof ResourceAccessException) {
            return true;  // 다시 큐에 넣어서 재처리
        }

        /**
         * 즉시 폐기할 예외 목록
         *  - IllegalArgumentException: 잘못된 입력값
         *  - DataIntegrityViolationException: 데이터 무결성 위반(중복 키, NULL 값 삽입 등)
         *  - JsonProcessingException: JSON 파싱 실패
         *  - EntityNotFoundException: DB에서 해당 엔티티 찾을 수 없음
         */
        if (e instanceof IllegalArgumentException ||
                e instanceof DataIntegrityViolationException ||
                e instanceof JsonProcessingException ||
                e instanceof EntityNotFoundException) {
            return false; // 재처리하지 않고 폐기 (DLQ로 보낼 수도 있음)
        }

        // 기본적으로 폐기
        return false;
    }
}
