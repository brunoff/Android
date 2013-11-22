package com.flexxo.mobil.infra.vo;

import java.util.ArrayList;
import java.util.List;

public class Imovel {

	private static List<Imovel> lista = new ArrayList<Imovel>();

	private String codigo;
	private String imovel;
	private String uf;
	private String cidade;
	private String bairro;
	private int tipo;

	public Imovel(String codigo, String imovel, String uf, String cidade,String bairro, int tipo) {
		this.codigo = codigo;
		this.imovel = imovel;
		this.uf = uf;
		this.cidade = cidade;
		this.bairro = bairro;
		this.tipo = tipo;
	}

	public static Imovel get(String codigo) {
		for (Imovel imovel : lista) {
			if (imovel.codigo.equals(codigo))
				return imovel;
		}

		return null;
	}

	public static List<Imovel> getAll() {
		return lista;
	}

	public static List<Imovel> getLista() {
		return lista;
	}
	
	//gets sets
	public String getCodigo() {
		return codigo;
	}

	public String getImovel() {
		return imovel;
	}

	public String getUf() {
		return uf;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public int getTipo() {
		return tipo;
	}	
}
