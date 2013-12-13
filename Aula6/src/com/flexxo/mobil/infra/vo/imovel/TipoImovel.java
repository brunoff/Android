package com.flexxo.mobil.infra.vo.imovel;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;
import com.flexxo.mobil.infra.dao.ImovelDao;
import com.flexxo.mobil.infra.dao.TipoImovelDao;

public class TipoImovel {
	public static enum Sql {
		TABLE_NAME("TipoImovel"), CODIGO("codigo_tpimov"), NOME("nome_tpimov");
		private String nome;

		private Sql(String nome) {
			this.nome = nome;
		}
		
		@Override
		public String toString() {
			return this.nome;
		}
	}
	
	private String codigo;
	private String nome;
	
	public TipoImovel(Cursor cursor) {
		this.codigo = cursor.getString(cursor.getColumnIndexOrThrow(Sql.CODIGO.toString()));
		this.nome = cursor.getString(cursor.getColumnIndexOrThrow(Sql.NOME.toString()));
	}
	
	public TipoImovel(JSONObject jsonObject) throws JSONException {
		this.codigo = jsonObject.getString("codigo");
		this.nome = jsonObject.getString("nome");
	}

	public static TipoImovel get(String codigo) {
		Cursor cursor = new TipoImovelDao().get(codigo);
		
		if (cursor.moveToFirst()){
			return new TipoImovel(cursor);
		}
		
		return null;		
	}

	public static List<TipoImovel> getAll() {
		Cursor cursor = new TipoImovelDao().getAll();
		List<TipoImovel> retorno = new ArrayList<TipoImovel>();
		
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			retorno.add(new TipoImovel(cursor));
		}
		
		return retorno;		
	}
	
	public void save() {
		TipoImovelDao dao = new TipoImovelDao();
		dao.save(this);
	}
	
	//Gets sets
	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public static void clearAll() {
		TipoImovelDao dao = new TipoImovelDao();
		dao.clearAll();	
		// TODO Auto-generated method stub
		
	}
}
