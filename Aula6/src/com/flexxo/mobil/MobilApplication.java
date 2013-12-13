package com.flexxo.mobil;

import org.json.JSONException;
import android.app.Application;
import android.os.AsyncTask;
import com.flexxo.mobil.infra.SqliteHelper;

public class MobilApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// init do banco de dados
		SqliteHelper.init(this);

	}
}
