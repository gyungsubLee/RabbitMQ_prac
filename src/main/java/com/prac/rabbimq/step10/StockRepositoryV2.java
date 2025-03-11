package com.prac.rabbimq.step10;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepositoryV2 extends JpaRepository<StockEntityV2, Long> {

    default StockEntityV2 findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(
                ()-> new RuntimeException("Stock not found"));
    }
}