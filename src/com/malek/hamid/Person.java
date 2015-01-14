package com.malek.hamid;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	/*
	 * weight of the user in kilograms
	 */
	private int weight;
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
	private boolean sex;
	/*
	 * user desired weight
	 */
	private int desiredWeight;
	/*
	 * user activity level
	 */
	private int activityLevel;
	/*
	 * user deadline for desired weight
	 */
	private String deadline;

	public int getDesiredWeight() {
		return desiredWeight;
	}

	public void setDesiredWeight(int desiredWeight) {
		this.desiredWeight = desiredWeight;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
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

	public Person(int weight, int height, String birthday, boolean sex) {
		super();
		this.weight = weight;
		this.height = height;
		this.birthday = birthday;
		this.setSex(sex);
	}

	public Person(Parcel parcel) {
		weight = Integer.parseInt(parcel.readString());
		height = Integer.parseInt(parcel.readString());
		birthday = parcel.readString();
		if (parcel.readString() == "1")
			sex = true;
		else
			sex = false;
		activityLevel = Integer.parseInt(parcel.readString());
	}

	/**
	 * this method returns the sex of the person if its true the person is
	 * female, otherwise male
	 * 
	 * @return boolean
	 */
	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
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
		if (sex)
			dest.writeString("1");
		else
			dest.writeString("0");
		dest.writeString(String.valueOf(activityLevel));
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
		if (sex) {
			temp = (float) (655 + (9.479866 * weight) + (1.8503947 * height) - (4.7 * getAge()));
		} else {
			temp = (float) (66 + (13.889106 * weight) + (5.0787429 * height) - (6.8 * getAge()));
		}
		return Math.round(temp*getActivityProportion());
	}

	@Override
	public String toString() {
		String temp = "The User has these info";
		String gender = !sex ? "his" : "her";
		temp += " , " + gender + " weight is " + weight;
		temp += " , " + gender + " height is " + height;
		gender = sex ? "she " : "he ";
		temp += ", and " + gender + "is " + getAge() + " years old. ";
		temp += gender + " should use " + getBMR() * getActivityLevel()
				+ " kcal everyday to keep weight.";
		return temp;
	}

	public int getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
	};
	
	private float getActivityProportion(){
		float temp = 1;
		switch (activityLevel) {
		case 1:
			temp = 1.2f;
			break;
		case 2:
			temp = 1.2f;
			break;
		case 3:
			temp = 1.2f;
			break;
		case 4:
			temp = 1.2f;
			break;
		case 5:
			temp = 1.2f;
			break;
		default:
			break;
		}
		return temp;
	}
}
