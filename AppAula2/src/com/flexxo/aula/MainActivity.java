package com.flexxo.aula;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btnOk;
	private EditText txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnOk = (Button) findViewById(R.id.activity_main_buttonOK);
		txt = (EditText) findViewById(R.id.editText1);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,MainActivity2.class);
				i.putExtra("usuario", txt.getText().toString());
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add("Teste").setIcon(R.drawable.ic_launcher)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add("Teste3").setIcon(R.drawable.icon_like)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add("Teste2");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String log = item.getTitle().toString();
		Log.d("Logger", log);

		Toast.makeText(this, log, Toast.LENGTH_LONG).show();

		Dialog dialog = new Dialog(this);
		dialog.setTitle(log);
		dialog.show();

		btnOk.setVisibility(View.INVISIBLE);

		return super.onOptionsItemSelected(item);
	}

}
