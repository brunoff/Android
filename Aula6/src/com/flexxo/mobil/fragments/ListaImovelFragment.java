package com.flexxo.mobil.fragments;

import java.io.File;
import java.util.List;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.flexxo.mobil.CadastroImovelActivity;
import com.flexxo.mobil.MenuActivity;
import com.flexxo.mobil.R;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.google.android.gms.ads.a;
import com.squareup.picasso.Picasso;

public class ListaImovelFragment extends ListFragment {
	private MenuActivity activity;
	private ListView lista;
	private List<Imovel> listaValores;

	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);

		this.lista = ((ListView) getView().findViewById(android.R.id.list));
		this.activity = ((MenuActivity) getActivity());
		this.lista.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				activity.gotoImovelEdicao((Imovel) adapter.getItemAtPosition(position));
				
				return true;
			}
		});
		loadListView();
	}

	private void loadListView() {
		listaValores = Imovel.getAll();
		ImoveisAdapter adapter = new ImoveisAdapter(this.activity, listaValores);

		this.lista.setAdapter(adapter);
		setListShown(true);
		activity.overlayMap(listaValores);
	}

	public void reload() {
		loadListView();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Imovel imovel = listaValores.get(position);
		if (imovel != null) {
			//Toast.makeText(activity, hash.get("nome").toString(), Toast.LENGTH_LONG).show();
			activity.gotoFocusImovel(imovel);
		}
		super.onListItemClick(l, v, position, id);

	}

	public class ImoveisAdapter extends ArrayAdapter<Imovel> {
		private List<Imovel> valores;

		public ImoveisAdapter(Context context, int resource, Imovel[] objects) {
			super(context, resource, objects);
		}

		public ImoveisAdapter(Context context, int resource, int textViewResourceId, Imovel[] objects) {
			super(context, resource, textViewResourceId, objects);
		}

		public ImoveisAdapter(Context context, int resource, int textViewResourceId) {
			super(context, resource, textViewResourceId);
		}

		public ImoveisAdapter(Context context, List<Imovel> valores) {
			super(context, R.layout.lista_imovel_fragment_row, valores);
			this.valores = valores;
		}

		public ImoveisAdapter(Context context, int resource) {
			super(context, resource);
		}

		public ImoveisAdapter(Context context, int resource, int textViewResourceId, List<Imovel> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
				convertView = inflater.inflate(R.layout.lista_imovel_fragment_row, parent, false);

			}
			
			Imovel imovel = valores.get(position);

			TextView textViewItem = (TextView) convertView.findViewById(R.id.lista_imovel_frag_row_nome);

			textViewItem.setText(imovel.getNome());
			
			ImageView imageView = (ImageView) convertView.findViewById(R.id.lista_imovel_frag_row_imagem);
			
			
			if (imovel.getFotos().size()>0){
				String extStorageDirectory = Environment.getExternalStorageDirectory().toString();				
				Picasso.with(activity).load(new File(extStorageDirectory,imovel.getCodigo()+"_"+imovel.getFotos().get(0)+".jpg")).into(imageView);
			}else{
				imageView.setImageResource(android.R.color.transparent);
			}
			

			return convertView;
		}
	}
}
