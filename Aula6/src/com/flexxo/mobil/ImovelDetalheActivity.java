package com.flexxo.mobil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.flexxo.mobil.CadastroImovelActivity.CustomPagerAdapter;
import com.flexxo.mobil.fragments.CadastroImovelFragment;
import com.flexxo.mobil.fragments.ImovelDetalheFragment;
import com.flexxo.mobil.fragments.ListaFotosFragment;
import com.flexxo.mobil.infra.vo.imovel.Imovel;

public class ImovelDetalheActivity extends FragmentActivity {
	private ListaFotosFragment listaFotos;
	private ImovelDetalheFragment detalhe;
	private ViewPager pager;
	private Imovel imovelAtual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imovel_detalhe_activity);
		if (getIntent().hasExtra("Imovel")) {
			imovelAtual = Imovel.get(getIntent().getExtras().getString("Imovel"));
		} else {
			imovelAtual = new Imovel();
		}

		listaFotos = new ListaFotosFragment();
		detalhe = new ImovelDetalheFragment();

		Map<String, Fragment> abas = new LinkedHashMap<String, Fragment>();
		abas.put("Dados", detalhe);
		abas.put("Fotos", listaFotos);
		CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager(), getActionBar(), abas);

		pager = (ViewPager) findViewById(R.id.detalhe_imovel_activity_view_pager);
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


		// FragmentTransaction localFragmentTransaction =
		// getFragmentManager().beginTransaction();
		// localFragmentTransaction.replace(R.id.imovel_detalhe_frame, new
		// ImovelDetalheFragment());
		// localFragmentTransaction.commit();
	}

	public class CustomPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments;
		private List<String> labels;

		public CustomPagerAdapter(FragmentManager fm, ActionBar actionBar, Map<String, Fragment> fragments) {
			super(fm);
			this.fragments = new ArrayList<Fragment>();
			this.labels = new ArrayList<String>();

			for (Entry<String, Fragment> fragment : fragments.entrySet()) {
				this.fragments.add(fragment.getValue());
				this.labels.add(fragment.getKey());

				actionBar.addTab(actionBar.newTab().setText(fragment.getKey()).setTabListener(tabListener));
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

	public Imovel getImovelAtual() {
		return imovelAtual;
	}
}
