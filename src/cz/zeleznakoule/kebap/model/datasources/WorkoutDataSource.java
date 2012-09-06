package cz.zeleznakoule.kebap.model.datasources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.zeleznakoule.kebap.R;
import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.entities.DayType;
import cz.zeleznakoule.kebap.model.entities.Workout;
import cz.zeleznakoule.kebap.model.idatasources.IWorkoutDataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;

public class WorkoutDataSource implements IWorkoutDataSource {
	
		// Database fields
		private SQLiteDatabase database;
		private SqlHelper dbHelper;
		
		
		private String[] allColumns = { SqlHelper.COLUMN_ID,
				SqlHelper.COLUMN_DATE, SqlHelper.COLUMN_DURATION, SqlHelper.COLUMN_NOTE, SqlHelper.COLUMN_DAY_TYPE };

		public WorkoutDataSource(Context context) {
			dbHelper = new SqlHelper(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public Workout create(Date date, int duration, String note, DayType dayType) {
			ContentValues values = new ContentValues();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			values.put(SqlHelper.COLUMN_DATE, formatter.format(date));
			values.put(SqlHelper.COLUMN_DURATION, duration);
			values.put(SqlHelper.COLUMN_NOTE, note);
			values.put(SqlHelper.COLUMN_DAY_TYPE, dayType.getId());
			
			long insertId = database.insert(SqlHelper.TABLE_WORKOUT, null, values);
			Cursor cursor = database.query(SqlHelper.TABLE_WORKOUT,
					allColumns, SqlHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			Workout newWorkout = cursorToWorkout(cursor);
			cursor.close();
			return newWorkout;
		}

		public Workout createTest(Date date, int duration, String note) {
			ContentValues values = new ContentValues();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			values.put(SqlHelper.COLUMN_DATE, formatter.format(date));
			values.put(SqlHelper.COLUMN_DURATION, duration);
			values.put(SqlHelper.COLUMN_NOTE, note);
			values.put(SqlHelper.COLUMN_DAY_TYPE, 1);
			
			long insertId = database.insert(SqlHelper.TABLE_WORKOUT, null, values);
			Cursor cursor = database.query(SqlHelper.TABLE_WORKOUT,
					allColumns, SqlHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			Workout newWorkout = cursorToWorkout(cursor);
			cursor.close();
			return newWorkout;
		}

		
		public void delete(Workout workout) {
			long id = workout.getId();
			database.delete(SqlHelper.TABLE_WORKOUT, SqlHelper.COLUMN_ID	+ " = " + id, null);
		}

		public List<Workout> getAll() {
			List<Workout> workouts = new ArrayList<Workout>();

			Cursor cursor = database.query(SqlHelper.TABLE_WORKOUT,
					allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Workout workout = cursorToWorkout(cursor);
				workouts.add(workout);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			
			return workouts;
		}
		
		
		@SuppressWarnings("deprecation")
		public SimpleCursorAdapter getSimpleCursorAdapter(Context context) {
		
			Cursor data = database.query(SqlHelper.TABLE_WORKOUT,
					allColumns, null, null, null, null, null);
	        
	        String[] from = new String[]{SqlHelper.COLUMN_DATE, SqlHelper.COLUMN_DURATION, SqlHelper.COLUMN_ID};
	        int[] to = new int[]{R.id.main, R.id.secondary, R.id.item_id};
	        
	        return new SimpleCursorAdapter(context, R.layout.workout_item_row, data, from, to);
		}
		
		
		
		/**
		 * TODO doplneno jen pro testovaci ucely
		 * @return
		 */
		public Cursor getCursorAll() {
			
			Cursor cursor = database.query(SqlHelper.TABLE_WORKOUT,
					allColumns, null, null, null, null, null);

			return cursor;
		}

		private Workout cursorToWorkout(Cursor cursor) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Workout workout = new Workout();
			workout.setId(cursor.getLong(0));
			try {
				workout.setDate(formatter.parse(cursor.getString(1)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			workout.setDuration(cursor.getInt(2));
			workout.setNote(cursor.getString(3));
			// Relace
			//int dayTypeID = cursor.getInt(4);
						
			return workout;
		}
	} 
