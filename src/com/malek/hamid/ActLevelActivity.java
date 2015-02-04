package com.malek.hamid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This activity is used for getting user activity level. Activity levels play a
 * role in user BMR. User can choose between five different activity levels
 */
public class ActLevelActivity extends Activity {

	/**
	 * text view for activity level description
	 */
	TextView activityDescription;
	/**
	 * button to save the activity level
	 */
	Button saveButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_level);
		/*
		 * initializing the elements of the activity
		 */
		saveButton = (Button) findViewById(R.id.save_activity);
		activityDescription = (TextView) findViewById(R.id.activity_description);

		/*
		 * defining on click listener for save button
		 */
		saveButton.setOnClickListener(new OnClickListener() {
			/*
			 * it gets user activity level and saves it as an object in database
			 * then it directs user to statistics activity. (non-Javadoc)
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						MainActivity.class);
				Person user = getIntent().getParcelableExtra("user");
				user.setActivityLevel((Integer) v.getTag());
				DatabaseHandler db = new DatabaseHandler(
						getApplicationContext());
				db.setuserInfo(user);
				user.setDesiredWeight(user.getWeight());
				intent.putExtra("user", user);
				finish();
				startActivity(intent);
			}
		});
	}

	/**
	 * this function is invoked in activity layout via ImageButton onClicks. it
	 * works like a radio button. it saves the selected activity level as a tag
	 * on saveButton
	 * [activity level=tag]
	 * [very high=5, high=4, medium=3, low=2, very low=1]
	 * @param v
	 *            : a view which is one of the images that work like radio buttons
	 */
	public void ActivityLevelListener(View v) {
		String s = (String) v.getTag();
		LinearLayout ll = (LinearLayout) v.getParent();
		for (int i = 0; i < ll.getChildCount(); i++)
			ll.getChildAt(i).setBackgroundColor(
					getResources().getColor(R.color.white));
		v.setBackgroundColor(getResources().getColor(R.color.black));
		if (s == getResources().getString(R.string.very_high)) {
			activityDescription.setText(s);
			saveButton.setTag(5);
		} else if (s == getResources().getString(R.string.high)) {
			activityDescription.setText(s);
			saveButton.setTag(4);
		} else if (s == getResources().getString(R.string.medium)) {
			activityDescription.setText(s);
			saveButton.setTag(3);
		} else if (s == getResources().getString(R.string.low)) {
			activityDescription.setText(s);
			saveButton.setTag(2);
		} else if (s == getResources().getString(R.string.very_low)) {
			activityDescription.setText(s);
			saveButton.setTag(1);
		}
	}
}
