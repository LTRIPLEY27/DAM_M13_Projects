package com.reseed.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.reseed.R;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.UserListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdminStadistics extends Fragment {

    PieChart pieChart;

    String userToken;

    Spinner spinnerTecnicos;

    JSONArray jsonArrayUsers;

    RequestQueue requestQueue;
    List<Integer> idsUsers;


    public FragmentAdminStadistics() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // instanciamos la requestQueue
        requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();

        //creamos los objetos
        jsonArrayUsers = new JSONArray();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_admin_stadistics, container, false);

        // Preparamos el arraylist que contendra los datos de las estadisticas.
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        spinnerTecnicos = view.findViewById(R.id.spinnerUsuariosTecnicos);

        // obtenemos el objeto para realizar las estadisticas.
        pieChart = view.findViewById(R.id.estadisticaPastel);

        // Preparamos como se verán los graficos.
        pieChart.setUsePercentValues(true);

        pieChart.getDescription().setEnabled(true);

        pieChart.setExtraOffsets(5,5,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.5f);

        pieChart.setDrawHoleEnabled(true);

        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleRadius(60f);

        pieEntries.add(new PieEntry(4f, "David"));
        pieEntries.add(new PieEntry(2f, "Roser"));
        pieEntries.add(new PieEntry(8f, "Nahia"));
        pieEntries.add(new PieEntry(1f, "Ibai"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Tecnicos");
        pieDataSet.setSliceSpace(4f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);

        pieChart.setData(pieData);

        pieChart.animateX(1000, Easing.EaseInOutCubic);

        requestData(userToken);

        return view;
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

                spinnerTecnicos.setAdapter(arrayAdapter);

                //activateProgressBar(false);
                spinnerTecnicos.setEnabled(true);
                return true;
            }
        });
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