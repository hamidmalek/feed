package com.malek.hamid;

import java.util.ArrayList;

import com.malek.hamid.handlers.DailyFoodsAdapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DailyLogActivity extends Activity {
	private ProgressBar progressBar;
	private String date;
	private int progress;
	private int waitMillis = 3000;
	private TextView dailyCalorie;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_log);
		DatabaseHandler db = new DatabaseHandler(this);
		date = getIntent().getCharSequenceExtra("date").toString();
		DailyLog dailyLog = db.getDayLog(date);
		ArrayList<Log> dayFoods = db.getUserNutLog(date);
		dailyCalorie = (TextView) findViewById(R.id.daily_calorie);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		lv = (ListView) findViewById(R.id.daily_log_list);
		progressBar.setMax(100);
		progress = 70;
		ObjectAnimator progressAnimation = ObjectAnimator.ofInt(progressBar,
				"progress", 0, progress);

		dailyCalorie.setText(dailyLog.getEnergy() + "");
		DailyFoodsAdapter dfa = new DailyFoodsAdapter(this, dayFoods);
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

}
