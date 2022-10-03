package com.rbcchallenge.stocksapi.services;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rbcchallenge.stocksapi.exceptions.ResourceNotFoundException;
import com.rbcchallenge.stocksapi.models.Stock;
import com.rbcchallenge.stocksapi.models.StockData;
import com.rbcchallenge.stocksapi.models.StockDataDTO;
import com.rbcchallenge.stocksapi.repositories.StockDataRepository;
import com.rbcchallenge.stocksapi.utils.CSVHelper;

@Service
public class StockDataServiceImpl implements StockDataService {

	private StockDataRepository stockDataRepository;
	private StockService stockService;

	public StockDataServiceImpl(StockDataRepository stockDataRepository, StockService stockService) {
		super();
		this.stockDataRepository = stockDataRepository;
		this.stockService = stockService;
	}

	@Override
	public StockData convertToStockData(StockDataDTO stockDataDTO) {
		StockData stockData = new StockData();
		Stock stock = new Stock();

		try {
			stock.setTicker(stockDataDTO.getStock());
			stockData.setStock(stock);

			String date = stockDataDTO.getDate();
			int delimiter1 = date.indexOf("/", 0);
			int delimiter2 = date.indexOf("/", delimiter1 + 1);
			String mm = date.substring(0, delimiter1);
			String dd = date.substring(delimiter1 + 1, delimiter2);
			String yyyy = date.substring(delimiter2 + 1);
			stockData.setDate(Date.valueOf(yyyy + "-" + mm + "-" + dd));

			stockData.setQuarter(Integer.valueOf(stockDataDTO.getQuarter()));

			// regex removes leading and trailing
			stockData.setOpen(Float.valueOf(stockDataDTO.getOpen().replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
			stockData.setHigh(Float.valueOf(stockDataDTO.getHigh().replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
			stockData.setLow(Float.valueOf(stockDataDTO.getLow().replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
			stockData.setClose(Float.valueOf(stockDataDTO.getClose().replaceAll("^\\D+", "").replaceAll("\\D+$", "")));

			stockData.setVolume(Long.valueOf(stockDataDTO.getVolume()));
			stockData.setPercentChangePrice(Float.valueOf(stockDataDTO.getPercentChangePrice()));

			if (!stockDataDTO.getPercentChangeVolumeOverLastWeek().isBlank()) {
				stockData.setPercentChangeVolumeOverLastWeek(
						Float.valueOf(stockDataDTO.getPercentChangeVolumeOverLastWeek()));
			}
			if (!stockDataDTO.getPreviousWeeksVolume().isBlank()) {
				stockData.setPreviousWeeksVolume(Long.valueOf(stockDataDTO.getPreviousWeeksVolume()));
			}
			if (!stockDataDTO.getNextWeeksOpen().isBlank()) {
				stockData.setNextWeeksOpen(
						Float.valueOf(stockDataDTO.getNextWeeksOpen().replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
			}
			if (!stockDataDTO.getNextWeeksClose().isBlank()) {
				stockData.setNextWeeksClose(Float
						.valueOf(stockDataDTO.getNextWeeksClose().replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
			}
			if (!stockDataDTO.getPercentChangeNextWeeksPrice().isBlank()) {
				stockData.setPercentChangeNextWeeksPrice(Float.valueOf(stockDataDTO.getPercentChangeNextWeeksPrice()));
			}

			stockData.setDaysToNextDividend(Integer.valueOf(stockDataDTO.getDaysToNextDividend()));
			stockData.setPercentReturnNextDividend(Float.valueOf(stockDataDTO.getPercentReturnNextDividend()));
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert payload.");
		}

		return stockData;
	}

	@Override
	public StockDataDTO convertToStockDataDTO(StockData stockData) {
		StockDataDTO stockDataDTO = new StockDataDTO();

		stockDataDTO.setQuarter(String.valueOf(stockData.getQuarter()));
		stockDataDTO.setStock(stockData.getStock().getTicker());
		stockDataDTO.setDate(new SimpleDateFormat("MM/dd/yyyy").format(stockData.getDate()));
		stockDataDTO.setOpen(String.format("$%.2f", stockData.getOpen()));
		stockDataDTO.setHigh(String.format("$%.2f", stockData.getHigh()));
		stockDataDTO.setLow(String.format("$%.2f", stockData.getLow()));
		stockDataDTO.setClose(String.format("$%.2f", stockData.getClose()));
		stockDataDTO.setVolume(String.valueOf(stockData.getVolume()));
		stockDataDTO.setPercentChangePrice(
				new BigDecimal(stockData.getPercentChangePrice()).stripTrailingZeros().toPlainString());
		stockDataDTO.setPercentChangeVolumeOverLastWeek(
				new BigDecimal(stockData.getPercentChangeVolumeOverLastWeek()).stripTrailingZeros().toPlainString());
		stockDataDTO.setPreviousWeeksVolume(String.valueOf(stockData.getPreviousWeeksVolume()));
		stockDataDTO.setNextWeeksOpen(String.format("$%.2f", stockData.getNextWeeksOpen()));
		stockDataDTO.setNextWeeksClose(String.format("$%.2f", stockData.getNextWeeksClose()));
		stockDataDTO.setPercentChangeNextWeeksPrice(
				new BigDecimal(stockData.getPercentChangeNextWeeksPrice()).stripTrailingZeros().toPlainString());
		stockDataDTO.setDaysToNextDividend(String.valueOf(stockData.getDaysToNextDividend()));
		stockDataDTO.setPercentReturnNextDividend(
				new BigDecimal(stockData.getPercentReturnNextDividend()).stripTrailingZeros().toPlainString());

		return stockDataDTO;
	}

	@Override
	public List<StockDataDTO> getStockDataByTicker(String ticker) {

		try {
			Stock stock = stockService.getStockByTicker(ticker);
			List<StockData> stockData = stockDataRepository.findByStock(stock);
			return stockData.stream().map(mStockData -> convertToStockDataDTO(mStockData)).collect(Collectors.toList());
		} catch (ResourceNotFoundException e) {
			return new ArrayList<>();
		}
	}

	/*
	 * Saves a record in stock_data table if corresponding stock exists in stock
	 * table, otherwise stock is saved first.
	 */
	@Override
	public StockDataDTO saveStockData(StockDataDTO stockDataDTO) {
		try {
			stockService.getStockByTicker(stockDataDTO.getStock());
		} catch (ResourceNotFoundException e) {
			Stock stock = new Stock();
			stock.setTicker(stockDataDTO.getStock());
			stockService.saveStock(stock);
		}

		StockData stockData = stockDataRepository.save(convertToStockData(stockDataDTO));
		return convertToStockDataDTO(stockData);
	}

	@Override
	public StockData saveStockData(StockData stockData) {
		try {
			stockService.getStockByTicker(stockData.getStock().getTicker());
		} catch (ResourceNotFoundException e) {
			Stock stock = new Stock();
			stock.setTicker(stockData.getStock().getTicker());
			stockService.saveStock(stock);
		}

		return stockDataRepository.save(stockData);
	}

	@Override
	public void insertBulkStockData(MultipartFile file) throws IOException {
		try (InputStream instream = file.getInputStream()) {
			List<StockData> stockDataList = CSVHelper.csvToStockData(file.getInputStream());
			for (StockData s : stockDataList) {
				saveStockData(s);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse CSV:" + e.getMessage());
		}
	}

}
