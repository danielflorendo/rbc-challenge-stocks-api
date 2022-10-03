package com.rbcchallenge.stocksapi.services;

import org.springframework.stereotype.Service;

import com.rbcchallenge.stocksapi.exceptions.ResourceNotFoundException;
import com.rbcchallenge.stocksapi.models.Stock;
import com.rbcchallenge.stocksapi.repositories.StockRepository;

@Service
public class StockServiceImpl implements StockService {

	StockRepository stockRepository;
	
	public StockServiceImpl(StockRepository stockRepository) {
		super();
		this.stockRepository = stockRepository;
	}

	@Override
	public Stock getStockByTicker(String ticker) throws ResourceNotFoundException {
		return stockRepository.findById(ticker).orElseThrow(() -> new ResourceNotFoundException("Stock", "ticker", ticker));
	}

	@Override
	public Stock saveStock(Stock stock) {
		return stockRepository.save(stock);
	}

}
