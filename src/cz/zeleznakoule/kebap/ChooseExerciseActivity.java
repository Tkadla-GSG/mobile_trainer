package cz.zeleznakoule.kebap;

import android.R.bool;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ChooseExerciseActivity extends ListActivity {

	private RelativeLayout searchBar = null;
	private boolean searchbarOn = false;
	public ListView listView = null;

	// Konstanty
	private int PADDING_LEFT = 0;
	private int PADDING_TOP = 0;
	private int PADDING_RIGHT = 0;
	private int PADDING_BOTTOM = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_exercise);

		initSpinner();

		// init listu
		// TODO pupulate from database
		String[] data = { "Kokot", "Holota", "Iskuda", "Tkadla", "Banjo",
				"Vedro", "Rybik", "Bus" };
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data));

		// vytazeni potrebnych referenci
		searchBar = (RelativeLayout) findViewById(R.id.searchbar);
		listView = this.getListView();
		PADDING_LEFT = listView.getPaddingLeft();
		PADDING_TOP = listView.getPaddingTop();
		PADDING_RIGHT = listView.getPaddingRight();
		PADDING_BOTTOM = listView.getPaddingBottom();

	}

	private void initSpinner() {
		Spinner filterSelector = (Spinner) findViewById(R.id.spinner_choose_excercise);

		// TODO populate spinner z databaze
		// TODO replace spinner layout with custom implementation
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);

		// TODO remove when database is in use
		adapter.add("RKC");
		adapter.add("HKC");
		adapter.add("Kettlebell muscle");
		adapter.add("All");

		// format polozky dropdown menu
		// TODO replace spinner dropdown layout with custom implementation
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

	/**
	 * Implementuje reakci na onClick udalost tlacitka @+id/toogleSearchBtn
	 * 
	 * @param view
	 *            TODO optimalizovat presunout animace do XML, vyhodit hardcoded
	 *            hodnoty
	 */
	public void ontoggleSearchBtnClick(View view) {

		if (searchbarOn) { // schovej bar
			TranslateAnimation hideSearchbarAnim = new TranslateAnimation(
					Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
					Animation.ABSOLUTE, searchBar.getHeight(),
					Animation.ABSOLUTE, 0);
			hideSearchbarAnim.setFillAfter(true);
			hideSearchbarAnim.setDuration(300);
			hideSearchbarAnim.setAnimationListener(new AnimationListener() {

				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				// reakce na zasunuti searchbaru, obnoveni puvodniho vzhledu
				// listView
				public void onAnimationEnd(Animation animation) {
					listView.setPadding(PADDING_LEFT, PADDING_TOP,
							PADDING_RIGHT, PADDING_BOTTOM);

				}
			});

			searchBar.startAnimation(hideSearchbarAnim);
			this.getListView().startAnimation(hideSearchbarAnim);
			searchbarOn = false;

		} else { // ukaz bar
			TranslateAnimation showSearchbarAnim = new TranslateAnimation(
					Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
					Animation.ABSOLUTE, 0, Animation.ABSOLUTE,
					searchBar.getHeight());
			showSearchbarAnim.setFillAfter(true);
			showSearchbarAnim.setDuration(300);
			showSearchbarAnim.setAnimationListener(new AnimationListener() {

				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}
				
				//reakce na spusteni searchbaru, padding_bottom zajisti, ze cely listview zustane citelny a scrollable i po posnuti dolu
				public void onAnimationEnd(Animation animation) {
					listView.setPadding(PADDING_LEFT, PADDING_TOP,
							PADDING_RIGHT, searchBar.getHeight());

				}
			});
			searchBar.startAnimation(showSearchbarAnim);
			listView.startAnimation(showSearchbarAnim);
			searchbarOn = true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_choose_exercise, menu);
		return true;
	}

}
