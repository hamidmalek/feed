package com.malek.hamid.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.malek.hamid.DatabaseHandler;
import com.malek.hamid.Log;
import com.malek.hamid.R;

public class DailyFoodsAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Log> data;
	public LayoutInflater inflater;
	/**
	 * groups of food by meal
	 */
	private SparseArray<MealGroup> groups;
	/**
	 * meals name
	 */
	private String[] mealsNames;
	private String stdUnit = "گرم";

	public DailyFoodsAdapter(Activity act, ArrayList<Log> data,
			String[] mealsNames) {
		activity = act;
		this.data = data;
		this.mealsNames = mealsNames;
		inflater = act.getLayoutInflater();
		createGroups();
	}

	private void createGroups() {
		// TODO Auto-generated method stub
		groups = new SparseArray<MealGroup>();
		MealGroup breakfast = new MealGroup("breakfast", 0);
		MealGroup lunch = new MealGroup("lunch", 1);
		MealGroup dinner = new MealGroup("dinner", 2);
		MealGroup snack = new MealGroup("snack", 3);
		for (int i = 0; i < data.size(); i++) {

			switch (data.get(i).getMealId()) {
			case 0:
				breakfast.children.add(data.get(i));
				break;
			case 1:
				lunch.children.add(data.get(i));
				break;
			case 2:
				dinner.children.add(data.get(i));
				break;
			case 3:
				snack.children.add(data.get(i));
				break;
			default:
				// do nothing
				System.out
						.println("it should not be here --- DailyFoodAdapter --- createGroup");
				break;
			}
		}
		groups.put(0, breakfast);
		groups.put(1, lunch);
		groups.put(2, dinner);
		groups.put(3, snack);
	}

	public Log getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition).getId();
	}

	/**
	 * ViewHolder class used for optimizing list view
	 * 
	 * @author hamid_
	 * 
	 */
	class ViewHolder {
		LinearLayout listRow;
		TextView foodName;
		TextView foodSize;
		TextView foodUnit;
	}

	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final Log log = getChild(groupPosition, childPosition);
		DatabaseHandler db = new DatabaseHandler(activity);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.daily_log_row, null);
			viewHolder = new ViewHolder();
			viewHolder.listRow = (LinearLayout) convertView
					.findViewById(R.id.daily_log_row_view);
			viewHolder.foodName = (TextView) convertView
					.findViewById(R.id.food_name);
			viewHolder.foodSize = (TextView) convertView
					.findViewById(R.id.food_size);
			viewHolder.foodUnit = (TextView) convertView
					.findViewById(R.id.food_unit);
			viewHolder.listRow.setTag(log);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.foodName.setText(db.getFood(log.getFoodId()).getName());
		viewHolder.foodSize.setText(log.getSize() + "");

		HashMap<Integer, String> unitsArray = db.getFoodUnits();
		if (!log.isStd()) {
			viewHolder.foodUnit.setText(unitsArray.get(db.getFood(
					log.getFoodId()).getUnit()));
		} else {
			viewHolder.foodUnit.setText(stdUnit);
		}

		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Toast.makeText(activity, children,
				// Toast.LENGTH_SHORT).show();
			}
		});

		viewHolder.listRow.setOnLongClickListener(new OnLongClickListener() {

			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("the tag is" + v.getTag());
				Toast.makeText(activity, "((Log)v.getTag()).getFoodId()" + "",
						2000).show();
				return false;
			}
		});

		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).children.size();
	}

	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return 4;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		// super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	public long getGroupId(int groupPosition) {
		System.out.println(groupPosition);
		return groups.get(groupPosition).getId();
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.meals_listrow_group, null);
		}
		((CheckedTextView) convertView).setText(mealsNames[groupPosition]);
		((CheckedTextView) convertView).setChecked(isExpanded);

		return convertView;

	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	public void setDataList(ArrayList<Log> dayFoods) {
		// TODO Auto-generated method stub
		data = dayFoods;
		createGroups();
		notifyDataSetChanged();
	}
}

/**
 * MealGroup class used for group of meals
 * 
 * @author hamid_
 * 
 */
class MealGroup {
	private String name;
	private int id;

	public final List<Log> children = new ArrayList<Log>();

	public MealGroup(String string, int id) {
		this.setName(string);
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
