package com.engineersgate.utility;

import java.io.File;
import java.io.FileOutputStream;

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

}
