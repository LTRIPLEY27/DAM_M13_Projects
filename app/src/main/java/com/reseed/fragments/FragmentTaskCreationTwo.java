package com.reseed.fragments;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.reseed.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentTaskCreationTwo extends Fragment {


	Polygon polygon;
	private GoogleMap googleMap2;
	PolygonOptions polygonOptions;
	List<Marker> markerList = new ArrayList<>();
	JSONObject taskJson, mapLocation, mapCoordenada;


	Button crearPoligonoBtn, guardarTareaBtn, limpiarMapaBtn;
	public FragmentTaskCreationTwo() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {

			getArguments().getString()

		}
		this.polygonOptions = new PolygonOptions();
	}

	private OnMapReadyCallback callback = new OnMapReadyCallback() {

		/**
		 * Manipulates the map once available.
		 * This callback is triggered when the map is ready to be used.
		 * This is where we can add markers or lines, add listeners or move the camera.
		 * In this case, we just add a marker near Sydney, Australia.
		 * If Google Play services is not installed on the device, the user will be prompted to
		 * install it inside the SupportMapFragment. This method will only be triggered once the
		 * user has installed Google Play services and returned to the app.
		 */
		@Override
		public void onMapReady(GoogleMap googleMap) {
			// Activamos los botones.
			enableButtons(true);

			googleMap2 = googleMap;

			//LatLng centroMapa = new LatLng(taskObj.getTaskLocation().getLatitud(), taskObj.getTaskLocation().getLongitud());

			// Comprovamos si tenemos los permisos requeridos. Si no los tenemos los pide.

			if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
					== PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
					== PackageManager.PERMISSION_GRANTED) {

				googleMap.setMyLocationEnabled(true);

			} else {

				ActivityCompat.requestPermissions(requireActivity(), new String[]{
						android.Manifest.permission.ACCESS_FINE_LOCATION,
						android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

				// Activamos la localizacion del dispositivo
				googleMap.setMyLocationEnabled(true);

			}

			googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
				@Override
				public void onMapClick(@NonNull LatLng latLng) {
					Log.i("Map clic",latLng.toString());

					markerList.add(googleMap.addMarker(new MarkerOptions()
							.position(latLng)));

					googleMap.clear();

					addMarkers(googleMap, markerList);

				}
			});
		}
	};

	private void limpiarDatos(GoogleMap googleMap){
		googleMap.clear();
		markerList.clear();
		try {
			polygonOptions.getPoints().clear();
		}catch (NullPointerException exception){
			Log.e("Poligon null",exception.getMessage());
		}

	}

	private void addPoligono(GoogleMap googleMap, PolygonOptions polygonOptions, List<Marker> markerList){
		polygonOptions.getPoints().clear();

		for (Marker marker:
				markerList) {
			polygonOptions.add(marker.getPosition());
		}

		polygonOptions.strokeWidth(0);
		polygonOptions.fillColor(Color.argb(0.45f,0,255,60));

		polygon = googleMap.addPolygon(polygonOptions);
	}

	private void addMarkers(GoogleMap googleMap, List<Marker> markerList){

		for (int i = 0; i < markerList.size(); i++) {
			googleMap.addMarker(new MarkerOptions().position(markerList.get(i).getPosition()).title(String.valueOf(i)));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.fragment_task_creation_two, container, false);

		crearPoligonoBtn = view.findViewById(R.id.crearAreaBtn);
		limpiarMapaBtn = view.findViewById(R.id.limpiarMapaBtn);
		guardarTareaBtn = view.findViewById(R.id.crearTareaSaveBtn);

		// desactivamos los botones.
		enableButtons(false);


		// Listeners de los botones.

		crearPoligonoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				polygonOptions.getPoints().clear();

				for (Marker marker:
						markerList) {
					polygonOptions = polygonOptions.add(marker.getPosition());
				}

				polygonOptions.strokeWidth(0);
				polygonOptions.fillColor(Color.argb(0.45f,0,255,60));
				polygon = googleMap2.addPolygon(polygonOptions);



				final LatLngBounds latLngBounds = getPolygonLatLngBounds(polygon);
				googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, POLYGON_PADDING_PREFERENCE));

			}
		});

		limpiarMapaBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				limpiarDatos(googleMap2);
			}
		});

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		SupportMapFragment mapFragment =
				(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapTaskCreation);
		if (mapFragment != null) {
			mapFragment.getMapAsync(callback);
		}
	}

	private void enableButtons(Boolean status){
		crearPoligonoBtn.setEnabled(status);
		guardarTareaBtn.setEnabled(status);
		limpiarMapaBtn.setEnabled(status);
	}

}