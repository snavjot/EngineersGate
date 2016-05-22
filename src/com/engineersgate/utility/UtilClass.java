package com.engineersgate.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class UtilClass {
	
	public static void writeMeanData(Double[] data, File file) {
		String lineToWrite = " ";
		for(double val : data) {
			lineToWrite = lineToWrite + " " + val;
		}
		try {
			FileOutputStream fWriter = new FileOutputStream(file);
			fWriter.write(lineToWrite.getBytes());
			fWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Double[] getMeanData(File file) {
		
		try {
			FileInputStream freader = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(freader));
			String meanDataLine = reader.readLine();
			meanDataLine = meanDataLine.trim();
			String[] data = meanDataLine.split(" ");
			Double[] meanVals = new Double[data.length];
			int i = 0;
			for(String val : data) {
				meanVals[i] = Double.parseDouble(val);
				i += 1;
			}
			reader.close();
			freader.close();
			return meanVals;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
