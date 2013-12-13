package com.flexxo.mobil.infra.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.flexxo.mobil.infra.SqliteHelper;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.flexxo.mobil.infra.vo.imovel.TipoImovel;

public class TipoImovelDao {

	public Cursor get(String codigo) {
		SQLiteDatabase database = SqliteHelper.getCurrent().getWritableDatabase();
		return database.rawQuery("select * from " + TipoImovel.Sql.TABLE_NAME + " where " + TipoImovel.Sql.CODIGO + " = ?", new String[] { codigo });
	}

	public Cursor getAll() {
		SQLiteDatabase database = SqliteHelper.getCurrent().getWritableDatabase();
		return database.rawQuery("select * from " + TipoImovel.Sql.TABLE_NAME, new String[] {});
	}

	public void save(TipoImovel tipoImovel) {
		SQLiteDatabase database = SqliteHelper.getCurrent().getWritableDatabase();
		database.insert(TipoImovel.Sql.TABLE_NAME.toString(), null, createContentValues(tipoImovel));
	}

	private ContentValues createContentValues(TipoImovel tipoImovel) {
		ContentValues cv = new ContentValues();
		cv.put(TipoImovel.Sql.CODIGO.toString(), tipoImovel.getCodigo());
		cv.put(TipoImovel.Sql.NOME.toString(), tipoImovel.getNome());

		return cv;
	}

	public void clearAll() {
		SQLiteDatabase database = SqliteHelper.getCurrent().getWritableDatabase();
		database.delete(TipoImovel.Sql.TABLE_NAME.toString(), null, null);
	}
}
