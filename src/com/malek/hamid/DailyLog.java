package com.malek.hamid;

/**
 * 
 * this class is a object form of table dailyNutritionLog
 * @author hamid_
 *
 */

public class DailyLog {
	
	private int day ;
	private int energy ;
	private int protein;
	
	
	public DailyLog(int day, int energy, int protein) {
		super();
		this.day = day;
		this.energy = energy;
		this.protein = protein;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public int getProtein() {
		return protein;
	}
	public void setProtein(int protein) {
		this.protein = protein;
	}
	
}
