package cz.zeleznakoule.kebap;

import java.util.Date;
import java.util.List;

import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.datasources.WorkoutDataSource;
import cz.zeleznakoule.kebap.model.entities.Excercise;
import cz.zeleznakoule.kebap.model.entities.Workout;
import cz.zeleznakoule.kebap.model.entities.WorkoutItem;
import static cz.zeleznakoule.kebap.interfaces.Constants.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class FeedFragment extends Fragment {

	private ListView workoutList = null;

	private WorkoutDataSource dataSource = null;
	private SimpleCursorAdapter adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Layout pro tento fragment, musi byt vraceno v return na konci
		// onCreate
		View view = inflater.inflate(R.layout.fragment_feed, container, false);

		workoutList = (ListView) view.findViewById(R.id.feedListView);

		// ProgressBar se bude ukazovat dokud ListView nedostane data z Cursoru
		// a databaze
		ProgressBar progressBar = new ProgressBar(getActivity());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		progressBar.setLayoutParams(params);
		progressBar.setIndeterminate(true);
		workoutList.setEmptyView(progressBar);

		// Pridani spinneru do korenoveho layoutu
		ViewGroup root = (ViewGroup) view.findViewById(R.id.feedLayout);
		root.addView(progressBar);

		// inicializace dat list view z databaze
		// TODO list view by mel nacitat jen omezene mnozstvi dat
		dataSource = new WorkoutDataSource(getActivity());
		dataSource.open();
		// dataSource.createTest(new Date(), 10, "NOTE");

		adapter = dataSource.getSimpleCursorAdapter(getActivity());

		workoutList.setAdapter(adapter);

		// reakce na kliknuti na polozku (workout) v listu
		workoutList.setClickable(true);
		workoutList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				String id = (String) ((TextView) arg1
						.findViewById(R.id.item_id)).getText();
				Intent i = new Intent(getActivity(), WorkoutActivity.class);
				i.putExtra("action", SHOW_WORKOUT);
				i.putExtra("id", id);
				startActivity(i);

			}
		});

		return view;
	}

	/**
	 * Metoda volana pri obnoveni fragmentu
	 */
	@Override
	public void onResume() {
		dataSource.open();

		super.onResume();
	}

	@Override
	public void onPause() {
		dataSource.close();
		super.onPause();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
}
