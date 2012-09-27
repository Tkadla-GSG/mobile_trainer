
package cz.zeleznakoule.kebap.shared;

import java.util.Calendar;

import cz.zeleznakoule.kebap.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.ImageView;

public class CalendarView extends ImageView {

	private static int CELL_WIDTH = 58;
	private static int CELL_HEIGH = 53;
	private static int CELL_MARGIN_TOP = 0;
	private static int CELL_MARGIN_LEFT = 0;
	private static float CELL_TEXT_SIZE;

	private Calendar mRightNow = null;
	private Cell mToday = null;
	private Cell mSelectedCell = null;
	private Cell[][] mCells = new Cell[6][7];
	private OnCellTouchListener mOnCellTouchListener = null;
	private OnSwipeListener mOnSwipeListener = null; 
	private MonthDisplayHelper mHelper;

	private VelocityTracker vTracker = null;
	private static int MIN_DISTANCE = 100;
	private static int THRESHOLD_VELOCITY = 200;
	private static int MAX_OFF_PATH = 250;
	private float downX, downY;

	public interface OnCellTouchListener {
		public void onTouch(Cell cell);
	}
	
	public interface OnSwipeListener {
		public void OnLeftToRightSwipe();
		public void OnRightToLeftSwipe();
		public void OnTopToBottomSwipe();
		public void OnBottomToTopSwipe();
	}
	
	public CalendarView(Context context) {
		this(context, null);
	}

	public CalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CalendarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		final ViewConfiguration vc = ViewConfiguration.get(context);
		MIN_DISTANCE = vc.getScaledTouchSlop();
		THRESHOLD_VELOCITY = vc.getScaledMinimumFlingVelocity();

