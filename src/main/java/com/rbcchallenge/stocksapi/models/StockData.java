package com.rbcchallenge.stocksapi.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock_data")
public class StockData {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int rid;
	
	@ManyToOne(targetEntity=Stock.class, optional=false)
	@JoinColumn(name="stock_fk", nullable=false)
	private Stock stock;
	
	@Column(nullable=false)
	private Date date;

	private int quarter;
	private float open;
	private float high;
	private float low;
	private float close;
	private long volume;
	private float percentChangePrice;

	@Column(nullable=true)
	private float percentChangeVolumeOverLastWeek;

	@Column(nullable=true)
	private long previousWeeksVolume;

	@Column(nullable=true)
	private float nextWeeksOpen;

	@Column(nullable=true)
	private float nextWeeksClose;
	
	@Column(nullable=true)
	private float percentChangeNextWeeksPrice;

	private int daysToNextDividend;
	private float percentReturnNextDividend;
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}

	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(float open) {
		this.open = open;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public float getPercentChangePrice() {
		return percentChangePrice;
	}
	public void setPercentChangePrice(float percentChangePrice) {
		this.percentChangePrice = percentChangePrice;
	}
	public float getPercentChangeVolumeOverLastWeek() {
		return percentChangeVolumeOverLastWeek;
	}
	public void setPercentChangeVolumeOverLastWeek(float percentChangeVolumeOverLastWeek) {
		this.percentChangeVolumeOverLastWeek = percentChangeVolumeOverLastWeek;
	}
	public long getPreviousWeeksVolume() {
		return previousWeeksVolume;
	}
	public void setPreviousWeeksVolume(long previousWeeksVolume) {
		this.previousWeeksVolume = previousWeeksVolume;
	}
	public float getNextWeeksOpen() {
		return nextWeeksOpen;
	}
	public void setNextWeeksOpen(float nextWeeksOpen) {
		this.nextWeeksOpen = nextWeeksOpen;
	}
	public float getNextWeeksClose() {
		return nextWeeksClose;
	}
	public void setNextWeeksClose(float nextWeeksClose) {
		this.nextWeeksClose = nextWeeksClose;
	}
	public float getPercentChangeNextWeeksPrice() {
		return percentChangeNextWeeksPrice;
	}
	public void setPercentChangeNextWeeksPrice(float percentChangeNextWeeksPrice) {
		this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
	}
	public int getDaysToNextDividend() {
		return daysToNextDividend;
	}
	public void setDaysToNextDividend(int daysToNextDividend) {
		this.daysToNextDividend = daysToNextDividend;
	}
	public float getPercentReturnNextDividend() {
		return percentReturnNextDividend;
	}
	public void setPercentReturnNextDividend(float percentReturnNextDividend) {
		this.percentReturnNextDividend = percentReturnNextDividend;
	}

}
