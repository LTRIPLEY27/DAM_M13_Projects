package com.reseed.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.timepicker.TimeFormat;
import com.reseed.R;
import com.reseed.interfaces.FragmentCreateTaskInterface;
import com.reseed.interfaces.FragmentTaskListInterface;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.UserListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Clase de creacion de tareas, estr fragment lanza otro fragment que finaliza la creacion de la tarea.
 */
public class FragmentTaskCreationOne extends Fragment {

    JSONObject jsonTask;
    JSONArray jsonArrayUsers;
    RequestQueue requestQueue;
    String userToken, typeUser;

    int userid;

    EditText editTextTitleTask;

    Spinner spinnerTecnicoTarea, spinnerStatusTarea, spinnerTipoTarea;

    CalendarView calendarViewTask;

    Button buttonCreateTaskNext;

    FragmentCreateTaskInterface fragmentCreateTaskInterface;
    List<Integer> idsUsers;

    public FragmentTaskCreationOne() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.typeUser = getArguments().getString("typeUser");
            this.userToken = getArguments().getString("token");
        }
        idsUsers = new ArrayList<>();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentCreateTaskInterface = (FragmentCreateTaskInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " deves implementar FragmentTaskListInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creation_one, container, false);

        // instanciamos la requestQueue
        requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();

        // Instanciamos los componentes en sus variables.

        this.editTextTitleTask = view.findViewById(R.id.editTextTitleTask);
        this.spinnerTecnicoTarea = view.findViewById(R.id.spinnerTecnicoTarea);
        this.spinnerStatusTarea = view.findViewById(R.id.spinnerStatusTarea);
        this.spinnerTipoTarea = view.findViewById(R.id.spinnerTipoTarea);
        this.calendarViewTask = view.findViewById(R.id.calendarViewTask);
        this.buttonCreateTaskNext = view.findViewById(R.id.buttonCreateTaskNext);

        // deshabilitamos la seleccion de usuarios durante la request.
        spinnerTecnicoTarea.setEnabled(false);

        // request para la peticion de usuarios.
        requestData(userToken);

        buttonCreateTaskNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Comprovamos que los datos se puedan crear.
                if (comprovarDatos()) {
                    // Enviamos los datos a traves de este metodo.
                    fragmentCreateTaskInterface.onEnvioCrearTarea(
                            prepareData(),
                            idsUsers.get(spinnerTecnicoTarea.getSelectedItemPosition()));
                }

            }
        });

        return view;
    }

    private String prepareData() {

        /**
         * {
         * "name" : "recogida de cortes de arboles",
         * "fecha_culminacion": "2023-04-20",
         * "tarea" : "ANALISIS",
         * "estatus" : "IN_PROGRESS"
         * }
         */

        jsonTask = new JSONObject();

        // Preparamos la informacion de la fecha.
        Date date = new Date();
        date.setTime(calendarViewTask.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateString = dateFormat.format(date);

        String textTitle = editTextTitleTask.getText().toString();



        // Creamos el JSONObject para enviar.
        try {
            jsonTask.put("name", textTitle);
            jsonTask.put("fecha_culminacion", dateString);
            jsonTask.put("tarea", convertTarea(spinnerTipoTarea.getSelectedItemPosition()));
            jsonTask.put("estatus", convertStatus(spinnerStatusTarea.getSelectedItemPosition()));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonTask.toString();
    }


    /**
     * Metodo para comprovar los datos entrados en los componentes.
     *
     * @return devuelve true si es correcto de lo contrario false.
     */
    private boolean comprovarDatos() {
        return editTextTitleTask.getText().length() != 0;
    }


    /**
     * Metodo para realizar la request de los usuarios tecnicos.
     *
     * @param userToken
     */
    private void requestData(String userToken) {

        UserListRequest userListRequest = new UserListRequest(userToken, "tecnicos", requestQueue);
        JSONObject jsResponse = new JSONObject();

        //activateProgressBar(true);
        userListRequest.sendRequest(new VolleyResponseInterface() {
            @Override
            public void onError(String message) {
                Log.e("Error login: ", message);
                //activateProgressBar(false);
                Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public boolean onResponse(Object response) {
                JSONArray jsResponse = (JSONArray) response;
                Log.i("Respuesta user info para creacion de tarea", jsResponse.toString());

                ArrayAdapter arrayAdapter = null;

                try {
                    arrayAdapter = new ArrayAdapter(
                            getContext(),
                            android.R.layout.simple_spinner_item,
                            arrayUsersName(jsResponse.getJSONArray(0),idsUsers));
                } catch (JSONException e) {
                    Log.e("Error peticion users", e.getMessage());
                }

                //refreshContent(jsResponse);

                spinnerTecnicoTarea.setAdapter(arrayAdapter);

                //activateProgressBar(false);
                spinnerTecnicoTarea.setEnabled(true);
                return true;
            }
        });
    }

    /**
     * Metodo que a partir de la posicion, devuelve el tipo de estado de forma de String
     * para hacer la request de cambio de estado.
     *
     * @param position
     * @return
     */
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

    /**
     * Metodo que a partir de la posicion, devuelve el tipo de estado de forma de String
     * para hacer la request de cambio de estado.
     *
     * @param position
     * @return
     */
    private String convertTarea(int position) {
        if (position == 0) {
            return "ANALISIS";
        } else if (position == 1) {
            return "REPLANTAR";
        } else {
            return "LIMPIEZA";
        }
    }

    private String[] arrayUsersName(JSONArray jsonArrayUsers, List<Integer> idsUsers) {

        this.jsonArrayUsers = jsonArrayUsers;
        Log.i("Data users",jsonArrayUsers.toString());

        int numUsers = jsonArrayUsers.length();
        String[] stringsName = new String[numUsers];

        for (int i = 0; i < numUsers; i++) {
            try {
                stringsName[i] = jsonArrayUsers.getJSONObject(i).getString("nombre").concat(" ").concat(jsonArrayUsers.getJSONObject(i).getString("apellido"));
                idsUsers.add(jsonArrayUsers.getJSONObject(i).getInt("id"));
            } catch (JSONException e) {
                Log.e("Error en creacion tarea", e.getMessage());
                //throw new RuntimeException(e);
            }
        }
        return stringsName;
    }
}