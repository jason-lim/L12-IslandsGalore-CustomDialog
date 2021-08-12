package com.myapplicationdev.android.islandsgalore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "islands.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_ISLANDS = "islands";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_SQUAREKM = "squarekm";
	private static final String COLUMN_STARS = "stars";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE TABLE Song
		// (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
		// singers TEXT, stars INTEGER, year INTEGER );
		String createSongTableSql = "CREATE TABLE " + TABLE_ISLANDS + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_NAME + " TEXT, "
				+ COLUMN_DESCRIPTION + " TEXT, "
				+ COLUMN_SQUAREKM + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
		db.execSQL(createSongTableSql);
		Log.i("info", createSongTableSql + "\ncreated tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLANDS);
		onCreate(db);
	}

	public long insertItem(String a, String b, int c, int stars) {
		// Get an instance of the database for writing
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, a);
		values.put(COLUMN_DESCRIPTION, b);
        values.put(COLUMN_SQUAREKM, c);
		values.put(COLUMN_STARS, stars);
		// Insert the row into the TABLE_SONG
		long result = db.insert(TABLE_ISLANDS, null, values);
		// Close the database connection
		db.close();
        Log.d("SQL Insert","" + result);
        return result;
	}

	public ArrayList<Item> getAllItems() {
		ArrayList<Item> itemslist = new ArrayList<Item>();
		String selectQuery = "SELECT " + COLUMN_ID + ","
				+ COLUMN_NAME + "," + COLUMN_DESCRIPTION + ","
				+ COLUMN_SQUAREKM + ","
				+ COLUMN_STARS + " FROM " + TABLE_ISLANDS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String a = cursor.getString(1);
				String b = cursor.getString(2);
				int c = cursor.getInt(3);
                int stars = cursor.getInt(4);

				Item newItem = new Item(id, a, b, c, stars);
                itemslist.add(newItem);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return itemslist;
	}

	public ArrayList<Item> getAllItemsByStars(int starsFilter) {
		ArrayList<Item> itemslist = new ArrayList<Item>();

		SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_SQUAREKM, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_ISLANDS, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
                int id = cursor.getInt(0);
                String a = cursor.getString(1);
                String b = cursor.getString(2);
                int c = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Item newItem = new Item(id, a, b, c, stars);
                itemslist.add(newItem);
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return itemslist;
	}

	public int updateItem(Item data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_SQUAREKM, data.getSquarekm());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ISLANDS, values, condition, args);
        db.close();
        return result;
    }


    public int deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLANDS, condition, args);
        db.close();
        return result;
    }

	public ArrayList<String> getSquareKm() {
		ArrayList<String> codes = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns= {COLUMN_SQUAREKM};

		Cursor cursor;
		cursor = db.query(true, TABLE_ISLANDS, columns, null, null, null, null, null, null);
		// Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
				codes.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return codes;
	}

	public ArrayList<Item> getAllItemsBySquareKm(int yearFilter) {
		ArrayList<Item> songslist = new ArrayList<Item>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_SQUAREKM, COLUMN_STARS};
		String condition = COLUMN_SQUAREKM + "= ?";
		String[] args = {String.valueOf(yearFilter)};

		Cursor cursor;
		cursor = db.query(TABLE_ISLANDS, columns, condition, args, null, null, null, null);

		// Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				int squarekm = cursor.getInt(3);
				int stars = cursor.getInt(4);

				Item newItem = new Item(id, name, description, squarekm, stars);
				songslist.add(newItem);
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return songslist;
	}
}
