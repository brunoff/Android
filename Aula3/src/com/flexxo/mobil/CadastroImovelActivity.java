package com.flexxo.mobil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.flexxo.mobil.infra.vo.imovel.TipoImovel;

public class CadastroImovelActivity extends Activity {
	
	private TextView txtCodigo;
	private TextView txtImovel;
	private Spinner spnUf;
	private Spinner spnTipo;
//	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_imovel_activity);
		
		txtCodigo = (TextView)findViewById(R.id.cadastro_imovel_txt_codigo);
		txtImovel = (TextView)findViewById(R.id.cadastro_imovel_txt_imovel);
		spnUf = (Spinner)findViewById(R.id.cadastro_imovel_txt_uf);
		spnTipo = (Spinner)findViewById(R.id.cadastro_imovel_txt_tipo);
//		imageView = (ImageView)findViewById(R.id.cadastro_imovel_image_view);
		
		initSpnTipo();
	}

	private void initSpnTipo() {
		List<Map<String, String>> valores = new ArrayList<Map<String,String>>();		
		Map<String,String> map;
		
		for (TipoImovel tipo : TipoImovel.getAll()) {
			map = new HashMap<String, String>();
			map.put("nome", tipo.getNome());
			valores.add(map);
		}
		
		String[] from = new String[] {"nome"};
		int[] to = new int[] {android.R.id.text1};
		SimpleAdapter adapter =  new SimpleAdapter(this, valores, android.R.layout.simple_list_item_1, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		spnTipo.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,1,10,"Foto").setIcon(android.R.drawable.ic_menu_camera).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, 1010); 
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == 1010) { 
	            Bitmap foto = (Bitmap) data.getExtras().get("data");
	            addFotoTela(foto);
	        } 
	}

	private void addFotoTela(Bitmap foto) {
		fotos.add(foto);
		ImageView image = new ImageView(CadastroImovelActivity.this);
		image.setImageBitmap(foto);
		image.setLayoutParams(new ViewGroup.LayoutParams(250, 250));

		
		LinearLayout linear = (LinearLayout)txtCodigo.getParent();
		linear.addView(image);
	}
	
	private List<Bitmap> fotos = new ArrayList<Bitmap>(); 
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("numero",fotos.size());
		for (int i = 0; i<fotos.size();i++) {
			outState.putParcelable("imagem"+i, fotos.get(i));
		}			
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		int total = savedInstanceState.getInt("numero");
		for(int i = 0; i < total;i++){
			addFotoTela((Bitmap)savedInstanceState.getParcelable("imagem"+i));
		}		
		super.onRestoreInstanceState(savedInstanceState);
	}
}
