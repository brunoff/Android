package com.flexxo.mobil.fragments;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.flexxo.mobil.CadastroImovelActivity;
import com.flexxo.mobil.ImovelDetalheActivity;
import com.flexxo.mobil.R;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.google.android.gms.internal.ac;

public class ListaFotosFragment extends ListFragment {
	private Imovel imovelAtual;
	private CadastroImovelActivity activity;
	private ImovelDetalheActivity activityDetalhe;
	
	private ListView lista;
	private List<String> listaValores;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		this.lista = ((ListView) getView().findViewById(android.R.id.list));
		
		if (getActivity() instanceof CadastroImovelActivity)
			this.activity = ((CadastroImovelActivity) getActivity());
		else
			this.activityDetalhe = ((ImovelDetalheActivity) getActivity());
		
		this.lista.setClickable(false);
		this.lista.setLongClickable(true);
		this.lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		registerForContextMenu(this.lista);
		loadListView();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	public void loadListView() {
		FotosAdapter adapter = null;
		if (activity != null){

			listaValores = activity.getImovelAtual().getFotos();
			adapter = new FotosAdapter(this.activity, listaValores);
			imovelAtual = activity.getImovelAtual();
		}else if (activityDetalhe != null){
			listaValores = activityDetalhe.getImovelAtual().getFotos();
			adapter = new FotosAdapter(this.activityDetalhe, listaValores);
			imovelAtual = activityDetalhe.getImovelAtual();
		}

		this.lista.setAdapter(adapter);
		
		setListShown(true);
	}

	public class FotosAdapter extends ArrayAdapter<String> {
		private List<String> valores;

		public FotosAdapter(Context context, int resource, int textViewResourceId) {
			super(context, resource, textViewResourceId);
		}

		public FotosAdapter(Context context, List<String> valores) {
			super(context, R.layout.lista_foto_fragment_row, valores);
			this.valores = valores;
		}

		public FotosAdapter(Context context, int resource) {
			super(context, resource);
		}

		public FotosAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int indexImg = position+1;
			if (convertView == null) {
				LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();

				convertView = inflater.inflate(R.layout.lista_foto_fragment_row, parent, false);

			}

			ImageView imageView = (ImageView) convertView.findViewById(R.id.lista_foto_frag_row_img);

			
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
						String file = extStorageDirectory+"/"+ imovelAtual.getCodigo() + "_" + indexImg + ".jpg";
//			File file = new File(extStorageDirectory, imovelAtual.getCodigo() + "_" + position + ".jpg");
			
			Bitmap myBitmap = BitmapFactory.decodeFile(file);
			imageView.setImageBitmap(myBitmap);
			

			return convertView;
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add("Excluir");
		super.onCreateContextMenu(menu, v, menuInfo);
	}	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int position = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
		imovelAtual.removerFoto(position);
		return super.onContextItemSelected(item);
	}
}
