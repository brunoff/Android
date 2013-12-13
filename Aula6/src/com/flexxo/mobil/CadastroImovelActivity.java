package com.flexxo.mobil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.flexxo.mobil.fragments.CadastroImovelFragment;
import com.flexxo.mobil.fragments.ListaFotosFragment;
import com.flexxo.mobil.fragments.ListaFotosFragment.FotosAdapter;
import com.flexxo.mobil.infra.vo.Endereco;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.flexxo.mobil.infra.vo.imovel.TipoImovel;

public class CadastroImovelActivity extends FragmentActivity {
	private Imovel imovelAtual;

	private EditText txtCodigo;
	private EditText txtNome;
	private EditText txtEndereco;
	private EditText txtBairro;
	private EditText txtCidade;
//	private EditText txtUf;
	private EditText txtPosicao;
	
	private Spinner spnTipo;
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_imovel_activity);
		
		pager = (ViewPager) findViewById(R.id.cadastro_imovel_activity_view_pager);		
		pager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(),getActionBar()));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		
		if (getIntent().hasExtra("Imovel")){
			imovelAtual = Imovel.get(getIntent().getExtras().getString("Imovel"));
		}else{
			imovelAtual = new Imovel();
		}
		
	}
	
	TabListener tabListener = new TabListener() {
		
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

			
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			pager.setCurrentItem(tab.getPosition());
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public class CustomPagerAdapter extends FragmentPagerAdapter  {

		public CustomPagerAdapter(FragmentManager fm,ActionBar actionBar) {
			super(fm);
			actionBar.addTab(actionBar.newTab()
                    .setText("Dados")
                    .setTabListener(tabListener));
			
			actionBar.addTab(actionBar.newTab()
                    .setText("Fotos")
                    .setTabListener(tabListener));
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0){
				return new CadastroImovelFragment();
			} else if (position == 1){
				return new ListaFotosFragment();
			}
			return null;
			
		}

		@Override
		public int getCount() {
			return 2;
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 10, "Foto").setIcon(android.R.drawable.ic_menu_camera).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, 2, 20, "Salvar").setIcon(R.drawable.ic_ab_salvar_light).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, 1010);
			break;
		case 2:
			salvarImovel();
		}
		return true;
	}

	private void salvarImovel() {
		imovelAtual.setCodigo(txtCodigo.getText().toString());
		imovelAtual.setNome(txtNome.getText().toString());
		
		Endereco e = new Endereco();
		e.setEndereco(txtEndereco.getText().toString());
		e.setBairro(txtBairro.getText().toString());
		e.setCidade(txtCidade.getText().toString());
		String aux = txtPosicao.getText().toString();
		e.setLatitude(Double.parseDouble(aux.split("/")[0]));
		e.setLongitude(Double.parseDouble(aux.split("/")[1]));
		imovelAtual.setEndereco(e);		
		
		if (imovelAtual.validar()) {
			buscarCordenadasImovel();
			imovelAtual.save();
			Toast.makeText(this, "Imovel salvo com sucesso!", Toast.LENGTH_LONG)
					.show();
			setResult(RESULT_OK);
			finish();
		} else
			Toast.makeText(this, "Erro ao salvar o imovel", Toast.LENGTH_LONG)
					.show();

	}

	private void buscarCordenadasImovel() {
		Geocoder geocoder = new Geocoder(this, Locale.US);
		try {
			List<Address> loc = geocoder.getFromLocationName(imovelAtual.getEndereco().getEndereco(), 5);
			if (loc.size() > 0){
				imovelAtual.getEndereco().setLatitude(loc.get(0).getLatitude());
				imovelAtual.getEndereco().setLongitude(loc.get(0).getLongitude());
			}
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
			Toast.makeText(this, "IOException:  " + e.getMessage(), 20).show();
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1010) {
			Bitmap foto = (Bitmap) data.getExtras().get("data");
			imovelAtual.adicionaFoto(foto);
			CustomPagerAdapter adapter = (CustomPagerAdapter)pager.getAdapter();
			ListaFotosFragment frag = (ListaFotosFragment)adapter.getItem(1);
			frag.loadListView();
		}
	}

	public Imovel getImovelAtual() {
		return imovelAtual;
	}

//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("numero", fotos.size());
//		for (int i = 0; i < fotos.size(); i++) {
//			outState.putParcelable("imagem" + i, fotos.get(i));
//		}
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		int total = savedInstanceState.getInt("numero");
//		for (int i = 0; i < total; i++) {
//			addFotoTela((Bitmap) savedInstanceState.getParcelable("imagem" + i));
//		}
//		super.onRestoreInstanceState(savedInstanceState);
//	}
}
