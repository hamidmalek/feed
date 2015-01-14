package com.malek.hamid.calendar;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.malek.hamid.DailyLogActivity;
import com.malek.hamid.MainActivity;
import com.malek.hamid.R;

/**
 * Customize the monthDays gridView
 */
public class MonthDaysArrayAdapter extends ArrayAdapter<CalendarCell> {
	public static int textColor = Color.LTGRAY;
	private Context context;
	private ArrayList<CalendarCell> monthLog ;
	
	public MonthDaysArrayAdapter(Context context, int textViewResourceId,
			List<CalendarCell> objects) {
		super(context, textViewResourceId, objects);
		this.monthLog = (ArrayList<CalendarCell>) objects;
		this.context = context;
	}

	// To prevent cell highlighted when clicked
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	// Set color to gray and text size to 12sp
	@SuppressLint("ResourceAsColor") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cellView = convertView;

		// For reuse
		if (convertView == null) {
			cellView = inflater.inflate(R.layout.custom_cell, null);
		}


		TextView tv1 = (TextView) cellView.findViewById(R.id.tv1);

		tv1.setTextColor(Color.BLACK);

		// Get dateTime of this cell
		cellView.setBackgroundColor(monthLog.get(position).getCellColor());
		if(monthLog.get(position).getCellColor() == Color.BLACK)
			tv1.setTextColor(Color.WHITE);
		
			

		tv1.setText("" + monthLog.get(position).getDay());
		if(monthLog.get(position).getCellColor() == Color.MAGENTA){
			tv1.setText("");
			cellView.setBackgroundColor(R.color.fragment_background);
		}
		cellView.setTag(monthLog.get(position).getDate());
		cellView.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(context, (CharSequence) v.getTag(), 10000).show();
				Intent intent = new Intent(getContext(), DailyLogActivity.class);
				intent.putExtra("date",(CharSequence) v.getTag());
				context.startActivity(intent);
			}
		});
		return cellView;
	}
	
	@Override
	public int getCount() {
		return monthLog.size();
	}

}
