package com.malek.hamid.handlers;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.malek.hamid.DatabaseHandler;
import com.malek.hamid.R;

public class FoodListAdapter extends BaseExpandableListAdapter {
	
	/**
	 * groups of food by categories
	 */
	private final SparseArray<Group> groups;
	public LayoutInflater inflater;
	public Activity activity;

	public FoodListAdapter(Activity act, SparseArray<Group> groups) {
		activity = act;
		this.groups = groups;
		inflater = act.getLayoutInflater();
	}

	public String getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition).getName();
	}

	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String children = getChild(groupPosition, childPosition);
		TextView text = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_details, null);
		}
		text = (TextView) convertView.findViewById(R.id.textView1);
		text.setText(children);
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(activity, children, Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).children.size();
	}

	public Object getGroup(int groupPosition) {
		DatabaseHandler db = new DatabaseHandler(activity);
		groups.get(groupPosition).children.clear();
		groups.get(groupPosition).children.addAll(db.getFoods(groupPosition+1));
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	public long getGroupId(int groupPosition) {
		return 0;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_group, null);
		}
		Group group = (Group) getGroup(groupPosition);
		((CheckedTextView) convertView).setText(group.getName());
		((CheckedTextView) convertView).setChecked(isExpanded);
		return convertView;
	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

//	public void setFoodsToCategory(int categoryId) {
//		DatabaseHandler db = new DatabaseHandler(activity);
//		groups.get(categoryId).children.addAll(db.getFoods(categoryId));
//		System.out.println("+++++++++++++++++++++++++++++++++");
//		notifyDataSetChanged();
//	}
}
