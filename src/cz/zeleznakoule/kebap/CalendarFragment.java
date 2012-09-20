package cz.zeleznakoule.kebap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static cz.zeleznakoule.kebap.interfaces.Constants.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

/**
 * TODO Trida by chtela tezkou udrzbu a zjednoduseni
 * @author Tkadla
 *
 */
public class CalendarFragment extends Fragment implements OnClickListener {

    private static int SWIPE_MIN_DISTANCE = 120;
    private static int SWIPE_MAX_OFF_PATH = 250;
    private static int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;
	
	private Button currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	private int month, year;
	private static final String dateTemplate = "MMMM yyyy";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.calendar_view, container, false);
		
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

        // nastaveni kalendare
		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);

		prevMonth = (ImageView) view.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (Button) view.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));

		nextMonth = (ImageView) view.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) view.findViewById(R.id.calendar);

		// Initialised
		adapter = new GridCellAdapter(getActivity(),
				R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		
		// TODO nefunguje spravne
		calendarView.setOnTouchListener(gestureListener);

		return view;
	}

	/**
	 * 
	 * @param month
	 * @param year
	 */
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(getActivity(),
				R.id.calendar_day_gridcell, month, year);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	public void onClick(View view) {
		if (view == prevMonth) {
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}

			setGridCellAdapterToDate(month, year);
		}
		if (view == nextMonth) {
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}

			setGridCellAdapterToDate(month, year);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * Privatni trida pro nakladani s rastrem kalendare
	 * 
	 * @author Tkadla
	 * 
	 */
	private class GridCellAdapter extends BaseAdapter implements
			OnClickListener {
		private static final String tag = "GridCellAdapter";
		private final Context _context;

		private final List<String> list;
		private static final int DAY_OFFSET = 1;

		// TODO language specific
		private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
				"Wed", "Thu", "Fri", "Sat" };
		private final String[] months = { "January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
				31, 30, 31 };
		private int daysInMonth;
		private int currentDayOfMonth;
		private int currentWeekDay;
		private Button gridcell;
		private TextView num_events_per_day;
		private final HashMap<String, Integer> eventsPerMonthMap;

		// Days in Current Month
		public GridCellAdapter(Context context, int textViewResourceId,
				int month, int year) {
			super();
			this._context = context;
			this.list = new ArrayList<String>();
			Calendar calendar = Calendar.getInstance();
			setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
			setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

			// Print Month
			printMonth(month, year);

			// Find events in month
			eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
		}

		private String getMonthAsString(int i) {
			return months[i];
		}

		private String getWeekDayAsString(int i) {
			return weekdays[i];
		}

		private int getNumberOfDaysOfMonth(int i) {
			return daysOfMonth[i];
		}

		public String getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		/**
		 * Prints Month
		 * 
		 * @param mm
		 * @param yy
		 */
		private void printMonth(int mm, int yy) {
			// The number of days to leave blank at
			// the start of this month.
			int trailingSpaces = 0;
			int daysInPrevMonth = 0;
			int prevMonth = 0;
			int prevYear = 0;
			int nextMonth = 0;
			int nextYear = 0;

			int currentMonth = mm - 1;
			String currentMonthName = getMonthAsString(currentMonth);
			daysInMonth = getNumberOfDaysOfMonth(currentMonth);

			// Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
			GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

			if (currentMonth == 11) {
				prevMonth = currentMonth - 1;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 0;
				prevYear = yy;
				nextYear = yy + 1;
			} else if (currentMonth == 0) {
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 1;
			} else {
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			}

			// Compute how much to leave before before the first day of the
			// month.
			// getDay() returns 0 for Sunday.
			int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
			trailingSpaces = currentWeekDay;

			if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
				++daysInMonth;
			}

			// Trailing Month days
			for (int i = 0; i < trailingSpaces; i++) {
				list.add(String
						.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
								+ i)
						+ "-GREY"
						+ "-"
						+ getMonthAsString(prevMonth)
						+ "-"
						+ prevYear);
			}

			// Current Month Days
			for (int i = 1; i <= daysInMonth; i++) {

				if (i == getCurrentDayOfMonth()) {
					list.add(String.valueOf(i) + "-BLUE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				} else {
					list.add(String.valueOf(i) + "-WHITE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				}
			}

			// Leading Month days
			for (int i = 0; i < list.size() % 7; i++) {
				list.add(String.valueOf(i + 1) + "-GREY" + "-"
						+ getMonthAsString(nextMonth) + "-" + nextYear);
			}
		}

		/**
		 * TODO napln z databaze NOTE: YOU NEED TO IMPLEMENT THIS PART Given the
		 * YEAR, MONTH, retrieve ALL entries from a SQLite database for that
		 * month. Iterate over the List of All entries, and get the dateCreated,
		 * which is converted into day.
		 * 
		 * @param year
		 * @param month
		 * @return
		 */
		private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
				int month) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();

			//TODO replace with database access
			Calendar cal = Calendar.getInstance();
			cal.set(2012, 9, 21);
			Date[] dates = new Date[3]; 
			dates[0] = cal.getTime(); 
			cal.set(2012,  9, 10);
			dates[1] = cal.getTime();
			cal.set(2012,  9, 9);
			dates[2] = cal.getTime();
			
			for(int i = 0; i < dates.length; i++){
				String day = DateFormat.format("d", dates[i]).toString();
				if (map.containsKey(day)) {
					Integer val = (Integer) map.get(day) + 1;
					map.put(day, val);
				} else {
					map.put(day, 1);
				}
			}
			return map;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) _context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.calendar_day_gridcell, parent,
						false);
			}

			// Get a reference to the Day gridcell
			gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);

			// TODO prekopat onclick listenery pro ruzne druhy dnu
			gridcell.setOnClickListener(this);

			// ACCOUNT FOR SPACING
			String[] day_color = list.get(position).split("-");
			String theday = day_color[0];
			String themonth = day_color[2];
			String theyear = day_color[3];
			
			// Set the Day GridCell
			gridcell.setText(theday);
			gridcell.setTag(theday + "-" + themonth + "-" + theyear);

			if (day_color[1].equals("GREY")) { // DISABLED
				gridcell.setTextColor(Color.LTGRAY);
			}
			if (day_color[1].equals("WHITE")) { // NORMAL
				gridcell.setTextColor(getResources().getColor(
						R.color.KebapDarkGray));
			}
			if (day_color[1].equals("BLUE")) { // TODAY
				gridcell.setTextColor(getResources().getColor(
						R.color.KebapWhite));
				gridcell.setBackgroundColor(getResources().getColor(
						R.color.KebapRed));
			}
			
			//Ma-li den event, naloz s nim odlisne
			if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
				if (eventsPerMonthMap.containsKey(theday)) {
					
					//Cerveny a podtrzeny text (tedy pokud to neni dnesni den) 
					gridcell.setPaintFlags(gridcell.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
					if (!day_color[1].equals("BLUE")) {
						gridcell.setTextColor(getResources().getColor(
							R.color.KebapRed));
					}
					
					//TODO staticky listener by asi byl lepsi
					gridcell.setOnClickListener( new OnClickListener() {
						
						@Override
						public void onClick(View v) {

							//TODO je-li v kalendari vic cviku na den, ukaz seznam
							//TODO je-li v kalendari jeden cvik ukaz ho
							
						}
					} );
					
					/*
					 * TODO pocet eventu v jednom dni pouzit jen v okamziku, kdy to bude treba
					num_events_per_day = (TextView) row
							.findViewById(R.id.num_events_per_day);
					Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
					
					num_events_per_day.setText( numEvents.toString() );
					*/
				}
			}

			return row;
		}

		@Override
		/**
		 * Implementuje reakci na klik na konkretni datum
		 * Rozparsuje data z kliknute bunky a preda je do nove aktivity
		 * TODO rozlisit mezi jiz zabukovanym a nezabukovanym dnem
		 * @param view
		 */
		public void onClick(View view) {
			String date_month_year = (String) view.getTag();
			SimpleDateFormat sdf = new SimpleDateFormat("d-MMMM-yyyy",
					Locale.UK);

			Date date = null;
			try {
				date = (Date) sdf.parse(date_month_year);
			} catch (ParseException e) {
				date = new Date();
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			Intent i = new Intent(view.getContext(), WorkoutActivity.class);
			i.putExtra("action", NEW_WORKOUT_FROM_DATE);
			i.putExtra("year", cal.get(Calendar.YEAR));
			i.putExtra("month", cal.get(Calendar.MONTH));
			i.putExtra("day", cal.get(Calendar.DATE));

			startActivity(i);
		}

		public int getCurrentDayOfMonth() {
			return currentDayOfMonth;
		}

		private void setCurrentDayOfMonth(int currentDayOfMonth) {
			this.currentDayOfMonth = currentDayOfMonth;
		}

		public void setCurrentWeekDay(int currentWeekDay) {
			this.currentWeekDay = currentWeekDay;
		}

		public int getCurrentWeekDay() {
			return currentWeekDay;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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
                
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                	Log.wtf("swipe", "Left");
                	
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	
                	Log.wtf("swipe", "Right");
                }             
             
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }

}
