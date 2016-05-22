package com.engineersgate.algos;

import java.io.BufferedReader;

import com.engineersgate.data.SharedData;
import com.engineersgate.executor.ProjectExecutor;

public class ComputeSum implements Runnable{
	
	private BufferedReader reader;
	
	public ComputeSum(BufferedReader reader) {
		this.reader = reader;
	}
	
	@Override
	public void run() {
		try {
			computeSum();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void computeSum() throws InterruptedException {
		Double[] sum = null;
		Long[] count = null;
		boolean flag = true;
		try {
			while(true) {
				String line = this.reader.readLine();
				if(line == null)
					break;
				try {
					line = line.trim();
					String [] doubleString = line.split(",");
					int i = 0;
					if(flag) {
						sum = new Double[doubleString.length];
						count = new Long[doubleString.length];
						for(i=0; i < sum.length; i++){
							sum[i] = new Double(0);
							count[i] = new Long(0);
						}
						flag = false;
					}
					i = 0;
					for(String valString : doubleString){
						try{
						double val = Double.parseDouble(valString);
						sum[i] += val;
						count[i] += 1;
						i += 1;
						} catch(Exception ex) {
							i += 1;
						}
					}
				} catch(Exception ex) {
					System.out.println("hello");
					System.out.println(ex.getMessage());
				}
			}
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		if(sum != null){
			ProcessedResult res = new ProcessedResult(sum, count);
			SharedData.addProcResult(res);
		}
	}
}
