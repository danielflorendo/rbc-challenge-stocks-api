package com.rbcchallenge.stocksapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rbcchallenge.stocksapi.models.StockDataDTO;
import com.rbcchallenge.stocksapi.services.StockDataService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/stock-data")
public class StockDataController {

	private StockDataService stockDataService;

	public StockDataController(StockDataService stockDataService) {
		super();
		this.stockDataService = stockDataService;
	}

	@Operation(summary = "Get stock data by ticker")
	@GetMapping("{ticker}")
	public List<StockDataDTO> getStockDataByTicker(@PathVariable @Parameter(example = "MSFT") String ticker) {
		return stockDataService.getStockDataByTicker(ticker);
	}

	@Operation(summary = "Insert a single stock data record")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Stock data inserted") })
	@PostMapping
	public ResponseEntity<StockDataDTO> createStockData(@RequestBody StockDataDTO stockDataDTO) {
		try {
			StockDataDTO responseEntity = stockDataService.saveStockData(stockDataDTO);
			return new ResponseEntity<StockDataDTO>(responseEntity, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<StockDataDTO>(new StockDataDTO(), HttpStatus.BAD_REQUEST);
		}

	}

	@Operation(summary = "Upload a CSV of stock data records")
	@PostMapping(path = "bulk-insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> bulkInsertStockData(@RequestParam("file") MultipartFile file) {
		try {
			stockDataService.insertBulkStockData(file);
			return new ResponseEntity<String>(
					"Bulk data inserted from \"" + file.getOriginalFilename() + "\" successfully.", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>("Failed to insert bulk data: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
