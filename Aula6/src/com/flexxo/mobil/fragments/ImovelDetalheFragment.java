package com.flexxo.mobil.fragments;

import com.flexxo.mobil.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImovelDetalheFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.imovel_detalhe_fragment, container,false);
		
//		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
