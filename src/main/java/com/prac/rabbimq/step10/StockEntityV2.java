package com.prac.rabbimq.step10;

import com.prac.rabbimq.step9.BaseEntity;
import com.prac.rabbimq.step10.dto.StockResDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockEntityV2 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private int stock;
    private boolean processed;

    @Builder
    public StockEntityV2(String userId, int stock) {
        this.userId = userId;
        this.stock = stock;
        this.processed = false;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public StockResDto toDto() {
        return StockResDto.builder()
                .id(id)
                .userId(userId)
                .stock(stock)
                .processed(processed)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}