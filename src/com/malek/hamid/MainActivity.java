package com.malek.hamid;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends FragmentActivity implements addFoodFragment.OnDBChangedListener{
	// private String[] mPlanetTitles;
	// private DrawerLayout mDrawerLayout;
	// private ListView mDrawerList;
	// ActionBar actionBar;
	// ViewPager mViewPager;
	// TabsPagerAdapter tabHandler;
	// private PagerSlidingTabStrip tabs;

	private final Handler handler = new Handler();

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private Person user;
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("Main Activity Start");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//		user = db.getUser();
		user = getIntent().getParcelableExtra("user");
		System.out.println("W:"+user.getWeight());
		System.out.println("G:"+ user.getDesiredWeight());
		//TODO user desired weight is zero ?!!!!
		adapter = new MyPagerAdapter(getSupportFragmentManager(), user);

		pager.setAdapter(adapter);

		tabs.setTextColorResource(R.color.tab_text_color);
		tabs.setViewPager(pager);

		if (db.getShowStat()) {
			StatScreenFragment statScreen = new StatScreenFragment();
			statScreen.setUser(user);
			statScreen.setStyle(DialogFragment.STYLE_NO_TITLE,
					R.style.FullscreenTheme);
			statScreen.show(getSupportFragmentManager(), "Hi");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// switch (item.getItemId()) {
		//
		// case R.id.action_contact:
		// addFoodFragment dialog = new addFoodFragment();
		// dialog.show(getSupportFragmentManager(), "QuickContactFragment");
		// return true;
		//
		// }
		//
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("NewApi")
	private void changeColor(int newColor) {

		tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] {
						oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}

		currentColor = newColor;

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@SuppressLint("NewApi")
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	public class MyPagerAdapter extends FragmentPagerAdapter {

		Person user;
		SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
		private final String[] TITLES = getResources().getStringArray(
				R.array.main_tabs_array);

		public MyPagerAdapter(FragmentManager fm, Person user) {
			super(fm);
			this.user = user;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}
		
		public Fragment getFragment(int position){
			return registeredFragments.get(position);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				StatusFragment sf = new StatusFragment();
				sf.setUser(this.user);
				registeredFragments.put(0, sf);
				return sf;
			case 1:
				return new FoodsFragment();
			case 2:
				return new LogFragment();
//			case 3:
//				return new WorkoutFragment();
			}
			return null;
		}

	}

	public void addFood(View view) {
		addFoodFragment dialog = new addFoodFragment();
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		db.getFoodList();
		dialog.show(getSupportFragmentManager(), "FoodList");
	}

	public void insertFoodToLog(View view) {
		Toast.makeText(getApplicationContext(), "Hi there :D", 10000).show();
	}
	
	public void setUser(Person user){
		this.user = user;
	}

	public void onDBChanged() {
		StatusFragment sfrag = (StatusFragment)adapter.getFragment(0);
		sfrag.updateProgressBar();
	}

}