package cz.zeleznakoule.kebap;

import java.util.List;
import java.util.Random;

import cz.zeleznakoule.kebap.model.datasources.ExcerciseDataSource;
import cz.zeleznakoule.kebap.model.entities.Excercise;
import cz.zeleznakoule.kebap.model.idatasources.IExcerciseDataSource;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class TestDatabaseActivity extends ListActivity {
	
	private IExcerciseDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		datasource = new ExcerciseDataSource(this);
		datasource.open();

		List<Excercise> values = datasource.getAll();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Excercise> adapter = new ArrayAdapter<Excercise>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Excercise> adapter = (ArrayAdapter<Excercise>) getListAdapter();
		Excercise excercise = null;
		switch (view.getId()) {
		case R.id.add:
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// Save the new comment to the database
			excercise = datasource.create(comments[nextInt]);
			adapter.add(excercise);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				excercise = (Excercise) getListAdapter().getItem(0);
				datasource.delete(excercise);
				adapter.remove(excercise);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

} 