package com.malek.hamid.calendar;

import android.graphics.Color;

public class CalendarCell {
	private int cellColor;
	private String date;

	public CalendarCell(int cellColor, String date) {
		super();
		this.cellColor = cellColor;
		this.date = date;
	}

	public int getCellColor() {
		return cellColor;
	}

	public void setCellColor(int cellColor) {
		this.cellColor = cellColor;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDay() {
		String[] dateArray = date.split("/");
		if (dateArray != null & dateArray[2] != null)
			return dateArray[2];
		else
			return "";
	}
}
