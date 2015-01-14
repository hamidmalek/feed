package com.malek.hamid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.malek.hamid.handlers.ActivityListAdapter;

/**
 */
public class ActLevelActivity extends Activity {

	ListView activityList;
	Button saveButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_level);
		activityList = (ListView) findViewById(R.id.activity_list);
		ActivityListAdapter al = new ActivityListAdapter(this, null);
		activityList.setAdapter(al);
		Button saveButton = (Button) findViewById(R.id.save_activity);
		saveButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						StatScreenActivity.class);
				Person user = getIntent().getParcelableExtra("user");
				user.setActivityLevel((Integer) activityList.getTag());
				DatabaseHandler db = new DatabaseHandler(
						getApplicationContext());
				db.setuserInfo(user);
				intent.putExtra("user", user);
				startActivity(intent);
				finish();
			}
		});
	}
}
