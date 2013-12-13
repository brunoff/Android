package com.flexxo.mobil.infra.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class Endereco {

	private String endereco;
	private String bairro;
	private String cidade;
	private String uf;
	private double latitude;
	private double longitude;
	
	public Endereco() {
	}

	public Endereco(JSONObject jsonObject) throws JSONException {	
		this.endereco = jsonObject.getString("endereco");
		this.bairro = jsonObject.getString("bairro");
		this.cidade = jsonObject.getString("cidade");
		this.uf = jsonObject.getString("uf");
		this.latitude= jsonObject.getDouble("latitude");
		this.longitude= jsonObject.getDouble("longitude");
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
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
