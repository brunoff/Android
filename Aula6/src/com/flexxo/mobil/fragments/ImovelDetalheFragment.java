package com.flexxo.mobil.fragments;

import com.flexxo.mobil.ImovelDetalheActivity;
import com.flexxo.mobil.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImovelDetalheFragment extends Fragment {

	private ImovelDetalheActivity activity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.activity = (ImovelDetalheActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.imovel_detalhe_fragment, container, false);
	}

}
