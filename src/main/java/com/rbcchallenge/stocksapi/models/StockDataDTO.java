package com.rbcchallenge.stocksapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema()
public class StockDataDTO {
	@Schema(example="1")
	private String quarter;
	@Schema(example="MSFT")
	private String stock;
	@Schema(example="1/7/2011")
	private String date;
	@Schema(example="$28.05")
	private String open;
	@Schema(example="$28.85")
	private String high;
	@Schema(example="$27.77")
	private String low;
	@Schema(example="$28.60")
	private String close;
	@Schema(example="328646154")
	private String volume;
	@Schema(example="1.96078")
	private String percentChangePrice;
	@Schema(example="0")
	private String percentChangeVolumeOverLastWeek;
	@Schema(example="0")
	private String previousWeeksVolume;
	@Schema(example="$28.20")
	private String nextWeeksOpen;
	@Schema(example="$28.30")
	private String nextWeeksClose;
	@Schema(example="0.35461")
	private String percentChangeNextWeeksPrice;
	@Schema(example="39")
	private String daysToNextDividend;
	@Schema(example="0.559441")
	private String percentReturnNextDividend;

	public String getQuarter() {
		return quarter;
	}
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getPercentChangePrice() {
		return percentChangePrice;
	}
	public void setPercentChangePrice(String percentChangePrice) {
		this.percentChangePrice = percentChangePrice;
	}
	@JsonProperty("percentChangeVolumeOverLastWk")
	public String getPercentChangeVolumeOverLastWeek() {
		return percentChangeVolumeOverLastWeek;
	}
	@JsonProperty("percentChangeVolumeOverLastWk")
	public void setPercentChangeVolumeOverLastWeek(String percentChangeVolumeOverLastWeek) {
		this.percentChangeVolumeOverLastWeek = percentChangeVolumeOverLastWeek;
	}
	public String getPreviousWeeksVolume() {
		return previousWeeksVolume;
	}
	public void setPreviousWeeksVolume(String previousWeeksVolume) {
		this.previousWeeksVolume = previousWeeksVolume;
	}
	public String getNextWeeksOpen() {
		return nextWeeksOpen;
	}
	public void setNextWeeksOpen(String nextWeeksOpen) {
		this.nextWeeksOpen = nextWeeksOpen;
	}
	public String getNextWeeksClose() {
		return nextWeeksClose;
	}
	public void setNextWeeksClose(String nextWeeksClose) {
		this.nextWeeksClose = nextWeeksClose;
	}
	public String getPercentChangeNextWeeksPrice() {
		return percentChangeNextWeeksPrice;
	}
	public void setPercentChangeNextWeeksPrice(String percentChangeNextWeeksPrice) {
		this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
	}
	public String getDaysToNextDividend() {
		return daysToNextDividend;
	}
	public void setDaysToNextDividend(String daysToNextDividend) {
		this.daysToNextDividend = daysToNextDividend;
	}
	public String getPercentReturnNextDividend() {
		return percentReturnNextDividend;
	}
	public void setPercentReturnNextDividend(String percentReturnNextDividend) {
		this.percentReturnNextDividend = percentReturnNextDividend;
	}
	
}
