package cz.zeleznakoule.kebap;

import cz.zeleznakoule.kebap.shared.FragmentGestureListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class ProfileFragment extends BaseFragment {

	private ViewFlipper flipper;
	private LinearLayout indicator;

	private Animation in_left_right;
	private Animation out_left_right;
	private Animation in_right_left;
	private Animation out_right_left;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_, container,
				false);

		// load animaci
		in_left_right = AnimationUtils.loadAnimation(getActivity(),
				R.anim.in_left_right);
		out_left_right = AnimationUtils.loadAnimation(getActivity(),
				R.anim.out_left_right);
		in_right_left = AnimationUtils.loadAnimation(getActivity(),
				R.anim.in_right_left);
		out_right_left = AnimationUtils.loadAnimation(getActivity(),
				R.anim.out_right_left);

		// detekovani gest
		FragmentGestureListener swipeDetector = new FragmentGestureListener(
				this);
		view.setOnTouchListener(swipeDetector);

		// nastaveni flipperu
		flipper = (ViewFlipper) view.findViewById(R.id.flipper);

		// nastaveni indicatoru stranek
		indicator = (LinearLayout) view
				.findViewById(R.id.profile_page_indicator);
		inflater.inflate(R.drawable.page_indicator_selected, indicator); 
		for (int i = 1; i < flipper.getChildCount(); i++) {
			inflater.inflate(R.drawable.page_indicator, indicator);
		}

		setupView01(view);

		return view;
	}

	private void setupView01(View view) {

		LinearLayout holder = (LinearLayout) view
				.findViewById(R.id.stats01holder);
		holder.addView(new Stats01(getActivity()));

	}

	@Override
	public void onRightToLeftSwipe() {
		removeHighlight();
		
		flipper.setInAnimation(in_right_left);
		flipper.setOutAnimation(out_right_left);

		flipper.showNext();
		
		addHighlight();
	}

	@Override
	public void onLeftToRightSwipe() {
		removeHighlight();
		
		flipper.setInAnimation(in_left_right);
		flipper.setOutAnimation(out_left_right);

		flipper.showPrevious();
		
		addHighlight();
	}

	/**
	 * Indicator setup 
	 * replace currently selected with not selected view
	 */
	private void removeHighlight() {
		View v = (View) indicator.getChildAt(flipper.getDisplayedChild());
		v.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.circle_shape));
	}

	/**
	 * Indicator setup 
	 * replace currently not selected with selected view
	 */
	private void addHighlight(){
		View v = (View) indicator.getChildAt(flipper.getDisplayedChild());
		v.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.circle_shape_selected));		
	}
}
