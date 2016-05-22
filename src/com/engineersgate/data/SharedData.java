package com.engineersgate.data;

import java.util.ArrayList;

import com.engineersgate.algos.ProcessedResult;

public class SharedData {

	private static ArrayList<ProcessedResult> result = new ArrayList<ProcessedResult>();
	
	public static void addProcResult(ProcessedResult res) {	
			synchronized(SharedData.class){
				result.add(res);
			}
	}
	
	public static ArrayList<ProcessedResult> getDataForMean() {
		return result;
	}
	
	public static ArrayList<ProcessedResult> getDataForSTD() {
		return result;
	}
}
