package com.rbcchallenge.stocksapi.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stocks")
public class Stock {
	
	@Id
	private String ticker;
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ticker);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(ticker, other.ticker);
	}

	@Override
	public String toString() {
		return "Stock [ticker=" + ticker + "]";
	}
	
}
