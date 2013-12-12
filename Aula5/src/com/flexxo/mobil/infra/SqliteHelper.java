package com.flexxo.mobil.infra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "mobil.sqlite";

	private static SqliteHelper current;
	private static Context mcontext;

	public static void init(Context context) {
		if (current == null) {
			current = new SqliteHelper(context, null);
			mcontext = context;
		}
		// Force recompile
		current.onUpgrade(current.getWritableDatabase(), 0, 1);
	}

	public static SqliteHelper getCurrent() {
		return current;
	}

	private String TAG = "sqlite";

	public SqliteHelper(Context context, CursorFactory factory) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);

	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		onUpgrade(database, 0, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		for (int i = oldVersion; i < newVersion; i++) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(mcontext.getAssets().open("version"+i+".sql")));
				String mLine = reader.readLine();
				database.beginTransaction();
				while (mLine != null) {
					Log.d(TAG, mLine);
					try{
						SQLiteStatement stat = database.compileStatement(mLine);
						stat.execute();
					}catch (SQLException ex){
						Log.d(TAG, mLine);
						ex.printStackTrace();
					}	

					mLine = reader.readLine();
				}
				database.setTransactionSuccessful();
				database.endTransaction();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}