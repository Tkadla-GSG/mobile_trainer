package cz.zeleznakoule.kebap;

import java.util.List;

import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.datasources.WorkoutDataSource;
import cz.zeleznakoule.kebap.model.entities.Excercise;
import cz.zeleznakoule.kebap.model.entities.Workout;
import cz.zeleznakoule.kebap.model.entities.WorkoutItem;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class FeedFragment extends Fragment {
	
	private ListView workoutList = null;
	
	private WorkoutDataSource dataSource = null; 
	private SimpleCursorAdapter adapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//Layout pro tento fragment, musi byt vraceno v return na konci onCreate
		View view = inflater.inflate( R.layout.fragment_feed, container, false );
		
		workoutList = (ListView) view.findViewById( R.id.feedListView );

		//ProgressBar se bude ukazovat dokud ListView nedostane data z Cursoru a databaze
        ProgressBar progressBar = new ProgressBar( getActivity() );
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT );
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams( params );
        progressBar.setIndeterminate( true );
        workoutList.setEmptyView( progressBar );
        
        //Pridani spinneru do korenoveho layoutu
        ViewGroup root = (ViewGroup) view.findViewById(R.id.feedLayout);
        root.addView(progressBar);
        
        //inicializace dat list view z databaze
        //TODO list view by mel nacitat jen omezene mnozstvi dat
		dataSource = new WorkoutDataSource( getActivity() );
        dataSource.open();
        
      /*  Cursor data = dataSource.getCursorAll();
        
        String[] from = new String[]{SqlHelper.COLUMN_DATE, SqlHelper.COLUMN_DURATION};
        int[] to = new int[]{R.id.main, R.id.secondary};
        
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.workout_item_row, data, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        
		workoutList.setAdapter(adapter);
		*/
        
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
