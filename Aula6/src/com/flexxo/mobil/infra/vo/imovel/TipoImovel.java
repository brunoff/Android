package com.flexxo.mobil.infra.vo.imovel;

import java.util.ArrayList;
import java.util.List;

public class TipoImovel {

	private static List<TipoImovel> lista = new ArrayList<TipoImovel>();

	private String codigo;
	private String nome;
	
	public static TipoImovel get(String codigo) {
		for (TipoImovel imovel : lista) {
			if (imovel.codigo.equals(codigo))
				return imovel;
		}

		return null;
	}

	public static List<TipoImovel> getAll() {
		return lista;
	}
	
	//Gets sets
	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}
}
