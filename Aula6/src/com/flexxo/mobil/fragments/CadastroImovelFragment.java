package com.flexxo.mobil.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import com.flexxo.mobil.CadastroImovelActivity;
import com.flexxo.mobil.R;
import com.flexxo.mobil.infra.vo.Endereco;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.flexxo.mobil.infra.vo.imovel.TipoImovel;

public class CadastroImovelFragment extends Fragment {
	private Imovel imovelAtual;
	private CadastroImovelActivity activity;

	private EditText txtCodigo;
	private EditText txtNome;
	private EditText txtEndereco;
	private EditText txtBairro;
	private EditText txtCidade;
	// private EditText txtUf;
	private EditText txtPosicao;

	private Spinner spnTipo;

	public View onCreateView(android.view.LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (CadastroImovelActivity) getActivity();
		imovelAtual = activity.getImovelAtual();
		View v = inflater.inflate(R.layout.cadastro_imovel_fragment, container, false);

		txtCodigo = (EditText) v.findViewById(R.id.cadastro_imovel_txt_codigo);
		txtNome = (EditText) v.findViewById(R.id.cadastro_imovel_txt_nome);
		txtEndereco = (EditText) v.findViewById(R.id.cadastro_imovel_txt_endereco);
		txtBairro = (EditText) v.findViewById(R.id.cadastro_imovel_txt_bairro);
		txtCidade = (EditText) v.findViewById(R.id.cadastro_imovel_txt_cidade);
		txtPosicao = (EditText) v.findViewById(R.id.cadastro_imovel_txt_posicao);
		spnTipo = (Spinner) v.findViewById(R.id.cadastro_imovel_spn_tipo);

		initSpnTipo();
		txtCodigo.setText(imovelAtual.getCodigo());
		txtNome.setText(imovelAtual.getNome());
		Endereco e = imovelAtual.getEndereco();

		txtEndereco.setText(e.getEndereco());
		txtBairro.setText(e.getBairro());
		txtCidade.setText(e.getCidade());
		txtPosicao.setText(e.getLatitude() + "/" + e.getLongitude());
		
		return v;
	}

	private void initSpnTipo() {
		List<Map<String, String>> valores = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		
		int selecionado = -1;
		List<TipoImovel> tipos = TipoImovel.getAll();
		for (int i = 0; i < tipos.size(); i++) {
			TipoImovel tipo = tipos.get(i);
			map = new HashMap<String, String>();
			map.put("codigo", tipo.getCodigo());
			map.put("nome", tipo.getNome());
			valores.add(map);
			
			if (imovelAtual.getTipo() != null && imovelAtual.getTipo().getCodigo() != null && tipo.getCodigo().equals(imovelAtual.getTipo().getCodigo())) {
				selecionado = i;
			}
		}

		String[] from = new String[] { "nome" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleAdapter adapter = new SimpleAdapter(activity, valores, android.R.layout.simple_list_item_1, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		spnTipo.setAdapter(adapter);
		if (selecionado != -1)
			spnTipo.setSelection(selecionado);
	}
	
	public void save(){
		imovelAtual.setCodigo(txtCodigo.getText().toString());
		imovelAtual.setNome(txtNome.getText().toString());
		
		Endereco e = new Endereco();
		e.setEndereco(txtEndereco.getText().toString());
		e.setBairro(txtBairro.getText().toString());
		e.setCidade(txtCidade.getText().toString());
		String aux = txtPosicao.getText().toString();
		e.setLatitude(Double.parseDouble(aux.split("/")[0]));
		e.setLongitude(Double.parseDouble(aux.split("/")[1]));
		imovelAtual.setEndereco(e);	
		if (spnTipo.getSelectedItem() != null)
			imovelAtual.setTipo(TipoImovel.get((((HashMap<String, String>)spnTipo.getSelectedItem()).get("codigo"))));

	}
}
