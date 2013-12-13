package com.flexxo.mobil;

import java.util.List;
import org.apache.http.ReasonPhraseCatalog;
import org.json.JSONException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager.Request;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.flexxo.mobil.fragments.ListaImovelFragment;
import com.flexxo.mobil.fragments.MapaFragment;
import com.flexxo.mobil.infra.vo.imovel.Imovel;

public class MenuActivity extends Activity {

	private MapaFragment mapaFragment;
	private ListaImovelFragment listFragment;
	private boolean tablet = false;

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
		new teste().execute((Void) null);
		if (findViewById(R.id.menu_frame_detail) != null)
			tablet = true;

		mapaFragment = new MapaFragment();
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		listFragment = new ListaImovelFragment();
		fragmentTransaction.replace(R.id.menu_frame_list, listFragment);
		if (tablet)
			fragmentTransaction.replace(R.id.menu_frame_detail, mapaFragment);
		fragmentTransaction.commit();
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 1, 10, "Adicionar").setIcon(android.R.drawable.ic_dialog_dialer);
		paramMenu.add(0, 2, 10, "Reload").setIcon(android.R.drawable.ic_btn_speak_now);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == 1) {
			startActivityForResult(new Intent(this, CadastroImovelActivity.class),1010);
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
