package com.rbcchallenge.stocksapi.services;

import com.rbcchallenge.stocksapi.models.Stock;

public interface StockService {
	Stock getStockByTicker(String ticker);
	Stock saveStock(Stock stock);
}
