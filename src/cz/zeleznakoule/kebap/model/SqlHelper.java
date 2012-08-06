package cz.zeleznakoule.kebap.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "kebap.db";
	private static final int DATABASE_VERSION = 1;
	
	// TODO Vytvoøit skript pro vytvoøení databáze
	private static final String CREATE_DATABASE = "";
	
	public SqlHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_DATABASE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		// TODO Vytvorit skript pro update databaze
		onCreate(database);
	}

}
