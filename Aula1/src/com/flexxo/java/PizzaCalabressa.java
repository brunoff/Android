package com.flexxo.java;

import java.util.ArrayList;
import java.util.List;

public class PizzaCalabressa extends Pizza {

	public PizzaCalabressa() {
		tempo = 2000;
	}

	@Override
	public List<Ingrediente> getIngredientes(boolean media) {
		List<Ingrediente> retorno = new ArrayList<Ingrediente>();
		retorno.add(new Ingrediente("molho", 200));
		retorno.add(new Ingrediente("tomate", 100));
		return retorno;
	}

}
