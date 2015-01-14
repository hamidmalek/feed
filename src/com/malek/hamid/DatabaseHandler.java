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

	// constructor
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO
		setForcedUpgrade(1);
	}

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
	public void setUserInfo(int weight, int height, int sex, String birthDate,
			int activityLevel, int fulfilled) {
		System.out.println("calling setUserInfo -- begin");
		String query = "INSERT INTO " + TABLE_USERINFO + "(" + KEY_WEIGHT + ","
				+ KEY_HEIGHT + "," + KEY_SEX + "," + KEY_BIRTHDATE + ","
				+ KEY_USER_ACTIVITY_LEVEL + "," + KEY_FULFILLED + ") values ("
				+ "'" + weight + "'," + "'" + height + "'," + "'" + sex + "',"
				+ "'" + birthDate + "','" + activityLevel + "'," + "'"
				+ fulfilled + "');";
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
		System.out.println("calling setUserInfo -- end");
	}

	/**
	 * @param person
	 *            : the object of the user that contains the info
	 */
	public void setuserInfo(Person person) {
		int sex = 0;
		if (person.getSex())
			sex = 1;
		setUserInfo(person.getWeight(), person.getHeight(), sex,
				person.getBirthday(), person.getActivityLevel(), 1);
	}

	/**
	 * this function returns true if user filled his information in advance.
	 * 
	 * @return
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
	 * @param logDate
	 *            : the date when the food served
	 * @param isStd
	 *            : true if the unit of the food is standard
	 * @param mealId
	 *            : the id of the meal of the food
	 */
	public void insertFoodinUserLog(int foodId, int size, String dateAdded,
			String logDate, boolean isStd, int mealId) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		int isStdtemp = 0;
		if (isStd)
			isStdtemp = 1;
		// inserting food in nutrition log table
		String query = "INSERT INTO " + TABLE_NUTRITION_LOG + "(" + KEY_SIZE
				+ "," + KEY_DATE_ADDED + "," + KEY_LOG_TABLE_FOOD_ID + ","
				+ KEY_LOG_DATE + "," + KEY_LOG_IS_STD + "," + KEY_MEAL_ID
				+ ") values (" + "'" + size + "'," + "'" + dateAdded + "',"
				+ "'" + foodId + "'," + "'" + logDate + "'," + "'" + isStdtemp
				+ "'," + "'" + mealId + "');";
		db.execSQL(query);
		// updating daily log table
		double energy = 0f;
		double protein = 0f;
		Food food = getFood(foodId);
		if (isStd) {
			energy = food.getCalorieSI() * size;
			protein = food.getStdProtein() * size;
		} else {
			energy = food.getCalorieUnit() * size;
			protein = food.getUnitProtein() * size;
		}
		if (getDayLog(logDate).getEnergy() == -1) {
			System.out.println("HERE");
			query = "INSERT INTO " + TABLE_DAILY_LOG + "(" + KEY_DAILY_LOG_DATE + ","
					+ KEY_DAILY_ENERGY + "," + KEY_DAILY_PROTEIN + ") values ("
					+ "'" + dateAdded + "'," + "'" + energy + "'," + "'"
					+ protein + "');";
		} else {
			System.out.println("THERE");
			DailyLog dayLog = getDayLog(logDate);
			query ="UPDATE " + TABLE_DAILY_LOG +
					" SET " + KEY_DAILY_ENERGY +"=" +(energy+dayLog.getEnergy()) +"," +
					KEY_DAILY_PROTEIN +"="+ (protein+dayLog.getProtein()) +
					" WHERE " +KEY_DAILY_LOG_DATE +"=" +logDate +";"
					;
			System.out.println("P:"+dayLog.getProtein());
			System.out.println("E:"+dayLog.getEnergy());
		}
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
		return foods;
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
		c.moveToFirst();
		int weight = c.getInt(c.getColumnIndex(KEY_WEIGHT));
		int height = c.getInt(c.getColumnIndex(KEY_HEIGHT));
		String birthday = c.getString(c.getColumnIndex(KEY_BIRTHDATE));
		boolean sex = false;
		if (c.getInt(c.getColumnIndex(KEY_SEX)) == 1)
			sex = true;
		Person user = new Person(weight, height, birthday, sex);
		return user;
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
		String query = "SELECT * FROM " + TABLE_NUTRITION_LOG 
				+ " WHERE "
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
			} while (c.moveToNext());
		}else{
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
			nutritionLog.add(new DailyLog(i + 1, -1, 0));
		}
		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String logDate = c.getString(c
						.getColumnIndex(KEY_DAILY_LOG_DATE));
				int energy = c.getInt(c.getColumnIndex(KEY_DAILY_ENERGY));
				int protein = c.getInt(c.getColumnIndex(KEY_DAILY_PROTEIN));
				int day = Integer.parseInt(logDate.split("/")[2]);
				nutritionLog.set(day, new DailyLog(day, energy, protein));
			} while (c.moveToNext());
		}
		return nutritionLog;
	}

	/**
	 * 
	 * @param date
	 *            : string of log date
	 * @return an array list of logs with the given date
	 */
	public DailyLog getDayLog(String date) {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_DAILY_LOG + " WHERE "
				+ KEY_DAILY_LOG_DATE + " = '" + date + "'";
		Cursor c = db.rawQuery(query, null);
		DailyLog d;
		if (c.getCount() != 0) {
			c.moveToFirst();
			String logDate = c.getString(c.getColumnIndex(KEY_DAILY_LOG_DATE));
			int energy = c.getInt(c.getColumnIndex(KEY_DAILY_ENERGY));
			int protein = c.getInt(c.getColumnIndex(KEY_DAILY_PROTEIN));
			int day = Integer.parseInt(logDate.split("/")[2]);
			d = new DailyLog(day, energy, protein);
		} else {
			d = new DailyLog(Integer.parseInt(date.split("/")[2]), -1, -1);
		}
		c.close();
		db.close();
		return d;
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
			return new Food(id, name, calorie, unitCalorie, unitId, categoryId);
		} else {
			return null;
		}
	}

	public int getProperEnergy() {
		// TODO Auto-generated method stub
		return 80;
	}
}
