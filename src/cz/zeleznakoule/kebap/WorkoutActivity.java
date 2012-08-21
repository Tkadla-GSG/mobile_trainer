package cz.zeleznakoule.kebap;

import android.os.Bundle;

public class WorkoutActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		// TODO use when needed (Home button) 
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_workout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		
		// TODO use when needed (Home button) 
		/*
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		*/
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void initActionBar() {
		actionBar.setTitle(R.string.title_activity_workout);

	}

}
