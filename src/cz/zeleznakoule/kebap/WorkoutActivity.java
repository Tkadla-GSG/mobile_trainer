package cz.zeleznakoule.kebap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import static cz.zeleznakoule.kebap.interfaces.Constants.*;

public class WorkoutActivity extends BaseActivity {

	private int ANIM_DURATION = 300;

	private boolean prepared = false;
	private int action = -1;
	
	private RelativeLayout dateLayout = null;
	private RelativeLayout lenghtLayout = null;
	private RelativeLayout dayTypeLayout = null;
	private RelativeLayout noteLayout = null;
	
	private TextView dateFieldTextView = null;
	private TextView lengthFieldTextView = null;
	private TextView noteFieldTextView = null;
	
	private LinearLayout drillList = null; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);

		// ziskani zpravy z volajici aktivity
		action = getIntent().getIntExtra("action", -1);
		
		//vytazeni referenci
		dateLayout = (RelativeLayout) findViewById(R.id.dateLayout);
		lenghtLayout = (RelativeLayout) findViewById(R.id.lengthLayout);
		dayTypeLayout = (RelativeLayout) findViewById(R.id.dayTypeLayout);
		noteLayout = (RelativeLayout) findViewById(R.id.noteLayout);
		
		dateFieldTextView = (TextView) findViewById(R.id.dateTextField);
		lengthFieldTextView = (TextView) findViewById(R.id.lengthTextField);
		noteFieldTextView = (TextView) findViewById(R.id.noteTextField);
		
		drillList = (LinearLayout) findViewById(R.id.drillList);

		// TODO use when needed (Home button)
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * Nastaveni pro editaci existujici workoutu
	 */
	private void editWorkout() {

	}

	/**
	 * Nastaveni pro existuji workout
	 */
	private void showWorkout() {
		// getIntent().getParcelableExtra(name);
	}

	/**
	 * Nastaveni pro novy workout
	 */
	private void newWorkout() {
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" ); 
		dateFieldTextView.setText( sdf.format( new Date() ));
		
		lengthFieldTextView.setText("5 min");
		
		animTransition(true);
	}
	
	public void onAddDrillBtnClick(View view){
		TextView item = (TextView) TextView.inflate(this, android.R.layout.simple_list_item_1, null );
		item.setText("Kokot");
		drillList.addView(item);
	}

	/**
	 * Animuje prechod z editacniho do prochazeciho modu
	 * @param forward true - prehravani vpred, false - prehravani vzad
	 */
	private void animTransition(boolean forward) {
		//TODO pravdepodobne by prospel presun na tridni promene
		
		int screenWidth = dateLayout.getWidth();
		int fieldWidth = dateFieldTextView.getWidth();

		ValueAnimator anim = ObjectAnimator.ofInt(0,
				-(screenWidth - fieldWidth) / 2);
		anim.setDuration(ANIM_DURATION);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			public void onAnimationUpdate(ValueAnimator animator) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						dateLayout.getLayoutParams());
				params.leftMargin = (Integer) animator.getAnimatedValue();
				
				dateLayout.setLayoutParams(params);
				lenghtLayout.setLayoutParams(params);
				dayTypeLayout.setLayoutParams(params);
				noteLayout.setLayoutParams(params);
			}
		});

		if (forward) {
			anim.start();
		} else {
			anim.reverse();
		}
	}

	/**
	 * Metoda volana pri kazdem zobrazeni activity Vyuziva se predevsim k
	 * ziskani rozmeru elementu a priprave layout, kterou neni mozne udelat v
	 * XML
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (!prepared) {
			
			//add buttons
			ImageButton dateBtn = (ImageButton) getLayoutInflater().inflate(R.drawable.edit_button, null);
			dateBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			ImageButton lengthBtn = (ImageButton) getLayoutInflater().inflate(R.drawable.edit_button, null);
			dateBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			ImageButton noteBtn = (ImageButton) getLayoutInflater().inflate(R.drawable.edit_button, null);
			dateBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					dateLayout.getLayoutParams());
			params.leftMargin = dateLayout.getWidth();
			
			dateLayout.addView(dateBtn, params);
			lenghtLayout.addView(lengthBtn, params);
			noteLayout.addView(noteBtn, params); 

			//act on actions 
			switch (action) {
			case CREATE_WORKOUT:
				newWorkout();
				break;
			case SHOW_WORKOUT:
				showWorkout();
				break;
			case EDIT_WORKOUT:
				editWorkout();
				break;
			default:
				newWorkout();
				break;
			}
			
			prepared = true;
		}	

		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_workout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {

		// TODO use when needed (Home button)
		/*
		 * switch (item.getItemId()) { case android.R.id.home:
		 * NavUtils.navigateUpFromSameTask(this); return true; }
		 */
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void initActionBar() {
		actionBar.setTitle(R.string.title_activity_workout);

	}

}
