package com.rbcchallenge.stocksapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rbcchallenge.stocksapi.models.StockData;
import com.rbcchallenge.stocksapi.models.StockDataDTO;

public interface StockDataService {
	
	StockData convertToStockData(StockDataDTO stockDataDTO);
	StockDataDTO convertToStockDataDTO(StockData stockData);
	
	StockDataDTO saveStockData(StockDataDTO stockDataDTO);
	StockData saveStockData(StockData stockData);
	void insertBulkStockData(MultipartFile file) throws IOException;
	List<StockDataDTO> getStockDataByTicker(String ticker);
}
