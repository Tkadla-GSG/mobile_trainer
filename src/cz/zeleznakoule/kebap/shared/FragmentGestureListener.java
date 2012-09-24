package cz.zeleznakoule.kebap.shared;

import cz.zeleznakoule.kebap.BaseFragment;

import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class FragmentGestureListener implements View.OnTouchListener {

	private BaseFragment fragment;
	private VelocityTracker vTracker = null;

	private static int MIN_DISTANCE = 100;
	private static int THRESHOLD_VELOCITY = 200;
	private static int MAX_OFF_PATH = 250;
	private float downX, downY;

	public FragmentGestureListener(BaseFragment fragment) {
		this.fragment = fragment;

		final ViewConfiguration vc = ViewConfiguration.get(fragment
				.getActivity());
		MIN_DISTANCE = vc.getScaledTouchSlop();
		THRESHOLD_VELOCITY = vc.getScaledMinimumFlingVelocity();
	}

	public void onRightToLeftSwipe() {
		fragment.onRightToLeftSwipe();
	}

	public void onLeftToRightSwipe() {
		fragment.onLeftToRightSwipe();
	}

	public void onTopToBottomSwipe() {
		fragment.onTopToBottomSwipe();
	}

	public void onBottomToTopSwipe() {
		fragment.onBottomToTopSwipe();
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			downX = event.getX();
			downY = event.getY();

			// velocity tracking
			if (vTracker == null) {
				vTracker = VelocityTracker.obtain();
			} else {
				vTracker.clear();
			}
			vTracker.addMovement(event);

			return true;
		}
		case MotionEvent.ACTION_MOVE: {
			vTracker.addMovement(event);
			vTracker.computeCurrentVelocity(1000); // 1000 pro pixely za sekundu

			return false; // not consuming event, just measuring
		}
		case MotionEvent.ACTION_UP: {

			// path tracking
			float deltaX = downX - event.getX();
			float deltaY = downY - event.getY();

			// swipe horizontal?
			if (Math.abs(deltaX) > MIN_DISTANCE
					&& Math.abs(vTracker.getXVelocity()) > THRESHOLD_VELOCITY) {

				if (Math.abs(deltaY) > MAX_OFF_PATH) // event is to much off, discard it
					return false;

				// left or right
				if (deltaX < 0) {
					this.onLeftToRightSwipe();
					return true;
				}
				if (deltaX > 0) {
					this.onRightToLeftSwipe();
					return true;
				}
			} else {
				return false; // We don't consume the event
			}

			// swipe vertical?
			if (Math.abs(deltaY) > MIN_DISTANCE
					&& Math.abs(vTracker.getYVelocity()) > THRESHOLD_VELOCITY) {

				if (Math.abs(deltaX) > MAX_OFF_PATH) // event is to much off, discard it
					return false;

				// top or down
				if (deltaY < 0) {
					this.onTopToBottomSwipe();
					return true;
				}
				if (deltaY > 0) {
					this.onBottomToTopSwipe();
					return true;
				}
			} else {
				return false; // We don't consume the event
			}

			return true;
		}
		}
		return false;
	}

}