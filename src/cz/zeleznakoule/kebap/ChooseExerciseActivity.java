package cz.zeleznakoule.kebap;

import com.actionbarsherlock.app.ActionBar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ChooseExerciseActivity extends ListChooseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void initListView() {
		// TODO populate from database
		String[] data = { "Kokot", "Holota", "Iskuda", "Tkadla", "Banjo",
				"Vedro", "Rybik", "Bus" };
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data));
	}

	@Override
	public void initActionBar() {

		// TODO populate spinner z databaze
		// TODO replace spinner layout with custom implementation
		// TODO remove when database is in use
        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = new ArrayAdapter<CharSequence>(context, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		list.add("RKC");
		list.add("HKC");
		list.add("Kettlebell muscle");
		list.add("All");
		
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setListNavigationCallbacks(list, this);
	}

	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		Log.d("chooseActivity", "selected " + itemPosition + " | " + itemId);
		return false;
	}

}

