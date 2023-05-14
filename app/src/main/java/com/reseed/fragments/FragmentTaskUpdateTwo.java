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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.reseed.AppActivity;
import com.reseed.R;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.objects.MapPoint;
import com.reseed.objects.TaskObj;
import com.reseed.requests.JsonDeleteRequest;
import com.reseed.requests.JsonPostRequest;
import com.reseed.requests.JsonPutRequest;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.util.JsonReseedUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentTaskUpdateTwo extends Fragment {


    Polygon polygon;
    TaskObj taskObj;
    Boolean datosModificados;
    private GoogleMap googleMap2;
    PolygonOptions polygonOptions;
    List<Marker> markerList = new ArrayList<>();
    JSONObject taskJson,oldJsonTask, mapLocation;
    String tarea,anteriorTarea, tipoUsuario, nombreUsuario, token;
    int idUsuario;
    JsonReseedUtils jsonReseedUtils;
    RequestQueue requestQueue;
    List<JSONObject> mapCoordenadas;

    Button crearPoligonoBtn, guardarTareaBtn, limpiarMapaBtn;

    public FragmentTaskUpdateTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instanciamos la requestqueue
        requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();

        // inicializamos reseedUtils.
        this.jsonReseedUtils = new JsonReseedUtils();

        // recuperamos la informacion del FragmentTaskCreationOne.
        if (getArguments() != null) {
            this.tarea = getArguments().getString("data");
            this.anteriorTarea = getArguments().getString("oldData");
            this.tipoUsuario = getArguments().getString("tipoUsuario");
            this.nombreUsuario = getArguments().getString("nombreUsuario");
            this.token = getArguments().getString("token");
            this.idUsuario = getArguments().getInt("idUsuario");
        }
        try {
            taskJson = new JSONObject(tarea);
            oldJsonTask = new JSONObject(anteriorTarea);

            // convertimos la antigua tarea a objeto tarea.
            taskObj = jsonReseedUtils.convertToTaskObject(oldJsonTask);

        } catch (JSONException e) {
            throw new RuntimeException(e);
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
                    Log.i("Map clic", latLng.toString());

                    markerList.add(googleMap.addMarker(new MarkerOptions()
                            .position(latLng)));

                    googleMap.clear();
                    addMarkers(googleMap, markerList);

                }
            });

            if (cargarDatosMapa()){
                datosModificados = false;
                enableButtons(true);
                crearPoligonoBtn.setEnabled(false);
            }else {
                // Activamos los botones.
                enableButtons(true);
                crearPoligonoBtn.setEnabled(false);
                guardarTareaBtn.setEnabled(false);
            }




        }
    };

    private void limpiarDatos(GoogleMap googleMap) {
        googleMap.clear();
        markerList.clear();
        try {
            polygonOptions.getPoints().clear();
        } catch (NullPointerException exception) {
            Log.e("Poligon null", exception.getMessage());
        }
        guardarTareaBtn.setEnabled(false);
    }

    private void addMarkers(GoogleMap googleMap, List<Marker> markerList) {

        for (int i = 0; i < markerList.size(); i++) {
            googleMap.addMarker(new MarkerOptions().position(markerList.get(i).getPosition()).title(String.valueOf(i)));
        }
        if (markerList.size() >= 3 && !crearPoligonoBtn.isEnabled()) {
            crearPoligonoBtn.setEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_task_update_two, container, false);

        crearPoligonoBtn = view.findViewById(R.id.crearAreaBtn);
        limpiarMapaBtn = view.findViewById(R.id.limpiarMapaBtn);
        guardarTareaBtn = view.findViewById(R.id.crearTareaSaveBtn);

        // desactivamos los botones.
        enableButtons(false);


        // Listeners de los botones.

        crearPoligonoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datosModificados = true;

                polygonOptions.getPoints().clear();

                for (Marker marker :
                        markerList) {
                    polygonOptions = polygonOptions.add(marker.getPosition());
                }

                polygonOptions.strokeWidth(0);
                polygonOptions.fillColor(Color.argb(0.45f, 0, 255, 60));
                polygon = googleMap2.addPolygon(polygonOptions);

                // Create the bounds object

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (int i = 0; i < markerList.size(); i++) {
                    builder.include(markerList.get(i).getPosition());
                }
                LatLngBounds latLngBoundsPolygon = builder.build();
                LatLng polygonCenter = latLngBoundsPolygon.getCenter();

                googleMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(polygonCenter, googleMap2.getCameraPosition().zoom));

                // guardamos la informacion del mapa para posterior uso
                guardarCoordMapa(polygonCenter, googleMap2.getCameraPosition().zoom);


                crearPoligonoBtn.setEnabled(false);
                // activamos el boton para guardar.
                guardarTareaBtn.setEnabled(true);
            }
        });

        limpiarMapaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarDatos(googleMap2);
            }
        });

        guardarTareaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // preparamos los datos para enviar
                prepararDatos();

                // Hacemos la request para crear la tarea.
                updateTarea(token,taskObj.getId());
            }
        });

        return view;
    }

    private void guardarCoordMapa(LatLng latLng, Float zoom){
        mapLocation = new JSONObject();

        try {
            mapLocation.put("centroLatitud", latLng.latitude);
            mapLocation.put("centroLongitud", latLng.longitude);
            mapLocation.put("zoom", zoom);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Metodo donde se preparan los datos antes de enviar para crear la tarea.
     */
    private void prepararDatos() {

        mapCoordenadas = new ArrayList<>();


        Log.i("Datos tarea", taskJson.toString());
        Log.i("ID Usuario", String.valueOf(idUsuario));
        Log.i("Datos mapLocation", mapLocation.toString());


        for (int i = 0; i < polygon.getPoints().size(); i++) {

            JSONObject jsonCoordenadas = new JSONObject();
            try {
                jsonCoordenadas.put("latitud", polygon.getPoints().get(i).latitude);
                jsonCoordenadas.put("longitud", polygon.getPoints().get(i).longitude);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Log.i("Datos coordenada ".concat(String.valueOf(i)), jsonCoordenadas.toString());
            mapCoordenadas.add(jsonCoordenadas);
        }
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

    private void enableButtons(Boolean status) {
        crearPoligonoBtn.setEnabled(status);
        guardarTareaBtn.setEnabled(status);
        limpiarMapaBtn.setEnabled(status);
    }

    /**
     * Medodo para realizar los POST request para guardar la tarea.
     *
     * @param token token del usuario.
     * @param idTarea el id de la tarea para poder realizar la request.
     */
    private void updateTarea(String token, String idTarea) {

        enableButtons(false);

        String url = "https://reseed-385107.ew.r.appspot.com/update/value/tarea/id/";
        url = url.concat(idTarea);

        JsonPutRequest saveTaskRequest = new JsonPutRequest(token, url, taskJson, requestQueue);

        saveTaskRequest.sendRequest(new VolleyResponseInterface() {
            @Override
            public void onError(String message) {
                /**/
                Log.e("Error login: ", message);

                Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public boolean onResponse(Object response) {
                JSONObject jsResponse = (JSONObject) response;
                Log.i("Respuesta user info", jsResponse.toString());


                if(datosModificados){

                    Toast toast = Toast.makeText(requireContext(), "Guardado parte 1 de 3", Toast.LENGTH_SHORT);
                    toast.show();

                    // Realizamos la siguiente request (eliminar la ubicacion).
                    eliminarUbicacion(token, Integer.parseInt(taskObj.getTaskLocation().getId()));

                }else{
                    // Llamamos a la lista de tareas de nuevo.
                    ((AppActivity)requireActivity()).tasksFragmentCall(null);
                }

                return false;
            }
        });
    }

    private void eliminarUbicacion(String token, int idUbicacion) {

        String url = "https://reseed-385107.ew.r.appspot.com/delete/typus/ubicacion/id/";
        url = url.concat(String.valueOf(idUbicacion));

        JsonDeleteRequest saveTaskRequest = new JsonDeleteRequest(token, url, requestQueue);
        saveTaskRequest.sendRequest(new VolleyResponseInterface() {
            @Override
            public void onError(String message) {
                /**/
                Log.e("Error login: ", message);
                Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public boolean onResponse(Object response) {
                //JSONObject jsResponse = (JSONObject) response;
                String responseString = (String) response;
                Log.i("Respuesta user info", responseString.toString());

                Toast toast = Toast.makeText(requireContext(), "Eliminando ubicacion.", Toast.LENGTH_SHORT);
                toast.show();

                salvarUbicacionMapa(token,Integer.parseInt(taskObj.getId()));
                return true;
            }
        });
    }

    private void salvarUbicacionMapa(String token, int idTarea) {

        String url = "https://reseed-385107.ew.r.appspot.com/ubicacion/tarea/";
        url = url.concat(String.valueOf(idTarea));

        JsonPostRequest saveTaskRequest = new JsonPostRequest(token, mapLocation, url, requestQueue);
        saveTaskRequest.sendRequest(new VolleyResponseInterface() {
            @Override
            public void onError(String message) {
                /**/
                Log.e("Error login: ", message);
                Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public boolean onResponse(Object response) {
                JSONObject jsResponse = (JSONObject) response;
                Log.i("Respuesta user info", jsResponse.toString());

                Toast toast = Toast.makeText(requireContext(), "Guardado ubicación parte 2 de 3", Toast.LENGTH_SHORT);
                toast.show();

                try {
                    salvarPuntos(jsResponse.getInt("id"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        });
    }

    private void salvarPuntos(int idMapa) {
        for (int i = 0; i < mapCoordenadas.size(); i++) {
            salvarPuntosMapa(token, idMapa, mapCoordenadas.get(i), mapCoordenadas.size()-1, i);
        }
    }


    private void salvarPuntosMapa(String token, int idMapa, JSONObject jsonObject, int totalPuntos, int numPunto) {

        String url = "https://reseed-385107.ew.r.appspot.com/coordenada/ubicacion/";
        url = url.concat(String.valueOf(idMapa));

        JsonPostRequest saveTaskRequest = new JsonPostRequest(token, jsonObject, url, requestQueue);
        saveTaskRequest.sendRequest(new VolleyResponseInterface() {
            @Override
            public void onError(String message) {
                /**/
                Log.e("Error login: ", message);
                Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public boolean onResponse(Object response) {
                JSONObject jsResponse = (JSONObject) response;
                Log.i("Respuesta user info", jsResponse.toString());

                Toast toast = Toast.makeText(requireContext(),
                        "Puntos guardados: ".
                                concat(String.valueOf(numPunto)).
                                concat(" de ").
                                concat(String.valueOf(totalPuntos)), Toast.LENGTH_SHORT);
                toast.show();

                if(totalPuntos == numPunto){
                    ((AppActivity)requireActivity()).tasksFragmentCall(null);
                }

                return true;
            }
        });


    }

    /**
     * Metodo para cargar los datos del mapa de la tarea.
     * @return devuelve true si se cargan correctamente, false si no se cargan.
     */
    private boolean cargarDatosMapa(){

        // limpiamos los puntos del mapa.
        markerList.clear();

        ArrayList<MapPoint> mapPoints = taskObj.getTaskLocation().getMapPoints();

        // si hay puntos en el mapa los cargamos.
        if(mapPoints.size() > 0){


            for (int i = 0; i < mapPoints.size(); i++) {
                // cargamos los puntos.
                LatLng latLngPoint = new LatLng(mapPoints.get(i).getLatitude(),mapPoints.get(i).getLongitude());

                markerList.add(googleMap2.addMarker(new MarkerOptions()
                        .position(latLngPoint)));
            }

        }

        if(markerList.size() > 0){
            polygonOptions.getPoints().clear();

            for (Marker marker :
                    markerList) {
                polygonOptions = polygonOptions.add(marker.getPosition());
            }

            polygonOptions.strokeWidth(0);
            polygonOptions.fillColor(Color.argb(0.45f, 0, 255, 60));
            polygon = googleMap2.addPolygon(polygonOptions);

            // Create the bounds object

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (int i = 0; i < markerList.size(); i++) {
                builder.include(markerList.get(i).getPosition());
            }
            LatLngBounds latLngBoundsPolygon = builder.build();
            LatLng polygonCenter = latLngBoundsPolygon.getCenter();

            googleMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(polygonCenter, taskObj.getTaskLocation().getZoom()));

            // guardamos la informacion del mapa para posterior uso
            guardarCoordMapa(polygonCenter, googleMap2.getCameraPosition().zoom);

            return true;
        }
        return false;
    }
}