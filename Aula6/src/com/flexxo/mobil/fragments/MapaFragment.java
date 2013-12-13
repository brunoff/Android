package com.flexxo.mobil.fragments;

import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends MapFragment {
	private GoogleMap googleMap;
	private HashMap<String,Marker> markers;
	
	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
	}
	
	public void overlayMap(List<Imovel> lista) {
		googleMap = getMap();
//		googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
//			
//			@Override
//			public View getInfoWindow(Marker arg0) {
//				
//				((MenuActivity)getActivity()).gotoImovel(new Imovel());
//				return null;
//			}
//			
//			@Override
//			public View getInfoContents(Marker arg0) {
//
//				return null;
//			}
//		});
		if (googleMap == null)
			return;
		
		googleMap.clear();
		
		markers = new HashMap<String, Marker>();
		LatLng latLng = null;
		
		for (Imovel imovel : lista) {
			latLng = new LatLng(imovel.getEndereco().getLatitude(), imovel.getEndereco().getLongitude());
		    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		    markers.put(imovel.getNome(), googleMap.addMarker(new MarkerOptions()
	            .position(latLng)
	            .title("Imovel")
	            .snippet("This is my spot!")
	            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
		}
		
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
//		if (latLng != null)
//			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
		
	}

	public void gotoImovel(Imovel imovel) {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		Marker m = markers.get(imovel.getNome());
		builder.include(m.getPosition());
		m.showInfoWindow();
//		for (Entry<String,Marker> entry : markers.entrySet()) {
			
//			if (entry.getValue().getPosition().latitude == imovel.getLatitude() &&
//				m.getPosition().longitude == imovel.getLongitude()){
//				builder.include(m.getPosition());
//				m.showInfoWindow();
//				break;
//		   }	   
//		}
		LatLngBounds bounds = builder.build();
		int padding = 600; 
		
		CameraUpdate cu3 = CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                .target(m.getPosition())
                .zoom(14)
                .build());
		
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
		CameraUpdate cu2 = CameraUpdateFactory.zoomTo(12);
//		googleMap.moveCamera(cu);
		googleMap.animateCamera(cu3);
	}
}