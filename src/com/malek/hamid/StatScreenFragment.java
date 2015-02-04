package com.malek.hamid;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 */
public class StatScreenFragment extends DialogFragment {

	TextView stat;
	ImageButton skipButton;
	Button dontShow;
	Person user;
	DatabaseHandler db ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_stat_screen,
				container, false);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogeStyle);
		/*
		 * initializing the elements of the fragment
		 */
		db = new DatabaseHandler(getActivity());


		stat = (TextView) rootView.findViewById(R.id.user_stat);
		System.out.println("STAT:" + generateStatInfo(user));
		stat.setText(generateStatInfo(user));

		skipButton = (ImageButton) rootView
				.findViewById(R.id.close_stat_button);
		skipButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dismiss();
			}
		});

		dontShow = (Button) rootView.findViewById(R.id.dont_show_button);
		dontShow.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				db.setShowStat(false);
			}
		});

		return rootView;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public String generateStatInfo(Person user) {
		String str = "وزن شما ";
		str += user.getWeight();
		str += " کیلوگرم";
		str += " و قد شما ";
		str += user.getHeight() / 100;
		str += " متر و ";
		str += user.getHeight() % 100;
		str += " سانتی‌متر است و همچنین شما ";
		str += user.getAge();
		str += " سال دارید.";
		str += "\n";
		str += "بنابراین";
		str += "\n";
		str += "شما باید روزانه ";
		str += user.getBMR();
		str += " کالری دریافت نمایید تا در وزن فعلی خود باقی بمانید.";

		return str;
	}
}
