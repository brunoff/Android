package com.flexxo.mobil.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.flexxo.mobil.ImovelDetalheActivity;
import com.flexxo.mobil.R;
import com.flexxo.mobil.infra.vo.Endereco;
import com.flexxo.mobil.infra.vo.imovel.Imovel;

public class ImovelDetalheFragment extends Fragment {

	private ImovelDetalheActivity activity;
	
	private TextView txtCodigo;
	private TextView txtNome;
	private TextView txtEndereco;
	private TextView txtBairro;
	private TextView txtCidade;
	private TextView txtPosicao;
	private TextView txtTipo;

	private Imovel imovelAtual;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imovel_detalhe_fragment, container, false);
		this.activity = (ImovelDetalheActivity) getActivity();
		this.imovelAtual = activity.getImovelAtual();
		
		txtCodigo = (TextView) v.findViewById(R.id.detalhe_imovel_txt_codigo);
		txtNome = (TextView) v.findViewById(R.id.detalhe_imovel_txt_nome);
		txtEndereco = (TextView) v.findViewById(R.id.detalhe_imovel_txt_endereco);
		txtBairro = (TextView) v.findViewById(R.id.detalhe_imovel_txt_bairro);
		txtCidade = (TextView) v.findViewById(R.id.detalhe_imovel_txt_cidade);
		txtPosicao = (TextView) v.findViewById(R.id.detalhe_imovel_txt_posicao);
		txtTipo = (TextView) v.findViewById(R.id.detalhe_imovel_txt_tipo);
	
		txtCodigo.setText(imovelAtual.getCodigo());
		txtNome.setText(imovelAtual.getNome());
		txtTipo.setText(imovelAtual.getTipo().getNome());
		Endereco e = imovelAtual.getEndereco();

		txtEndereco.setText(e.getEndereco());
		txtBairro.setText(e.getBairro());
		txtCidade.setText(e.getCidade());
		txtPosicao.setText(e.getLatitude() + "/" + e.getLongitude());
		
		
		
		return v;
	}

}
