package com.flexxo.mobil.infra.vo.imovel;

import java.util.ArrayList;
import java.util.List;

import android.provider.ContactsContract.CommonDataKinds.Im;
import android.text.TextUtils;

public class Imovel {

	private static List<Imovel> lista = new ArrayList<Imovel>();
	
	static {
		Imovel imovel = new Imovel();
		
		imovel.setImovel("11");
		imovel.setCidade("Caxias do sul");
		imovel.setLatitude(-29.180379d);
		imovel.setLongitude(-51.208649d);
				
		lista.add(imovel);
		imovel = new Imovel();
		
		imovel.setImovel("223");
		imovel.setCidade("Caxias do sul");
		imovel.setLatitude(-29.174665d);
		imovel.setLongitude(-51.140671d);
		lista.add(imovel);
		
		imovel = new Imovel();
		
		imovel.setImovel("32");
		imovel.setCidade("Caxias do sul");
		imovel.setLatitude(-29.179425d);
		imovel.setLongitude(-51.179852);		
		lista.add(imovel);
	}

	private String codigo;
	private String imovel;
	private String endereco;
	private String cidade;
	private String bairro;
	private String uf;
	private TipoImovel tipo;
	
	private double latitude;
	private double longitude;
	
	public Imovel(){
	}

	public Imovel(String codigo, String imovel, String endereco, String cidade, String bairro, String uf, TipoImovel tipo) {
		this.codigo = codigo;
		this.imovel = imovel;
		this.endereco = endereco;
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
	
	public void save() {
		lista.add(this);
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

	public String getEndereco() {
		return endereco;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setImovel(String imovel) {
		this.imovel = imovel;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public void setTipo(TipoImovel tipo) {
		this.tipo = tipo;
	}

	public boolean validar() {
		if (this.codigo == null || this.codigo.trim().length() == 0)
			return false;
		
		if (this.imovel == null || this.imovel.trim().length() == 0)
			return false;
		
		return true;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
