package com.flexxo.mobil.infra.vo.imovel;

import java.util.ArrayList;
import java.util.List;

public class Imovel {

	private static List<Imovel> lista = new ArrayList<Imovel>();

	private String codigo;
	private String imovel;
	private String cidade;
	private String bairro;
	private String uf;
	private TipoImovel tipo;

	public Imovel(String codigo, String imovel, String cidade, String bairro,
			String uf, TipoImovel tipo) {
		this.codigo = codigo;
		this.imovel = imovel;
		this.cidade = cidade;
		this.bairro = bairro;
		this.uf = uf;
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

	// gets sets
	public String getCodigo() {
		return codigo;
	}

	public String getImovel() {
		return imovel;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public String getUf() {
		return uf;
	}

	public TipoImovel getTipo() {
		return tipo;
	}
}
