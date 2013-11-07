package com.flexxo.mobil;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.flexxo.mobil.infra.vo.Usuario;

public class LoginActivity extends Activity {
	private EditText txt;
	private EditText txt2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button btn = (Button)findViewById(R.id.button1);
		txt = (EditText)findViewById(R.id.editText1);
		txt2 = (EditText)findViewById(R.id.editText2);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (txt.getText().toString()==null)
					txt.setError("NULO");
				
				if (txt2.getText().toString().equals(""))
					txt2.setError("NULO");
				
				Usuario usuario = Usuario.get(txt.getText().toString());
				if (usuario != null){
					if (txt2.getText().toString().equals(usuario.getSenha()))
						startActivity(new Intent(LoginActivity.this,LogingActivity.class));
				}
				List<Usuario> usuarios = Usuario.getAll();
				Toast.makeText(LoginActivity.this, String.valueOf(usuarios.size()), Toast.LENGTH_LONG).show();
				//				else
//					txt.setError("QUe isssoooo");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
