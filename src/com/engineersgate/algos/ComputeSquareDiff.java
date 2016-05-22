package com.engineersgate.algos;

import java.io.BufferedReader;

import com.engineersgate.data.SharedData;

public class ComputeSquareDiff implements Runnable{

	private BufferedReader reader;
	private Double[] meanData;
	
	public ComputeSquareDiff(BufferedReader reader, Double[] meanData) {
		this.reader = reader;
		this.meanData = meanData;
	}
	
	@Override
	public void run() {
		try {
			computeSquareDiff();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void computeSquareDiff() throws InterruptedException {
		Double[] squareDiff = null;
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
						squareDiff = new Double[doubleString.length];
						count = new Long[doubleString.length];
						for(i=0; i < squareDiff.length; i++){
							squareDiff[i] = new Double(0);
							count[i] = new Long(0);
						}
						flag = false;
					}
					i = 0;
					for(String valString : doubleString){
						try{
						double val = Double.parseDouble(valString);
						squareDiff[i] += ((this.meanData[i] - val) * (this.meanData[i] - val));
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
		if(squareDiff != null){
			ProcessedResult res = new ProcessedResult(squareDiff, count);
			SharedData.addProcResult(res);
		}
	}
	
}
