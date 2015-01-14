package com.malek.hamid;

public class Log {
	private int id;
	private String dateAdded;
	private String dateServed;
	private float size;
	private boolean isStd;
	private int foodId;
	private int mealId;

	public Log(int id, String dateAdded, String dateServed, float size,
			boolean isStd, int foodId, int mealId) {
		super();
		this.id = id;
		this.dateAdded = dateAdded;
		this.dateServed = dateServed;
		this.size = size;
		this.isStd = isStd;
		this.foodId = foodId;
		this.mealId = mealId;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getDateServed() {
		return dateServed;
	}

	public void setDateServed(String dateServed) {
		this.dateServed = dateServed;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public boolean isStd() {
		return isStd;
	}

	public void setStd(boolean isStd) {
		this.isStd = isStd;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return foodId+":"+mealId+":"+size;
	}
}
