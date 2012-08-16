package cz.zeleznakoule.kebap;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.*;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

import android.widget.ListView;
import android.widget.RelativeLayout;

public abstract class ListChooseActivity extends SherlockActivity implements
		ActionBar.OnNavigationListener {

	private RelativeLayout searchBar = null;
	protected ListView listView = null;
	private int actionBarHeight = 0; 
	
	// Flags
	private boolean searchbarOn = false;
	private boolean measured = false; 

	// Konstanty
	private int ANIM_DURATION = 300;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO duplikace kodu, neexistuje dedicnost od BaseActivity
		// Odebira titulek aktivity, vcetne ikony (neni podpora pod verzi 11)
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Vynucuje portrait mode pro aktivitu
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Actionbar theme
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);

		setContentView(R.layout.list_choose_activity);

		// vytazeni potrebnych referenci
		searchBar = (RelativeLayout) findViewById(R.id.searchbar);
		listView = (ListView) findViewById(R.id.list);

		// inicializace
		initActionBar();
		initListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(R.string.search_btn)
				.setIcon(R.drawable.search_button_selector)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO menu by se melo tahat z XML, zde by se melo swithovat pomoci
		// item, getItemId()
		if (item.getTitle().toString().equals(getString(R.string.search_btn))) { // menu toggleSearch Btn
			onToggleSearchBtnClick();
			return true;
		}

		return super.onOptionsItemSelected(item);

	}

	/**
	 * Metoda volana pri kazdem zobrazeni activity
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(!measured){
			actionBarHeight = getSupportActionBar().getHeight();
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( searchBar.getLayoutParams() );
			params.height = actionBarHeight;
			params.topMargin = -actionBarHeight;
			searchBar.setLayoutParams(params);
			
			measured = true;
		}
		super.onWindowFocusChanged(hasFocus);
	}

	/**
	 * Init ListView dedeneho od ListActivity
	 */
	public abstract void initListView();

	/**
	 * Init Spinner v Actionbaru
	 */
	public abstract void initActionBar();

	/**
	 * Implementuje reakci na onClick udalost tlacitka @+id/toogleSearchBtn
	 */
	public void onToggleSearchBtnClick() {
		ValueAnimator anim = ObjectAnimator.ofInt(-actionBarHeight, 0);
		anim.setDuration(ANIM_DURATION);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			public void onAnimationUpdate(ValueAnimator animator) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						listView.getLayoutParams());
				params.topMargin = (Integer) animator.getAnimatedValue() + actionBarHeight;
				listView.setLayoutParams(params);

				params = new RelativeLayout.LayoutParams(searchBar
						.getLayoutParams());
				params.topMargin = (Integer) animator.getAnimatedValue();
				searchBar.setLayoutParams(params);
			}
		});

		if (searchbarOn) { // schovej bar
			anim.reverse();
			searchbarOn = false;

		} else { // ukaz bar
			anim.start();
			searchbarOn = true;
		}

	}
}
