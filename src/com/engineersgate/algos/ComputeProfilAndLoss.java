package com.engineersgate.algos;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ComputeProfilAndLoss {

	private BufferedReader reader;
	private double position;
	private double basePrice;
	private ArrayList<Double> profitLossU;
	private ArrayList<Double> profitLossR;
	public ComputeProfilAndLoss(BufferedReader reader) {
		this.reader = reader;
		this.profitLossU = new ArrayList<Double>();
		this.profitLossR = new ArrayList<Double>();
	}
	
	public void computeProfitLoss() {
		try {
			String sale = reader.readLine();
			sale.trim();
			String[] data = sale.split(" ");
			double qty = Double.parseDouble(data[0]);
			double price = Double.parseDouble(data[1]);
			this.basePrice = price;
			this.position = qty;
			double avgPrice = price;
			profitLossU.add(new Double(0));
			profitLossR.add(new Double(0));
			while(true) {
				sale.trim();
				data = sale.split(" ");
				qty = Double.parseDouble(data[0]);
				price = Double.parseDouble(data[1]);
				if(qty > 0) {
					avgPrice = ((avgPrice * this.position) + (price * qty)) / (this.position + qty);
					this.position += qty;
					double currprofitLossU = profitLossU.get(profitLossU.size() - 1);
					profitLossU.add(currprofitLossU + ((avgPrice - this.basePrice)/this.position));
					
					double currprofitLossR = profitLossR.get(profitLossR.size() - 1);
					profitLossR.add(currprofitLossR);
				}
				else {
					this.position += qty;
					
					double currprofitLossR = profitLossR.get(profitLossR.size() - 1);
					profitLossR.add(currprofitLossR + ((price - avgPrice) * (qty * -1)));
					
					double currprofitLossU = profitLossU.get(profitLossU.size() - 1);
					profitLossU.add(currprofitLossU + ((avgPrice - this.basePrice)/this.position));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
