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
import android.os.Environment;
import android.widget.ImageView;
import com.flexxo.mobil.infra.dao.ImovelDao;
import com.flexxo.mobil.infra.vo.Endereco;
import com.google.android.gms.internal.au;

public class Imovel {
	public static enum Sql {
		TABLE_NAME("Imovel"),

		CODIGO("codigo_imov"),
		TIPO("tipo_imov"),
		NOME("nome_imov"),
		ENDERECO("endereco_imov"), 
		BAIRRO("bairro_imov"), 
		CIDADE("cidade_imov"), 
		UF("uf_imov"), 
		LATITUDE("latitude_imov"), 
		LONGITUDE("longitude_imov");

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
		tipo = new TipoImovel();
	}

	public Imovel(Cursor cursor) {
		this();
		
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
		this.tipo = TipoImovel.get(cursor.getString(cursor.getColumnIndexOrThrow(Sql.TIPO.toString())));
		carregaFotos();
	}

	public Imovel(JSONObject jsonObject) throws JSONException {
		this();
		
		this.codigo = jsonObject.getString("codigo");
		this.nome = jsonObject.getString("nome");

		if (!jsonObject.isNull("endereco"))
			this.endereco = new Endereco(jsonObject.getJSONObject("endereco"));

		if (!jsonObject.isNull("tipoImovel"))
			this.tipo = new TipoImovel(jsonObject.getJSONObject("tipoImovel"));
		carregaFotos();
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
	
	public void saveOrUpdate(){
		ajustaFotos();
		ImovelDao dao = new ImovelDao();
		dao.saveOrUpdate(this);
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
	
	private void carregaFotos(){
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		File aux;
		int i=1;
		while (true) {
			aux = new File(extStorageDirectory,codigo+"_"+i+".jpg");
			if (!aux.exists())
				break;
			else
				fotos.add(String.valueOf(i));
			i++;
		}
	}
	
	private void ajustaFotos() {
		
		for (String index : fotos) {
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			File fCerto = new File(extStorageDirectory, codigo + "_" + index + ".jpg"); 
			if (!fCerto.exists()){
				new File(extStorageDirectory,"nullo_"+index+".jpg").renameTo(fCerto);
			}				
		}
	}

	public List<String> getFotos() {
		return fotos;
	}

	public void removerFoto(int position) {
		fotos.remove(position);
	}
}
