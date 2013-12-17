package com.flexxo.mobil.fragments;

import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.flexxo.mobil.MenuActivity;
import com.flexxo.mobil.R;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.google.android.gms.internal.ac;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MarkerOptionsCreator;

public class MapaFragment extends MapFragment {
	private GoogleMap googleMap;
	private HashMap<String, Marker> markers;
	private HashMap<Marker, Imovel> markersImoveis;
	
	
	private MenuActivity activity;

	public void onActivityCreated(Bundle paramBundle) {
		this.activity = (MenuActivity) getActivity();
		super.onActivityCreated(paramBundle);
	}

	public void overlayMap(List<Imovel> lista) {
		googleMap = getMap();
		// googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
		//
		// @Override
		// public View getInfoWindow(Marker arg0) {
		//
		// ((MenuActivity)getActivity()).gotoImovel(new Imovel());
		// return null;
		// }
		//
		// @Override
		// public View getInfoContents(Marker arg0) {
		//
		// return null;
		// }
		// });
		if (googleMap == null)
			return;

		googleMap.clear();

		markers = new HashMap<String, Marker>();
		markersImoveis = new HashMap<Marker, Imovel>();
		
		LatLng latLng = null;

		for (Imovel imovel : lista) {
			latLng = new LatLng(imovel.getEndereco().getLatitude(), imovel.getEndereco().getLongitude());
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			Marker m = googleMap.addMarker(
					new MarkerOptions()
					.position(latLng)
					.title(imovel.getTipo().getNome())
					.snippet(imovel.getNome())
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			
			markers.put(imovel.getCodigo(), m);
			markersImoveis.put(m, imovel);
		}

		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
		googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				Imovel imovel = markersImoveis.get(marker);
				activity.gotoImovelDetalhe(imovel);
			}
		});
		// if (latLng != null)
		// googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
		// 10));

	}
	
	public void gotoImovel(Imovel imovel) {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		
		Marker m = markers.get(imovel.getCodigo());
		builder.include(m.getPosition());
		m.showInfoWindow();
		// for (Entry<String,Marker> entry : markers.entrySet()) {

		// if (entry.getValue().getPosition().latitude == imovel.getLatitude()
		// &&
		// m.getPosition().longitude == imovel.getLongitude()){
		// builder.include(m.getPosition());
		// m.showInfoWindow();
		// break;
		// }
		// }
		LatLngBounds bounds = builder.build();
		int padding = 600;

		CameraUpdate cu3 = CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target(m.getPosition()).zoom(14).build());

		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
		CameraUpdate cu2 = CameraUpdateFactory.zoomTo(12);
		// googleMap.moveCamera(cu);
		googleMap.animateCamera(cu3);
	}
	
	public class CustomInfoWindowAdapter implements InfoWindowAdapter {
		private View viewCache;

		public CustomInfoWindowAdapter() {
			this.viewCache = activity.getLayoutInflater().inflate(R.layout.info_window_map, null);
		}

		@Override
		public View getInfoWindow(Marker arg0) {
			return null;
		}

		@Override
		public View getInfoContents(Marker marker) {
			Imovel imovel = markersImoveis.get(marker);

			TextView txt = (TextView) viewCache.findViewById(R.id.info_window_map_nome);
			txt.setText(imovel.getNome());
			
			txt = (TextView) viewCache.findViewById(R.id.info_window_map_bairro);
			txt.setText(imovel.getEndereco().getBairro());
			
			ImageView img = (ImageView) viewCache.findViewById(R.id.info_window_map_imagem);
			img.setImageResource(R.drawable.ic_launcher);

			return viewCache;
		}
	}
}