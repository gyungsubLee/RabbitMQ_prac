package com.prac.rabbimq.step10.dto;

import com.prac.rabbimq.step10.StockEntityV2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class StockReqDto {

    private final String userId;
    private final int stock;

    public StockEntityV2 toEntity() {
        return StockEntityV2.builder()
                .userId(userId)
                .stock(stock)
                .build();
    }
}