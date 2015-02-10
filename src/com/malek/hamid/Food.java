package com.malek.hamid;

public class Food {
	private int id;
	private String name;
	private double stdEnergy;
	private double stdProtein;
	private double unitEnergy;
	private double unitProtein;
	private int categoryId;
	private int unitId;

	/**
	 * 
	 @param name
	 *            : the food name
	 * @param calorieSI
	 *            : calorie of food per 100 gram
	 * @param category
	 *            : category of food which is from an enumeration provided in
	 *            this class
	 * @param unitId
	 *            : non SI unit of the food such as glass, spoon, ... .
	 *            enumeration is provided in this class
	 * @param calorieUnit
	 *            : calorie of the unit
	 * @param categoryId
	 *            : category id of food
	 */
	public Food(int id, String name, double calorieSI, int calorieUnit,
			int unitId, int categoryId) {
		this.id = id;
		this.name = name;
		this.stdEnergy = calorieSI;
		this.unitEnergy = calorieUnit;
		this.setCategoryId(categoryId);
		this.unitId = unitId;
	}

	/**
	 * 
	 * @param name
	 *            : the food name
	 * @param calorieSI
	 *            : calorie of food per 100 gram
	 * @param category
	 *            : category of food which is from an enumeration provided in
	 *            this class
	 */
	public Food(int id, String name, int calorieSI, int categoryId) {
		this.id = id;
		this.name = name;
		this.stdEnergy = calorieSI;
		this.categoryId = categoryId;
		this.unitId = 0;
		this.unitEnergy = -1;
	}

	public int getUnit() {
		return unitId;
	}

	public void setUnit(int unit) {
		this.unitId = unit;
	}

	public double getCalorieSI() {
		return stdEnergy;
	}

	public void setCalorieSI(int calorieSI) {
		this.stdEnergy = calorieSI;
	}

	public double getCalorieUnit() {
		return unitEnergy;
	}

	public void setCalorieUnit(int calorieUnit) {
		this.unitEnergy = calorieUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * enumeration of the non SI units for foods
	 * 
	 * @author hamid_
	 * 
	 */
	public enum Unit {
		none, spoon, glass;
	}

	@Override
	public String toString() {
		return getName();
	}

	public double getStdProtein() {
		return stdProtein;
	}

	public void setStdProtein(double stdProtein) {
		this.stdProtein = stdProtein;
	}

	public double getUnitProtein() {
		return unitProtein;
	}

	public void setUnitProtein(double unitProtein) {
		this.unitProtein = unitProtein;
	}
}
