package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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
		
		setupView01(view);
	
		return view;
	}

	private void setupView01(View view) {
		
		LinearLayout holder = (LinearLayout) view.findViewById( R.id.stats01holder );
		holder.addView( new Stats01(getActivity()) );
		
	}

	@Override
	public void onClick(View v) {

	}
	
	/**
	 * Swipe listener
	 * @author Tkadla
	 *
	 */
	private class MyGestureDetector extends SimpleOnGestureListener {
		
		Animation in_left_right =  AnimationUtils.loadAnimation( getActivity(), R.anim.in_left_right);
		Animation out_left_right =  AnimationUtils.loadAnimation( getActivity(), R.anim.out_left_right);
		Animation in_right_left =  AnimationUtils.loadAnimation( getActivity(), R.anim.in_right_left);
		Animation out_right_left =  AnimationUtils.loadAnimation( getActivity(), R.anim.out_right_left);
		
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

                	flipper.setInAnimation( in_right_left );
            		flipper.setOutAnimation( out_right_left );
                	
                	flipper.showNext();
                	
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	
                	flipper.setInAnimation( in_left_right );
            		flipper.setOutAnimation( out_left_right );
                	
                	flipper.showPrevious();
                	
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
