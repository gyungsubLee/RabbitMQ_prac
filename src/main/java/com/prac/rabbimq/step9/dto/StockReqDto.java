package com.prac.rabbimq.step9.dto;

import com.prac.rabbimq.step9.StockEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class StockReqDto {

    private final Long userId;
    private final int stock;

    public StockEntity toEntity() {
        return StockEntity.builder()
                .userId(userId)
                .stock(stock)
                .build();
    }
}
