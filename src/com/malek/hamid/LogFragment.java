package com.malek.hamid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malek.hamid.calendar.ShamsiCalendar;

public class LogFragment extends Fragment {

	private String name;
	private ShamsiCalendar shamsiCalendar;
	private FragmentActivity myContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_log, container,
				false);
		name = getResources().getString(R.string.fragment_log);
		shamsiCalendar = new ShamsiCalendar(getActivity());

		
		// Attach to the activity
		FragmentTransaction t = myContext.getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar, shamsiCalendar);
		t.commit();
		
		return rootView;
	}

	public String getName() {
		return name;
	}
	
	
	@Override
	public void onAttach(Activity activity) {
	    myContext=(FragmentActivity) activity;
	    super.onAttach(activity);
	}
	
	@Override
	public void onResume() {
		FragmentTransaction t = myContext.getSupportFragmentManager().beginTransaction();
		shamsiCalendar = new ShamsiCalendar(getActivity());
		t.replace(R.id.calendar, shamsiCalendar);
		t.commit();
		super.onResume();
	}
}
