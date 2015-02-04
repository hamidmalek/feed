package com.malek.hamid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
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

/**
 * this class is a dialog fragment that uses when user wants to save a food in
 * the log. it has functions that must called before showing this fragment to
 * user. you must set the date that log should saved in it and also the food
 * that should saved; use the setDate and setFood for these.
 * 
 * @author hamid_
 * 
 */
public class addFoodFragment extends DialogFragment implements
		DialogInterface.OnDismissListener {
	// callback
	private OnDBChangedListener mCallback;

	public interface OnDBChangedListener {
		public void onDBChanged();
	}

	public void onDismiss(DialogInterface dialog) {
		mCallback.onDBChanged();
		super.onDismiss(dialog);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnDBChangedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDBChangedListener");
		}
	}

	JDF jdf = new JDF();
	private final SparseArray<Group> foodGroup = new SparseArray<Group>();
	private Food food;
	private boolean isStd;
	/**
	 * date of the log that food should saved in it. it has a default value
	 * which is the current date.
	 */
	private String date = jdf.getIranianDate();
	private TextView foodName;
	private TextView foodCategory;
	/**
	 * it is textView that is showing to user when the food has no specific
	 * unit.
	 */
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

		View rootView = inflater.inflate(R.layout.fragment_add_foods,
				container, false);
		/*
		 * initializing the elements of the fragment
		 */
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
		// defining meals for saving data in datebase
		/**
		 * string array of meals which user must select for saving log in
		 * database
		 */
		String[] mealsArray = getResources()
				.getStringArray(R.array.meals_array);
		ArrayList<String> mealsList = new ArrayList<String>();
		for (String str : mealsArray)
			mealsList.add(str);
		ArrayAdapter<String> mealAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				mealsList);
		mealSelect.setAdapter(mealAdapter);

		/*
		 * setting elements of fragment
		 */
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
		/*
		 * defining onClickListener for save button (insertFood)
		 */
		insertFood.setOnClickListener(new OnClickListener() {

			/*
			 * it checks if user left some fields empty (non-Javadoc)
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
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
					System.out.println("addFoodFragment date  " + date);
					db.insertFoodinUserLog(food.getId(),
							Integer.parseInt(size), jdf.getIranianDate(), date,
							radioStdUnit.isChecked(),
							mealSelect.getSelectedItemPosition());
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
