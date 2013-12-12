package com.flexxo.mobil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flexxo.mobil.infra.vo.Usuario;

public class LoginActivity extends Activity {
	private EditText txtUsuario;
	private EditText txtSenha;
	private Button btn;

	private AsyncTask<Void, String, Usuario> taskUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_activity);

		btn = (Button) findViewById(R.id.button1);
		txtUsuario = (EditText) findViewById(R.id.editText1);
		txtSenha = (EditText) findViewById(R.id.editText2);
		txtSenha.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
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
						dialog = new ProgressDialog(LoginActivity.this);
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
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Usuario usuario = Usuario.get(txtUsuario.getText()
								.toString());
						if (usuario != null) {
							if (txtSenha.getText().toString()
									.equals(usuario.getSenha())) {

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
							Toast.makeText(LoginActivity.this,
									"Bem vindo " + usuario.getUsuario(),
									Toast.LENGTH_SHORT).show();
							startActivity(new Intent(LoginActivity.this,
									MenuActivity.class));
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

	
}
