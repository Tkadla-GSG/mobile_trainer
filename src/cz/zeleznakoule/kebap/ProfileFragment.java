package cz.zeleznakoule.kebap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.support.v4.app.Fragment;

public class ProfileFragment extends Fragment implements View.OnClickListener {

	private ViewFlipper flipper;
	
    private static int SWIPE_MIN_DISTANCE = 120;
    private static int SWIPE_MAX_OFF_PATH = 250;
    private static int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;
    private LinearLayout indicator;
    private View selectedIndicator;
    private View notSelectedIndicator; 


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_, container,
				false);
		
		// detekovani gest
		gestureDetector = new GestureDetector( getActivity(), new MyGestureDetector() );
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch( View v, MotionEvent event ) {
                return gestureDetector.onTouchEvent( event );
            }
        };
        
        final ViewConfiguration vc = ViewConfiguration.get( getActivity() );
        SWIPE_MIN_DISTANCE = vc.getScaledTouchSlop();
        SWIPE_THRESHOLD_VELOCITY = vc.getScaledMinimumFlingVelocity();

        // nastaveni flipperu
		flipper = (ViewFlipper) view.findViewById( R.id.flipper );
		view.setOnClickListener( this );
		view.setOnTouchListener( gestureListener );
		
		// nastaveni indicatoru stranek
	    indicator = (LinearLayout) view.findViewById( R.id.profile_page_indicator );
		
	    inflater.inflate( R.drawable.page_indicator_selected, indicator);	//first page will be always selected at first
		for(int i = 1; i < flipper.getChildCount(); i++){
			inflater.inflate( R.drawable.page_indicator, indicator);
		}
		
		//TODO nastaveni view

		return view;
	}

	@Override
	public void onClick(View v) {

	}
	
	/**
	 * Swipe listener
	 * @author Tkadla
	 *
	 */
	class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                
                // indicator setup (replace currently selected with not selected)
                View v = (View)indicator.getChildAt( flipper.getDisplayedChild() );         
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_shape));
                
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                	flipper.showPrevious();
                	
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                	flipper.showNext();
                	
                }
                
                // indicator setup (add selected page)
                v = (View)indicator.getChildAt( flipper.getDisplayedChild() );         
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_shape_selected));;
               
                
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }
}
