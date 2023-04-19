package com.reseed;

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
import android.widget.TextView;

import com.reseed.interfaces.FragmentTaskListInterface;
import com.reseed.interfaces.RecyclerViewInterface;
import com.reseed.objects.TaskObj;
import com.reseed.util.JsonReseedUtils;
import com.reseed.util.adapter.TaskAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentTaskList extends Fragment implements RecyclerViewInterface {

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

	private RecyclerView recyclerView;

	private ImageView imageViewNoTask;

	private TaskAdapter adapter;

	private TextView textViewNoTareas;

	private ArrayList<TaskObj> listaTareas;

	public FragmentTaskList() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			try {
				jsonObjectTasks = new JSONObject(getArguments().getString("data"));
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		extractTasks(jsonObjectTasks);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_task_list, container, false);
		recyclerView = view.findViewById(R.id.recyclerTask);
		textViewNoTareas = view.findViewById(R.id.textNoTareas);
		//imageViewNoTask = (ImageView) view.findViewById(R.id.no_task_img);


		// Si no hay tareas se hace visible el text view.
		//todo canviar el textview por algo más sugerente.

		if (listaTareas != null) {

			recyclerView.setVisibility(View.VISIBLE);
			textViewNoTareas.setVisibility(View.GONE);


			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			adapter = new TaskAdapter(listaTareas,this);

			recyclerView.setAdapter(adapter);
		} else {
			Log.e("Lista tareas", "Esta vacio.");
			recyclerView.setVisibility(View.GONE);
			textViewNoTareas.setVisibility(View.VISIBLE);
		}
		return view;
	}

	/**
	 * Metodo para extraer las tareas del JSON.
	 */
	private void extractTasks(JSONObject jsonUserInfo) {
		try {
			JsonReseedUtils jsonReseedUtils = new JsonReseedUtils();
			this.listaTareas = jsonReseedUtils.convertToTaskObj(jsonUserInfo);
		} catch (JSONException e) {
			Log.e("Error convertToTaskObj", e.getMessage());
			//throw new RuntimeException(e);
		}
	}

	@Override
	public void onItemClicked(int posicion) {

		//Log.i("Recycler View Click!!",listaTareas.get(posicion).getName());
		fragmentTaskListInterface.onEnvioDatos("Envio de datos.");
	}
}