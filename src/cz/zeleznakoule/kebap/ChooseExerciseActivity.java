package cz.zeleznakoule.kebap;

import com.actionbarsherlock.app.ActionBar;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ChooseExerciseActivity extends ListChooseActivity {

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
        Context context = actionBar.getThemedContext();
        ArrayAdapter<CharSequence> list = new ArrayAdapter<CharSequence>(context, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		// TODO populate spinner z databaze
		// TODO remove when database is in use
		list.add("RKC");
		list.add("HKC");
		list.add("Kettlebell muscle");
		list.add("All");
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setListNavigationCallbacks(list, this);
	}

	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO pridat filtrovani listu
		Log.d("chooseActivity", "selected " + itemPosition + " | " + itemId);
		return false;
	}

}

