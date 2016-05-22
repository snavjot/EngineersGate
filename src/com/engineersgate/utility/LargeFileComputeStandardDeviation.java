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
import com.engineersgate.algos.ComputeSquareDiff;
import com.engineersgate.algos.ComputeStandardDeviation;
import com.engineersgate.algos.ComputeSum;

public class LargeFileComputeStandardDeviation implements Runnable{
	
	private final ExecutorService processor;
	private final File inputFile;
	private final File outputFile;
	private final File meanDataFile;
	private int chunkSize;
	private int currOffset;
	private boolean keepProcessing;
	
	public LargeFileComputeStandardDeviation(ExecutorService processor, File infile, File outFile, File meanFile, int chunkSize) {
		this.processor = processor;
		this.inputFile = infile;
		this.outputFile = outFile;
		this.meanDataFile = meanFile;
		this.chunkSize = chunkSize;
		this.currOffset = 0;
		this.keepProcessing = true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * Reads Input Time series data block by block and calls ComputeSum
	 * Once Compute sum is done it calls ComputeMean to process the mean.
	 */
	@Override
	public void run() {
		Double[] meanData = UtilClass.getMeanData(this.meanDataFile);
		while(this.keepProcessing) {
			BufferedReader reader = getBlockOfdata();
			processor.execute(new ComputeSquareDiff(reader, meanData));
		}
		/*
		 * Call Shutdown on processor. Once it shuts down execute ComputeMean
		 */
		processor.shutdown();
		while(true){
			/* 
			 * Checks if all tasks are finished
			 */
			if(processor.isTerminated()) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * Call ComputeMean
		 */
		Double[] mean = ComputeStandardDeviation.computeSTD();
		UtilClass.writeMeanData(mean, this.outputFile);
		System.out.println("Done12345");
	}
	
	private BufferedReader getBlockOfdata() {
		byte[] arr = null;
		try {
			FileInputStream fReader = new FileInputStream(this.inputFile);
			if(inputFile.length() < this.currOffset)
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
			RandomAccessFile rReader = new RandomAccessFile(this.inputFile, "r");
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
