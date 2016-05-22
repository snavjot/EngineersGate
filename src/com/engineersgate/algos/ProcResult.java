package com.engineersgate.algos;

public class ProcResult {

	private Double[] sum;
	private Long[] count;
	
	public ProcResult(Double[] sum, Long[] count) {
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
