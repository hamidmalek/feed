package com.malek.hamid.calendar;

public class ShamsiDate {
	private int month;
	private int year;
	String[] iranianMonthes = { "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد",
			"شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند" };

	public ShamsiDate(int month, int year) {
		if (month > 12) {
			this.month = 1;
			this.year = ++year;
		} else if (month < 1) {
			this.month = 12;
			this.year = --year;
		} else {
			this.month = month;
			this.year = year;
		}
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public String getMonthName() {
		return iranianMonthes[month - 1];
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.year+"/"+this.month;
	}
}
