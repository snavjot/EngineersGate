package com.engineersgate.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
//import java.io.RandomAccessFile;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import com.engineersgate.algos.ComputeMean;

public class LargeFileReader implements Runnable{
	
	private final ExecutorService processor;
	private final File file;
	private int chunkSize;
	private int currOffset;
	private boolean keepProcessing;
	
	public LargeFileReader(ExecutorService processor, File file, int chunkSize) {
		this.processor = processor;
		this.file = file;
		this.chunkSize = chunkSize;
		this.currOffset = 0;
		this.keepProcessing = true;
	}
	
	@Override
	public void run() {
		System.out.println("Reading");
		while(this.keepProcessing) {
			BufferedReader reader = readData1();
			processor.execute(new ComputeMean(reader));
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		System.out.println("Done12345");
	}
	
//	private BufferedReader readData() {
//		BufferedReader bReader = null;
//		try {
//			FileInputStream fReader= new FileInputStream(this.file);
//			RandomAccessFile rReader = new RandomAccessFile(this.file, "r");
//			
//			rReader.seek(this.currOffset + this.chunkSize);
//			
//			while(true) {
//				int read = rReader.read();
//				if(read == '\n' || read == -1)
//					break;
//			}
//			long start = this.currOffset;
//			long end = rReader.getFilePointer();
//			byte[] arr = new byte[((int)rReader.getFilePointer() - this.currOffset)];
//			int read = fReader.read(arr, this.currOffset, arr.length);
//			
//			bReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(arr)));
//		} catch(Exception ex) {
//		}
//		
//		return bReader;
//	}
	
	private BufferedReader readData1() {
		byte[] arr = null;
		try {
			FileInputStream fReader = new FileInputStream(this.file);
			if(file.length() < this.currOffset)
				this.keepProcessing = false;
			fReader.skip(this.currOffset);
			arr = new byte[(int)this.sizeToread()];
			int read = fReader.read(arr);
			this.currOffset += read;
			fReader.close();
			if(read < arr.length)
				this.keepProcessing = false;
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		BufferedReader bReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(arr)));
		
		return bReader;
	}
	
	private long sizeToread() {
		try{
			RandomAccessFile rReader = new RandomAccessFile(this.file, "r");
			rReader.seek(this.currOffset + this.chunkSize);
			while(true) {
				int read = rReader.read();
				if(read == '\n' || read == -1)
					break;
			}
			long end = rReader.getFilePointer();
			rReader.close();
			return end - this.currOffset;
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}

}
