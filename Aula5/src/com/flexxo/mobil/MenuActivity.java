package com.flexxo.mobil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.flexxo.mobil.fragments.ListaImovelFragment;
import com.flexxo.mobil.fragments.MapaFragment;
import com.flexxo.mobil.infra.vo.imovel.Imovel;

public class MenuActivity extends Activity {

	private MapaFragment mapaFragment;
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
		
		if (findViewById(R.id.menu_frame_detail)!=null)
			tablet = true;

		mapaFragment = new MapaFragment();
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.menu_frame_list, new ListaImovelFragment());
		if (tablet)
			fragmentTransaction.replace(R.id.menu_frame_detail, mapaFragment);
		fragmentTransaction.commit();
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 1, 10, "Adicionar").setIcon(android.R.drawable.ic_dialog_dialer)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		paramMenu.add(0, 2, 10, "Reload").setIcon(android.R.drawable.ic_btn_speak_now);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == 1) {
			// startActivity(new Intent(this, CadastroImovelActivity.class));
			mapaFragment.overlayMap(valores);
		} else {
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.replace(R.id.menu_frame_list, new MapaFragment());
			trans.commit();
			
			FrameLayout frame = (FrameLayout) findViewById(R.id.menu_frame_detail);
			frame.setVisibility(View.GONE);
			
			FrameLayout frameList = (FrameLayout) findViewById(R.id.menu_frame_list);
			frameList.setLayoutParams(
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
			
		}
		//	new ReadWeatherJSONFeedTask().execute("http://10.0.0.103/MvcApplication1/api/testeapi");
		//}
		return true;
	}

	public String readJSONFeed(String URL) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest httpRequest = new HttpGet(URL);
		httpRequest.addHeader("Accept-Encoding", "gzip");
		try {
			HttpResponse response = httpClient.execute(httpRequest);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
				inputStream.close();
			} else {
				Log.d("JSON", "Failed to download file");
			}
		} catch (Exception e) {
			Log.e("readJSONFeed", e.getLocalizedMessage());
		}

		return stringBuilder.toString();

	}

	public byte[] decompress(byte[] compressed) throws IOException {
		GZIPInputStream gzipInputStream;
		if (compressed.length > 4) {
			gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressed, 4, compressed.length - 4));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int value = 0; value != -1;) {
				value = gzipInputStream.read();
				if (value != -1) {
					baos.write(value);
				}
			}
			gzipInputStream.close();
			baos.close();

			return baos.toByteArray();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unused")
	private class ReadWeatherJSONFeedTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			return readJSONFeed(urls[0]);
		}

		protected void onPostExecute(String result) {
			try {
				// JSONObject jsonObject = new JSONObject(result);
				// JSONArray arr = new JSONArray(result)
				JSONArray jsonArrayGeoPoint = new JSONArray(result);
				// new JSONArray(jsonObject
				// .getString("jsondataResult").toString());
				// Toast.makeText(
				// getApplicationContext(),
				// jsonArrayGeoPoint.getString(0).toString() + "||"
				// + jsonArrayGeoPoint.getString(1).toString(),
				// Toast.LENGTH_SHORT).show();

				// String[] strArrtemp = new String[5];
				// Double[] strArrLat = new Double[5];
				// Double[] strArrLon = new Double[5];

				for (int i = 0; i < jsonArrayGeoPoint.length(); i++) {
					try {
						Log.d("Teste", jsonArrayGeoPoint.getJSONObject(i).getString("ID"));

					} catch (JSONException e) {
						Log.e("JsonArray ERROR", e.getLocalizedMessage());
					}
				}

				// String[] arrytemp = new String[2];
				// String temp;
				//
				// for (int i = 0; i < strArrtemp.length; i++) {
				// temp = strArrtemp[i].toString();
				// strArrLat[i] = Double.parseDouble(temp.substring(0, 6));
				// strArrLon[i] = Double.parseDouble(temp.substring(7, 13));
				// }
				// Toast.makeText(
				// getApplicationContext(),
				// "Lat1=" + strArrLat[1].toString() + " & Lon1="
				// + strArrLon.toString(), Toast.LENGTH_SHORT)
				// .show();
				// Button getDir;
				// getDir = (Button)findViewById(R.id.getLocationBtn);
				// getDir.setText(strArrLat[1].toString());

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("ReadWeatherJSONFeedTask", e.getMessage());
			}
		}
	}

	private ArrayList<HashMap<String, Object>> valores = new ArrayList<HashMap<String, Object>>();

	public void overlayMap(ArrayList<HashMap<String, Object>> lista) {
		this.valores = lista;
		// mapaFragment.overlayMap(valores);
	}

	public void gotoImovel(Imovel imovel) {
		mapaFragment.gotoImovel(imovel);
	}

}
