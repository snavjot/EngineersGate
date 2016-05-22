package com.engineersgate.executor;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.engineersgate.algos.ProcResult;
import com.engineersgate.utility.LargeFileReader;

public class ProjectExecutor {

	private static final ExecutorService fileReader = Executors.newSingleThreadExecutor();
	private static final ExecutorService processor = Executors.newFixedThreadPool(4);
	private static ArrayList<ProcResult> result = new ArrayList<ProcResult>();
	public static void main(String[] args) throws InterruptedException {
		String filePath = "/home/navjot/development/rest/engineersgate/dataLarge-2";
		File file = new File(filePath);
		System.out.println("Starting");
		fileReader.execute(new LargeFileReader(processor, file, 8192*10));
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
		while(true) {
			if(processor.isTerminated()) {
				break;
			}
			Thread.sleep(1000);
		}
		compute();
		System.out.println("Done and Res: " + result.size());
	}

	public static void addProcResult(ProcResult res) {
		
		synchronized(ProjectExecutor.class){
			result.add(res);
		}
	}
	
	public static void compute() {
		Double sum[] = null;
		Long count[] = null;
		boolean flag = false;
		for(ProcResult res : result) {
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
