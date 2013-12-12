package com.flexxo.mobil;

import com.flexxo.mobil.infra.SqliteHelper;

import android.app.Application;

public class MobilApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//init do banco de dados
		SqliteHelper.init(this);
	}
}
