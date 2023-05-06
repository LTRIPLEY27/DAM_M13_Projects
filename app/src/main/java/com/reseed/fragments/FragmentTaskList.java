package com.reseed.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.reseed.R;
import com.reseed.interfaces.FragmentTaskListInterface;
import com.reseed.interfaces.RecyclerViewInterface;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.objects.TaskObj;
import com.reseed.requests.JsonArrayGetRequest;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.JsonGetRequest;
import com.reseed.util.JsonReseedUtils;
import com.reseed.adapter.TaskAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentTaskList extends Fragment implements RecyclerViewInterface {


	RequestQueue requestQueue;

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private FragmentTaskListInterface fragmentTaskListInterface;


	private JSONObject jsonObjectTasks = new JSONObject();

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			fragmentTaskListInterface = (FragmentTaskListInterface) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() + " deves implementar FragmentTaskListInterface");
		}
	}

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private String userToken, typeUser;
	private RecyclerView recyclerView;

	private ProgressBar progressBarTasks;

	private ImageView imageViewNoTask;

	private TaskAdapter adapter;

	private TextView textViewNoTareas;

	private ArrayList<TaskObj> listaTareas;

	private JsonReseedUtils jsonReseedUtils;

	private JSONObject jsonObjectUser;
	private  JSONArray jsonObjectAdmin, jsonArrayAdminTasks;

	public FragmentTaskList() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			//jsonObjectTasks = new JSONObject(getArguments().getString("data"));
			userToken = getArguments().getString("token");
			typeUser = getArguments().getString("typeUser");
		}
		// instanciamos la requestqueue
		requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();

		jsonReseedUtils = new JsonReseedUtils();
		jsonArrayAdminTasks = new JSONArray();
		listaTareas = new ArrayList<>();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_task_list, container, false);
		recyclerView = view.findViewById(R.id.recyclerTask);
		textViewNoTareas = view.findViewById(R.id.textNoTareas);
		progressBarTasks = view.findViewById(R.id.progressBarTasks);

		// Se controla el tipo de usuario.
		if(typeUser.contentEquals("ADMIN")){

			requestAdminData(userToken);

		}else{
			requestUserData(userToken);
		}

		// Una vez hecha la request se actualiza la lista de tarea.

		if (listaTareas != null) {

			recyclerView.setVisibility(View.VISIBLE);
			textViewNoTareas.setVisibility(View.GONE);


			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			adapter = new TaskAdapter(listaTareas, this);

			recyclerView.setAdapter(adapter);
		} else {
			Log.e("Lista tareas", "Esta vacio.");
			recyclerView.setVisibility(View.GONE);
			textViewNoTareas.setVisibility(View.VISIBLE);
		}

		return view;

	}

	private void requestUserData(String token) {
		String url = "";

		url = "https://reseed-385107.ew.r.appspot.com/perfil";

		JsonGetRequest userInfoRequest = new JsonGetRequest(token, requestQueue,url);
		JSONObject jsResponse = new JSONObject();

		activateProgressBar(true);

		userInfoRequest.sendRequest(new VolleyResponseInterface() {
			@Override
			public void onError(String message) {
				Log.e("Error login: ", message);
				Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
				toast.show();
				activateProgressBar(false);

			}

			@Override
			public boolean onResponse(Object response) {
				JSONObject jsResponse = (JSONObject) response;
				Log.i("Respuesta user info", jsResponse.toString());
				activateProgressBar(false);
				refreshUserContent(jsResponse);
				return true;
			}
		});

	}

	/**
	 * Metodo para activar o desactivar la barra de progreso, durante la request.
	 * @param b boolen para activar o no la barra de progreso.
	 */
	private void activateProgressBar(boolean b){
		if(b){
			progressBarTasks.setVisibility(View.VISIBLE);
		}else {
			progressBarTasks.setVisibility(View.GONE);
		}

	}

	private void requestAdminData(String token) {
		String url = "";

		url = "https://reseed-385107.ew.r.appspot.com/results/tareas";

		JsonArrayGetRequest userInfoRequest = new JsonArrayGetRequest(token, requestQueue,url);
		JSONObject jsResponse = new JSONObject();

		activateProgressBar(true);
		userInfoRequest.sendRequest(new VolleyResponseInterface() {
			@Override
			public void onError(String message) {
				Log.e("Error login: ", message);
				Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
				toast.show();
				//changeStatusInputUser(true);
				activateProgressBar(false);

			}

			@Override
			public boolean onResponse(Object response) {
				JSONArray jsResponse = (JSONArray) response;
				Log.i("Respuesta user info", jsResponse.toString());
				refreshAdminContent(jsResponse);
				activateProgressBar(false);
				//openAppActivity(jsResponse);

				//refreshContent(jsResponse);
				return true;
			}
		});
		//This is for Headers If You Needed

	}

	private void refreshAdminContent(JSONArray jsonArray) {

		this.jsonObjectAdmin = jsonArray;

		extractAdminTasks(jsonArray);
		recyclerView.getAdapter().notifyDataSetChanged();
	}

	// todo hacer que cuando entra el admin vea todas las tareas de todos.
	private void extractAdminTasks(JSONArray jsonArray) {
		this.listaTareas.clear();
		int numTareas = 0;
		int positionJsonArray = 0;

		try {
			numTareas = jsonArray.getJSONArray(0).length();

			for (int i = 0; i < numTareas; i++) {
				if(!jsonArray.getJSONArray(0).getJSONObject(i).isNull("ubicacion")){

					jsonArrayAdminTasks.put(positionJsonArray,jsonArray.getJSONArray(0).getJSONObject(i));

					listaTareas.add(jsonReseedUtils.convertToTaskObject(jsonArray.getJSONArray(0).getJSONObject(i)));
					positionJsonArray++;
				}
			}

		} catch (JSONException e) {
			Log.e("Error convertToTaskObj", e.getMessage());
		}
	}


	private void refreshUserContent(JSONObject jsonObject) {
		this.jsonObjectUser = jsonObject;

		extractUserTasks(jsonObject);
		recyclerView.getAdapter().notifyDataSetChanged();
	}

	/**
	 * Metodo para extraer las tareas del JSON.
	 */
	private void extractUserTasks(JSONObject jsonUserInfo) {
		this.listaTareas.clear();
		try {

			listaTareas.addAll(jsonReseedUtils.convertToTasksObjects(jsonUserInfo));
		} catch (JSONException e) {
			Log.e("Error convertToTaskObj", e.getMessage());
			//throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo llamado cuando se realiza un clic desde {@link TaskAdapter} donde se añade el listener al objeto.
	 *
	 * @param posicion posicion del objeto dentro del array de tareas.
	 */
	@Override
	public void onItemClicked(int posicion) {

		if(typeUser.contentEquals("ADMIN")){
			try {
				fragmentTaskListInterface.onEnvioDatos(jsonArrayAdminTasks.getJSONObject(posicion).toString());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}else{

			try {
				fragmentTaskListInterface.onEnvioDatos(jsonReseedUtils.extractJsonTask(jsonObjectUser, posicion).toString());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}


	}
}