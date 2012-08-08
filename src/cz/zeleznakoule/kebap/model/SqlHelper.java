package cz.zeleznakoule.kebap.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

	// Database variables
	private static final String DATABASE_NAME = "Kebap.DB";
	private static final int DATABASE_VERSION = 1;
	
	// Column names
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TAGS = "tags";
	public static final String COLUMN_STARRED = "starred";
	public static final String COLUMN_EXCERCISE_CATEGORY = "excercise_category";
	public static final String COLUMN_WEIGHT = "weight";
	public static final String COLUMN_DURATION = "duration";
	public static final String COLUMN_REST_DURATION = "rest_duration";
	public static final String COLUMN_BREATHS = "breaths";
	public static final String COLUMN_WORKOUT_ITEM = "workout_item";
	public static final String COLUMN_EXCERCISE = "excercise";
	public static final String COLUMN_NOTE = "note";
	public static final String COLUMN_DAY_TYPE = "day_type";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_REPEATS = "repeats";
	public static final String COLUMN_WORKOUT = "workout";
	public static final String COLUMN_DRILL = "drill";
	
	// Table names
	public static final String TABLE_DAY_TYPES = "DayTypes";
	public static final String TABLE_DRILLS = "Drills";
	public static final String TABLE_EXCERCISES = "Excercises";
	public static final String TABLE_EXCERCISE_CATEGORIES = "ExcerciseCategories";
	public static final String TABLE_SERIES = "Series";
	public static final String TABLE_WORKOUT = "Workouts";
	public static final String TABLE_WORKOUT_ITEMS = "WorkoutItems";
	
	// Create tables scripts
	private static final String CREATE_TABLE_DAY_TYPES = "create table "
			+ TABLE_DAY_TYPES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " varchar(64) not null);";
	
	private static final String CREATE_TABLE_DRILLS = "create table "
			+ TABLE_DRILLS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " varchar(64) not null);";
	
	private static final String CREATE_TABLE_EXCERCISES = "create table "
			+ TABLE_EXCERCISES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " varchar(64) not null, " + COLUMN_STARRED
			+ " integer not null, " + COLUMN_EXCERCISE_CATEGORY
			+ " integer not null, " + COLUMN_TAGS
			+ " text null);";
	
	
	// Create database script
	private static final String CREATE_DATABASE = CREATE_TABLE_DAY_TYPES + CREATE_TABLE_DRILLS + CREATE_TABLE_EXCERCISES;
	
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
