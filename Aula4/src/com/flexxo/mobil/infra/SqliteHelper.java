package com.flexxo.mobil.infra;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.flexxo.mobil.infra.vo.Usuario;

public class SqliteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "mobil.sqlite";

	private static SqliteHelper current;

	public static void init(Context context) {
		if (current == null)
			current = new SqliteHelper(context, null);
//		current.onUpgrade(current.getWritableDatabase(), -1, -1);
	}

	public static SqliteHelper getCurrent() {
		return current;
	}

	public SqliteHelper(Context context, CursorFactory factory) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	public SqliteHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);

	}

	@Override
	public void onCreate(SQLiteDatabase database) {
//		String sql = "Create table " + Usuario.Sql.TABLE_NAME + " (" + Usuario.Sql.NOME + " TEXT NOT NULL PRIMARY KEY ," + Usuario.Sql.SENHA + " TEXT NOT NULL)";

//		database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//		if (oldVersion == -1 && newVersion == -1) {
//			String sql;
//			sql = "delete from " + Usuario.Sql.TABLE_NAME;
//			database.execSQL(sql);
//			SQLiteStatement statement;  
//			
//			database.beginTransaction();
//			long ini;int count = 0;
//			ini = System.currentTimeMillis();
//			sql = "insert into " + Usuario.Sql.TABLE_NAME + " values (?, ?)";
//			statement = database.compileStatement(sql);
//			for (int i = 0; i < 10; i++) {
//				statement.bindString(1, "bruno"+String.valueOf(i));
//				statement.bindString(2, "123"+String.valueOf(i));
//				statement.executeInsert();
//				count++;
//				if (System.currentTimeMillis()-ini> 1000l){
//					Log.d("CustomLogger","RPS="+String.valueOf(count));
//					count = 0;
//					ini = System.currentTimeMillis();
//				}
////				database.execSQL(sql);
//			}
//			database.setTransactionSuccessful();
//			database.endTransaction();
//			Log.d("CustomLogger", "Upgrade");
//		}

	}

}