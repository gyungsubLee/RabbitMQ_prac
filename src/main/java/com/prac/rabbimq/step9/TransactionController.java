package com.prac.rabbimq.step9;

import com.prac.rabbimq.step9.dto.StockReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class TransactionController {
    private final MessageProducer messageProducer;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody StockReqDto stockReqDto,
                                              @RequestParam(required = false, defaultValue = "success") String testCase) {

        log.info("Send Message : {}", stockReqDto.toString());

        try {
            messageProducer.sendMessage(stockReqDto, testCase);
            return ResponseEntity.ok("Message sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MQ 트랜잭션 실패 : " + e.getMessage());
        }
    }
}