package com.reseed.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.reseed.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentTaskCreationTwo extends Fragment {


	Polygon polygon;

	PolygonOptions polygonOptions;

	List<Marker> markerList = new ArrayList<>();

	Button crearPoligonoBtn, guardarTareaBtn;

	public FragmentTaskCreationTwo() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {

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

					//markerOptionsList.add(new MarkerOptions().position(latLng).draggable(true));

					googleMap.clear();


					for (int i = 0; i < markerList.size(); i++) {
						googleMap.addMarker(new MarkerOptions().position(markerList.get(i).getPosition()).draggable(true).title(String.valueOf(i)));
						markerList.get(i).showInfoWindow();
					}

					markerList.add(googleMap.addMarker(new MarkerOptions()
							.position(latLng)
							.draggable(true)));
					markerList.get(markerList.size()-1).showInfoWindow();

					//polygonOptions.add(markerList.get(markerList.size() - 1).getPosition());

					googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
						@Override
						public void onMarkerDrag(@NonNull Marker marker) {

						}

						@Override
						public void onMarkerDragEnd(@NonNull Marker marker) {

						}

						@Override
						public void onMarkerDragStart(@NonNull Marker marker) {

						}
					});

					if(markerList.size() > 2){

						/*for (Marker marker:
						     markerList) {

							//polygonOptions.add(marker.getPosition());
							polygon = googleMap.addPolygon(polygonOptions);
						}*/

						//googleMap.addPolygon(polygonOptions);
						//polygon = googleMap.addPolygon(new PolygonOptions().addAll(markerList.));
						//googleMap.addPolygon(polygon);
						//googleMap.addPolygon(polygonOptions.add(latLng));
					}

					//googleMap.addPolygon(polygonOptions.add(latLng));
				}
			});
			// Colocamos la camara en la posición de la tarea.
			//googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centroMapa, taskObj.getTaskLocation().getZoom()));

			// Comprovamos si hay algun poligono y lo mostramos en el mapa.

			//int numberPoints = taskObj.getTaskLocation().getMapPoints().size();

			/*if (numberPoints > 0){
				PolygonOptions polygonMap = new PolygonOptions();

				for (int i = 0; i < numberPoints; i++) {

					polygonMap.add(
							new LatLng(
									taskObj.getTaskLocation().getMapPoints().get(i).getLatitude(),
									taskObj.getTaskLocation().getMapPoints().get(i).getLongitude())
					);
				}
				googleMap.addPolygon(polygonMap);
			}*/


		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.fragment_task_creation_two, container, false);

		crearPoligonoBtn = view.findViewById(R.id.crearAreaBtn);
		guardarTareaBtn = view.findViewById(R.id.crearTareaSaveBtn);

		// Listeners de los botones.

		crearPoligonoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

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
}