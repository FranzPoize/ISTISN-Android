package com.franzp.istisn.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.franzp.istisn.fragment.QuotesFragment.SORT;
import com.franzp.istisn.model.Quote;

public class DBHelper {
	
	private SQLiteDatabase db;
	private OpenHelper helper;
	
	public DBHelper(Context context) {
		helper = new OpenHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
		db.close();
	}
	
	public Cursor getQuotes(SORT order){
		String sql = "SELECT * FROM quote ORDER BY";
		switch (order) {
		case FLOP:
			sql+=" nbFlop";
			break;
		case TOP:
			sql+=" nbTop";
			break;
		case TIME_DESC:
			sql+=" _id";
			break;

		default:
			break;
		}
		sql+=" DESC";
		return db.rawQuery(sql, null);
	}
	
	public void insertQuote(Quote quote) {
		String sql = String.format("INSERT INTO quote VALUES (\"%s\",\"%s\", \"%s\", \"%s\", \"%s\",\"%s\")", quote.getId(),
				quote.getAuthor(),quote.getMessage(),quote.getNbFlop(),quote.getNbTop(),quote.getCreationdate());
		db.execSQL(sql);
	}
	
	private class OpenHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "istisn";
		private static final int DATABASE_VERSION = 1;

		private static final String CREATE_QUOTE_TABLE = "CREATE TABLE quote (_id INTEGER PRIMARY KEY, author TEXT, message TEXT,nbFlop INTEGER,nbTop INTEGER, creationdate TEXT);";

		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_QUOTE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {			
		}
	}

}

