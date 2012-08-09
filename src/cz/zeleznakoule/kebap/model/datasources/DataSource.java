package cz.zeleznakoule.kebap.model.datasources;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cz.zeleznakoule.kebap.model.SqlHelper;
import cz.zeleznakoule.kebap.model.idatasources.IDataSource;

/**
 * Zakladni trida pro vsechny DataSource tridy
 * @author Iskuda
 */
public abstract class DataSource implements IDataSource {

	// Database fields
	protected SQLiteDatabase database;
	protected SqlHelper dbHelper;

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
}
