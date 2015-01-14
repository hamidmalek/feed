package com.malek.hamid;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.malek.hamid.handlers.FoodListAdapter;
import com.malek.hamid.handlers.FoodSearchAdapter;
import com.malek.hamid.handlers.Group;

public class FoodsFragment extends Fragment {

	private ListView searcResult;
	private SearchView foodSearchView;
	private ExpandableListView foodList;
	private FoodSearchAdapter fsa;
	private final SparseArray<Group> foodGroup = new SparseArray<Group>();
	public static HashMap<Integer, String> foodCategories = new HashMap<Integer, String>();
	public static HashMap<Integer, String> foodUnits = new HashMap<Integer, String>();
	DatabaseHandler db;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// defining data base handler for all works :D
		db = new DatabaseHandler(getActivity());
		/*
		 * It is a map of food categories and their IDs. It is used across the
		 * application for showing the category of the food to the user
		 */
		foodCategories = db.getFoodCategories();
		/*
		 * It as a map of food units and their IDs. It is used in the
		 * application for getting the Units of food
		 */
		foodUnits = db.getFoodUnits();
		/*
		 * getting root view
		 */
		View rootView = inflater.inflate(R.layout.fragment_foods, container,
				false);
		// -------------- Foods List Section -------------------------------
		/*
		 * list of foods that is shown to user in an expandable list view
		 */
		foodList = (ExpandableListView) rootView.findViewById(R.id.food_list);
		/*
		 * creating data for list of foods
		 */
		createData();
		/*
		 * defining and setting the adapter of the food list
		 */
		final FoodListAdapter adapter = new FoodListAdapter(getActivity(),
				foodGroup);
		foodList.setAdapter(adapter);
		// foodList.setOnItemClickListener(new OnItemClickListener() {
		//
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // adapter.setFoodsToCategory(position+1);
		// }
		// });

		// ------------- Foods Search Section ------------------------------
		/*
		 * search input
		 */
		foodSearchView = (SearchView) rootView
				.findViewById(R.id.search_bar_food);
		/*
		 * list view of the search results
		 */
		searcResult = (ListView) rootView.findViewById(R.id.search_result_food);
		/*
		 * defining and setting the adapter of the foods search
		 */
		fsa = new FoodSearchAdapter(getActivity(), new ArrayList<Food>());
		searcResult.setAdapter(fsa);
		/*
		 * on click listener of the food search bar
		 */
		foodSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			public boolean onQueryTextSubmit(String query) {
				db = new DatabaseHandler(getActivity());
				if (query.length() > 1) {
					fsa.setDataList(db.getFoodObjectList(query));
				}
				return true;
			}

			public boolean onQueryTextChange(String newText) {
				DatabaseHandler db = new DatabaseHandler(getActivity());
				if (newText.length() > 1) {
					fsa.setDataList(db.getFoodObjectList(newText));
				} else {
					fsa.setDataList(new ArrayList<Food>());
				}
				return true;
			}
		});

		return rootView;
	}

	public void createData() {
		for (int i = 0; i < foodCategories.size(); i++) {
			foodGroup.append(i, new Group(foodCategories.get(i + 1), i + 1));
		}
	}
}
