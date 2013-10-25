package com.flexxo.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Pizzaria {
	
	public static void main(String[] args) {
		Pizzaria p = new Pizzaria();
		p.aberta();
	}
	
	private HashMap<String, Ingrediente> estoque;
	
	public Pizzaria() {
		estoque = new HashMap<String, Ingrediente>();
		
		estoque.put("molho", new Ingrediente("molho",200));
		estoque.put("tomate", new Ingrediente("tomate",200));
	}
	
	public void aberta(){
		try{
			Scanner scan = new Scanner(System.in);
			while (true){
				System.out.println("Calabresa 1");
				System.out.println("Quatro queijos 2");
				String nome = scan.next();
				if (nome.equals("1")){
					PizzaCalabressa calabresa = new PizzaCalabressa();
					if (preparar(calabresa, false)){
						aquecer(calabresa);
					}	
				}else if (nome.equals("2")){
					PizzaCalabressa calabresa = new PizzaCalabressa();
					if (preparar(calabresa, false)){
						aquecer(calabresa);
					}
				}
			}
			

		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private boolean preparar(Pizza pizza, boolean media) throws Exception{
		for (Ingrediente ingrediente : pizza.getIngredientes(media)) {
			Ingrediente itemEstoque = estoque.get(ingrediente.getNome());
			if (ingrediente.getQuantidade() > itemEstoque.getQuantidade()){
				System.out.println("sem estoque");
//				throw new Exception();
			} else {
				itemEstoque.setQuantidade(itemEstoque.getQuantidade()-ingrediente.getQuantidade());
			}
		} 
		
		return true;
	}
	
	private void aquecer(Pizza pizza){
		try {
			Thread.sleep(pizza.tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
