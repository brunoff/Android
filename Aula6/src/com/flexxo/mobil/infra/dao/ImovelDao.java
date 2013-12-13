package com.flexxo.mobil.infra.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.flexxo.mobil.infra.SqliteHelper;
import com.flexxo.mobil.infra.vo.imovel.Imovel;

public class ImovelDao {
	
	public Cursor get(String codigo){
		SQLiteDatabase database= SqliteHelper.getCurrent().getWritableDatabase();
		return database.rawQuery("select * from "+Imovel.Sql.TABLE_NAME+" where "+Imovel.Sql.CODIGO+" = ?", new String[]{codigo});
	}
	
	public Cursor getAll(){
		SQLiteDatabase database= SqliteHelper.getCurrent().getWritableDatabase();
		return database.rawQuery("select * from "+Imovel.Sql.TABLE_NAME, new String[]{});
	}
	
	public void save(Imovel imovel){
		SQLiteDatabase database= SqliteHelper.getCurrent().getWritableDatabase();
		database.insert(Imovel.Sql.TABLE_NAME.toString(), null, createContentValues(imovel));
	}
	
	private ContentValues createContentValues(Imovel imovel) {
		ContentValues cv = new ContentValues();
		cv.put(Imovel.Sql.CODIGO.toString(), imovel.getCodigo());
		cv.put(Imovel.Sql.NOME.toString(), imovel.getNome());
		cv.put(Imovel.Sql.ENDERECO.toString(), imovel.getEndereco().getEndereco());
		cv.put(Imovel.Sql.BAIRRO.toString(), imovel.getEndereco().getBairro());
		cv.put(Imovel.Sql.CIDADE.toString(), imovel.getEndereco().getCidade());
		cv.put(Imovel.Sql.UF.toString(), imovel.getEndereco().getUf());
		cv.put(Imovel.Sql.LATITUDE.toString(), imovel.getEndereco().getLatitude());
		cv.put(Imovel.Sql.LONGITUDE.toString(), imovel.getEndereco().getLongitude());
		
		return cv;
	}

	public void clearAll() {
		SQLiteDatabase database= SqliteHelper.getCurrent().getWritableDatabase();
		database.delete(Imovel.Sql.TABLE_NAME.toString(), null, null);		
	}	
}
