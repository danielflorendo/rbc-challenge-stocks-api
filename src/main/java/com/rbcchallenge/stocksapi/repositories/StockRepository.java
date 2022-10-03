package com.rbcchallenge.stocksapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbcchallenge.stocksapi.models.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {

}
