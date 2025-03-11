package com.prac.rabbimq.step10.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StockResDto {

    private final Long id;
    private final String userId;
    private final int stock;
    private final boolean processed;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public StockResDto(Long id, String userId, int stock, boolean processed, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.stock = stock;
        this.processed = processed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}