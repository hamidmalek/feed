package com.malek.hamid.calendar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.malek.hamid.DailyLog;
import com.malek.hamid.DatabaseHandler;
import com.malek.hamid.JDF;
import com.malek.hamid.R;

public class ShamsiCalendar extends DialogFragment {

	/******** finals ********************/
	final String[] DAYS_OF_WEEK = { "شنبه", "یکشنبه", "دوشنبه", "سه شنبه",
			"چهارشنبه", "پنجشنبه", "جمعه" };
	Activity activity;

	/********* variables *****************/
	TextView monthTitleTextView;
	Button leftArrowButton;
	Button rightArrowButton;
	GridView monthdayGridView;
	GridView weekdayGridView;
	int showNavigationArrows;
	ShamsiDate date;
	JDF jdf = new JDF();

	/********* constructor ***************/

	public ShamsiCalendar(Activity activity) {
		date = new ShamsiDate(jdf.getIranianMonth(), jdf.getIranianYear());
		this.activity = activity;
	}

	public ShamsiCalendar(ShamsiDate date, Activity activity) {
		this.date = date;
		this.activity = activity;
	}

	/**
	 * Setup view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// To support keeping instance for dialog
		if (getDialog() != null) {
			setRetainInstance(true);
		}

		// Inflate layout
		View view = inflater.inflate(R.layout.calendar_view, container, false);

		// For the monthTitleTextView
		monthTitleTextView = (TextView) view
				.findViewById(R.id.calendar_month_year_textview);

		// For the left arrow button
		leftArrowButton = (Button) view.findViewById(R.id.calendar_left_arrow);
		rightArrowButton = (Button) view
				.findViewById(R.id.calendar_right_arrow);

		// Navigate to previous month when user click
		leftArrowButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				prevMonth();
			}

		});

		// Navigate to next month when user click
		rightArrowButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				nextMonth();
			}

		});

		// Show navigation arrows depend on initial arguments
		setShowNavigationArrows(showNavigationArrows);

		// For the weekday gridView
		weekdayGridView = (GridView) view.findViewById(R.id.weekday_gridview);
		WeekdayArrayAdapter weekdaysAdapter = new WeekdayArrayAdapter(
				getActivity(), R.layout.week_day_cell, getDaysOfWeek());
		weekdayGridView.setAdapter(weekdaysAdapter);

		// For the month days gridView
		monthdayGridView = (GridView) view
				.findViewById(R.id.month_days_gridview);
		MonthDaysArrayAdapter monthdaysAdapter = new MonthDaysArrayAdapter(
				getActivity(), R.layout.week_day_cell, getDaysOfMonth());
		monthdayGridView.setAdapter(monthdaysAdapter);

		// For month year textView
		monthTitleTextView.setText(date.getMonthName() + "    "
				+ date.getYear());
		return view;
	}

	private void prevMonth() {
		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.replace(
				R.id.calendar,
				new ShamsiCalendar(new ShamsiDate(date.getMonth() - 1, date
						.getYear()), this.activity));
		t.commit();

	}

	private void nextMonth() {
		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.replace(
				R.id.calendar,
				new ShamsiCalendar(new ShamsiDate(date.getMonth() + 1, date
						.getYear()), this.activity));
		t.commit();

	}

	private void setShowNavigationArrows(int flag) {
		// TODO
		switch (flag) {
		case 1:

			break;

		default:
			break;
		}
	}

	protected ArrayList<String> getDaysOfWeek() {
		ArrayList<String> list = new ArrayList<String>();
		for (String str : DAYS_OF_WEEK)
			list.add(str);
		return list;
	}

	private List<CalendarCell> getDaysOfMonth() {
		ArrayList<CalendarCell> datesOfMonth = new ArrayList<CalendarCell>();
		JDF temp = new JDF();
		temp.setIranianDate(date.getYear(), date.getMonth(), 1);
		JDF tempPrev = new JDF();
		tempPrev.setIranianDate(date.getYear(), date.getMonth(), 1);
		tempPrev.previousDay();
		DatabaseHandler db = new DatabaseHandler(activity);
		ArrayList<DailyLog> logList = db.getMonthLog(new ShamsiDate(date
				.getMonth(), date.getYear()));
		
		int properEnergy = db.getProperEnergy();

		int d = tempPrev.getIranianDay() + 1;
		int startDay = temp.getDayOfWeek();
		startDay = ((startDay + 2) % 7);
		// days of previous month
		for (int i = d - startDay; i < d; i++)
			datesOfMonth.add(new CalendarCell(Color.MAGENTA, tempPrev
					.getIranianYear()
					+ "/"
					+ tempPrev.getIranianMonth()
					+ "/"
					+ i));
		JDF lastDayOfMonth = new JDF();
		lastDayOfMonth.setIranianDate(date.getYear(), date.getMonth(), 28);
		while (lastDayOfMonth.getIranianMonth() == date.getMonth())
			lastDayOfMonth.nextDay();
		lastDayOfMonth.previousDay();
		int monthDays = lastDayOfMonth.getIranianDay();

		JDF today = new JDF();
		for (int i = 0; i < monthDays; i++) {
			int c;
			int energyOfDay = logList.get(i + 1).getEnergy();
			if (date.getYear() > today.getIranianYear()
					|| (date.getYear() == today.getIranianYear() && date
							.getMonth() > today.getIranianMonth())
					|| (date.getYear() == today.getIranianYear()
							&& date.getMonth() == today.getIranianMonth() && today
							.getIranianDay()  < (i + 1)))
				c = Color.GRAY;
			else if (energyOfDay == -1)
				c = Color.BLACK;
			else if (energyOfDay > properEnergy)
				c = Color.RED;
			else
				c = Color.GREEN;

			datesOfMonth.add(new CalendarCell(c, date.getYear() + "/"
					+ ((date.getMonth())) + "/" + (i + 1)));
		}
		int numberOfDays = 43 - datesOfMonth.size();
		int newxYear = date.getMonth() < 12 ? date.getYear()
				: date.getYear() + 1;
		for (int i = 1; i < numberOfDays; i++)
			datesOfMonth.add(new CalendarCell(Color.MAGENTA, newxYear + "/"
					+ ((date.getMonth() % 12) + 1) + "/" + (i)));
		return datesOfMonth;
	}

}
