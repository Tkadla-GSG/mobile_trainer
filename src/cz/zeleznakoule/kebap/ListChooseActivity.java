package cz.zeleznakoule.kebap;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public abstract class ListChooseActivity extends ListActivity {

	protected RelativeLayout searchBar = null;
	protected boolean searchbarOn = false;
	protected Spinner filterSelector = null; 

	// Konstanty
	protected int PADDING_LEFT = 0;
	protected int PADDING_TOP = 0;
	protected int PADDING_RIGHT = 0;
	protected int PADDING_BOTTOM = 0;
	protected int ANIM_DURATION = 300; 

	// Animace
	protected TranslateAnimation hideSearchbarAnim = null;
	protected TranslateAnimation showSearchbarAnim = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO duplikace kodu, neexistuje dedicnost od BaseActivity
        // Odebira titulek aktivity, vcetne ikony (neni podpora pod verzi 11)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Vynucuje portrait mode pro aktivitu
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.list_choose_activity);
		
		// vytazeni potrebnych referenci
		searchBar = (RelativeLayout) findViewById(R.id.searchbar);
		filterSelector = (Spinner) findViewById(R.id.spinner_choose_excercise);
		PADDING_LEFT = getListView().getPaddingLeft();
		PADDING_TOP = getListView().getPaddingTop();
		PADDING_RIGHT = getListView().getPaddingRight();
		PADDING_BOTTOM = getListView().getPaddingBottom();
		
		initSpinner();

		initListView();

	}
	
	/**
	 * Init ListView dedeneho od ListActivity
	 */
	public abstract void initListView();

	/**
	 * Init Spinner v Actionbaru
	 */
	public abstract void initSpinner();

	/**
	 * Pripravuje animaci pro prehrani
	 * Nastaveno pro potreby vysunuti a zasunuti searchbaru
	 * @param fromXDelta
	 * @param toXDelta
	 * @param fromYDelta
	 * @param toYDelta
	 * @param duration
	 * @param padding_bottom
	 * @return
	 */
	private TranslateAnimation createAnim(int fromXDelta, int toXDelta,
			int fromYDelta, int toYDelta, int duration, final int padding_bottom) {
		TranslateAnimation anim = new TranslateAnimation(
				Animation.ABSOLUTE, fromXDelta, Animation.ABSOLUTE, toXDelta,
				Animation.ABSOLUTE, fromYDelta, Animation.ABSOLUTE, toYDelta);

		anim.setFillAfter(true);
		anim.setDuration(ANIM_DURATION);
		anim.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			// reakce na zasunuti searchbaru, obnoveni puvodniho vzhledu
			// listView
			public void onAnimationEnd(Animation animation) {
				getListView().setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT,
						padding_bottom);

			}
		});
		
		return anim;
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
			
			hideSearchbarAnim = createAnim(0, 0, searchBar.getHeight(), 0, 300, PADDING_BOTTOM);			
			searchBar.startAnimation(hideSearchbarAnim);
			getListView().startAnimation(hideSearchbarAnim);
			searchbarOn = false;

		} else { // ukaz bar
			
			showSearchbarAnim = createAnim(0, 0, 0, searchBar.getHeight(), 300, searchBar.getHeight());
			searchBar.startAnimation(showSearchbarAnim);
			getListView().startAnimation(showSearchbarAnim);
			searchbarOn = true;
		}

	}
}
