package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class FeedFragment extends Fragment {
	
	private ListView workoutList = null;

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
		
		return view; 
	}

	/**
	 * Metoda volana pri obnoveni fragmentu
	 */
	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

}
