package com.flexxo.mobil;

import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.flexxo.mobil.fragments.ListaImovelFragment;
import com.flexxo.mobil.fragments.MapaFragment;
import com.flexxo.mobil.infra.vo.imovel.Imovel;

public class MenuActivity extends Activity {

	private MapaFragment mapaFragment;
	private ListaImovelFragment listFragment;
	private boolean tablet = false;

	private DrawerLayout dl;
	private ActionBarDrawerToggle dt;

	@Override
	public void onBackPressed() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage("Voce deseja fazer logoff?");
		dialog.setNegativeButton("Não", null);
		dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		});
		dialog.show();
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.menu_activity);
		

//		new teste().execute((Void) null);
		if (findViewById(R.id.menu_drawer_layout) != null){
			dl = (DrawerLayout) findViewById(R.id.menu_drawer_layout);
//			dl.setDrawerShadow(R.drawable.ic_launcher, GravityCompat.START);
			dt = new ActionBarDrawerToggle(this, dl, R.drawable.ic_ab_salvar_light,
					R.string.auth_client_requested_by_msg, R.string.action_settings);

//			dt = new ActionBarDrawerToggle(this, dl, R.drawable.ic_ab_salvar_light,
//					R.string.auth_client_requested_by_msg, R.string.action_settings) {
//				/** Called when drawer is closed */
//				public void onDrawerClosed(View view) {
//					// getActionBar().setTitle("tti1");
////					invalidateOptionsMenu();
//				}
//
//				/** Called when a drawer is opened */
//				public void onDrawerOpened(View drawerView) {
//					// getActionBar().setTitle("JAVATECHIG.COM");
////					invalidateOptionsMenu();
//				}
//			};

			// Setting DrawerToggle on DrawerLayout
			dl.setDrawerListener(dt);

			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayShowHomeEnabled(true);
		}

		mapaFragment = new MapaFragment();
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		listFragment = new ListaImovelFragment();
		fragmentTransaction.replace(R.id.menu_frame_list, listFragment);
		fragmentTransaction.replace(R.id.menu_frame_detail, mapaFragment);
		fragmentTransaction.commit();
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 1, 10, "Adicionar").setIcon(
				android.R.drawable.ic_dialog_dialer);
		paramMenu.add(0, 2, 10, "Reload").setIcon(
				android.R.drawable.ic_btn_speak_now);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

		if (dt != null && dt.onOptionsItemSelected(paramMenuItem)) {
			return true;
		}
		

		if (paramMenuItem.getItemId() == 1) {
			startActivityForResult(new Intent(this,
					CadastroImovelActivity.class), 1010);
		} else {
			new teste().execute((Void) null);
		}

		// FragmentTransaction trans = getFragmentManager().beginTransaction();
		// trans.replace(R.id.menu_frame_list, new MapaFragment());
		// trans.commit();
		//
		// FrameLayout frame = (FrameLayout)
		// findViewById(R.id.menu_frame_detail);
		// frame.setVisibility(View.GONE);
		//
		// FrameLayout frameList = (FrameLayout)
		// findViewById(R.id.menu_frame_list);
		// frameList.setLayoutParams(
		// new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		// LinearLayout.LayoutParams.MATCH_PARENT));
		// }
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1010 && resultCode == RESULT_OK)
			listFragment.reload();
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class teste extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				WebService ws = new WebService();
				ws.receberTiposImovel();
				ws.receberImovel();

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			listFragment.reload();
			super.onPostExecute(result);
		}

	}

	public void overlayMap(List<Imovel> lista) {
		mapaFragment.overlayMap(lista);
	}

	public void gotoImovel(Imovel imovel) {
		mapaFragment.gotoImovel(imovel);
	}

}
