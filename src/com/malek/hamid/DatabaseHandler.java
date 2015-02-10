package com.malek.hamid;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.malek.hamid.calendar.ShamsiDate;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHandler extends SQLiteAssetHelper {

	// database version
	private static final int DATABASE_VERSION = 1;

	// database name
	private static final String DATABASE_NAME = "foodsv1";

	// tables name
	private static final String TABLE_FOOD = "foods";
	private static final String TABLE_USERINFO = "userInfo";
	private static final String TABLE_NUTRITION_LOG = "userNutritionLog";
	private static final String TABLE_FOOD_CATEGORIES = "foodCategories";
	private static final String TABLE_ACTIVITY_LEVEL = "activityLevels";
	private static final String TABLE_FOOD_UNITS = "foodUnits";
	private static final String TABLE_DAILY_LOG = "dailyNutritionLog";
	private static final String TABLE_APP_SETTING = "appSetting";

	// foods table columns names
	private static final String KEY_FOOD_NAME = "FoodName";
	private static final String KEY_FOOD_ID_FOOD_TABLE = "FoodId";
	private static final String KEY_STD_ENERGY = "StdEnergy";
	private static final String KEY_STD_PROTEIN = "StdProtein";
	private static final String KEY_SEC_UNIT_ID = "SecUnitId";
	private static final String KEY_UNIT_ENERGY = "UnitEnergy";
	private static final String KEY_UNIT_PROTEIN = "UnitProtein";
	private static final String KEY_CATEGORY_ID = "CategoryId";
	private static final int FOOD_RESULT_LIMIT = 10;

	private static final String KEY_CATEGORY = "Category";
	// userInfo table columns names
	private static final String KEY_SEX = "Sex";
	private static final String KEY_WEIGHT = "Weight";
	private static final String KEY_HEIGHT = "Height";
	private static final String KEY_BIRTHDATE = "BirthDate";
	private static final String KEY_FULFILLED = "Fulfilled";
	private static final String KEY_USER_ACTIVITY_LEVEL = "ActivityLevelId";
	private static final String KEY_USER_GOAL_WEIGHT = "GoalWeight";
	private static final String KEY_USER_GOAL_DEADLINE = "GoalDeadline";

	// user nutrition log table columns names
	private static final String KEY_LOG_TABLE_FOOD_ID = "FoodId";
	private static final String KEY_DATE_ADDED = "DateAdded";
	private static final String KEY_SIZE = "size";
	private static final String KEY_LOG_ID = "LogId";
	private static final String KEY_LOG_IS_STD = "LogIsStd";
	private static final String KEY_LOG_DATE = "LogDate";
	private static final String KEY_MEAL_ID = "MealId";

	// activity level table columns names
	private static final String KEY_ACTIVITY_LEVEL_ID = "ActLeId";
	private static final String KEY_ACTIVITY_LEVEL_DESCRIPTION = "ActLevDesc";
	private static final String KEY_ACTIVITY_LEVEL_PROPORTION = "ActLevProp";

	// food units table columns names
	private static final String KEY_UNIT_PROPORTION = "UnitProportion";
	private static final String KEY_UNIT_ID = "UnitId";
	private static final String KEY_UNIT_NAME = "UnitName";

	// user daily nutrition log
	private static final String KEY_DAILY_PROTEIN = "DailyProtein";
	private static final String KEY_DAILY_ENERGY = "DailyEnergy";
	private static final String KEY_DAILY_LOG_DATE = "Date";
	private static final String KEY_DAILY_LOG_STATUS = "Status";

	// app setting columns
	private static final String KEY_SHOW_STAT = "ShowStat";

	// constructor
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO
		setForcedUpgrade(1);
	}

	// //////////////////// USER FUNCTIONS /////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * 
	 * this function insert the information about user into database
	 * 
	 * @param weight
	 *            : the unit is centimeter
	 * @param height
	 *            : the unit is kilogram
	 * @param sex
	 *            : 0 means male and 1 means female
	 * @param birthDate
	 *            : birth date should be in format yyyy/mm/dd
	 * 
	 */
	public void setUserInfo(float weight, int height, int sex,
			String birthDate, int activityLevel, int fulfilled) {
		System.out.println("calling setUserInfo -- begin");
		if (getUser() == null) {
			SQLiteDatabase db = this.getWritableDatabase();
			System.out.println("user is null");
			String query = "INSERT INTO " + TABLE_USERINFO + "(" + KEY_WEIGHT
					+ "," + KEY_HEIGHT + "," + KEY_SEX + "," + KEY_BIRTHDATE
					+ "," + KEY_USER_ACTIVITY_LEVEL + "," + KEY_FULFILLED + ","
					+ KEY_USER_GOAL_WEIGHT + ") values (" + "'" + weight + "',"
					+ "'" + height + "'," + "'" + sex + "'," + "'" + birthDate
					+ "','" + activityLevel + "'," + "'" + fulfilled + "',"
					+ "'" + weight + "');";
			db.execSQL(query);
			db.close();
		} else {
			SQLiteDatabase db = this.getWritableDatabase();
			System.out.println("user is not null");
			String query = "UPDATE " + TABLE_USERINFO + " SET " + KEY_WEIGHT
					+ "=" + weight + "," + KEY_HEIGHT + "=" + height + ","
					+ KEY_BIRTHDATE + "=" + birthDate + ","
					+ KEY_USER_ACTIVITY_LEVEL + "=" + activityLevel + ","
					+ KEY_FULFILLED + "=" + fulfilled + "," + KEY_SEX + "="
					+ sex + ";";
			db.execSQL(query);
			db.close();
		}
		System.out.println("calling setUserInfo -- end");
	}

	/**
	 * this function modifies user's weight
	 * 
	 * @param weight
	 *            : the proper weight of user
	 */
	public void updateUserWeight(float weight) {
		if (getUser() != null) {
			String query = "UPDATE " + TABLE_USERINFO + " SET " + KEY_WEIGHT
					+ "=" + weight + ";";
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * this function changes user height
	 * 
	 * @param height
	 *            : proper height
	 */
	public void updateUserHeight(int height) {
		if (getUser() != null) {
			String query = "UPDATE " + TABLE_USERINFO + " SET " + KEY_HEIGHT
					+ "=" + height + ";";
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * this function modifies user's sex
	 * 
	 * @param sex
	 *            : 0 means male and 1 means female
	 */
	public void updateUserSex(int sex) {
		if (getUser() != null) {
			String query = "UPDATE " + TABLE_USERINFO + " SET " + KEY_SEX + "="
					+ sex + ";";
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * this function modifies user's goal weight
	 * 
	 * @param goalWeight
	 *            : proper goal weight for user
	 */
	public void updateUserGoal(float goalWeight) {
		if (getUser() != null) {
			String query = "UPDATE " + TABLE_USERINFO + " SET "
					+ KEY_USER_GOAL_WEIGHT + "=" + goalWeight + ";";
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * this function modifies user's birth date
	 * 
	 * @param birthDate
	 *            : the proper birth date that should be in format yyyy/mm/dd
	 */
	public void updateUserBirthDate(String birthDate) {
		if (getUser() != null) {
			String query = "UPDATE " + TABLE_USERINFO + " SET " + KEY_BIRTHDATE
					+ "=" + birthDate + ";";
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * this function modifies user's goal deadline
	 * 
	 * @param deadLine
	 *            : the proper deadline that should be in format yyyy/mm/dd
	 */
	public void updateUserDeadLine(String deadLine) {
		if (getUser() != null) {
			String query = "UPDATE " + TABLE_USERINFO + " SET "
					+ KEY_USER_GOAL_DEADLINE + "='" + deadLine + "';";
			System.out.println(query);
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * this function modifies user's activity level
	 * 
	 * @param deadLine
	 *            : the proper deadline that should be in format yyyy/mm/dd
	 */
	public void updateUserActivityLevel(int activityLevel) {
		if (getUser() != null) {
			String query = "UPDATE " + TABLE_USERINFO + " SET "
					+ KEY_USER_ACTIVITY_LEVEL + "=" + activityLevel + ";";
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * @param person
	 *            : the object of the user that contains the info
	 */
	public void setuserInfo(Person person) {
		setUserInfo(person.getWeight(), person.getHeight(), person.getSex(),
				person.getBirthday(), person.getActivityLevel(), 1);
	}

	/**
	 * this function returns true if user filled his information in advance.
	 * 
	 * @return true if user information is fulfilled
	 */
	public boolean getUserFulfilled() {
		String query = "SELECT " + KEY_FULFILLED + " FROM " + TABLE_USERINFO;
		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("first call of database --- getUserFulFilled");
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			System.out.println("users:" + c.getCount());
			c.moveToFirst();
			if (c.getString(0).equals("1")) {
				c.close();
				db.close();
				return true;
			} else {
				c.close();
				db.close();
				return false;
			}
		} else {
			c.close();
			db.close();
			return false;
		}
	}

	/**
	 * this function return the user of the application
	 * 
	 * @return Person: the user as a Person object
	 */
	public Person getUser() {
		String query = "SELECT * FROM " + TABLE_USERINFO;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		if (c.moveToFirst()) {
			float weight = c.getFloat(c.getColumnIndex(KEY_WEIGHT));
			int height = c.getInt(c.getColumnIndex(KEY_HEIGHT));
			String birthday = c.getString(c.getColumnIndex(KEY_BIRTHDATE));
			int sex = c.getInt(c.getColumnIndex(KEY_SEX));
			int activityLevel = c.getInt(c
					.getColumnIndex(KEY_USER_ACTIVITY_LEVEL));
			int goalWeight = c.getInt(c.getColumnIndex(KEY_USER_GOAL_WEIGHT));
			String goalDeadline = c.getString(c
					.getColumnIndex(KEY_USER_GOAL_DEADLINE));
			Person user = new Person(weight, height, birthday, sex);
			user.setDesiredWeight(goalWeight);
			System.out.println("1---" + goalDeadline);
			user.setDeadline(goalDeadline);
			System.out.println("2----" + user.getDeadline());
			user.setActivityLevel(activityLevel);
			c.close();
			db.close();
			return user;
		} else {
			db.close();
			return null;
		}
	}

	// //////////////////// FOOD FUNCTIONS /////////////////////////
	// //////////////////////////////////////////////////////////////

	public ArrayList<String> getData() {
		ArrayList<String> ret = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + TABLE_FOOD;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ret.add(cursor.getString(cursor.getColumnIndex(KEY_FOOD_NAME)));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return ret;
	}

	/**
	 * this function returns the list of foods in an array list
	 * 
	 * @return
	 */
	public ArrayList<Food> getFoodList() {
		ArrayList<Food> foods = new ArrayList<Food>();
		String query = "SELECT * FROM " + TABLE_FOOD;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String name = c.getString(c.getColumnIndex(KEY_FOOD_NAME));
				// int id = c.getInt(c.getColumnIndex(KEY_FOOD_ID_FOOD_TABLE));
				String unit = c.getString(c.getColumnIndex(KEY_SEC_UNIT_ID));
				int energy = c.getInt(c.getColumnIndex(KEY_UNIT_ENERGY));
				// String siUnit = c.getString(c.getColumnIndex(KEY_SIUNIT));
				int siEnergy = c.getInt(c.getColumnIndex(KEY_STD_ENERGY));
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return foods;
	}

	/**
	 * insert food to database of user food log
	 * 
	 * @param foodId
	 *            : food is used for its id
	 * @param size
	 *            : size is used to calculate calorie
	 * @param dateAdded
	 *            : the time when food added to database
	 * @param dateServed
	 *            : the date when the food served
	 * @param isStd
	 *            : true if the unit of the food is standard
	 * @param mealId
	 *            : the id of the meal of the food
	 */
	public void insertFoodinUserLog(int foodId, int size, String dateAdded,
			String dateServed, boolean isStd, int mealId) {
		SQLiteDatabase db = this.getWritableDatabase();

		int isStdtemp = 0;
		if (isStd)
			isStdtemp = 1;
		// inserting food in nutrition log table
		String query = "INSERT INTO " + TABLE_NUTRITION_LOG + "(" + KEY_SIZE
				+ "," + KEY_DATE_ADDED + "," + KEY_LOG_TABLE_FOOD_ID + ","
				+ KEY_LOG_DATE + "," + KEY_LOG_IS_STD + "," + KEY_MEAL_ID
				+ ") values (" + "'" + size + "'," + "'" + dateAdded + "',"
				+ "'" + foodId + "'," + "'" + dateServed + "'," + "'"
				+ isStdtemp + "'," + "'" + mealId + "');";

		System.out.println("+++++++++++++++++++++");
		System.out.println(query);
		System.out.println("+++++++++++++++++++++");
		db.execSQL(query);
		// updating daily log table
		double energy = 0f;
		double protein = 0f;
		Food food = getFood(foodId);
		if (isStd) {
			energy = food.getCalorieSI() * size / 100;
			protein = food.getStdProtein() * size / 100;
		} else {
			energy = food.getCalorieUnit() * size;
			protein = food.getUnitProtein() * size;
		}
		System.out.println("logDate" + dateServed);
		if (getDayLog(dateServed).getEnergy() == -1) {
			System.out.println("HERE");
			query = "INSERT INTO " + TABLE_DAILY_LOG + "(" + KEY_DAILY_LOG_DATE
					+ "," + KEY_DAILY_ENERGY + "," + KEY_DAILY_PROTEIN
					+ ") values (" + "'" + dateServed + "'," + "'" + energy
					+ "'," + "'" + protein + "');";
		} else {
			System.out.println("THERE");
			DailyLog dayLog = getDayLog(dateServed);
			query = "UPDATE " + TABLE_DAILY_LOG + " SET " + KEY_DAILY_ENERGY
					+ " = " + (energy + dayLog.getEnergy()) + ","
					+ KEY_DAILY_PROTEIN + "=" + (protein + dayLog.getProtein())
					+ " WHERE " + KEY_DAILY_LOG_DATE + "= '" + dateServed
					+ "';";
		}
		System.out
				.println("query of inserting data in daily log or updating data");
		System.out.println(query);
		db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public ArrayList<String> getFoodList(String query) {
		ArrayList<String> temp = new ArrayList<String>();
		String str = "SELECT * FROM " + TABLE_FOOD + " WHERE " + KEY_FOOD_NAME
				+ " LIKE '%" + query + "%' LIMIT " + FOOD_RESULT_LIMIT;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(str, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String name = c.getString(c.getColumnIndex(KEY_FOOD_NAME));
				// int id = c.getInt(c.getColumnIndex(KEY_FOOD_ID_FOOD_TABLE));
				// String unit = c.getString(c.getColumnIndex(KEY_UNIT));
				// int energy = c.getInt(c.getColumnIndex(KEY_ENERGY));
				// String siUnit = c.getString(c.getColumnIndex(KEY_SIUNIT));
				// int siEnergy = c.getInt(c.getColumnIndex(KEY_SIENERGY));
				// System.out.println(name + unit + energy + siEnergy + siUnit);
				temp.add(name);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return temp;
	}

	/**
	 * this function returns the foods their names are similar to the input
	 * query
	 * 
	 * @param query
	 *            : the string that is searched for the foods
	 * @return ArrayList<Food> : foods are returned in an array list of food
	 *         objects
	 */
	public ArrayList<Food> getFoodObjectList(String query) {
		query = query.trim();
		ArrayList<Food> temp = new ArrayList<Food>();
		String str = "SELECT * FROM " + TABLE_FOOD + " WHERE " + KEY_FOOD_NAME
				+ " LIKE '%" + query + "%' LIMIT " + FOOD_RESULT_LIMIT;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(str, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String name = c.getString(c.getColumnIndex(KEY_FOOD_NAME));
				int id = c.getInt(c.getColumnIndex(KEY_FOOD_ID_FOOD_TABLE));
				int energy = c.getInt(c.getColumnIndex(KEY_UNIT_ENERGY));
				int unitId = c.getInt(c.getColumnIndex(KEY_SEC_UNIT_ID));
				int categoryId = c.getInt(c.getColumnIndex(KEY_CATEGORY_ID));
				int unitCalorie = c.getInt(c.getColumnIndex(KEY_UNIT_ENERGY));
				temp.add(new Food(id, name, energy, unitCalorie, unitId,
						categoryId));
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return temp;
	}

	/**
	 * this function returns the categories of the foods
	 * 
	 * @return String of food Categories
	 */
	public HashMap<Integer, String> getFoodCategories() {
		SQLiteDatabase db = this.getWritableDatabase();
		HashMap<Integer, String> foodCategories = new HashMap<Integer, String>();
		String query = "SELECT * FROM " + TABLE_FOOD_CATEGORIES;
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String name = c.getString(c.getColumnIndex(KEY_CATEGORY));
				int id = c.getInt(c.getColumnIndex(KEY_CATEGORY_ID));
				foodCategories.put(id, name);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return foodCategories;
	}

	/**
	 * this function returns the foods in a specific category
	 * 
	 * @param categoryId
	 * @return Array List of foods
	 */
	public ArrayList<Food> getFoods(int categoryId) {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<Food> foods = new ArrayList<Food>();
		String query = "SELECT * FROM " + TABLE_FOOD + " WHERE "
				+ KEY_CATEGORY_ID + "=" + categoryId;
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				int id = c.getInt(c.getColumnIndex(KEY_FOOD_ID_FOOD_TABLE));
				String name = c.getString(c.getColumnIndex(KEY_FOOD_NAME));
				int calorie = c.getInt(c.getColumnIndex(KEY_STD_ENERGY));
				int unitId = c.getInt(c.getColumnIndex(KEY_SEC_UNIT_ID));
				int unitCalorie = c.getInt(c.getColumnIndex(KEY_UNIT_ENERGY));
				foods.add(new Food(id, name, calorie, unitCalorie, unitId,
						categoryId));
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return foods;
	}

	/**
	 * this function reads units from data base
	 * 
	 * @return HashMap: contains the list of food units
	 */
	public HashMap<Integer, String> getFoodUnits() {
		SQLiteDatabase db = this.getWritableDatabase();
		HashMap<Integer, String> foodUnits = new HashMap<Integer, String>();
		String query = "SELECT * FROM " + TABLE_FOOD_UNITS;
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String name = c.getString(c.getColumnIndex(KEY_UNIT_NAME));
				int id = c.getInt(c.getColumnIndex(KEY_UNIT_ID));
				foodUnits.put(id, name);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return foodUnits;
	}

	/**
	 * 
	 * @param date
	 *            : string of log date
	 * @return an array list of logs with the given date
	 */
	public ArrayList<Log> getUserNutLog(String date) {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<Log> nutritionLog = new ArrayList<Log>();
		String query = "SELECT * FROM " + TABLE_NUTRITION_LOG + " WHERE "
				+ KEY_LOG_DATE + "= '" + date + "'";
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				int id = c.getInt(c.getColumnIndex(KEY_LOG_ID));
				String dateAdded = c
						.getString(c.getColumnIndex(KEY_DATE_ADDED));
				String dateServed = c.getString(c.getColumnIndex(KEY_LOG_DATE));
				int size = c.getInt(c.getColumnIndex(KEY_SIZE));
				boolean isStd = true;
				if (c.getInt(c.getColumnIndex(KEY_LOG_IS_STD)) == 0)
					isStd = false;
				int foodId = c.getInt(c.getColumnIndex(KEY_LOG_TABLE_FOOD_ID));
				int mealId = c.getInt(c.getColumnIndex(KEY_MEAL_ID));
				nutritionLog.add(new Log(id, dateAdded, dateServed, size,
						isStd, foodId, mealId));
				System.out.println(id + "::" + size + "=" + dateServed);
			} while (c.moveToNext());
		} else {
		}
		c.close();
		db.close();
		return nutritionLog;
	}

	/**
	 * 
	 * @param date
	 *            : string of log date
	 * @return an array list of logs with the given date
	 */
	public ArrayList<DailyLog> getMonthLog(ShamsiDate date) {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<DailyLog> nutritionLog = new ArrayList<DailyLog>();
		String query = "SELECT * FROM " + TABLE_DAILY_LOG + " WHERE "
				+ KEY_DAILY_LOG_DATE + " LIKE '" + date + "%'";
		Cursor c = db.rawQuery(query, null);
		for (int i = 0; i < 32; i++) {
			nutritionLog.add(new DailyLog(i + 1, -1, 0, -1));
		}
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String logDate = c.getString(c
						.getColumnIndex(KEY_DAILY_LOG_DATE));
				int energy = c.getInt(c.getColumnIndex(KEY_DAILY_ENERGY));
				int protein = c.getInt(c.getColumnIndex(KEY_DAILY_PROTEIN));
				int day = Integer.parseInt(logDate.split("/")[2]);
				int status = c.getInt(c.getColumnIndex(KEY_DAILY_LOG_STATUS));
				nutritionLog.set(day,
						new DailyLog(day, energy, protein, status));
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return nutritionLog;
	}

	/**
	 * this function returns the food with a specific id.
	 * 
	 * @param foodId
	 * @return Food :with the given food id, null if the food does not exists
	 */
	public Food getFood(int foodId) {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_FOOD + " WHERE "
				+ KEY_FOOD_ID_FOOD_TABLE + "=" + foodId;
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			int id = c.getInt(c.getColumnIndex(KEY_FOOD_ID_FOOD_TABLE));
			String name = c.getString(c.getColumnIndex(KEY_FOOD_NAME));
			int calorie = c.getInt(c.getColumnIndex(KEY_STD_ENERGY));
			int unitId = c.getInt(c.getColumnIndex(KEY_SEC_UNIT_ID));
			int unitCalorie = c.getInt(c.getColumnIndex(KEY_UNIT_ENERGY));
			int categoryId = c.getInt(c.getColumnIndex(KEY_CATEGORY_ID));
			c.close();
			db.close();
			return new Food(id, name, calorie, unitCalorie, unitId, categoryId);
		} else {
			db.close();
			return null;
		}
	}

	public int getProperEnergy() {
		// TODO Auto-generated method stub
		Person user = getUser();
		float weight = user.getWeight();
		float goal = user.getDesiredWeight();
		float dif = goal - weight;
		int days = 0;
		JDF now = new JDF();
		String deadline = user.getDeadline();

		if (deadline != null) {
			while (!now.getIranianDate().matches(deadline)) {
				days++;
				now.nextDay();
				if (days > 100)
					break;
			}
			int calorieToLose = (int) dif * 7000;
			return user.getBMR() + (calorieToLose / days);
		} else {
			return user.getBMR();
		}
	}

	// ////////// DAILY LOG FUNCTIONS //////////////////////////////
	// /////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param date
	 *            : string of log date
	 * @return an array list of logs with the given date
	 */
	public DailyLog getDayLog(String date) {
		System.out.println("========= getDayLog invoked");
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_DAILY_LOG + " WHERE "
				+ KEY_DAILY_LOG_DATE + " = '" + date + "'";
		System.out.println(query);
		Cursor c = db.rawQuery(query, null);
		DailyLog d;
		if (c.getCount() != 0) {
			c.moveToFirst();
			int energy = 0;
			int protein = 0;
			String logDate = c.getString(c.getColumnIndex(KEY_DAILY_LOG_DATE));
			int day = Integer.parseInt(logDate.split("/")[2]);
			do {
				energy += c.getInt(c.getColumnIndex(KEY_DAILY_ENERGY));
				protein += c.getInt(c.getColumnIndex(KEY_DAILY_PROTEIN));
			} while (c.moveToNext());
			d = new DailyLog(day, energy, protein, -1);
		} else {
			d = new DailyLog(Integer.parseInt(date.split("/")[2]), -1, -1, -1);
		}
		System.out.println(d.getDay() + "::" + d.getEnergy() + "--"
				+ d.getProtein());
		c.close();
		db.close();
		System.out.println("========= end of getDayLog");
		return d;
	}

	// ////////// APP SETTING FUNCTIONS ////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * this function reads the proper column in setting table to see if the user
	 * wants to see his or her statistics
	 * 
	 * @return 1 if user want to see his or her statistics and otherwise 0
	 */
	public boolean getShowStat() {
		String query = "SELECT * FROM " + TABLE_APP_SETTING;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			int show = c.getInt(c.getColumnIndex(KEY_SHOW_STAT));
			c.close();
			db.close();
			if (show == 1) {
				return true;
			} else {
				return false;
			}
		} else {
			c.close();
			db.close();
			return true;
		}

	}

	/**
	 * this function sets the user preference to see the stat or not
	 * 
	 * @param show
	 *            : if it's true user wants to see the stat page again and
	 *            otherwise false
	 */
	public void setShowStat(boolean show) {
		int temp = 0;
		if (show)
			temp = 1;
		String query = "UPDATE " + TABLE_APP_SETTING + " SET " + KEY_SHOW_STAT
				+ " = " + temp + " ; ";
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
	}

	/**
	 * this function insert a new food into food tables
	 * @param food
	 *            : the food that should be added to foods table
	 */
	public void addNewFood(Food food) {
		String query = "INSERT INTO " + TABLE_FOOD + "(" + KEY_FOOD_NAME + ","
				+ KEY_CATEGORY_ID + "," + KEY_STD_ENERGY + ","
				+ KEY_SEC_UNIT_ID + "," + KEY_UNIT_ENERGY + ") values (" + "'"
				+ food.getName() + "'," + "'" + food.getCategoryId() + "',"
				+ "'" + food.getCalorieSI() + "'," + "'" + food.getUnit()
				+ "'," + "'" + food.getCalorieUnit() + "');";

		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
	}
}
