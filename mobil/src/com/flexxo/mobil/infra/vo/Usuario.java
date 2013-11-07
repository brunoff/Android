package com.flexxo.mobil.infra.vo;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import com.flexxo.mobil.infra.dao.UsuarioDao;

public class Usuario {

	public static enum Sql {
		TABLE_NAME("Usuarios"), NOME("nome_usu"), SENHA("senha_usu");
		private String nome;

		private Sql(String nome) {
			this.nome = nome;
		}
	}

	private String usuario;
	private String senha;

	public Usuario(Cursor cursor) {
		usuario = cursor.getString(0);
		senha = cursor.getString(1);
	}

	public static Usuario get(String nome) {
		UsuarioDao dao = new UsuarioDao();
		Cursor cursor = dao.get(nome);
		if (cursor.moveToFirst()){
			return new Usuario(cursor);
		}
		return null;
	}

	public static List<Usuario> getAll(){
		UsuarioDao dao = new UsuarioDao();
		Cursor cursor = dao.getAll();
		List<Usuario> retorno = new ArrayList<Usuario>(); 
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			retorno.add(new Usuario(cursor));
//			Log.d("CustomLogger",cursor.getCount()+"/"+cursor.getPosition());			
		}
		return retorno; 
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
