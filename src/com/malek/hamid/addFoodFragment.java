package com.malek.hamid;

import java.util.ArrayList;

import android.R.anim;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.malek.hamid.handlers.Group;

public class addFoodFragment extends DialogFragment {

	JDF jdf = new JDF();
	private final SparseArray<Group> foodGroup = new SparseArray<Group>();
	private Food food;
	private boolean isStd;
	private String date = jdf.getIranianDate();
	private TextView foodName;
	private TextView foodCategory;
	private TextView unitNoRadio;
	private EditText foodSize;
	private TextView foodSecUnit;
	private RadioGroup radioUnit;
	private RadioButton radioStdUnit;
	private RadioButton radioSecUnit;
	private Button insertFood;
	private Spinner mealSelect;
	boolean flag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_add_foods_2,
				container, false);
		foodName = (TextView) rootView.findViewById(R.id.add_page_food_name);
		foodCategory = (TextView) rootView
				.findViewById(R.id.add_page_food_category);
		insertFood = (Button) rootView.findViewById(R.id.insert_food_to_log);
		foodSize = (EditText) rootView.findViewById(R.id.unit_size_edit);
		radioStdUnit = (RadioButton) rootView.findViewById(R.id.radio_std_unit);
		radioSecUnit = (RadioButton) rootView.findViewById(R.id.radio_sec_unit);
		radioUnit = (RadioGroup) rootView.findViewById(R.id.radio_unit);
		unitNoRadio = (TextView) rootView.findViewById(R.id.unit_no_radio);
		mealSelect = (Spinner) rootView.findViewById(R.id.meal_id);

		String[] temp = getResources().getStringArray(R.array.meals_array);
		ArrayList<String> templist = new ArrayList<String>();
		for (String str : temp)
			templist.add(str);
		ArrayAdapter<String> mealadapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				templist);
		mealSelect.setAdapter(mealadapter);

		foodName.setText(food.getName());
		foodCategory.setText(FoodsFragment.foodCategories.get(food
				.getCategoryId()));
		if (food.getUnit() == 0) {
			radioUnit.setVisibility(View.GONE);
			unitNoRadio.setVisibility(View.VISIBLE);
			flag = false;
		} else {
			radioSecUnit.setText(FoodsFragment.foodUnits.get(food.getUnit()));
			flag = true;
		}
		insertFood.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DatabaseHandler db = new DatabaseHandler(getActivity());
				String size = foodSize.getText().toString();
				if (size.matches("")) {
					Toast.makeText(getActivity(), "Type The food Size", 2000)
							.show();
				} else if (radioUnit.getCheckedRadioButtonId() == -1 && flag) {
					Toast.makeText(getActivity(), "please select a food unit",
							2000).show();
				} else {
					db.insertFoodinUserLog(food.getId(),
							Integer.parseInt(size), jdf.getIranianDate(), date,
							radioStdUnit.isChecked(),mealSelect.getSelectedItemPosition());
					dismiss();
				}
			}

		});
		return rootView;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setIsStd(boolean isStd) {
		this.isStd = isStd;
	}

}
