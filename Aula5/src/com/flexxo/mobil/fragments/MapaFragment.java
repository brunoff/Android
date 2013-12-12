package com.flexxo.mobil.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;

import com.flexxo.mobil.MenuActivity;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
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

public class MapaFragment extends MapFragment {
	private GoogleMap googleMap;
	private HashMap<String,Marker> markers;
	
	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
	}
	
	public void overlayMap(ArrayList<HashMap<String, Object>> lista) {
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
		
		markers = new HashMap<String, Marker>();
		LatLng latLng = null;
		
		for (HashMap<String, Object> hashMap : lista) {
			Imovel imovel = (Imovel)hashMap.get("imovel");
			latLng = new LatLng(imovel.getLatitude(), imovel.getLongitude());
		    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		    markers.put(imovel.getImovel(), googleMap.addMarker(new MarkerOptions()
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
		Marker m = markers.get(imovel.getImovel());
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