package com.engineersgate.executor;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.engineersgate.algos.ProcessedResult;
import com.engineersgate.utility.LargeFileComputeMean;
import com.engineersgate.utility.LargeFileComputeStandardDeviation;

public class ProjectExecutor {

	private static final ExecutorService fileReader = Executors.newSingleThreadExecutor();
	private static final ExecutorService processor = Executors.newFixedThreadPool(4);
	private static ArrayList<ProcessedResult> result = new ArrayList<ProcessedResult>();
	public static void main(String[] args) throws InterruptedException {
		String infilePath = "/home/navjot/development/rest/engineersgate/dataLarge-2";
		String meanFilePath = "/home/navjot/development/rest/engineersgate/ComputedMean";
		String outfilePath = "/home/navjot/development/rest/engineersgate/STD";
		File inFile = new File(infilePath);
		File meanFile = new File(meanFilePath);
		File outFile = new File(outfilePath);
		System.out.println("Starting");
		fileReader.execute(new LargeFileComputeStandardDeviation(processor, inFile, outFile, meanFile, 8192*10));
		Thread.sleep(1000);
		fileReader.shutdown();
		fileReader.awaitTermination(1000, TimeUnit.MILLISECONDS);
		while(true){
			if(fileReader.isTerminated()) {
				processor.shutdown();
				processor.awaitTermination(100000, TimeUnit.MILLISECONDS);
				break;
			}
			Thread.sleep(1000);
		}
//		while(true) {
//			if(processor.isTerminated()) {
//				break;
//			}
//			Thread.sleep(1000);
//		}
//		compute();
		System.out.println("Done and Res: " + result.size());
	}

	public static void addProcResult(ProcessedResult res) {
		
		synchronized(ProjectExecutor.class){
			result.add(res);
		}
	}
	
	public static void compute() {
		Double sum[] = null;
		Long count[] = null;
		boolean flag = false;
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
		for(int i=0; i< sum.length; i++){
		System.out.println("Sum : " + sum[i]);
		System.out.println("Count :" + count[i]);
		System.out.println("Mean: " + sum[i]/count[i]);
		}
	}
}
