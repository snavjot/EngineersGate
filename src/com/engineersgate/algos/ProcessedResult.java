package com.engineersgate.algos;

public class ProcessedResult {

	private Double[] sum;
	private Long[] count;
	
	public ProcessedResult(Double[] sum, Long[] count) {
		this.sum = sum;
		this.count = count;
	}
	
	public Double[] getSum() {
		return this.sum;
	}
	
	public Long[] getCount() {
		return this.count;
	}
}
