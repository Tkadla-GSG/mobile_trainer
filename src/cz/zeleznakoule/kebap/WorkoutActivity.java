package cz.zeleznakoule.kebap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import cz.zeleznakoule.kebap.model.datasources.DayTypeDataSource;
import cz.zeleznakoule.kebap.model.datasources.WorkoutDataSource;
import cz.zeleznakoule.kebap.model.entities.DayType;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import static cz.zeleznakoule.kebap.interfaces.Constants.*;

public class WorkoutActivity extends BaseFragmentActivity {

	private int ANIM_DURATION = 300;
	private int FAST_ANIM = 1;

	private boolean prepared = false;
	private int action = -1;

	private Spinner dayType = null;

	private RelativeLayout dateLayout = null;
	private RelativeLayout lenghtLayout = null;
	private RelativeLayout dayTypeLayout = null;
	private RelativeLayout noteLayout = null;

	private TextView dateFieldTextView = null;
	private TextView lengthFieldTextView = null;
	private EditText noteFieldTextView = null;

	private Button addDrillBtn = null;
	private Button saveWorkoutBtn = null;

	private LinearLayout drillList = null;

	private LayoutInflater mInflater = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);

		// ziskani zpravy z volajici aktivity
		action = getIntent().getIntExtra("action", -1);

		// naplneni spinneru daytype
		dayType = (Spinner) findViewById(R.id.dayTypeSpinner);
		DayTypeDataSource data = new DayTypeDataSource(this);
		data.open();
		ArrayAdapter<String> adapter = data.getArrayAdapter(this);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		data.close();
		dayType.setAdapter(adapter);

		// vytazeni referenci
		dateLayout = (RelativeLayout) findViewById(R.id.dateLayout);
		lenghtLayout = (RelativeLayout) findViewById(R.id.lengthLayout);
		dayTypeLayout = (RelativeLayout) findViewById(R.id.dayTypeLayout);
		noteLayout = (RelativeLayout) findViewById(R.id.noteLayout);

		dateFieldTextView = (TextView) findViewById(R.id.dateTextField);
		lengthFieldTextView = (TextView) findViewById(R.id.lengthTextField);
		noteFieldTextView = (EditText) findViewById(R.id.noteTextField);

		addDrillBtn = (Button) findViewById(R.id.addDrillBtn);
		saveWorkoutBtn = (Button) findViewById(R.id.saveWorkoutBtn);

		drillList = (LinearLayout) findViewById(R.id.drillList);

		mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		// TODO use when needed (Home button)
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * Nastaveni pro editaci existujici workoutu
	 */
	private void editWorkout() {

	}

	/**
	 * Nastaveni pro existuji workout TODO reakce na id, ktere neexistuje
	 */
	private void showWorkout() {
		int id = getIntent().getIntExtra("id", 0);

		WorkoutDataSource datasource = new WorkoutDataSource(this);
		datasource.open();

		lengthFieldTextView.setText("12 min 32 sec");
		dateFieldTextView.setText("09/09/2013");
		noteFieldTextView.setText("Muj nejlepsi workout");

		/* REMOVE */
		int i = 6;
		while (i-- > 0) {
			View row = mInflater.inflate(R.layout.drill_item_row, null);

			TextView main = (TextView) row.findViewById(R.id.main);
			main.setText("Dummy");

			TextView secondary = (TextView) row.findViewById(R.id.secondary);
			secondary.setText("Browsable workout");

			TextView idField = (TextView) row.findViewById(R.id.item_id);
			idField.setText("" + counter);

			drillList.addView(row);
		}

		datasource.close();

		addDrillBtn.setVisibility(View.GONE);
		saveWorkoutBtn.setVisibility(View.GONE);
		noteFieldTextView.setFocusable(false);
		dayType.setEnabled(false);

		// TODO show progress bar during database loading
		// TODO parse workout
	}

	/**
	 * Nastaveni pro novy workout
	 */
	private void newWorkout() {
		// Pro novy workout dnesni datum
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dateFieldTextView.setText(sdf.format(new Date()));

		lengthFieldTextView.setText("5 min 0 sec");

		makeWorkoutEditable();

		animTransition(true, FAST_ANIM);
	}

	/**
	 * Nastaveni prostredi pro editovani
	 */
	private void makeWorkoutEditable() {

		addDrillBtn.setVisibility(View.VISIBLE);
		saveWorkoutBtn.setVisibility(View.VISIBLE);
		noteFieldTextView.setFocusable(true);
		dayType.setEnabled(true);
	}

	/**
	 * Implementuje reakci na onClick udalost tlacitka Save Workout btn Uklada
	 * hotovy workout do databaze
	 * 
	 * @param view
	 */
	public void onSaveWorkoutBtnClick(View view) {
		WorkoutDataSource data = new WorkoutDataSource(this);
		DayTypeDataSource dayTypes = new DayTypeDataSource(this);

		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = (Date) sdf.parse((String) dateFieldTextView.getText());
		} catch (ParseException e) {
			// No need to act on exception, cal already has todays date
		}

		dayTypes.open();
		DayType day = dayTypes.getByName((String) dayType.getSelectedItem());
		dayTypes.close();

		// TODO: Opravit prasování duration
		data.open();
		data.create(date, 10, noteFieldTextView.getText().toString(), day);
		data.close();

		Intent i = new Intent(this, MainActivity.class);
		startActivityForResult(i, 0);
	}

	/**
	 * Implementuje reakci na onClick udalost tlacitka Remove btn Odebira
	 * polozku z listu TODO doplnit zasahy to databaze
	 * 
	 * @param view
	 */
	public void onRemoveBtnClick(View view) {
		drillList.removeView((View) view.getParent());
		Log.d("workoutActivity onremove",
				"removed item with id "
						+ ((TextView) ((View) view.getParent())
								.findViewById(R.id.item_id)).getText());
	}

	/**
	 * Implementuje reakci na onClick udalost tlacitka Add Drill Pridava novou
	 * polozku do listu TODO doplnit zasahy to databaze TODO remove counter
	 * variable
	 * 
	 * @param view
	 */
	int counter = 0;

	public void onAddDrillBtnClick(View view) {

		Intent i = new Intent(view.getContext(), ChooseExerciseActivity.class);
		startActivityForResult(i, 0);

		/* REMOVE */
		View row = mInflater.inflate(R.layout.drill_item_row, null);

		TextView main = (TextView) row.findViewById(R.id.main);
		main.setText("kokot " + counter);

		TextView secondary = (TextView) row.findViewById(R.id.secondary);
		secondary
				.setText("super velky chupr dupr kokot s dlouhym textem este vetsi kokot");

		TextView id = (TextView) row.findViewById(R.id.item_id);
		id.setText("" + counter);

		drillList.addView(row);

		counter++;
	}

	/**
	 * Animuje prechod z editacniho do prochazeciho modu
	 * 
	 * @param forward
	 *            true - prehravani vpred, false - prehravani vzad
	 */
	private void animTransition(boolean forward, int duration) {
		// TODO pravdepodobne by prospel presun na tridni promene

		int screenWidth = dateLayout.getWidth();
		int fieldWidth = dateFieldTextView.getWidth();

		ValueAnimator anim = ObjectAnimator.ofInt(0, -3
				* (screenWidth - fieldWidth) / 4);
		anim.setDuration(duration);
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

			// add buttons
			ImageButton dateBtn = (ImageButton) getLayoutInflater().inflate(
					R.drawable.edit_button, null);
			dateBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					DialogFragment fragment = new DatePickerFragment();
					fragment.show(getSupportFragmentManager(), "datePicker");
				}
			});

			ImageButton lengthBtn = (ImageButton) getLayoutInflater().inflate(
					R.drawable.edit_button, null);
			lengthBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DialogFragment fragment = new LengthPickerFragment();
					fragment.show(getSupportFragmentManager(), "lengthPicker");
				}
			});

			ImageButton noteBtn = (ImageButton) getLayoutInflater().inflate(
					R.drawable.edit_button, null);
			noteBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					manager.showSoftInput(noteFieldTextView,
							InputMethodManager.SHOW_FORCED);
				}
			});

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					dateLayout.getLayoutParams());
			params.leftMargin = dateLayout.getWidth();

			dateLayout.addView(dateBtn, params);
			lenghtLayout.addView(lengthBtn, params);
			noteLayout.addView(noteBtn, params);

			// act on actions
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

		switch (action) {
		case CREATE_WORKOUT:
			getSupportMenuInflater()
					.inflate(R.menu.activity_workout_edit, menu);
			break;
		case SHOW_WORKOUT:
			getSupportMenuInflater()
					.inflate(R.menu.activity_workout_show, menu);
			break;
		case EDIT_WORKOUT:
			getSupportMenuInflater()
					.inflate(R.menu.activity_workout_edit, menu);
			break;
		default:
			getSupportMenuInflater()
					.inflate(R.menu.activity_workout_edit, menu);
			break;
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {

		switch (item.getItemId()) {
		//case android.R.id.home:
			//NavUtils.navigateUpFromSameTask(this);
			//return true;
		case R.id.menu_editworkout:
			
			action = EDIT_WORKOUT; 
			
			animTransition(true, ANIM_DURATION);
			
			//TODO change menu
			//getSupportMenuInflater().inflate(R.menu.activity_workout_edit, null);
			
			return true;
		case R.id.menu_loadpreset:
			return true; 
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void initActionBar() {
		actionBar.setTitle(R.string.title_activity_workout);

	}

}
