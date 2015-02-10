package com.malek.hamid;

import java.util.ArrayList;
import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.malek.hamid.handlers.DailyFoodsAdapter;

/**
 * this activity is a used to show to user when he or show wants to know about a
 * specific date nutrition log. it has a progress bar and also a list of foods
 * 
 * @author hamid_
 * 
 */
public class DailyLogActivity extends FragmentActivity implements
		addFoodFragment.OnDBChangedListener {
	/**
	 * the progress bar which is shown to user in every daily log activity
	 */
	private ProgressBar progressBar; // TODO
	/**
	 * the string of date, used for add food fragment and other things
	 */
	private String date;
	/**
	 * progress of the day log
	 */
	private int progress;
	/**
	 * duration of progress bar animation
	 */
	private int waitMillis = 3000;
	/**
	 * text view of the current day calorie
	 */
	private TextView dailyCalorie;
	/**
	 * list of day foods, it should be expandable to distinguish between meals
	 * of the day
	 */
	private ExpandableListView dayFoodsELV;
	/**
	 * Button of add food, it shows a dialog for user to add the served food
	 */
	private ImageButton addFood;
	/**
	 * the object of user
	 */
	private Person user;
	/**
	 * the object of database
	 */
	private DatabaseHandler db;
	/**
	 * the adapter for day food list
	 */
	DailyFoodsAdapter dfa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * setting content view
		 */
		setContentView(R.layout.activity_daily_log);
		System.out.println(getIntent().getCharSequenceExtra("date"));
		/*
		 * reading date from intent
		 */
		date = getIntent().getCharSequenceExtra("date").toString();
		System.out.println("((((((((((((((((((((((((((" + date + "))");
		/*
		 * initializing data base object and reading current day log and day
		 * foods and also user from it
		 */
		db = new DatabaseHandler(getApplicationContext());
		DailyLog dailyLog = db.getDayLog(date);
		ArrayList<Log> dayFoods = db.getUserNutLog(date);
		user = db.getUser();
		/*
		 * initializing form elements
		 */
		dailyCalorie = (TextView) findViewById(R.id.daily_calorie);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		dayFoodsELV = (ExpandableListView) findViewById(R.id.daily_log_list);
		addFood = (ImageButton) findViewById(R.id.add_food_in_log);

		/*
		 * setting on click listener for add food button, it shows a dialog
		 * fragment for user to add the food
		 */
		addFood.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FoodsFragment dialog = new FoodsFragment();
				dialog.setStyle(DialogFragment.STYLE_NO_TITLE,
						R.style.DialogeStyle);
				dialog.setDate(date);
				dialog.show(getSupportFragmentManager(), "Hello");
			}
		});
		/*
		 * the progress bar initializing and animation
		 */
		progressBar.setMax(user.getBMR() + 500);
		progress = dailyLog.getEnergy();
		ObjectAnimator progressAnimation = ObjectAnimator.ofInt(progressBar,
				"progress", 0, progress);

		if (dailyLog.getEnergy() != -1)
			dailyCalorie.setText(dailyLog.getEnergy() + "");
		else
			dailyCalorie.setText("-");

		progressAnimation.setDuration(waitMillis);
		progressAnimation.setInterpolator(new DecelerateInterpolator());

		AnimatorSet animSet = new AnimatorSet();
		animSet.playTogether(progressAnimation);
		animSet.start();
		/*
		 * food list setting
		 */
		dfa = new DailyFoodsAdapter(this, dayFoods,getResources()
				.getStringArray(R.array.meals_array));
		dayFoodsELV.setAdapter(dfa);
		for (int i = 0; i < dfa.getGroupCount(); i++) {
			dayFoodsELV.expandGroup(i);
			System.out.println("count:"+dayFoodsELV.getCount());
			System.out.println("i is:"+i);
		}
	}

	public void setProgressBar(int progress) {
		this.progress = progress;
	}

	public void addProgress(int progress) {

	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * this function is used as a callback for changing the progress bar view
	 */
	public void onDBChanged() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(getApplicationContext());
		DailyLog todayLog = db.getDayLog(date);
		db.close();
		int prevProgress = progress;
		progress = todayLog.getEnergy();
		// name = getResources().getString(R.string.fragment_status);
		ObjectAnimator progressAnimation = ObjectAnimator.ofInt(progressBar,
				"progress", prevProgress, progress);
		if (todayLog.getEnergy() == -1) {
			dailyCalorie.setText("-");
			dailyCalorie.setGravity(Gravity.CENTER);
		} else
			dailyCalorie.setText("" + todayLog.getEnergy());

		progressAnimation.setDuration(waitMillis);
		progressAnimation.setInterpolator(new DecelerateInterpolator());
		AnimatorSet animSet = new AnimatorSet();
		animSet.playTogether(progressAnimation);
		animSet.start();
		ArrayList<Log> dayFoods = db.getUserNutLog(date);
		dfa.setDataList(dayFoods);

	}

}
