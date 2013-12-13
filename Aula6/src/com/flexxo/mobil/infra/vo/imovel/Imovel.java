package com.flexxo.mobil.infra.vo.imovel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Im;
import com.flexxo.mobil.infra.dao.ImovelDao;
import com.flexxo.mobil.infra.dao.UsuarioDao;
import com.flexxo.mobil.infra.vo.Endereco;
import com.flexxo.mobil.infra.vo.Usuario;
import com.flexxo.mobil.infra.vo.Usuario.Sql;
import com.google.android.gms.internal.en;
import com.google.android.gms.internal.fo;

public class Imovel {

	// private static List<Imovel> lista = new ArrayList<Imovel>();

	// static {
	// Imovel imovel = new Imovel();
	//
	// imovel.setImovel("11");
	// imovel.setCidade("Caxias do sul");
	// imovel.setLatitude(-29.180379d);
	// imovel.setLongitude(-51.208649d);
	//
	// lista.add(imovel);
	// imovel = new Imovel();
	//
	// imovel.setImovel("223");
	// imovel.setCidade("Caxias do sul");
	// imovel.setLatitude(-29.174665d);
	// imovel.setLongitude(-51.140671d);
	// lista.add(imovel);
	//
	// imovel = new Imovel();
	//
	// imovel.setImovel("32");
	// imovel.setCidade("Caxias do sul");
	// imovel.setLatitude(-29.179425d);
	// imovel.setLongitude(-51.179852);
	// lista.add(imovel);
	// }

	public static enum Sql {
		TABLE_NAME("Imovel"),

		CODIGO("codigo_imov"), NOME("nome_imov"), ENDERECO("endereco_imov"), BAIRRO("bairro_imov"), CIDADE("cidade_imov"), UF("uf_imov"), LATITUDE("latitude_imov"), LONGITUDE("longitude_imov");

		private String nome;

		private Sql(String nome) {
			this.nome = nome;
		}

		@Override
		public String toString() {
			return this.nome;
		}
	}

	private String codigo;
	private String nome;
	private Endereco endereco;
	private TipoImovel tipo;
	private HashMap<String, String> extras;
	private List<String> fotos;

	public Imovel() {
		extras = new HashMap<String, String>();
		fotos = new ArrayList<String>();
	}

	public Imovel(Cursor cursor) {
		this.codigo = cursor.getString(cursor.getColumnIndexOrThrow(Sql.CODIGO.toString()));
		this.nome = cursor.getString(cursor.getColumnIndexOrThrow(Sql.NOME.toString()));
		Endereco e = new Endereco();
		e.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(Sql.ENDERECO.toString())));
		e.setBairro(cursor.getString(cursor.getColumnIndexOrThrow(Sql.BAIRRO.toString())));
		e.setCidade(cursor.getString(cursor.getColumnIndexOrThrow(Sql.CIDADE.toString())));
		e.setUf(cursor.getString(cursor.getColumnIndexOrThrow(Sql.UF.toString())));
		e.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(Sql.LATITUDE.toString())));
		e.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(Sql.LONGITUDE.toString())));

		this.endereco = e;
	}

	public Imovel(JSONObject jsonObject) throws JSONException {
		this.codigo = jsonObject.getString("codigo");
		this.nome = jsonObject.getString("nome");

		if (!jsonObject.isNull("endereco"))
			this.endereco = new Endereco(jsonObject.getJSONObject("endereco"));

		if (!jsonObject.isNull("tipoImovel"))
			this.tipo = new TipoImovel(jsonObject.getJSONObject("tipoImovel"));
	}

	public static Imovel get(String codigo) {
		Cursor cursor = new ImovelDao().get(codigo);

		if (cursor.moveToFirst()) {
			return new Imovel(cursor);
		}

		return null;
	}

	public static List<Imovel> getAll() {
		Cursor cursor = new ImovelDao().getAll();
		List<Imovel> retorno = new ArrayList<Imovel>();

		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			retorno.add(new Imovel(cursor));
		}

		return retorno;
	}

	public void save() {
		ImovelDao dao = new ImovelDao();
		dao.save(this);
	}

	// gets sets
	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public TipoImovel getTipo() {
		return tipo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setTipo(TipoImovel tipo) {
		this.tipo = tipo;
	}

	public boolean validar() {
		if (this.codigo == null || this.codigo.trim().length() == 0)
			return false;

		if (this.nome == null || this.nome.trim().length() == 0)
			return false;

		return true;
	}

	public Endereco getEndereco() {
		if (endereco == null)
			return new Endereco();
		return endereco;
	}

	public static void clearAll() {
		ImovelDao dao = new ImovelDao();
		dao.clearAll();
	}

	public void adicionaFoto(Bitmap foto) {
		int index = fotos.size() + 1;
		fotos.add(String.valueOf(index));

		// Bitmap bbicon
		// bbicon=BitmapFactory.decodeResource(getResources(),R.drawable.bannerd10);
		// ByteArrayOutputStream baosicon = new ByteArrayOutputStream();
		// bbicon.compress(Bitmap.CompressFormat.PNG,0, baosicon);
		// bicon=baosicon.toByteArray();

		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		OutputStream outStream = null;
		File file = new File(extStorageDirectory, codigo + "_" + index + ".jpg");
		try {
			outStream = new FileOutputStream(file);
			foto.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<String> getFotos() {
		return fotos;
	}
}
