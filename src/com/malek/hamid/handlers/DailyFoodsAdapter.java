package com.malek.hamid.handlers;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.malek.hamid.DatabaseHandler;
import com.malek.hamid.Food;
import com.malek.hamid.FoodsFragment;
import com.malek.hamid.Log;
import com.malek.hamid.R;
import com.malek.hamid.addFoodFragment;

public class DailyFoodsAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;
	private ArrayList<Log> data;

	public DailyFoodsAdapter(Activity a, ArrayList<Log> d) {
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
		DatabaseHandler db = new DatabaseHandler(activity);
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.daily_log_row, null);
		
		TextView foodName = (TextView) vi.findViewById(R.id.food_name); // food name 
		TextView foodSize = (TextView) vi.findViewById(R.id.food_size); //food size
		TextView foodUnit = (TextView) vi.findViewById(R.id.food_unit); // food unit
		
		Log log = data.get(position);

		foodName.setText(db.getFood(log.getFoodId()).getName());
		foodSize.setText(log.getSize()+"");
		foodUnit.setText("dddd");
		return vi;
	}

	public void setDataList(ArrayList<Food> foods) {
		data.clear();
		notifyDataSetChanged();
	}

}
