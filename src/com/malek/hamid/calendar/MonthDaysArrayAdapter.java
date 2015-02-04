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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malek.hamid.DailyLogActivity;
import com.malek.hamid.R;

/**
 * Customize the monthDays gridView
 */
public class MonthDaysArrayAdapter extends ArrayAdapter<CalendarCell> {
	public static int textColor = Color.LTGRAY;
	private Context context;
	private ArrayList<CalendarCell> monthLog;

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

	static class ViewHolder {
		RelativeLayout cell;
		ImageView background;
		TextView day;
	}

	// Set color to gray and text size to 12sp
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cellView = convertView;
		ViewHolder viewHolder = null;

		// For reuse
		if (convertView == null) {
			cellView = inflater.inflate(R.layout.custom_cell, null);
			viewHolder = new ViewHolder();
			viewHolder.day = (TextView) cellView.findViewById(R.id.tv1);
			viewHolder.background = (ImageView) cellView
					.findViewById(R.id.cell_background);
			cellView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) cellView.getTag();
		}

		viewHolder.day.setTextColor(Color.BLACK);

		// Get dateTime of this cell
		// cellView.setBackgroundColor(monthLog.get(position).getCellColor());
		// cellView.setBackgroundColor(null);

		if (monthLog.get(position).getCellColor() == Color.BLACK)
			viewHolder.day.setTextColor(Color.BLACK);
		else
			viewHolder.day.setTextColor(Color.BLACK);

		if (monthLog.get(position).getCellColor() == Color.MAGENTA) {
			viewHolder.day.setText("");
			viewHolder.background.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.day.setText("" + monthLog.get(position).getDay());
			viewHolder.background.setVisibility(View.VISIBLE);
		}
		viewHolder.background.setTag(monthLog.get(position).getDate());
		viewHolder.background.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), DailyLogActivity.class);
				intent.putExtra("date", (CharSequence) v.getTag());
				context.startActivity(intent);
			}
		});

		switch (monthLog.get(position).getCellColor()) {
		case Color.RED:
			viewHolder.background
					.setImageResource(R.drawable.calendar_cell_red);
			break;
		case Color.GREEN:
			viewHolder.background
					.setImageResource(R.drawable.calendar_cell_green);
			break;
		case Color.GRAY:
			viewHolder.background
					.setImageResource(R.drawable.calendar_cell_gray);
			viewHolder.background.setOnClickListener(null);
			break;
		default:
			break;
		}

		// viewHolder.background.setVisibility(View.GONE);
		return cellView;
	}

	@Override
	public int getCount() {
		return monthLog.size();
	}

}
