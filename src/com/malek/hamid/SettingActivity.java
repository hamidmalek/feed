package com.malek.hamid;

import net.simonvt.numberpicker.NumberPicker;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	// -- Calendar Elements ----------------------
	public static final String G_DAY = "gDay";
	public static final String G_MONTH = "gMonth";
	public static final String G_YEAR = "gYear";
	public static final String J_DAY = "jDay";
	public static final String J_MONTH = "jMonth";
	public static final String J_YEAR = "jYear";
	private String[] monthNames = { "فروردین", "اردیبهشت", "خرداد", "تیر",
			"مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند" };
	private String[] dec = { ".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7",
			".8", ".9" };
	private NumberPicker npDay;
	private NumberPicker npMonth;
	private NumberPicker npYear;

	// -- Form Elements --------------------------
	private NumberPicker weight;
	private NumberPicker weightDec;
	private NumberPicker height;
	ImageButton manButton;
	ImageButton womanButton;
	private Button submit;
	private ScrollView scroll;
	private DatabaseHandler db;
	int sex = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		db = new DatabaseHandler(getApplicationContext());
		setContentView(R.layout.setting);
	}

}