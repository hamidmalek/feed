package com.malek.hamid.handlers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.malek.hamid.FoodsFragment;
import com.malek.hamid.LogFragment;
import com.malek.hamid.R;
import com.malek.hamid.StatusFragment;
import com.malek.hamid.WorkoutFragment;

public class TabsPagerAdapter  extends FragmentPagerAdapter{


	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 3:
			return new WorkoutFragment();
		case 2:
			return new LogFragment();
		case 0:
			return new StatusFragment();
		case 1:
			return new FoodsFragment();
		}

		return null;
	}
	public String getName(int index, Activity activity){
		switch (index) {
		case 3:
			// Workout fragment activity
			return activity.getResources().getString(R.string.fragment_workout);
		case 2:
			// Nutrition Log fragment activity
			return activity.getResources().getString(R.string.fragment_log);
		case 0:
			// Today Status fragment activity
			return activity.getResources().getString(R.string.fragment_status);
		case 1:
			// Foods fragment activity
			return activity.getResources().getString(R.string.fragment_foods);
		}

		return null;
	}
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
