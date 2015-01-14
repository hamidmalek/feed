package com.malek.hamid.handlers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.malek.hamid.R;

public class ActivityListAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;

	public ActivityListAdapter(Activity a, RadioGroup rg) {
		activity = a;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return 6;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.activity_level_row, null);
			TextView tv = (TextView) vi.findViewById(R.id.activity_desc);
			tv.setText(position + tv.getText().toString());
			RadioButton rb = (RadioButton)vi.findViewById(R.id.radio_activity);
			rb.setTag(position);
			rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int i = (Integer) buttonView.getTag();
					ListView lv = (ListView) buttonView.getParent().getParent();
					for(int j = 0 ; j < getCount() ; j++){
						if(j != i && isChecked){
							RadioButton rbTemp = (RadioButton)lv.getChildAt(j).findViewById(R.id.radio_activity);
							rbTemp.setChecked(false);
						}else{
							lv.setTag(i);
						}
					}
				}
			});
		return vi;
	}

	public void itemClicked(int index){
		for(int i = 0 ; i < getCount() ; i++){
			if(i != index){
				Object item = getItem(i);
			}
		}
	}

}
