package cz.zeleznakoule.kebap;

import cz.zeleznakoule.kebap.shared.CalendarView;
import cz.zeleznakoule.kebap.shared.Cell;

import static cz.zeleznakoule.kebap.shared.Constants.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * TODO Trida by chtela tezkou udrzbu a zjednoduseni
 * 
 * @author Tkadla
 * 
 */
public class CalendarFragment extends BaseFragment implements
		CalendarView.OnCellTouchListener, CalendarView.OnSwipeListener {

	private CalendarView mView = null;
	private ImageButton prevMonthBtn = null;
	private ImageButton nextMonthBtn = null; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_calendar, container,
				false);

		mView = (CalendarView) view.findViewById(R.id.calendar);
		mView.setOnCellTouchListener(this);
		mView.setOnSwipeListener(this);
		
		prevMonthBtn = (ImageButton) view.findViewById( R.id.prevMonth );
		prevMonthBtn.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mView.previousMonth(); 
			}
		} );
		
		nextMonthBtn = (ImageButton) view.findViewById( R.id.nextMonth );
		nextMonthBtn.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mView.nextMonth(); 
			}
		} );

		return view;
	}

	/**
	 * Implements reaction on calendar cell click
	 */
	public void onTouch(Cell cell) {
		int year = mView.getYear();
		int month = mView.getMonth();
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
		Intent ret = new Intent( getActivity(), WorkoutActivity.class);
		ret.putExtra("year", year);
		ret.putExtra("month", month);
		ret.putExtra("day", day);
		ret.putExtra("action", NEW_WORKOUT_FROM_DATE);
		startActivity(ret);

		return;
	}

	public void OnLeftToRightSwipe() {
		mView.previousMonth();
	}

	public void OnRightToLeftSwipe() {
		mView.nextMonth();
	}

	public void OnTopToBottomSwipe() {
		// TODO Auto-generated method stub

	}

	public void OnBottomToTopSwipe() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
}
