package com.malek.hamid;

import net.simonvt.numberpicker.NumberPicker;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

public class LoginActivity extends Activity {
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
		/*
		 * checking if user filled his data in advance, user must be directed to
		 * main activity
		 */
		System.out.println("just before checking if there any user!");
		if (db.getUserFulfilled()) {
			System.out.println("user is fulfilled");
			Intent intent = new Intent(getBaseContext(),
					MainActivity.class);
			Person user = db.getUser();
			intent.putExtra("user", user);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.login);
		// -- Calendar --------------------------- BEGIN
		NumberPicker.OnValueChangeListener onChangeListener = new NumberPicker.OnValueChangeListener() {

			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				if (picker == npMonth) {
					if (newVal <= 6) {
						npDay.setMaxValue(31);
					} else {
						npDay.setMaxValue(30);
					}
				}

			}
		};
		npYear = (NumberPicker) findViewById(R.id.npYear);
		npMonth = (NumberPicker) findViewById(R.id.npMonth);
		npDay = (NumberPicker) findViewById(R.id.npDay);

		npYear.setBackgroundColor(Color.TRANSPARENT);

		npMonth.setOnValueChangedListener(onChangeListener);
		JDF jdf = new JDF();
		int iranianYear = jdf.getIranianYear();
		int iranianMonth = jdf.getIranianMonth();
		int iranianDay = jdf.getIranianDay();

		npYear.setMinValue(1300);
		npYear.setMaxValue(iranianYear);
		npYear.setWrapSelectorWheel(false);
		npMonth.setMinValue(1);
		npMonth.setMaxValue(12);
		npMonth.setDisplayedValues(monthNames);

		npDay.setMinValue(1);
		npDay.setMaxValue(31);

		npYear.setValue(1370);
		npMonth.setValue(10);
		npDay.setValue(22);

		npYear.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		npMonth.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		npDay.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		// -- Calendar --------------------------- END

		// -- Form Elements Initialization ---------------
		// scroll = (ScrollView) findViewById(R.id.login_scroll);
		weight = (NumberPicker) findViewById(R.id.weight);
		weightDec = (NumberPicker) findViewById(R.id.weightDec);
		height = (NumberPicker) findViewById(R.id.height);

		// --- Number Picker initializing ------------------
		height.setMaxValue(250);
		height.setMinValue(120);
		height.setValue(160);
		height.setWrapSelectorWheel(false);
		height.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		weight.setMaxValue(160);
		weight.setMinValue(40);
		weight.setValue(70);
		weight.setWrapSelectorWheel(false);
		weight.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		weightDec.setMaxValue(9);
		weightDec.setMinValue(0);
		weightDec.setDisplayedValues(dec);

		// ----- Sex Buttons actions ----------------
		manButton = (ImageButton) findViewById(R.id.man_image);
		womanButton = (ImageButton) findViewById(R.id.woman_image);

		manButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				manButton.setImageResource(R.drawable.man_selected);
				womanButton.setImageResource(R.drawable.woman);
				sex = 0;
			}
		});

		womanButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				manButton.setImageResource(R.drawable.man);
				womanButton.setImageResource(R.drawable.woman_selected);
				sex = 1;
			}
		});

		// ---- submit button action ----------------
		submit = (Button) findViewById(R.id.btnsave);

		submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// checking if user did not choose a sex
				if (sex == -1) {
					Toast.makeText(getApplicationContext(),
							R.string.login_message, Toast.LENGTH_LONG).show();
				} else {

					float userWeight = weight.getValue() + weightDec.getValue()
							/ 10;
					int userHeight = height.getValue();
					String userBD = createBDString(npYear, npMonth, npDay);

					// directing user to activity level activity
					Intent intent = new Intent(getBaseContext(),
							ActLevelActivity.class);

					Person user = new Person(userWeight, userHeight, userBD,
							sex);
					intent.putExtra("user", user);
					startActivity(intent);
				}

			}

			/**
			 * method for generating birthday string in yyyy/mm/dd format
			 * 
			 * @param year
			 * @param month
			 * @param day
			 * @return String
			 */
			private String createBDString(NumberPicker year,
					NumberPicker month, NumberPicker day) {
				String res = "";
				String monthVal = "";
				if (month.getValue() < 10)
					monthVal = "0" + month.getValue();
				else
					monthVal = "" + month.getValue();
				res += year.getValue() + "/" + monthVal + "/" + day.getValue();
				return res;
			}
		});
	}

}