package com.malek.hamid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatusFragment extends Fragment {
	private String name;
	private ProgressBar progressBar;
	private int progress;
	private int waitMillis = 1000;
	private TextView todayCalorie;
	private TextView dailyCalorie;
	private Person user;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_status, container,
				false);
		todayCalorie = (TextView) rootView.findViewById(R.id.today_calorie);
		dailyCalorie = (TextView) rootView.findViewById(R.id.daily_calorie);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		progressBar.setMax(100);
		progress = 70;
		name = getResources().getString(R.string.fragment_status);
		ObjectAnimator progressAnimation = ObjectAnimator.ofInt(progressBar,
				"progress", 0, progress);
		
		dailyCalorie.setText(user.getBMR()+"");
		progressAnimation.setDuration(waitMillis);
		progressAnimation.setInterpolator(new DecelerateInterpolator());

		AnimatorSet animSet = new AnimatorSet();
		animSet.playTogether(progressAnimation);
		animSet.start();
		return rootView;
	}

	public String getName() {
		return name;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void addProgress(int progress) {

	}

	public void setUser(Person user) {
		this.user = user;
	}
	

}
