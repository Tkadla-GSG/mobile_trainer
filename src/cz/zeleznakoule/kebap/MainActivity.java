package cz.zeleznakoule.kebap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends BaseActivity implements ActionBar.TabListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// TODO remove when not needed
	public void onChooseExcerciseBtnClick(View view) {
		Intent i = new Intent(view.getContext(), ChooseExerciseActivity.class);
		startActivityForResult(i, 0);
	}

	@Override
	public void initActionBar() {
		actionBar.setTitle(R.string.app_name);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// TODO chybi ikony
		ActionBar.Tab tab = actionBar.newTab();
		tab.setText(R.string.feed_tab).setTabListener(this);
		actionBar.addTab(tab, true); // vybrany tab

		tab = actionBar.newTab();
		tab.setText(R.string.calendar_tab).setTabListener(this);
		actionBar.addTab(tab);

		tab = actionBar.newTab();
		tab.setText(R.string.profile_tab).setTabListener(this);
		actionBar.addTab(tab);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		Fragment switchTo = null; 
		switch (tab.getPosition()) {

		case 0:  switchTo = new FeedFragment();
			break;
		case 1: switchTo = new CalendarFragment();
			break;
		case 2: switchTo = new FeedFragment();
			break;
		default: switchTo = new FeedFragment();

		}
		
		//TODO nullpointer exception, but fuck why?
		//ft.replace(android.R.id.content, switchTo);

	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		// TODO chybi xhdpi a ldpi zdroj pro ic_compose
		menu.add(R.string.workout_btn).setIcon(R.drawable.ic_compose)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO menu by se melo tahat z XML, zde by se melo swithovat pomoci
		// item.getItemId()
		if (item.getTitle().toString().equals(getString(R.string.workout_btn))) { // GOTO
																					// Create
																					// workout
																					// //
																					// Btn

			Intent i = new Intent(this, WorkoutActivity.class);
			startActivityForResult(i, 0);

			return true;
		}

		return super.onOptionsItemSelected(item);

	}

}
