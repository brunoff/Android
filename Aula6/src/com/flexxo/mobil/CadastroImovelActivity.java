package com.flexxo.mobil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.flexxo.mobil.infra.vo.imovel.TipoImovel;

public class CadastroImovelActivity extends Activity {
	private Imovel imovelAtual;

	private TextView txtCodigo;
	private TextView txtImovel;
	private TextView txtEndereco;
	private Spinner spnUf;
	private Spinner spnTipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_imovel_activity);

		txtCodigo = (TextView) findViewById(R.id.cadastro_imovel_txt_codigo);
		txtImovel = (TextView) findViewById(R.id.cadastro_imovel_txt_imovel);
		txtEndereco = (TextView) findViewById(R.id.cadastro_imovel_txt_endereco);
		spnUf = (Spinner) findViewById(R.id.cadastro_imovel_txt_uf);
		spnTipo = (Spinner) findViewById(R.id.cadastro_imovel_txt_tipo);

		imovelAtual = new Imovel();
		initSpnTipo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 10, "Foto").setIcon(android.R.drawable.ic_menu_camera)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, 2, 20, "Salvar").setIcon(R.drawable.ic_ab_salvar_light)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
		imovelAtual.setImovel(txtImovel.getText().toString());
		imovelAtual.setEndereco(txtEndereco.getText().toString());

		if (imovelAtual.validar()) {
			buscarCordenadasImovel();
			imovelAtual.save();
			Toast.makeText(this, "Imovel salvo com sucesso!", Toast.LENGTH_LONG)
					.show();
			finish();
		} else
			Toast.makeText(this, "Erro ao salvar o imovel", Toast.LENGTH_LONG)
					.show();

	}

	private void buscarCordenadasImovel() {

		Geocoder geocoder = new Geocoder(this, Locale.US);

		try {
			List<Address> loc = geocoder.getFromLocationName(imovelAtual.getEndereco(), 5);
			if (loc.size() > 0){
				imovelAtual.setLatitude(loc.get(0).getLatitude());
				imovelAtual.setLongitude(loc.get(0).getLongitude());
			}
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
			Toast.makeText(this, "IOException:  " + e.getMessage(), 20).show();
		}
		

	}

	private void addFotoTela(Bitmap foto) {
		fotos.add(foto);
		ImageView image = new ImageView(CadastroImovelActivity.this);
		image.setImageBitmap(foto);
		image.setLayoutParams(new ViewGroup.LayoutParams(250, 250));

		LinearLayout linear = (LinearLayout) txtCodigo.getParent();
		linear.addView(image);
	}

	private List<Bitmap> fotos = new ArrayList<Bitmap>();

	private void initSpnTipo() {
		List<Map<String, String>> valores = new ArrayList<Map<String, String>>();
		Map<String, String> map;

		for (TipoImovel tipo : TipoImovel.getAll()) {
			map = new HashMap<String, String>();
			map.put("nome", tipo.getNome());
			valores.add(map);
		}

		String[] from = new String[] { "nome" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleAdapter adapter = new SimpleAdapter(this, valores,
				android.R.layout.simple_list_item_1, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		spnTipo.setAdapter(adapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1010) {
			Bitmap foto = (Bitmap) data.getExtras().get("data");
			addFotoTela(foto);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("numero", fotos.size());
		for (int i = 0; i < fotos.size(); i++) {
			outState.putParcelable("imagem" + i, fotos.get(i));
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		int total = savedInstanceState.getInt("numero");
		for (int i = 0; i < total; i++) {
			addFotoTela((Bitmap) savedInstanceState.getParcelable("imagem" + i));
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
}
