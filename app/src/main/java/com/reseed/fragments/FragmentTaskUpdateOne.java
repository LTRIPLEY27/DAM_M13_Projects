package com.reseed.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.reseed.AppActivity;
import com.reseed.R;
import com.reseed.interfaces.FragmentCreateTaskInterface;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.UserListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Clase de creacion de tareas, estr fragment lanza otro fragment que finaliza la creacion de la tarea.
 */
public class FragmentTaskUpdateOne extends Fragment {

    JSONObject jsonTask, originalTask;
    JSONArray jsonArrayUsers;
    RequestQueue requestQueue;
    String userToken, typeUser, nameUser;

    String[] stringsName;

    int userid;

    EditText editTextTitleTask;

    Spinner spinnerTecnicoTarea, spinnerStatusTarea, spinnerTipoTarea;

    CalendarView calendarViewTask;

    Button buttonCreateTaskNext;

    List<Integer> idsUsers;

    public FragmentTaskUpdateOne() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

            // recuperamos la infomacion de la tarea.
            try {
                jsonTask = new JSONObject(getArguments().getString("data"));
                originalTask = jsonTask;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            this.nameUser = getArguments().getString("nombreUsuario");
            this.typeUser = getArguments().getString("tipoUsuario");
            this.userToken = getArguments().getString("token");
        }
        idsUsers = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_update_one, container, false);

        // instanciamos la requestQueue
        requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();

        // Instanciamos los componentes en sus variables.

        this.editTextTitleTask = view.findViewById(R.id.editTextTitleUpdateTask);
        this.spinnerTecnicoTarea = view.findViewById(R.id.spinnerTecnicoTareaUpdateTask);
        this.spinnerStatusTarea = view.findViewById(R.id.spinnerStatusTareaUpdateTask);
        this.spinnerTipoTarea = view.findViewById(R.id.spinnerTipoTareaUpdateTask);
        this.calendarViewTask = view.findViewById(R.id.calendarViewUpdateTask);
        this.buttonCreateTaskNext = view.findViewById(R.id.buttonUpdateTaskNext);

        // deshabilitamos la seleccion de usuarios durante la request.
        spinnerTecnicoTarea.setEnabled(false);

        // deshabilitamos el boton siguiente
        buttonCreateTaskNext.setEnabled(false);

        // listener del boton siguiente
        buttonCreateTaskNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Comprovamos que los datos se puedan crear.
                if (comprovarDatos()) {

                    // Enviamos los datos a traves de este metodo.
                    ((AppActivity)requireActivity()).llamadaFragmentUpdateTwo(prepareData(),
                            idsUsers.get(spinnerTecnicoTarea.getSelectedItemPosition()), originalTask.toString());
                }
            }
        });

        // request para la peticion de usuarios.
        requestData(userToken);
        return view;
    }

    private String prepareData() {

        /**
         * {
         * "name": "restauración del jardín de la asamblea",
         * "fecha_culminacion": "2023-04-18",
         * "tarea": "LIMPIEZA",
         * "estatus": "NEW",
         * "tecnico": "davidf",
         * "admin": "eduard",
         * "ubicacionId" : 7
         * }
         */

        jsonTask = new JSONObject();

        // Preparamos la informacion de la fecha.
        Date date = new Date();
        date.setTime(calendarViewTask.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateString = dateFormat.format(date);

        String textTitle = editTextTitleTask.getText().toString();

        String nameTaskUser = "";

        // buscamos el username del usuario seleccionado, y lo guradamos en el json.
        for (int i = 0; i < jsonArrayUsers.length(); i++) {

            try {

                String nombreTecnico = jsonArrayUsers.getJSONObject(i).getString("nombre")
                        .concat(" ")
                        .concat(jsonArrayUsers.getJSONObject(i).getString("apellido"));


                if (nombreTecnico.contentEquals(stringsName[i])) {
                    nameTaskUser = jsonArrayUsers.getJSONObject(i).getString("nombre");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


        // Creamos el JSONObject para enviar.
        try {
            jsonTask.put("name", textTitle);
            jsonTask.put("fecha_culminacion", dateString);
            jsonTask.put("tarea", convertTareaToString(spinnerTipoTarea.getSelectedItemPosition()));
            jsonTask.put("estatus", convertStatusToString(spinnerStatusTarea.getSelectedItemPosition()));
            jsonTask.put("tecnico", jsonArrayUsers.getJSONObject(spinnerTecnicoTarea.getSelectedItemPosition()).getString("user"));
            jsonTask.put("admin", nameUser );
            jsonTask.put("ubicacionId", originalTask.getJSONObject("ubicacion").getInt("id"));

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
                Log.i("Respuesta user info", jsResponse.toString());

                ArrayAdapter arrayAdapter = null;

                try {
                    arrayAdapter = new ArrayAdapter(
                            getContext(),
                            android.R.layout.simple_spinner_item,
                            arrayUsersName(jsResponse.getJSONArray(0), idsUsers));
                } catch (JSONException e) {
                    Log.e("Error peticion users", e.getMessage());
                }

                spinnerTecnicoTarea.setAdapter(arrayAdapter);

                //activateProgressBar(false);
                spinnerTecnicoTarea.setEnabled(true);

                // habilitamos el boton siguiente
                buttonCreateTaskNext.setEnabled(true);

                // colocamos la informacion en los componentes.
                populateData(jsonTask);

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
    private String convertStatusToString(int position) {
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
     * Metodo para calcular el estado de la tarea,
     *
     * @param status string del estatus de la tarea.
     * @return devuelve un int que se usa en la posicion del desplegable.
     */
    private int convertStatusToInt(String status) {

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

    /**
     * Metodo que a partir de la posicion, devuelve el tipo de estado de forma de String
     * para hacer la request de cambio de estado.
     *
     * @param position
     * @return
     */
    private String convertTareaToString(int position) {
        if (position == 0) {
            return "ANALISIS";
        } else if (position == 1) {
            return "REPLANTAR";
        } else {
            return "LIMPIEZA";
        }
    }

    private int convertTareaToInt(String value) {
        if (value.contentEquals("ANALISIS")) {
            return 0;
        } else if (value.contentEquals("REPLANTAR")) {
            return 1;
        } else {
            return 2;
        }
    }

    private String[] arrayUsersName(JSONArray jsonArrayUsers, List<Integer> idsUsers) {

        this.jsonArrayUsers = jsonArrayUsers;
        Log.i("Data users", jsonArrayUsers.toString());

        int numUsers = jsonArrayUsers.length();
        stringsName = new String[numUsers];

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

    /**
     * Metodo para llenar la informacion de los componentes
     *
     * @param jsonTask objeto de la tarea.
     */
    private void populateData(JSONObject jsonTask) {
        /*

        this.editTextTitleTask = view.findViewById(R.id.editTextTitleTask);
        this.spinnerTecnicoTarea = view.findViewById(R.id.spinnerTecnicoTarea);
        this.spinnerStatusTarea = view.findViewById(R.id.spinnerStatusTarea);
        this.spinnerTipoTarea = view.findViewById(R.id.spinnerTipoTarea);
        this.calendarViewTask = view.findViewById(R.id.calendarViewTask);
        this.buttonCreateTaskNext = view.findViewById(R.id.buttonCreateTaskNext);

         */

        // creamos la variable para gestionar la fecha.
        Date fecha;
        // preparamos el formato de fecha para realizar la conversion.
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


        try {
            editTextTitleTask.setText(jsonTask.getString("name"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < jsonArrayUsers.length(); i++) {
            try {
                if (jsonArrayUsers.getJSONObject(i).getString("user").equals(jsonTask.getString("tecnico"))) {
                    spinnerTecnicoTarea.setSelection(i);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            spinnerStatusTarea.setSelection(convertStatusToInt(jsonTask.getString("estatus")));
            spinnerTipoTarea.setSelection(convertTareaToInt(jsonTask.getString("tarea")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // tratamos la fecha para ponerla en el calendario.
        // Preparamos la informacion de la fecha.

        try {
            String fechaString = jsonTask.getString("fecha_culminacion");
            fecha = dateFormat.parse(fechaString);
        }catch (NullPointerException | ParseException | JSONException exception){
            throw new RuntimeException(exception);
        }

        if(fecha != null){
            calendarViewTask.setDate(fecha.getTime());
        }

    }
}