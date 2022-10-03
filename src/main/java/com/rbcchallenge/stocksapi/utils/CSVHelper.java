package com.rbcchallenge.stocksapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.rbcchallenge.stocksapi.models.Stock;
import com.rbcchallenge.stocksapi.models.StockData;

public class CSVHelper {

	public static List<StockData> csvToStockData(InputStream instream) {
		List<StockData> stockDataList = new ArrayList<>();

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Stock stock = new Stock();
				stock.setTicker(csvRecord.get("stock"));

				StockData stockData = new StockData();
				stockData.setStock(stock);
				stockData.setQuarter(Integer.valueOf(csvRecord.get("quarter")));

				String date = csvRecord.get("date");
				int delimiter1 = date.indexOf("/", 0);
				int delimiter2 = date.indexOf("/", delimiter1 + 1);
				String mm = date.substring(0, delimiter1);
				String dd = date.substring(delimiter1 + 1, delimiter2);
				String yyyy = date.substring(delimiter2 + 1);
				stockData.setDate(Date.valueOf(yyyy + "-" + mm + "-" + dd));

				stockData.setOpen(Float.valueOf(csvRecord.get("open").replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
				stockData.setHigh(Float.valueOf(csvRecord.get("high").replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
				stockData.setLow(Float.valueOf(csvRecord.get("low").replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
				stockData.setClose(Float.valueOf(csvRecord.get("close").replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
				stockData.setVolume(Long.valueOf(csvRecord.get("volume")));
				stockData.setPercentChangePrice(Float.valueOf(csvRecord.get("percent_change_price")));

				if (!csvRecord.get("percent_change_volume_over_last_wk").isBlank()) {
					stockData.setPercentChangeVolumeOverLastWeek(
							Float.valueOf(csvRecord.get("percent_change_volume_over_last_wk")));
				}
				if (!csvRecord.get("previous_weeks_volume").isBlank()) {
					stockData.setPreviousWeeksVolume(Long.valueOf(csvRecord.get("previous_weeks_volume")));
				}
				if (!csvRecord.get("next_weeks_open").isBlank()) {
					stockData.setNextWeeksOpen(Float.valueOf(csvRecord.get("next_weeks_open").replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
				}
				if (!csvRecord.get("next_weeks_close").isBlank()) {
					stockData.setNextWeeksClose(Float.valueOf(csvRecord.get("next_weeks_close").replaceAll("^\\D+", "").replaceAll("\\D+$", "")));
				}
				if (!csvRecord.get("percent_change_next_weeks_price").isBlank()) {
					stockData.setPercentChangeNextWeeksPrice(
							Float.valueOf(csvRecord.get("percent_change_next_weeks_price")));
				}

				stockData.setDaysToNextDividend(Integer.valueOf(csvRecord.get("days_to_next_dividend")));
				stockData.setPercentReturnNextDividend(Float.valueOf(csvRecord.get("percent_return_next_dividend")));

				stockDataList.add(stockData);
			}

		} catch (IOException e) {
			throw new RuntimeException("Failed to parse file: " + e.getMessage());
		}

		return stockDataList;
	}
}
