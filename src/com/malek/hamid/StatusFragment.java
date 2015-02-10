package com.malek.hamid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pascalwelsch.holocircularprogressbar.HoloCircularProgressBar;

public class StatusFragment extends Fragment {
	private String name;
	private HoloCircularProgressBar progressBar;
	private float progress;
	private int waitMillis = 4000;
	private TextView todayCalorie;
	private TextView dailyCalorie;
	private Person user;
	private ImageButton addFoodButton;
	private DatabaseHandler db;
	private TextView goalText;
	private Button goalButton;
	JDF date = new JDF();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("Status Fragment Start");
		View rootView = inflater.inflate(R.layout.fragment_status, container,
				false);
		db = new DatabaseHandler(getActivity().getApplicationContext());
		todayCalorie = (TextView) rootView.findViewById(R.id.today_calorie);
		dailyCalorie = (TextView) rootView.findViewById(R.id.daily_calorie);
		progressBar = (HoloCircularProgressBar) rootView
				.findViewById(R.id.progressBar);
		goalButton = (Button) rootView.findViewById(R.id.set_goal_button);
		addFoodButton = (ImageButton) rootView
				.findViewById(R.id.addFood_inStatus);
		goalText = (TextView) rootView.findViewById(R.id.goal_text);

		if (user.getWeight() != user.getDesiredWeight()) {
			goalText.setText(Html.fromHtml(getGaolMessage()));
			ViewGroup parent = (ViewGroup) goalButton.getParent();
			parent.removeView(goalButton);
		}
		goalButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), GoalActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
		DailyLog todayLog = db.getDayLog(date.getIranianDate());
		db.close();
		name = getResources().getString(R.string.fragment_status);
		if (todayLog.getEnergy() == -1) {
			todayCalorie.setText("-");
			todayCalorie.setGravity(Gravity.CENTER);
		} else
			todayCalorie.setText("" + todayLog.getEnergy());

		dailyCalorie.setText(db.getProperEnergy() + "");
		addFoodButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				FoodsFragment dialog = new FoodsFragment();
				dialog.setStyle(DialogFragment.STYLE_NO_TITLE,
						R.style.DialogeStyle);
				dialog.setDate(date.getIranianDate());
				dialog.show(getActivity().getSupportFragmentManager(), "Hello");

			}
		});
		// //////// Progress Animation ///////////////////////////
		progressBar.setMarkerProgress((float) user.getBMR()
				/ (user.getBMR() + 500));
		progress = (float) todayLog.getEnergy() / (user.getBMR() + 500);
		ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(progressBar,
				"progress", 0, progress);
		progressAnimation.setDuration(waitMillis);
		progressAnimation.setInterpolator(new DecelerateInterpolator());
		progressAnimation.start();
		return rootView;
	}

	private String getGaolMessage() {
		String str = "";
		str += "شما می‌خواهید طی ";
		JDF deadline = new JDF();
		int goalYear = Integer.parseInt(user.getDeadline().split("/")[0]);
		int goalMonth = Integer.parseInt(user.getDeadline().split("/")[1]);
		int goalDay = Integer.parseInt(user.getDeadline().split("/")[2]);
		deadline.setIranianDate(goalYear, goalMonth, goalDay);
		JDF now = new JDF();
		int days = 0;
		while (!now.getIranianDate().matches(deadline.getIranianDate())) {
			days++;
			deadline.previousDay();
		}
		str += "<font color='#00BBFF'>" + days + "</font>";
		str += " روز آینده به میزان ";
		str += "<font color='#EE0000'>"
				+ Math.abs(user.getWeight() - user.getDesiredWeight())
				+ "</font>";
		str += " کیلوگرم ";
		if (user.getDesiredWeight() < user.getWeight()) {
			str += "لاغر";
		} else {
			str += "چاق";
		}
		str += " شوید.";
		return str;
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

	public void updateProgressBar() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(getActivity().getApplicationContext());
		DailyLog todayLog = db.getDayLog(date.getIranianDate());
		db.close();
		float prevProgress = progress;
		progress = todayLog.getEnergy();
		name = getResources().getString(R.string.fragment_status);
		ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(progressBar,
				"progress", prevProgress, progress);
		if (todayLog.getEnergy() == -1) {
			todayCalorie.setText("-");
			todayCalorie.setGravity(Gravity.CENTER);
		} else
			todayCalorie.setText("" + todayLog.getEnergy());

		dailyCalorie.setText(user.getBMR() + "");
		progressAnimation.setDuration(waitMillis);
		progressAnimation.setInterpolator(new DecelerateInterpolator());
		progressAnimation.start();

	}

}
