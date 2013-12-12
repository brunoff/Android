package com.flexxo.mobil.infra.vo;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import com.flexxo.mobil.infra.SqliteHelper;
import com.flexxo.mobil.infra.dao.UsuarioDao;
import com.google.android.gms.internal.cu;

public class Usuario {

	public static enum Sql {
		TABLE_NAME("Usuarios"), NOME("nome_usu"), SENHA("senha_usu");
		private String nome;

		private Sql(String nome) {
			this.nome = nome;
		}
		
		@Override
		public String toString() {
			return this.nome;
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
		this.usuario = cursor.getString(cursor.getColumnIndexOrThrow(Sql.NOME.toString()));
		this.senha = cursor.getString(cursor.getColumnIndexOrThrow(Sql.SENHA.toString()));	
	}

	public static Usuario get(String nome) {
		Cursor cursor = new UsuarioDao().get(nome);
		
		if (cursor.moveToFirst()){
			return new Usuario(cursor);
		}
		
		return null;
	}

//	public static List<Usuario> getAll() {
//		return lista;
//	}

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
