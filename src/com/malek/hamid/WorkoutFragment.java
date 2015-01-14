package com.malek.hamid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WorkoutFragment extends Fragment {
	
	private String name;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_log, container, false);
		name = getResources().getString(R.string.fragment_log);
		return rootView;
	}
	public String getName(){
		return name;
	}
}
