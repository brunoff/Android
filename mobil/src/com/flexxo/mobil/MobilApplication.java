package com.flexxo.mobil;

import com.flexxo.mobil.infra.SqliteHelper;
import com.flexxo.mobil.infra.vo.Usuario;
import android.app.Application;

public class MobilApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		SqliteHelper.init(this);
	}
}
