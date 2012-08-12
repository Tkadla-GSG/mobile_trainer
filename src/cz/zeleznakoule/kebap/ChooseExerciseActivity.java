package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

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
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data));
	}

	@Override
	public void initSpinner() {
		// TODO populate spinner z databaze
		// TODO replace spinner layout with custom implementation
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, R.drawable.custom_spinner_layout);

		// TODO remove when database is in use
		adapter.add("RKC");
		adapter.add("HKC");
		adapter.add("Kettlebell muscle");
		adapter.add("All");

		// format polozky dropdown menu
		// TODO replace spinner dropdown layout with custom implementation
		adapter.setDropDownViewResource(R.drawable.custom_spinner_dropdown_layout);

		filterSelector.setAdapter(adapter);

		// Spinner onSelect listener
		filterSelector.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO implementovat reakci na vybrani
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// Not implemented
			}
		});

	}

}

