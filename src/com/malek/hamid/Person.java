package com.malek.hamid;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	
	private final int WEIGHT_LOSE_RATE = 100;
	
	/*
	 * weight of the user in kilograms
	 */
	private float weight;
	/*
	 * height of the user in centimeter
	 */
	private int height;
	/*
	 * birthday string in the yyyy/mm/dd format
	 */
	private String birthday;
	/*
	 * user gender, true for female and false for male
	 */
	private int sex;
	/*
	 * user desired weight
	 */
	private float desiredWeight;
	/*
	 * user activity level
	 */
	private int activityLevel;
	/*
	 * user deadline for desired weight
	 */
	private String deadline;

	public float getDesiredWeight() {
		return desiredWeight;
	}

	public void setDesiredWeight(float f) {
		this.desiredWeight = f;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return String of birthday in the yyyy/mm/dd format
	 */
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Person(float weight, int height, String birthday, int sex) {
		super();
		this.weight = weight;
		this.height = height;
		this.birthday = birthday;
		this.setSex(sex);
	}

	public Person(Parcel parcel) {
		weight = Float.parseFloat(parcel.readString());
		height = Integer.parseInt(parcel.readString());
		birthday = parcel.readString();
		sex = Integer.parseInt(parcel.readString());
		activityLevel = Integer.parseInt(parcel.readString());
		desiredWeight = Float.parseFloat(parcel.readString());
		deadline = parcel.readString();
	}

	/**
	 * this method returns the sex of the person if its true the person is
	 * female, otherwise male
	 * 
	 * @return boolean
	 */
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public double getBMI() {
		return weight / Math.pow((height / 100), 2);
	}

	/**
	 * this function calculate the person's age by date of birth
	 * 
	 * @return : age of person in years
	 */
	public int getAge() {
		int birthYear = Integer.parseInt(getBirthday().split("/")[0]);
		JDF jdf = new JDF();
		int currentYear = jdf.getIranianYear();
		return currentYear - birthYear;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(String.valueOf(weight));
		dest.writeString(String.valueOf(height));
		dest.writeString(String.valueOf(birthday));
		dest.writeString(String.valueOf(sex));
		dest.writeString(String.valueOf(activityLevel));
		dest.writeString(String.valueOf(desiredWeight));
		dest.writeString(String.valueOf(deadline));
	}

	public static final Creator<Person> CREATOR = new Creator<Person>() {
		public Person createFromParcel(Parcel parcel) {
			return new Person(parcel);
		}

		public Person[] newArray(int i) {
			return new Person[0];
		}
	};

	/**
	 * this function calculate the BMR of the user it uses formulas that is on
	 * the link below http://weightloss.about.com/od/eatsmart/a/blcalintake.htm
	 * 
	 * @return the BMR of the user
	 */
	public int getBMR() {
		float temp = 0;
		if (sex == 1) {
			temp = (float) (655 + (9.479866 * weight) + (1.8503947 * height) - (4.7 * getAge()));
		} else {
			temp = (float) (66 + (13.889106 * weight) + (5.0787429 * height) - (6.8 * getAge()));
		}
		return Math.round(temp * getActivityProportion());
	}

	@Override
	public String toString() {
		String temp = "The User has these info";
		return temp;
	}

	public int getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
	};

	private float getActivityProportion() {
		float temp = 1;
		switch (activityLevel) {
		case 1:
			temp = 1.2f;
			break;
		case 2:
			temp = 1.3f;
			break;
		case 3:
			temp = 1.4f;
			break;
		case 4:
			temp = 1.5f;
			break;
		case 5:
			temp = 1.6f;
			break;
		default:
			temp = 1.2f;
			break;
		}
		return temp;
	}

	/**
	 * this function calculate proper calorie for user based on the deadline and
	 * the bmr
	 * 
	 * @return
	 */
	public int getProperCalorie() {
		if (desiredWeight < weight) {
			float difWeight = weight - desiredWeight ;
			//TODO
		}else{
			
		}
		return 0;
	}
}
