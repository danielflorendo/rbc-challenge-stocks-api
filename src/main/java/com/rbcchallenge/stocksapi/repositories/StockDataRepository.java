package com.rbcchallenge.stocksapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbcchallenge.stocksapi.models.Stock;
import com.rbcchallenge.stocksapi.models.StockData;

public interface StockDataRepository extends JpaRepository<StockData, Long> {
	
	List<StockData> findByStock(Stock stock);
}
