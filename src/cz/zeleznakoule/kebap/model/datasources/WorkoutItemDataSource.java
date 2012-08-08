package cz.zeleznakoule.kebap.model.datasources;



import java.util.ArrayList;

import java.util.List;

import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.entities.Drill;
import cz.zeleznakoule.kebap.model.entities.Workout;
import cz.zeleznakoule.kebap.model.entities.WorkoutItem;
import cz.zeleznakoule.kebap.model.idatasources.IWorkoutItemDataSource;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WorkoutItemDataSource implements IWorkoutItemDataSource {
	
		// Database fields
		private SQLiteDatabase database;
		private SqlHelper dbHelper;
		
		private String[] allColumns = { SqlHelper.COLUMN_ID,
				SqlHelper.COLUMN_DRILL, SqlHelper.COLUMN_WORKOUT, SqlHelper.COLUMN_REPEATS, SqlHelper.COLUMN_NOTE };

		public WorkoutItemDataSource(Context context) {
			dbHelper = new SqlHelper(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public WorkoutItem create(Drill drill, Workout workout, int repeats, String note) {
			ContentValues values = new ContentValues();
			values.put(SqlHelper.COLUMN_REPEATS, repeats);
			values.put(SqlHelper.COLUMN_NOTE, note);
			// Relations
			values.put(SqlHelper.COLUMN_WORKOUT, workout.getId());
			values.put(SqlHelper.COLUMN_DRILL, drill.getId());
			
			long insertId = database.insert(SqlHelper.TABLE_WORKOUT_ITEMS, null, values);
			Cursor cursor = database.query(SqlHelper.TABLE_WORKOUT_ITEMS,
					allColumns, SqlHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			WorkoutItem newWorkoutItem = cursorToWorkoutItem(cursor);
			cursor.close();
			return newWorkoutItem;
		}

		public void delete(WorkoutItem workoutItem) {
			long id = workoutItem.getId();
			database.delete(SqlHelper.TABLE_WORKOUT_ITEMS, SqlHelper.COLUMN_ID	+ " = " + id, null);
		}

		public List<WorkoutItem> getAll() {
			List<WorkoutItem> workoutItems = new ArrayList<WorkoutItem>();

			Cursor cursor = database.query(SqlHelper.TABLE_WORKOUT_ITEMS,
					allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				WorkoutItem workoutItem = cursorToWorkoutItem(cursor);
				workoutItems.add(workoutItem);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return workoutItems;
		}

		private WorkoutItem cursorToWorkoutItem(Cursor cursor) {
			WorkoutItem workoutItem = new WorkoutItem();
			workoutItem.setId(cursor.getLong(0));
			workoutItem.setRepeats(cursor.getInt(1));
			workoutItem.setNote(cursor.getString(2));
			// Get relations
			int workoutId = cursor.getInt(3);
			int drillId = cursor.getInt(4);
			// TODO Nacist dalsi informace pres cizi klice	
			return workoutItem;
		}
	} 
