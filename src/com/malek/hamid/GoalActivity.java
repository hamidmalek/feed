package com.malek.hamid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GoalActivity extends Activity {
	// -- Form Elements --------------------------
	private EditText weight;
	private EditText duration;
	private Spinner policy;
	private Spinner timeScale;
	private Button submit;
	private DatabaseHandler db;
	private Person user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.goal);
		db = new DatabaseHandler(getApplicationContext());

		// -- Form Elements Initialization ---------------
		weight = (EditText) findViewById(R.id.goal_weight);
		duration = (EditText) findViewById(R.id.goal_duration);
		policy = (Spinner) findViewById(R.id.spinner_lose_or_gain);
		timeScale = (Spinner) findViewById(R.id.spinner_goal_duration_scale);
		submit = (Button) findViewById(R.id.save_goal);

		user = getIntent().getParcelableExtra("user");
		double userBmi = user.getBMI();

		submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/*
				 * checkig if user didn't insert anything
				 */
				if (weight.getText().toString().length() == 0
						|| duration.getText().toString().length() == 0)
					return;
				float goalWeight = user.getWeight();
				if (policy.getSelectedItemPosition() == 0) {
					goalWeight = goalWeight
							- Integer.parseInt(weight.getText().toString());
				} else {
					goalWeight = goalWeight
							+ Integer.parseInt(weight.getText().toString());
				}

				JDF jdf = new JDF();
				int days = 0;
				days = Integer.parseInt(duration.getText().toString());
				if (timeScale.getSelectedItemPosition() == 0) {
					days = days * 7;
				} else {
					days = days * 30;
				}
				jdf.nextDay(days);
				db.updateUserGoal(goalWeight);
				db.updateUserDeadLine(jdf.getIranianDate());
				System.out.println("User deadline saved as "+ jdf.getIranianDate());
				Toast.makeText(getApplicationContext(),
						goalWeight + " in " + jdf.getIranianDate(),
						Toast.LENGTH_LONG).show();
				Person user = db.getUser();
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				user.setDeadline(jdf.getIranianDate());
				intent.putExtra("user", user);
				System.out.println("DL2:"+user.getDeadline());
				startActivity(intent);
			}
		});
	}
}