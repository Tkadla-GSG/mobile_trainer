package cz.zeleznakoule.kebap.model.datasources;

import java.util.ArrayList;
import java.util.List;

import cz.zeleznakoule.kebap.R;
import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.entities.DayType;
import cz.zeleznakoule.kebap.model.entities.Workout;
import cz.zeleznakoule.kebap.model.idatasources.IDayTypeDataSource;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;

public class DayTypeDataSource extends DataSource implements IDayTypeDataSource {

	public DayTypeDataSource(Context context) {
		dbHelper = new SqlHelper(context);
	}

	private String[] allColumns = { SqlHelper.COLUMN_ID, SqlHelper.COLUMN_NAME };

	
	/**
	 * Vrací object DayType vyhledaný podle jména
	 * @param name
	 * @return
	 */
	public DayType getByName(String name) {
		Cursor cursor = database.query(SqlHelper.TABLE_DAY_TYPES,
				allColumns, SqlHelper.COLUMN_NAME + " = '" + name + "'", null,
				null, null, null);
		cursor.moveToFirst();
		DayType dayType = cursorToDayType(cursor);
		cursor.close();
		return dayType;
	}
	
	
	
	public List<DayType> getAll() {
		List<DayType> dayTypes = new ArrayList<DayType>();

		Cursor cursor = database.query(SqlHelper.TABLE_DAY_TYPES, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			DayType dayType = cursorToDayType(cursor);
			dayTypes.add(dayType);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return dayTypes;
	}

	/**
	 * Nacteni dat pro dropdown menu
	 * @param context
	 * @return ArrayAdapter<String>
	 */
	public ArrayAdapter<String> getArrayAdapter(Context context) {
		Cursor cursor = database.query(SqlHelper.TABLE_DAY_TYPES, allColumns,
				null, null, null, null, null);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.drawable.ic_spinner_layout);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			adapter.add(cursor.getString(1));
			cursor.moveToNext();
		}
		cursor.close();
		return adapter;
	}

	private DayType cursorToDayType(Cursor cursor) {
		DayType dayType = new DayType();
		dayType.setId(cursor.getLong(0));
		dayType.setName(cursor.getString(1));
		return dayType;
	}
}
