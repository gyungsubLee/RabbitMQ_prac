package com.prac.rabbimq.step10;

import com.prac.rabbimq.step10.dto.StockReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class TransactionController {

    private final MessageProducer messageProducer;

    @PostMapping
    public ResponseEntity<String> publishMessage(@RequestBody StockReqDto stockReqDto, @RequestParam boolean testCase) {
        System.out.println("Publisher Confirms Send message : " + stockReqDto.toString());

        try {
            messageProducer.sendMessage(stockReqDto, testCase);
            return ResponseEntity.ok("Publisher Confirms sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Publisher Confirms 트랜잭션 실패 : " + e.getMessage());
        }
    }
}