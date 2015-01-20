package com.malek.hamid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 */
public class StatScreenActivity extends Activity {
	
	TextView stat;
	Button skipButton;
	Button dontShow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stat_screen);
		
		skipButton = (Button) findViewById(R.id.skip_button);
		dontShow = (Button) findViewById(R.id.dont_show_button);
		stat = (TextView) findViewById(R.id.fullscreen_content);
		final Person user = getIntent().getParcelableExtra("user");
		stat.setText(user.toString());

		skipButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MainActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
				finish();
			}
		});
		
		dontShow.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), GoalActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
				finish();
				
			}
		});
	}
}
