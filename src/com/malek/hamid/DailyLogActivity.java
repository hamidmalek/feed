package com.malek.hamid;

import java.util.ArrayList;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
	private ProgressBar progressBar;
	private String date;
	private int progress;
	private int waitMillis = 3000;
	private TextView dailyCalorie;
	private ListView lv;
	private ImageButton addFood;
	private Person user;
	private DatabaseHandler db;
	DailyFoodsAdapter dfa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_log);
		System.out.println(getIntent().getCharSequenceExtra("date"));
		date = getIntent().getCharSequenceExtra("date").toString();
		System.out.println("(((((((((((((((((((((((((("+date+"))");
		db = new DatabaseHandler(getApplicationContext());
		DailyLog dailyLog = db.getDayLog(date);
		ArrayList<Log> dayFoods = db.getUserNutLog(date);
		dailyCalorie = (TextView) findViewById(R.id.daily_calorie);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		lv = (ListView) findViewById(R.id.daily_log_list);
		addFood = (ImageButton) findViewById(R.id.add_food_in_log);
		user = db.getUser();

		addFood.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FoodsFragment dialog = new FoodsFragment();
				dialog.setStyle(DialogFragment.STYLE_NO_TITLE,
						R.style.DialogeStyle);
				dialog.setDate(date);
				dialog.show(getSupportFragmentManager(), "Hello");
			}
		});

		progressBar.setMax(user.getBMR() + 500);
		
		progress = dailyLog.getEnergy();
		ObjectAnimator progressAnimation = ObjectAnimator.ofInt(progressBar,
				"progress", 0, progress);

		if (dailyLog.getEnergy() != -1)
			dailyCalorie.setText(dailyLog.getEnergy() + "");
		else
			dailyCalorie.setText("-");
		dfa = new DailyFoodsAdapter(this, dayFoods);
		lv.setAdapter(dfa);
		progressAnimation.setDuration(waitMillis);
		progressAnimation.setInterpolator(new DecelerateInterpolator());

		AnimatorSet animSet = new AnimatorSet();
		animSet.playTogether(progressAnimation);
		animSet.start();
	}

	public void setProgressBar(int progress) {
		this.progress = progress;
	}

	public void addProgress(int progress) {

	}

	public void setDate(String date) {
		this.date = date;
	}

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
