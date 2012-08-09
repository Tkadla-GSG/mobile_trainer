package cz.zeleznakoule.kebap.model.datasources;

import java.util.ArrayList;
import java.util.List;

import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.entities.ExcerciseCategory;
import cz.zeleznakoule.kebap.model.idatasources.IExcerciseCategoryDataSource;
import android.content.Context;
import android.database.Cursor;

public class ExcerciseCategoryDataSource extends DataSource implements IExcerciseCategoryDataSource {

	public ExcerciseCategoryDataSource(Context context) {
		dbHelper = new SqlHelper(context);
	}

	private String[] allColumns = { SqlHelper.COLUMN_ID, SqlHelper.COLUMN_NAME };

	public List<ExcerciseCategory> getAll() {
		List<ExcerciseCategory> excerciseCategories = new ArrayList<ExcerciseCategory>();

		Cursor cursor = database.query(SqlHelper.TABLE_EXCERCISE_CATEGORIES, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ExcerciseCategory excerciseCategory = cursorToExcerciseCategory(cursor);
			excerciseCategories.add(excerciseCategory);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return excerciseCategories;
	}

	private ExcerciseCategory cursorToExcerciseCategory(Cursor cursor) {
		ExcerciseCategory excerciseCategory = new ExcerciseCategory();
		excerciseCategory.setId(cursor.getLong(0));
		excerciseCategory.setName(cursor.getString(1));
		return excerciseCategory;
	}
}
