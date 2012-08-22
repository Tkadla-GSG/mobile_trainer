package cz.zeleznakoule.kebap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends BaseFragmentActivity {

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
		//FEED TAB
		ActionBar.Tab tab = actionBar.newTab();
		tab .setIcon(R.drawable.tabicon_feed_selector)
			.setTabListener(
                new TabListener<FeedFragment>(this, "Feed tab",
                		FeedFragment.class, null));
		actionBar.addTab(tab, true); // defaultni tab

		//CALENDAR TAB
		tab = actionBar.newTab();
		tab	.setIcon(R.drawable.tabicon_calendar_selector)
			.setTabListener(
                new TabListener<CalendarFragment>(this, "Calendar tab",
                		CalendarFragment.class, null));

		//PROFILE TAB
		actionBar.addTab(tab);
		tab = actionBar.newTab();
		tab	.setIcon(R.drawable.tabicon_profile_selector)
			.setTabListener(
                new TabListener<ProfileFragment>(this, "Profile tab",
                		ProfileFragment.class, null));;
		actionBar.addTab(tab);
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
		if (item.getTitle().toString().equals(getString(R.string.workout_btn))) { // GOTO Create workout Btn

			Intent i = new Intent(this, WorkoutActivity.class);
			startActivityForResult(i, 0);

			return true;
		}

		return super.onOptionsItemSelected(item);

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt("tab", getSupportActionBar()
	            .getSelectedNavigationIndex());
	}

}
