package com.flexxo.mobil.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.flexxo.mobil.MenuActivity;
import com.flexxo.mobil.R;
import com.flexxo.mobil.infra.vo.imovel.Imovel;

public class ListaImovelFragment extends ListFragment {
	private MenuActivity activity;
	private ListView lista;
	private ArrayList<HashMap<String, Object>> listaValores;

	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
		
		this.lista = ((ListView) getView().findViewById(android.R.id.list));
		this.activity = ((MenuActivity) getActivity());
		Toast.makeText(activity , "aa", Toast.LENGTH_LONG).show();
		loadListView();
	}

	private void loadListView() {
		listaValores = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hash;
		
		for (Imovel imovel : Imovel.getAll()) {
			hash = new HashMap<String, Object>();
			hash.put("nome", imovel.getImovel());
			hash.put("cidade", imovel.getCidade());
			hash.put("imovel", imovel);
			listaValores.add(hash);
		}

		String[] from = { "nome", "cidade" };
		int[] to = { R.id.lista_imovel_frag_row_nome, R.id.lista_imovel_frag_row_cidade };
		SimpleAdapter adapter = new SimpleAdapter(this.activity, listaValores,
				R.layout.lista_imovel_fragment_row, from, to);
		
		this.lista.setAdapter(adapter);
		setListShown(true);
		//activity.overlayMap(listaValores);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HashMap<String,Object> hash = listaValores.get(position);
		if (hash!=null){
			Toast.makeText(activity, hash.get("nome").toString(), Toast.LENGTH_LONG).show();
//			activity.gotoImovel((Imovel)hash.get("imovel"));
		}
		super.onListItemClick(l, v, position, id);

	}
}