		initCalendarView();
	}

	private void initCalendarView() {
		mRightNow = Calendar.getInstance();

		Resources res = getResources(); 
		CELL_TEXT_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 
				res.getDimension(R.dimen.small_text), res.getDisplayMetrics()) ;

		mHelper = new MonthDisplayHelper(mRightNow.get(Calendar.YEAR),
				mRightNow.get(Calendar.MONTH), mRightNow.getFirstDayOfWeek());

	}

	private void initCells() {
		class _calendar {
			public int day;
			public boolean thisMonth;

			public _calendar(int d, boolean b) {
				day = d;
				thisMonth = b;
			}

			public _calendar(int d) {
				this(d, false);
			}
		}
		;
		_calendar tmp[][] = new _calendar[6][7];

		for (int i = 0; i < tmp.length; i++) {
			int n[] = mHelper.getDigitsForRow(i);
			for (int d = 0; d < n.length; d++) {
				if (mHelper.isWithinCurrentMonth(i, d))
					tmp[i][d] = new _calendar(n[d], true);
				else
					tmp[i][d] = new _calendar(n[d]);

			}
		}

		Calendar today = Calendar.getInstance();
		int thisDay = 0;
		mToday = null;
		if (mHelper.getYear() == today.get(Calendar.YEAR)
				&& mHelper.getMonth() == today.get(Calendar.MONTH)) {
			thisDay = today.get(Calendar.DAY_OF_MONTH);
		}
		// build cells
		Rect Bound = new Rect(CELL_MARGIN_LEFT, CELL_MARGIN_TOP, CELL_WIDTH
				+ CELL_MARGIN_LEFT, CELL_HEIGH + CELL_MARGIN_TOP);

		for (int week = 0; week < mCells.length; week++) {
			for (int day = 0; day < mCells[week].length; day++) {
				if (tmp[week][day].thisMonth) {
					// mark weekends
					if (mHelper.getWeekStartDay() == Calendar.MONDAY
							&& day == 5 || day == 6)
						mCells[week][day] = new WeekendCell(getContext(), tmp[week][day].day,
								new Rect(Bound), CELL_TEXT_SIZE);
					else if (mHelper.getWeekStartDay() == Calendar.SATURDAY
							&& day == 0 || day == 6)
						mCells[week][day] = new WeekendCell(getContext(), tmp[week][day].day,
								new Rect(Bound), CELL_TEXT_SIZE);
					else
						mCells[week][day] = new Cell(getContext(), tmp[week][day].day,
								new Rect(Bound), CELL_TEXT_SIZE);
				} else {
					mCells[week][day] = new DisabledCell(getContext(), tmp[week][day].day,
							new Rect(Bound), CELL_TEXT_SIZE);
				}

				Bound.offset(CELL_WIDTH, 0); // move to next column

				// get today
				if (tmp[week][day].day == thisDay && tmp[week][day].thisMonth) {
					mToday = mCells[week][day];
				}
			}
			Bound.offset(0, CELL_HEIGH); // move to next row and first column
			Bound.left = CELL_MARGIN_LEFT;
			Bound.right = CELL_MARGIN_LEFT + CELL_WIDTH;
		}
	}

	@Override
	public void onLayout(boolean changed, int left, int top, int right,
			int bottom) {

		/**
		 * Prizpusobi velikost kalendare velikosti obrazivky (velikosti parenta)
		 */
		CELL_WIDTH = getWidth() / mCells[0].length;
		CELL_HEIGH = getHeight() / mCells.length;

		initCells();
		super.onLayout(changed, left, top, right, bottom);
	}

	public void setTimeInMillis(long milliseconds) {
		mRightNow.setTimeInMillis(milliseconds);
		initCells();
		this.invalidate();
	}

	public int getYear() {
		return mHelper.getYear();
	}

	public int getMonth() {
		return mHelper.getMonth();
	}

	public void nextMonth() {
		mHelper.nextMonth();
		initCells();
		invalidate();
	}

	public void previousMonth() {
		mHelper.previousMonth();
		initCells();
		invalidate();
	}

	public boolean firstDay(int day) {
		return day == 1;
	}

	public boolean lastDay(int day) {
		return mHelper.getNumberOfDaysInMonth() == day;
	}

	public void goToday() {
		Calendar cal = Calendar.getInstance();
		mHelper = new MonthDisplayHelper(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH));
		initCells();
		invalidate();
	}

	public Calendar getDate() {
		return mRightNow;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			for (Cell[] week : mCells) {
				for (Cell day : week) {
					if (day.hitTest((int) event.getX(), (int) event.getY())) {
						day.setSelected();
						invalidate(day.getBound());
						mSelectedCell = day;
					}
				}
			}

			downX = event.getX();
			downY = event.getY();

			// velocity tracking
			if (vTracker == null) {
				vTracker = VelocityTracker.obtain();
			} else {
				vTracker.clear();
			}
			vTracker.addMovement(event);

			break;

		case MotionEvent.ACTION_MOVE:

			for (Cell[] week : mCells) {
				for (Cell day : week) {
					if (day.hitTest((int) event.getX(), (int) event.getY())) {
						// pokud se posunu mimo vybrany datum, vypnu zvyrazneni
						if (mSelectedCell != null && !day.isSelected()) {
							mSelectedCell.setNotSelected();
							invalidate(mSelectedCell.getBound());
							mSelectedCell = null;

						}
					}
				}
			}

			vTracker.addMovement(event);
			vTracker.computeCurrentVelocity(1000); // 1000 pro pixely za sekundu

			break;

		case MotionEvent.ACTION_UP:

			// Nektera bunka je vybrana, jedna se o klik na datum
			if (mSelectedCell != null) { 
				mSelectedCell.setNotSelected();
				invalidate(mSelectedCell.getBound());
				mOnCellTouchListener.onTouch(mSelectedCell);
				mSelectedCell = null;
				
			} else {
			
				// path tracking
				float deltaX = downX - event.getX();
				float deltaY = downY - event.getY();

				// swipe horizontal?
				if (Math.abs(deltaX) > MIN_DISTANCE
						&& Math.abs(vTracker.getXVelocity()) > THRESHOLD_VELOCITY) {

					if (Math.abs(deltaY) > MAX_OFF_PATH)
						return false;

					// left or right
					if (deltaX < 0) {
						mOnSwipeListener.OnRightToLeftSwipe();							
						return true;
					}
					if (deltaX > 0) {
						mOnSwipeListener.OnLeftToRightSwipe();			
						return true;
					}
				} else {
					return false; // We don't consume the event
				}

				// swipe vertical?
				if (Math.abs(deltaY) > MIN_DISTANCE
						&& Math.abs(vTracker.getYVelocity()) > THRESHOLD_VELOCITY) {

					if (Math.abs(deltaX) > MAX_OFF_PATH)
						return false;

					// top or down
					if (deltaY < 0) {
						mOnSwipeListener.OnBottomToTopSwipe();
						return true;
					}
					if (deltaY > 0) {
						mOnSwipeListener.OnTopToBottomSwipe();
						return true;
					}
				} else {
					return false; // We don't consume the event
				}

				return true;
			}

			break;
		default:
			break;
		}

		return true;
	}

	public void setOnCellTouchListener(OnCellTouchListener p) {
		mOnCellTouchListener = p;
	}
	
	public void setOnSwipeListener(OnSwipeListener p) {
		mOnSwipeListener = p;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw background
		super.onDraw(canvas);

		// draw today
		if (mToday != null && !mToday.equals(mSelectedCell)) {
			mToday.isToday();
		}

		// draw cells
		for (Cell[] week : mCells) {
			for (Cell day : week) {
				day.draw(canvas);
			}
		}
	}

	public class EventCell extends Cell {
		public EventCell(Context context, int dayOfMon, Rect rect, float s) {
			super(context, dayOfMon, rect, s);
			textColor = context.getResources().getColor(R.color.KebapRed);
			setTextColor(context.getResources().getColor(R.color.KebapRed), true);
		}
	}

	public class DisabledCell extends Cell {
		public DisabledCell(Context context, int dayOfMon, Rect rect, float s) {
			super(context, dayOfMon, rect, s);
			textColor = Color.LTGRAY;
			setTextColor(Color.LTGRAY);
		}
	}

	private class WeekendCell extends Cell {
		public WeekendCell(Context context, int dayOfMon, Rect rect, float s) {
			super(context, dayOfMon, rect, s);
			textColor = context.getResources().getColor(R.color.KebapLightRed);
			setTextColor(context.getResources().getColor(R.color.KebapLightRed));
		}

	}

}
