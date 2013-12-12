package com.flexxo.mobil;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.flexxo.mobil.fragments.ImovelDetalheFragment;

public class ImovelDetalheActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imovel_detalhe_activity);
		
		FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
	    localFragmentTransaction.replace(R.id.imovel_detalhe_frame, new ImovelDetalheFragment());
	    localFragmentTransaction.commit();
	}

}
