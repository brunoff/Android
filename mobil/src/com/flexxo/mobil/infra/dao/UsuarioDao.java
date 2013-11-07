package com.flexxo.mobil.infra.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.flexxo.mobil.infra.SqliteHelper;
import com.flexxo.mobil.infra.vo.Usuario;

public class UsuarioDao {
	
	public Cursor get(String nome){
		SQLiteDatabase database= SqliteHelper.getCurrent().getWritableDatabase();
		return database.rawQuery("select * from "+Usuario.Sql.TABLE_NAME+" where "+Usuario.Sql.NOME+" = ?", new String[]{nome});
	}
	

	public Cursor getAll(){
		SQLiteDatabase database= SqliteHelper.getCurrent().getWritableDatabase();
		return database.rawQuery("select * from "+Usuario.Sql.TABLE_NAME, new String[]{});
	}
	
}
