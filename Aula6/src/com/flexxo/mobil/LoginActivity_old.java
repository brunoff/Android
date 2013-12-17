package com.flexxo.mobil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.flexxo.mobil.infra.vo.Usuario;

public class LoginActivity_old extends Activity {
	private EditText txtUsuario;
	private EditText txtSenha;
	private Button btn;

	private AsyncTask<Void, String, Usuario> taskUsuario;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_activity);
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		mDrawerLayout.setDrawerShadow(R.drawable.ic_launcher,
	            GravityCompat.START);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_ab_salvar_dark, R.string.app_name, R.string.action_settings) {
			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
//				getActionBar().setTitle("tti1");
				invalidateOptionsMenu();
			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
//				getActionBar().setTitle("JAVATECHIG.COM");
				invalidateOptionsMenu();
			}
		};

		// Setting DrawerToggle on DrawerLayout
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	    getActionBar().setHomeButtonEnabled(true);

	    getActionBar().setDisplayShowHomeEnabled(true);
		
		// mDrawerLayout.closeDrawers();
		// mDrawerToggle = new ActionBarDrawerToggle(
		// this, /* host Activity */
		// mDrawerLayout, /* DrawerLayout object */
		// R.drawable.ic_ab_salvar_dark, /* nav drawer icon to replace 'Up'
		// caret */
		// R.string.app_name, /* "open drawer" description */
		// R.string.action_settings /* "close drawer" description */
		// ) {
		//
		// /** Called when a drawer has settled in a completely closed state. */
		// public void onDrawerClosed(View view) {
		// getActionBar().setTitle("titiooooooo");
		// }
		//
		// /** Called when a drawer has settled in a completely open state. */
		// public void onDrawerOpened(View drawerView) {
		// getActionBar().setTitle("afe");
		// }
		// };
		//
		// // Set the drawer toggle as the DrawerListener
		// mDrawerLayout.setDrawerListener(mDrawerToggle);
		//
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setHomeButtonEnabled(true);

		if (1 == 1)
			return;

		// btn = (Button) findViewById(R.id.button1);
		// txtUsuario = (EditText) findViewById(R.id.editText1);
		// txtSenha = (EditText) findViewById(R.id.editText2);
		txtSenha.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					btn.performClick();
					return true;
				}
				return false;
			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (taskUsuario != null)
					return;

				String usuario = txtUsuario.getText().toString();
				String senha = txtSenha.getText().toString();

				if (TextUtils.isEmpty(usuario)) {
					txtUsuario.requestFocus();
					txtUsuario.setError("Informe seu usuario!");
					return;
				}

				if (TextUtils.isEmpty(senha)) {
					txtSenha.requestFocus();
					txtSenha.setError("Informe sua senha!");
					return;
				}

				taskUsuario = new AsyncTask<Void, String, Usuario>() {
					ProgressDialog dialog;

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						dialog = new ProgressDialog(LoginActivity_old.this);
						dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						dialog.setIndeterminate(true);
						dialog.setTitle("Aguarde");
						dialog.setCancelable(false);
						dialog.show();

						txtUsuario.setError(null);
						txtSenha.setError(null);
						publishProgress("Inicializando");
					}

					@Override
					protected Usuario doInBackground(Void... params) {
						publishProgress("Executando");
						if (1 == 1)
							return new Usuario("bruno", "bruno");

						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Usuario usuario = Usuario.get(txtUsuario.getText().toString());
						if (usuario != null) {
							if (txtSenha.getText().toString().equals(usuario.getSenha())) {

								return usuario;
							}
						}
						return null;

					}

					@Override
					protected void onPostExecute(Usuario usuario) {
						super.onPostExecute(usuario);
						publishProgress("Finalizando");
						txtSenha.setText("");
						if (usuario != null) {
							Toast.makeText(LoginActivity_old.this, "Bem vindo " + usuario.getUsuario(), Toast.LENGTH_SHORT).show();
							startActivity(new Intent(LoginActivity_old.this, MenuActivity.class));
						} else {
							txtUsuario.setError("Usuário ou senha inválidos");
							txtUsuario.requestFocus();
						}
						dialog.dismiss();
						taskUsuario = null;
					}

					protected void onProgressUpdate(String[] values) {
						dialog.setMessage(values[0]);
					};

				};
				taskUsuario.execute((Void) null);

				// Usuario usuarios = Usuario.get(txt.getText().toString());
				// Toast.makeText(LoginActivity.this,
				// String.valueOf(usuarios.size()), Toast.LENGTH_LONG).show();
				// else
				// txt.setError("QUe isssoooo");
			}
		});
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    //mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
//	    mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (mDrawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
