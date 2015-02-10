package com.malek.hamid;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewFoodActivity extends Activity {
	// -- Form Elements --------------------------
	private DatabaseHandler db;
	private EditText newFoodName;
	private Spinner foodCategoriesSpinner;
	private Spinner foodUnitsSpinner;
	private EditText foodStdCalorie;
	private EditText food2ndUnitCalorie;
	private Button saveFood;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_new_food);
		db = new DatabaseHandler(getApplicationContext());

		// -- Form Elements Initialization ---------------
		newFoodName = (EditText) findViewById(R.id.new_food_name);
		foodCategoriesSpinner = (Spinner) findViewById(R.id.new_food_category);
		foodStdCalorie = (EditText) findViewById(R.id.new_food_std_calorie);
		foodUnitsSpinner = (Spinner) findViewById(R.id.new_food_2nd_unit);
		food2ndUnitCalorie = (EditText) findViewById(R.id.new_food_2nd_unit_calorie);
		saveFood = (Button) findViewById(R.id.add_new_food_button);
		ArrayList<String> categoriesArray = new ArrayList<String>();
		HashMap<Integer, String> foodCategoriesHashmap = db.getFoodCategories();
		for (int i = 1; i <= foodCategoriesHashmap.size(); i++) {
			categoriesArray.add(foodCategoriesHashmap.get(i));
			System.out.println("CAT:" + categoriesArray.get(i - 1));
		}

		ArrayAdapter<String> foodCategoriesAdapter = new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line, categoriesArray);
		foodCategoriesSpinner.setAdapter(foodCategoriesAdapter);

		// //////////////////////
		HashMap<Integer, String> foodUnitsMap = db.getFoodUnits();
		ArrayList<String> foodUnitsArray = new ArrayList<String>();
		System.out.println(foodUnitsMap.size());
		for (int i = 1; i <= foodUnitsMap.size(); i++) {
			foodUnitsArray.add(foodUnitsMap.get(i));
			System.out.println("UNIT:" + foodUnitsArray);
		}

		ArrayAdapter<String> foodUnitsAdapter = new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line, foodUnitsArray);
		foodUnitsSpinner.setAdapter(foodUnitsAdapter);

		saveFood.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String foodName = newFoodName.getText().toString();
				int foodCategory = foodCategoriesSpinner
						.getSelectedItemPosition() + 1;
				int food2ndUnit = foodUnitsSpinner.getSelectedItemPosition() + 1;
				int stdCalorie = Integer.parseInt(foodStdCalorie.getText()
						.toString());
				int secondUnitCalorie = Integer.parseInt(food2ndUnitCalorie
						.getText().toString());
				Food food = new Food(-1, foodName, stdCalorie,
						secondUnitCalorie, food2ndUnit, foodCategory);
				db.addNewFood(food);
			}
		});
	}
}