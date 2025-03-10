package com.prac.rabbimq.step9;

import com.prac.rabbimq.step9.dto.StockResDto;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int stock;
    private boolean processed;

    @Builder
    public StockEntity(Long userId, int stock) {
        this.userId = userId;
        this.stock = stock;
        this.processed = false;
    }

    public void msgReceiveProcessed() {
        this.processed = true;
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