package com.flexxo.mobil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
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
import com.google.android.gms.internal.fr;

public class CadastroImovelActivity extends FragmentActivity {
	private Imovel imovelAtual;
	private ViewPager pager;
	private ListaFotosFragment listaFotos;
	private CadastroImovelFragment dados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_imovel_activity);
		
		listaFotos = new ListaFotosFragment();
		dados = new CadastroImovelFragment();
		
		Map<String, Fragment> abas = new LinkedHashMap<String, Fragment>(); 		
		abas.put("Dados", dados );
		abas.put("Fotos", listaFotos);		
		CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager(),getActionBar(), abas);
		
		pager = (ViewPager) findViewById(R.id.cadastro_imovel_activity_view_pager);		
		pager.setAdapter(adapter);
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
		private List<Fragment> fragments;
		private List<String> labels;
		public CustomPagerAdapter(FragmentManager fm,ActionBar actionBar,Map<String, Fragment> fragments) {
			super(fm);
			this.fragments = new ArrayList<Fragment>();
			this.labels = new ArrayList<String>();
			
			for (Entry<String, Fragment> fragment : fragments.entrySet()) {
				this.fragments.add(fragment.getValue());
				this.labels.add(fragment.getKey());
				
				actionBar.addTab(actionBar.newTab()
	                    .setText(fragment.getKey())
	                    .setTabListener(tabListener));
			}
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
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
		dados.save();	
		
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
			listaFotos.loadListView();
//			CustomPagerAdapter adapter = (CustomPagerAdapter)pager.getAdapter();
//			adapter.notifyDataSetChanged();
//			ListaFotosFragment frag = (ListaFotosFragment)adapter.getItem(1);
			
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
