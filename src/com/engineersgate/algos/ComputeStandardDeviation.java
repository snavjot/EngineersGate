package com.engineersgate.algos;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.engineersgate.data.SharedData;

public class ComputeStandardDeviation {
	
	public static Double[] computeSTD() {
		Double sum[] = null;
		Long count[] = null;
		boolean flag = false;
		ArrayList<ProcessedResult> result = SharedData.getDataForSTD();
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
			resultMean[i] = new Double(Math.sqrt(sum[i]/count[i]));
		}
		return resultMean;
	}
}
