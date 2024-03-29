package cz.zeleznakoule.kebap.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

	// Database variables
	private static final String DATABASE_NAME = "Kebap.DB";
	private static final int DATABASE_VERSION = 1;

	// Column names
	public static final String COLUMN_ID = "_id";
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
	
	private static final String INSERT_TABLE_DAY_TYPES_1 = "insert into "
			+ TABLE_DAY_TYPES + " VALUES (1, 'Hard');"; 
	
	private static final String INSERT_TABLE_DAY_TYPES_2 = "insert into "
			+ TABLE_DAY_TYPES + " VALUES (2, 'Medium');"; 
	
	private static final String INSERT_TABLE_DAY_TYPES_3 = "insert into "
			+ TABLE_DAY_TYPES + " VALUES (3, 'Testing');"; 
	
	
	

	private static final String CREATE_TABLE_DRILLS = "create table "
			+ TABLE_DRILLS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " varchar(64) not null);";

	private static final String CREATE_TABLE_EXCERCISES = "CREATE TABLE "
			+ TABLE_EXCERCISES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " varchar(64) not null, " + COLUMN_STARRED + " integer, "
			+ COLUMN_EXCERCISE_CATEGORY + " integer, " + COLUMN_TAGS + " text"
			+ ");";

	private static final String CREATE_TABLE_EXCERCISE_CATEGORIES = "CREATE TABLE "
			+ TABLE_EXCERCISE_CATEGORIES
			+ "("
			+ COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_NAME
			+ " varchar(64) not null" + ");";

	private static final String CREATE_TABLE_SERIES = "CREATE TABLE "
			+ TABLE_SERIES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_WEIGHT
			+ " integer, " + COLUMN_DURATION + " integer, "
			+ COLUMN_REST_DURATION + " integer, " + COLUMN_BREATHS + " integer"
			+ ");";

	private static final String CREATE_TABLE_WORKOUTS = "CREATE TABLE "
			+ TABLE_WORKOUT + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_DATE
			+ " datetime not null, " + COLUMN_DURATION + " integer, "
			+ COLUMN_NOTE + " text, " + COLUMN_DAY_TYPE + " integer);";
	

	private static final String CREATE_TABLE_WORKOUT_ITEMS = "CREATE TABLE "
			+ TABLE_WORKOUT_ITEMS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_REPEATS
			+ " integer, " + COLUMN_NOTE + " text, " + COLUMN_WORKOUT + " integer, " + COLUMN_DRILL + " integer);";

	// Insert to tables scripts
	// TODO Napsat insert skripty
	
	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.beginTransaction();
		try {
			database.execSQL(CREATE_TABLE_DAY_TYPES);
			database.execSQL(CREATE_TABLE_DRILLS);
			database.execSQL(CREATE_TABLE_EXCERCISES);
			database.execSQL(CREATE_TABLE_EXCERCISE_CATEGORIES);
			database.execSQL(CREATE_TABLE_SERIES);
			database.execSQL(CREATE_TABLE_WORKOUT_ITEMS);
			database.execSQL(CREATE_TABLE_WORKOUTS);
			// Insert statements DAY_TYPES
			database.execSQL(INSERT_TABLE_DAY_TYPES_1);
			database.execSQL(INSERT_TABLE_DAY_TYPES_2);
			database.execSQL(INSERT_TABLE_DAY_TYPES_3);
			
			
			
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_TYPES);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_DRILLS);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXCERCISE_CATEGORIES);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXCERCISES);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_ITEMS);
		onCreate(database);
	}

}
