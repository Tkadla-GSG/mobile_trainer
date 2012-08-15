package cz.zeleznakoule.kebap;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ObjectAnimator;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public abstract class ListChooseActivity extends ListActivity {

	protected RelativeLayout searchBar = null;
	protected boolean searchbarOn = false;
	protected Spinner filterSelector = null; 

	// Konstanty
	protected int ANIM_DURATION = 300; 

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
		filterSelector = (Spinner) findViewById(R.id.spinner_list_choose);
		
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
	 * Implementuje reakci na onClick udalost tlacitka @+id/toogleSearchBtn
	 * 
	 * @param view
	 */
	public void ontoggleSearchBtnClick(View view) {
		
		ValueAnimator anim = ObjectAnimator.ofInt(0, searchBar.getHeight());
		anim.setDuration(ANIM_DURATION);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			
			public void onAnimationUpdate(ValueAnimator animator) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( getListView().getLayoutParams() );
				params.topMargin = (Integer) animator.getAnimatedValue();
				getListView().setLayoutParams(params);
				
				params = new RelativeLayout.LayoutParams(searchBar.getLayoutParams());
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
