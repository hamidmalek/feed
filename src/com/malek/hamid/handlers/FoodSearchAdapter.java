package com.malek.hamid.handlers;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.malek.hamid.Food;
import com.malek.hamid.FoodsFragment;
import com.malek.hamid.R;
import com.malek.hamid.addFoodFragment;

public class FoodSearchAdapter extends BaseAdapter {

	private FragmentActivity activity;
	private static LayoutInflater inflater = null;
	private ArrayList<Food> data;
	private String date;

	public FoodSearchAdapter(FragmentActivity a, ArrayList<Food> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.search_results, null);

		TextView foodName = (TextView) vi
				.findViewById(R.id.food_name_in_search); // food
		// name
		TextView foodCategory = (TextView) vi.findViewById(R.id.category); // food
																			// category
		ImageButton addFood = (ImageButton) vi
				.findViewById(R.id.add_food_in_search); // add food button
		Food food = data.get(position);
		addFood.setTag(food);

		foodName.setText(food.getName());
		foodCategory.setText(FoodsFragment.foodCategories.get(food
				.getCategoryId()));
		addFood.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				addFoodFragment dialog = new addFoodFragment();
				dialog.setStyle(DialogFragment.STYLE_NO_TITLE,
						R.style.DialogeStyle);
				dialog.setDate(date);
				dialog.setFood((Food) v.getTag());
				dialog.show(activity.getSupportFragmentManager(), "Hello");
			}
		});
		return vi;
	}

	public void setDataList(ArrayList<Food> foods) {
		data.clear();
		data.addAll(foods);
		notifyDataSetChanged();
	}

	public void setDate(String date) {
		this.date = date;
	}

}
