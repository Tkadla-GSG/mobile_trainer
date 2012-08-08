package cz.zeleznakoule.kebap.model.datasources;

import java.util.ArrayList;
import java.util.List;

import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.entities.Excercise;
import cz.zeleznakoule.kebap.model.idatasources.IExcerciseDataSource;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ExcerciseDataSource implements IExcerciseDataSource {
	
		// Database fields
		private SQLiteDatabase database;
		private SqlHelper dbHelper;
		
		
		private String[] allColumns = { SqlHelper.COLUMN_ID,
				SqlHelper.COLUMN_NAME };

		public ExcerciseDataSource(Context context) {
			dbHelper = new SqlHelper(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public Excercise create(String name) {
			ContentValues values = new ContentValues();
			values.put(SqlHelper.COLUMN_NAME, name);
			long insertId = database.insert(SqlHelper.TABLE_EXCERCISES, null, values);
			Cursor cursor = database.query(SqlHelper.TABLE_EXCERCISES,
					allColumns, SqlHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			Excercise newComment = cursorToExcercise(cursor);
			cursor.close();
			return newComment;
		}

		public void delete(Excercise excercise) {
			long id = excercise.getId();
			System.out.println("Excercise deleted with id: " + id);
			database.delete(SqlHelper.TABLE_EXCERCISES, SqlHelper.COLUMN_ID	+ " = " + id, null);
		}

		public List<Excercise> getAll() {
			List<Excercise> excercises = new ArrayList<Excercise>();

			Cursor cursor = database.query(SqlHelper.TABLE_EXCERCISES,
					allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Excercise excercise = cursorToExcercise(cursor);
				excercises.add(excercise);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return excercises;
		}

		private Excercise cursorToExcercise(Cursor cursor) {
			Excercise excercise = new Excercise();
			excercise.setId(cursor.getLong(0));
			excercise.setName(cursor.getString(1));
			return excercise;
		}
	} 
