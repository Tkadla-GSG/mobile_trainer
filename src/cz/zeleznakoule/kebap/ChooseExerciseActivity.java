package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ChooseExercise extends BaseActivity {

	private Spinner filterSelector = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_exercise);

		initSpinner(); 
		
	}

	private void initSpinner() {
		filterSelector = (Spinner)findViewById(R.id.spinner_choose_excercise);
		
		//TODO populate spinner z databaze
		//TODO replace spinner layout with custom implementation
		ArrayAdapter <CharSequence> adapter =
				  new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
						
		//TODO remove when database is in use
		adapter.add("RKC");
		adapter.add("HKC");
		adapter.add("Kettlebell muscle");
		adapter.add("All");
        
        //format polozky dropdown menu 
		//TODO replace spinner dropdown layout with custom implementation
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        filterSelector.setAdapter(adapter);
        
        // Spinner onSelect listener
        filterSelector.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						//TODO implementovat reakci na vybrani 
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// Not implemented
					}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_choose_exercise, menu);
		return true;
	}

}
