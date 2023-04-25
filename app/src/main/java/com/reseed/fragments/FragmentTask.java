package com.reseed.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.reseed.R;
import com.reseed.comparators.CommentComparator;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.objects.TaskComment;
import com.reseed.objects.TaskObj;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.UserAddCommentRequest;
import com.reseed.requests.UserTaskStatusUpdateRequest;
import com.reseed.util.JsonReseedUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentTask extends Fragment {

	TaskObj taskObj;
	JsonReseedUtils jsonReseedUtils;
	TextView textViewTecnico, textViewNombreTarea, textViewFechaFinalTarea, textViewFechaCreacionTarea, textViewNombreAdmin, textViewComentarios;
	Spinner statusSpinner;

	UserAddCommentRequest sendCommentRequest;
	String userToken;
	RequestQueue requestQueue;

	Button addCommentBtn;
	Boolean firstLoad;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//inicializamos el JsonReseedUtils
		jsonReseedUtils = new JsonReseedUtils();

		if (getArguments() != null) {
			try {
				taskObj = jsonReseedUtils.convertToTaskObject(new JSONObject(getArguments().getString("data")));
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
			userToken = getArguments().getString("token");

			firstLoad = true;

			try {
				// instanciamos la requestqueue
				requestQueue = SingletonReqQueue.getInstance(requireContext().getApplicationContext()).getRequestQueue();
			} catch (IllegalStateException exception) {
				Log.e("Error en la RequestQueue", exception.getMessage());
			}
		}
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

			LatLng centroMapa = new LatLng(taskObj.getTaskLocation().getLatitud(), taskObj.getTaskLocation().getLongitud());

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
			// Colocamos la camara en la posición de la tarea.
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centroMapa, taskObj.getTaskLocation().getZoom()));
		}
	};

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, container, false);

		populateDataOnView(view);

		return view;
	}

	/**
	 * Este metodo es usado para llenar la informacion de los objetos de la View.
	 *
	 * @param view vista donde se cargan los objetos.
	 */
	private void populateDataOnView(View view) {
		textViewTecnico = view.findViewById(R.id.textViewTecnico);
		textViewNombreTarea = view.findViewById(R.id.textViewNombreTarea);
		textViewFechaFinalTarea = view.findViewById(R.id.textViewFechaFinalTarea);
		textViewFechaCreacionTarea = view.findViewById(R.id.textViewFechaCreacionTarea);
		textViewNombreAdmin = view.findViewById(R.id.textViewNombreAdmin);
		textViewComentarios = view.findViewById(R.id.textViewComentarios);
		statusSpinner = view.findViewById(R.id.statusSpinner);
		addCommentBtn = view.findViewById(R.id.addCommentBtn);


		textViewTecnico.setText(taskObj.getTecnico());
		textViewNombreTarea.setText(taskObj.getName());
		textViewFechaFinalTarea.setText(taskObj.getFecha_culminacion());
		textViewFechaCreacionTarea.setText(taskObj.getFecha_creacion());
		textViewNombreAdmin.setText(taskObj.getAdmin());

		// Leemos los comentarios del objeto tarea
		textViewComentarios.setText(readComents(taskObj.getTaskComments()));
		statusSpinner.setSelection(calculateStatus(taskObj.getEstatus()));

		// Listener que lanza el update del estado de la tarea.
		statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				// Si no es la la primera modificacion del spinner
				if (!firstLoad) {
					Log.i("Select spinner", String.valueOf(statusSpinner.getSelectedItemPosition()));
					actualizarEstatusTarea(statusSpinner.getSelectedItemPosition());

				} else {
					firstLoad = false;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		// Listener del boton añadir comentario, que lanza una ventana modal donde añades el comentario.
		addCommentBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

				final View customDialog = getLayoutInflater().inflate(R.layout.dialog_add_comment, null);

				builder.setView(customDialog);

				builder.setTitle("Añadir comentario");

				builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

						addComent(((EditText) customDialog.findViewById(R.id.commentEditText)).getText().toString());

					}
				});

				AlertDialog commentDialog = builder.create();

				commentDialog.show();

			}
		});
	}

	/**
	 * Metodo para leer los comentarios
	 *
	 * @param taskComments Objeto Tarea
	 * @return Devuelve en formato String los comentarios de la Tarea.
	 */
	private String readComents(ArrayList<TaskComment> taskComments) {
		taskComments.sort(new CommentComparator());

		StringBuilder comments = new StringBuilder();

		for (TaskComment taskComment :
				taskComments) {
			comments.append(taskComment.getDescripcion()).append("\n");
			comments.append(taskComment.getTecnico()).append("\n");
			comments.append(taskComment.getFecha()).append("\n");
			comments.append("----------").append("\n");
		}

		return comments.toString();
	}

	/**
	 * Metodo papra realizar la request usando {@link UserAddCommentRequest}
	 *
	 * @param textComment
	 */
	private void addComent(String textComment) {

		UserAddCommentRequest userAddComment = new UserAddCommentRequest(userToken, textComment, taskObj.getId(), taskObj.getTecnico(), taskObj.getAdmin(), requestQueue);


		userAddComment.sendRequest(new VolleyResponseInterface() {
			@Override
			public void onError(String message) {
				/**/
				Log.e("Error login: ", message);

				if (message.contains("403")) {
					Toast toast = Toast.makeText(requireContext(), "Update incorrecto.", Toast.LENGTH_LONG);
					toast.show();
				} else {
					Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
					toast.show();
				}
				//Todo make disable the addComent when is making the request.
				//changeStatusInputUserPasswd(true);
			}

			@Override
			public boolean onResponse(Object response) {

				//Si la respuesta
				JSONObject jsResponse = (JSONObject) response;

				Toast toast = Toast.makeText(requireContext(), "Comentario añadido.", Toast.LENGTH_LONG);
				toast.show();

				// Leemos los comentarios del objeto tarea
				textViewComentarios.setText(readComents(taskObj.getTaskComments()));
				return true;

			}
		});


	}

	private void actualizarEstatusTarea(int selectedItemPosition) {
		/*
		{
		"name": "restauración del jardín de la asamblea",
		"fecha_culminacion": "2023-04-18",
		"tarea": "LIMPIEZA",
		"estatus": "NEW",
		"tecnico": "davidf",
		"admin": "eduard",
		"ubicacionId" : 7
		}
		 */

		JSONObject requestJson = new JSONObject();

		try {
			requestJson.put("name", taskObj.getName());
			requestJson.put("tarea", taskObj.getTarea());
			requestJson.put("estatus", convertStatus(selectedItemPosition));
			requestJson.put("tecnico", taskObj.getTecnico());
			requestJson.put("admin", taskObj.getAdmin());
			requestJson.put("ubicacionId", taskObj.getTaskLocation().getId());

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		UserTaskStatusUpdateRequest userStatusUpdateComment = new UserTaskStatusUpdateRequest(userToken, taskObj.getId(), requestJson, requestQueue);

		userStatusUpdateComment.sendRequest(new VolleyResponseInterface() {
			@Override
			public void onError(String message) {
				/**/
				Log.e("Error login: ", message);

				if (message.contains("403")) {
					Toast toast = Toast.makeText(requireContext(), "Update incorrecto.", Toast.LENGTH_LONG);
					toast.show();
				} else {
					Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
					toast.show();
				}
				//Todo make disable the addComent when is making the request.
				//changeStatusInputUserPasswd(true);
			}

			@Override
			public boolean onResponse(Object response) {

				//Si la respuesta
				JSONArray jsResponse = (JSONArray) response;


				Toast toast = Toast.makeText(requireContext(), "Estatus cambiado.", Toast.LENGTH_LONG);
				toast.show();

				// Leemos los comentarios del objeto tarea
				textViewComentarios.setText(readComents(taskObj.getTaskComments()));
				return true;
			}
		});


	}


	private int calculateStatus(String status) {

		if (status.contentEquals("IN_PROGRESS")) {

			return 0;

		} else if (status.contentEquals("NEW")) {

			return 1;

		} else if (status.contentEquals("DONE")) {

			return 2;

		} else if (status.contentEquals("TO_DO")) {

			return 3;

		} else {

			return 4;

		}

	}

	private String convertStatus(int position) {


		if (position == 0) {
			return "IN_PROGRESS";
		} else if (position == 1) {
			return "NEW";
		} else if (position == 2) {
			return "DONE";
		} else if (position == 3) {
			return "TO_DO";
		} else {
			return "ON_HOLD";
		}
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		SupportMapFragment mapFragment =
				(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
		if (mapFragment != null) {
			mapFragment.getMapAsync(callback);
		}
	}
}