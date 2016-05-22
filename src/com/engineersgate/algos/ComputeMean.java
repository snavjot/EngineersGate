package com.engineersgate.algos;

import java.util.ArrayList;

import com.engineersgate.data.SharedData;

public class ComputeMean {

	public static Double[] computeMean() {
		Double sum[] = null;
		Long count[] = null;
		boolean flag = false;
		ArrayList<ProcessedResult> result = SharedData.getDataForMean();
		for(ProcessedResult res : result) {
			if(flag) {
				Double[] tempSum = res.getSum();
				Long[] tempCount = res.getCount();
				for(int i=0; i< tempSum.length; i++) {
					sum[i] += tempSum[i];
					count[i] += tempCount[i];
				}
			}
			else {
				sum = res.getSum();
				count = res.getCount();
				flag = true;
			}
		}
		Double[] resultMean = new Double[sum.length];
		for(int i=0; i< sum.length; i++){
			System.out.println("Sum : " + sum[i]);
			System.out.println("Count :" + count[i]);
			System.out.println("Mean: " + sum[i]/count[i]);
			resultMean[i] = new Double(sum[i]/count[i]);
		}
		return resultMean;
	}
}
