package com.rbcchallenge.stocksapi;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbcchallenge.stocksapi.models.StockDataDTO;
import com.rbcchallenge.stocksapi.services.StockDataService;
import com.rbcchallenge.stocksapi.services.StockService;
import com.rbcchallenge.stocksapi.utils.CSVHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class StockDataControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockDataService stockDataService;
	@MockBean
	private StockService stockService;
	@MockBean
	private CSVHelper csvHelper;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void postStockDataReturnSavedStockData() throws Exception {
		StockDataDTO stockDataDTO = new StockDataDTO();
		stockDataDTO.setQuarter("1");
		stockDataDTO.setStock("AA");
		stockDataDTO.setDate("1/14/2011");
		stockDataDTO.setOpen("$16.71");
		stockDataDTO.setHigh("$16.71");
		stockDataDTO.setLow("15.64");
		stockDataDTO.setClose("15.97");
		stockDataDTO.setVolume("242963398");
		stockDataDTO.setPercentChangePrice("-4.42849");
		stockDataDTO.setPercentChangeVolumeOverLastWeek("1.380223028");
		stockDataDTO.setPreviousWeeksVolume("239655616");
		stockDataDTO.setNextWeeksOpen("$16.19");
		stockDataDTO.setNextWeeksClose("$15.79");
		stockDataDTO.setPercentChangeNextWeeksPrice("-2.47066");
		stockDataDTO.setDaysToNextDividend("19");
		stockDataDTO.setPercentReturnNextDividend("0.187852");

		given(stockDataService.saveStockData(any(StockDataDTO.class)))
				.willAnswer((invocation -> invocation.getArgument(0)));

		ResultActions response = mockMvc.perform((post("/api/stock-data")).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stockDataDTO)));

		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.quarter", is(stockDataDTO.getQuarter())))
				.andExpect(jsonPath("$.stock", is(stockDataDTO.getStock())))
				.andExpect(jsonPath("$.date", is(stockDataDTO.getDate())))
				.andExpect(jsonPath("$.open", is(stockDataDTO.getOpen())))
				.andExpect(jsonPath("$.high", is(stockDataDTO.getHigh())))
				.andExpect(jsonPath("$.low", is(stockDataDTO.getLow())))
				.andExpect(jsonPath("$.close", is(stockDataDTO.getClose())))
				.andExpect(jsonPath("$.volume", is(stockDataDTO.getVolume())))
				.andExpect(jsonPath("$.percentChangePrice", is(stockDataDTO.getPercentChangePrice())))
				.andExpect(jsonPath("$.percentChangeVolumeOverLastWk",
						is(stockDataDTO.getPercentChangeVolumeOverLastWeek())))
				.andExpect(jsonPath("$.previousWeeksVolume", is(stockDataDTO.getPreviousWeeksVolume())))
				.andExpect(jsonPath("$.nextWeeksOpen", is(stockDataDTO.getNextWeeksOpen())))
				.andExpect(jsonPath("$.nextWeeksClose", is(stockDataDTO.getNextWeeksClose())))
				.andExpect(jsonPath("$.percentChangeNextWeeksPrice", is(stockDataDTO.getPercentChangeNextWeeksPrice())))
				.andExpect(jsonPath("$.daysToNextDividend", is(stockDataDTO.getDaysToNextDividend())))
				.andExpect(jsonPath("$.percentReturnNextDividend", is(stockDataDTO.getPercentReturnNextDividend())));
	}

	@Test
	public void postBadStockDataReturnNullStockData() throws Exception {
		StockDataDTO stockDataDTO = new StockDataDTO();

		when(stockDataService.saveStockData(any(StockDataDTO.class))).thenThrow(RuntimeException.class);

		ResultActions response = mockMvc.perform((post("/api/stock-data")).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stockDataDTO)));

		response.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.quarter").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.stock").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.date").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.open").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.high").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.low").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.close").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.volume").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.percentChangePrice").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.percentChangeVolumeOverLastWk").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.previousWeeksVolume").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.nextWeeksOpen").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.nextWeeksClose").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.percentChangeNextWeeksPrice").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.daysToNextDividend").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.percentReturnNextDividend").value(IsNull.nullValue()));
	}

	@Test
	public void getTickerReturnListStockData() throws Exception {
		String ticker = "AA";

		List<StockDataDTO> stockDataList = new ArrayList<>();
		StockDataDTO stockDataDTO = new StockDataDTO();

		stockDataDTO.setQuarter("1");
		stockDataDTO.setStock("AA");
		stockDataDTO.setDate("1/14/2011");
		stockDataDTO.setOpen("$16.71");
		stockDataDTO.setHigh("$16.71");
		stockDataDTO.setLow("15.64");
		stockDataDTO.setClose("15.97");
		stockDataDTO.setVolume("242963398");
		stockDataDTO.setPercentChangePrice("-4.42849");
		stockDataDTO.setPercentChangeVolumeOverLastWeek("1.380223028");
		stockDataDTO.setPreviousWeeksVolume("239655616");
		stockDataDTO.setNextWeeksOpen("$16.19");
		stockDataDTO.setNextWeeksClose("$15.79");
		stockDataDTO.setPercentChangeNextWeeksPrice("-2.47066");
		stockDataDTO.setDaysToNextDividend("19");
		stockDataDTO.setPercentReturnNextDividend("0.187852");
		stockDataList.add(stockDataDTO);

		given(stockDataService.getStockDataByTicker(ticker)).willReturn(stockDataList);

		ResultActions response = mockMvc.perform(get("/api/stock-data/{ticker}", ticker));

		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$[0].quarter", is(stockDataDTO.getQuarter())))
				.andExpect(jsonPath("$[0].stock", is(stockDataDTO.getStock())))
				.andExpect(jsonPath("$[0].date", is(stockDataDTO.getDate())))
				.andExpect(jsonPath("$[0].open", is(stockDataDTO.getOpen())))
				.andExpect(jsonPath("$[0].high", is(stockDataDTO.getHigh())))
				.andExpect(jsonPath("$[0].low", is(stockDataDTO.getLow())))
				.andExpect(jsonPath("$[0].close", is(stockDataDTO.getClose())))
				.andExpect(jsonPath("$[0].volume", is(stockDataDTO.getVolume())))
				.andExpect(jsonPath("$[0].percentChangePrice", is(stockDataDTO.getPercentChangePrice())))
				.andExpect(jsonPath("$[0].percentChangeVolumeOverLastWk",
						is(stockDataDTO.getPercentChangeVolumeOverLastWeek())))
				.andExpect(jsonPath("$[0].previousWeeksVolume", is(stockDataDTO.getPreviousWeeksVolume())))
				.andExpect(jsonPath("$[0].nextWeeksOpen", is(stockDataDTO.getNextWeeksOpen())))
				.andExpect(jsonPath("$[0].nextWeeksClose", is(stockDataDTO.getNextWeeksClose())))
				.andExpect(
						jsonPath("$[0].percentChangeNextWeeksPrice", is(stockDataDTO.getPercentChangeNextWeeksPrice())))
				.andExpect(jsonPath("$[0].daysToNextDividend", is(stockDataDTO.getDaysToNextDividend())))
				.andExpect(jsonPath("$[0].percentReturnNextDividend", is(stockDataDTO.getPercentReturnNextDividend())));

	}

	@Test
	public void postCSVStockDataReturnOK() throws Exception {
		byte[] content = null;

		try {
			content = Files.readAllBytes(Paths.get("src/test/resources/dow_jones_index.data"));
		} catch (IOException e) {

		}

		MockMultipartFile mockFile = new MockMultipartFile("file", "dow_jones_index.data",
				MediaType.APPLICATION_OCTET_STREAM_VALUE, content);

		doNothing().when(stockDataService).insertBulkStockData(mockFile);

		mockMvc.perform(multipart("/api/stock-data/bulk-insert").file(mockFile)).andExpect(status().isOk());

	}

	@Test
	public void postBadCSVStockDataReturnBadRequest() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile("file", "dow_jones_index.data",
				MediaType.APPLICATION_OCTET_STREAM_VALUE, "test".getBytes());

		doThrow(new RuntimeException()).when(stockDataService).insertBulkStockData(mockFile);
		mockMvc.perform(multipart("/api/stock-data/bulk-insert").file(mockFile)).andExpect(status().isBadRequest());

	}
}
