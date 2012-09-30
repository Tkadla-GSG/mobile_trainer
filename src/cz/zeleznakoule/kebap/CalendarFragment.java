package cz.zeleznakoule.kebap;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.zeleznakoule.kebap.shared.CalendarView;
import cz.zeleznakoule.kebap.shared.CalendarCell;

import static cz.zeleznakoule.kebap.shared.Constants.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * Implementuje chovani ViewFlipperu pro potreby kalendere
 * 
 * TODO renormalizace offscreen bufferu je asi mozne provadet i v inAnimListeneru. Podle me je ale diskutabilni zda by to prineslo nejaky prospech
 * @author Tkadla
 * 
 */
public class CalendarFragment extends BaseFragment implements
		CalendarView.OnCellTouchListener, CalendarView.OnSwipeListener {

	private CalendarView mView1 = null;
	private CalendarView mView2 = null;
	private CalendarView onScreen = null;
	private CalendarView offScreen = null;
	private ViewFlipper flipper = null;
	private TextView currMonth = null;
	private ImageButton prevMonthBtn = null;
	private ImageButton nextMonthBtn = null;

	private SimpleDateFormat sdf = null;

	private Animation in_left_right;
	private Animation out_left_right;
	private Animation in_right_left;
	private Animation out_right_left;
	private boolean AnimIsRunning = false; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_calendar, container,
				false);

		// load animaci
		inListener inlistener = new inListener();
		in_left_right = AnimationUtils.loadAnimation(getActivity(),
				R.anim.in_left_right);
		in_left_right.setAnimationListener( inlistener );
		
		out_left_right = AnimationUtils.loadAnimation(getActivity(),
				R.anim.out_left_right);
		out_left_right.setAnimationListener(new leftRightListener());
		
		in_right_left = AnimationUtils.loadAnimation(getActivity(),
				R.anim.in_right_left);
		in_right_left.setAnimationListener( inlistener );
		
		out_right_left = AnimationUtils.loadAnimation(getActivity(),
				R.anim.out_right_left);
		out_right_left.setAnimationListener(new rightLeftListener());

		mView1 = (CalendarView) view.findViewById(R.id.calendar1);
		mView1.setOnCellTouchListener(this);
		mView1.setOnSwipeListener(this);

		mView2 = (CalendarView) view.findViewById(R.id.calendar2);
		mView2.setOnCellTouchListener(this);
		mView2.setOnSwipeListener(this);

		// buffer init 
		//TODO possible room for error, when mView is not in fact first child of ViewFlipper
		onScreen = mView1;
		offScreen = mView2;

		flipper = (ViewFlipper) view.findViewById(R.id.calendarFlipper);

		currMonth = (TextView) view.findViewById(R.id.currentMonth);

		prevMonthBtn = (ImageButton) view.findViewById(R.id.prevMonth);
		prevMonthBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				flipLeftRight();
			}
		});

		nextMonthBtn = (ImageButton) view.findViewById(R.id.nextMonth);
		nextMonthBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				flipRightLeft();
			}
		});

		sdf = new SimpleDateFormat("MMMM yyyy");
		setLabel();

		return view;
	}

	/**
	 * Pretoceni kalendare z prava do leva (Dalsi mesic)
	 */
	private void flipRightLeft() {
		// flip only when previous flip is complete
		if (!AnimIsRunning) {
			offScreen.nextMonth();
			switchBuffers();

			flipper.setInAnimation(in_right_left);
			flipper.setOutAnimation(out_right_left);

			flipper.showNext();

			setLabel();
		}
	}

	/**
	 * Pretoceni kalendare z leva do prava (Predchozi mesic)
	 */
	private void flipLeftRight() {
		//flip only when previous flip is complete
		if (!AnimIsRunning) {
			offScreen.previousMonth();
			switchBuffers();

			flipper.setInAnimation(in_left_right);
			flipper.setOutAnimation(out_left_right);

			flipper.showPrevious();

			setLabel();
		}
	}

	/**
	 * Aktualizuje nazev mesice 
	 */
	private void setLabel() {
		Date date = onScreen.getDisplayedMonth();
		currMonth.setText(sdf.format(date));
	}

	/**
	 * Prohazuje onScreen a offScreen buffery
	 */
	private void switchBuffers() {
		CalendarView temp = offScreen;
		offScreen = onScreen;
		onScreen = temp;
	}

	/**
	 * Reakce na klik na bunku kalendere
	 */
	public void onTouch(CalendarCell cell) {
		int year = onScreen.getYear();
		int month = onScreen.getMonth();
		int day = cell.getDayOfMonth();

		// FIX issue 6: make some correction on month and year
		if (cell instanceof CalendarView.DisabledCell) {
			// oops, not pick current month...
			if (day < 15) {
				// pick one beginning day? then a next month day
				if (month == 11) {
					month = 0;
					year++;
				} else {
					month++;
				}

			} else {
				// otherwise, previous month
				if (month == 0) {
					month = 11;
					year--;
				} else {
					month--;
				}
			}
		}

		// create new activity to fill new workout
		Intent ret = new Intent(getActivity(), WorkoutActivity.class);
		ret.putExtra("year", year);
		ret.putExtra("month", month);
		ret.putExtra("day", day);
		ret.putExtra("action", NEW_WORKOUT_FROM_DATE);
		startActivity(ret);

		return;
	}

	public void OnLeftToRightSwipe() {
		flipRightLeft();
	}

	public void OnRightToLeftSwipe() {
		flipLeftRight();
	}

	public void OnTopToBottomSwipe() {
	}

	public void OnBottomToTopSwipe() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	/**
	 * Privatni trida listener in animaci viewflipperu
	 * Udrzuje prehled o tom, jestli viewflipper prave provadi animace (AnimIsRunning)
	 * @author Tkadla
	 *
	 */
	class inListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {	}

		@Override
		public void onAnimationRepeat(Animation animation) {	}

		@Override
		public void onAnimationStart(Animation animation) {	
			AnimIsRunning = true; 
		}
		
	}
	
	/**
	 * Privatni trida listener out animaci viewflipperu
	 * Udrzuje prehled o tom, jestli viewflipper prave provadi animace (AnimIsRunning)
	 * @author Tkadla
	 *
	 */
	class outListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			AnimIsRunning = false; 
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	}

	/**
	 * Privatni trida listener out animaci
	 * Provadi specificky kod nutny pro provoz flipperu pri leftRight swipu
	 * @author Tkadla
	 *
	 */
	class leftRightListener extends outListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			offScreen.previousMonth();
			super.onAnimationEnd(animation);
		}

	}

	/**
	 * Privatni trida listener out animaci
	 * Provadi specificky kod nutny pro provoz flipperu pri rightLeft swipu
	 * @author Tkadla
	 *
	 */
	class rightLeftListener extends outListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			offScreen.nextMonth();
			super.onAnimationEnd(animation);
		}

	}

}
