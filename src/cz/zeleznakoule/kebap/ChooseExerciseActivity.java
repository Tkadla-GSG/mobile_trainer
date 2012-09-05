package cz.zeleznakoule.kebap;

import com.actionbarsherlock.app.ActionBar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ChooseExerciseActivity extends ListChooseActivity {

	// TODO toto se asi da presunout do rodice

	private ArrayAdapter<CharSequence> navigationList = null;

	@Override
	public void initListView() {
		// TODO populate from database
		String[] data = { "Kokot RKC", "Holota RKC", "Iskuda HKC", "Tkadla",
				"Banjo", "Vedro", "Rybik", "Bus" };

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);

		listView.setAdapter(adapter);
	}

	@Override
	public void initActionBar() {
		Context context = actionBar.getThemedContext();
		navigationList = new ArrayAdapter<CharSequence>(context,
				R.layout.sherlock_spinner_item);
		navigationList
				.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		// TODO populate spinner z databaze
		// TODO remove when database is in use
		navigationList.add("RKC");
		navigationList.add("HKC");
		navigationList.add("Kettlebell muscle");
		navigationList.add("All");

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setListNavigationCallbacks(navigationList, this);
	}

	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO tohle moc nefunguje
		/*CharSequence s = navigationList.getItem(itemPosition);
		
		if( !s.equals("All") ){
			adapter.getFilter().filter(s); 
			adapter.setNotifyOnChange(true);
		}*/
		
		return false;
	}



}
