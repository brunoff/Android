package com.flexxo.mobil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
		
		
		Button btnCadastrar = (Button)findViewById(R.id.menu_btn_cadastro_imovel);
		
		btnCadastrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MenuActivity.this, CadastroImovelActivity.class));
			}
		});
	}
}
