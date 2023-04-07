package com.reseed;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reseed.objects.TaskObj;
import com.reseed.util.adapter.TaskAdapter;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTaskList#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmentTaskList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    private ImageView imageViewNoTask;

    private TaskAdapter adapter;

    private TextView textViewNoTareas;

    private ArrayList <TaskObj> listaTareas;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_task_list.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTaskList newInstance(String param1, String param2) {
        FragmentTaskList fragment = new FragmentTaskList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTaskList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        if(listaTareas != null){

            recyclerView.setVisibility(View.VISIBLE);
            textViewNoTareas.setVisibility(View.GONE);
            //imageViewNoTask.setVisibility(View.GONE);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new TaskAdapter(listaTareas);
            //adapter.setClickListener();
            recyclerView.setAdapter(adapter);
        }else{
            Log.e("Lista tareas","Esta vacio.");
            recyclerView.setVisibility(View.GONE);
            textViewNoTareas.setVisibility(View.VISIBLE);
            //imageViewNoTask.setVisibility(View.VISIBLE);
        }
        return view;
    }

    /**
     * Metodo para extraer las tareas del JSON.
     */
    private void extractTasks(){
        try {
            this.userTaskObjs = jsonReseedUtils.convertToTaskObj(userJSONInfo);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }
}