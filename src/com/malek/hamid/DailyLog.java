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
	private int status;
	
	
	public DailyLog(int day, int energy, int protein, int status) {
		super();
		this.day = day;
		this.energy = energy;
		this.protein = protein;
		this.status = status;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
