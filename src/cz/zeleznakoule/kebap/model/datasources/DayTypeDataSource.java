package cz.zeleznakoule.kebap.model.datasources;

import java.util.ArrayList;
import java.util.List;

import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.entities.DayType;
import cz.zeleznakoule.kebap.model.idatasources.IDayTypeDataSource;
import android.content.Context;
import android.database.Cursor;

public class DayTypeDataSource extends DataSource implements IDayTypeDataSource {

	public DayTypeDataSource(Context context) {
		dbHelper = new SqlHelper(context);
	}

	private String[] allColumns = { SqlHelper.COLUMN_ID, SqlHelper.COLUMN_NAME };

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

	private DayType cursorToDayType(Cursor cursor) {
		DayType dayType = new DayType();
		dayType.setId(cursor.getLong(0));
		dayType.setName(cursor.getString(1));
		return dayType;
	}
}
