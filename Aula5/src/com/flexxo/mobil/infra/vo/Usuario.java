package com.flexxo.mobil.infra.vo;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.flexxo.mobil.infra.SqliteHelper;

public class Usuario {

	public static enum Sql {
		TABLE_NAME("Usuarios"), NOME("nome_usu"), SENHA("senha_usu");
		private String nome;

		private Sql(String nome) {
			this.nome = nome;
		}
	}
	
	private static List<Usuario> lista = new ArrayList<Usuario>();
	
	//Construtor estático
	static {
		for (int i = 0;i< 1000;i++){
			lista.add(new Usuario("bruno"+i, "bruno"+i));
		}
	}
	

	private String usuario;
	private String senha;

	public Usuario(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}
	
	public Usuario(Cursor cursor) {
		this.usuario = cursor.getString(0);
		this.senha = cursor.getString(1);
	
	}

	public static Usuario get(String nome) {
		String[] parametros = new String[1]; 
		parametros[0] = nome;
		Cursor cursor = SqliteHelper.getCurrent().getWritableDatabase().rawQuery("select usuario,senha from imoveis where nome = ?", parametros);
		if (cursor.moveToFirst()){
			return new Usuario(cursor);
		}
		
		return null;
	}

	public static List<Usuario> getAll() {
		// UsuarioDao dao = new UsuarioDao();
		// Cursor cursor = dao.getAll();
		// List<Usuario> retorno = new ArrayList<Usuario>();
		// for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem =
		// cursor.moveToNext()) {
		// retorno.add(new Usuario(cursor));
		// // Log.d("CustomLogger",cursor.getCount()+"/"+cursor.getPosition());
		// }
		return lista;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
