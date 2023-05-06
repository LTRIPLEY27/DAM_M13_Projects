package com.reseed.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.reseed.R;
import com.reseed.adapter.UserAdapter;
import com.reseed.interfaces.RecyclerViewInterface;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.objects.UserObj;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.UserListRequest;
import com.reseed.util.JsonReseedUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentUsersList extends Fragment implements RecyclerViewInterface {

    private String userToken;
    private ProgressBar progressBarUsers;
    private RequestQueue requestQueue;
    private JsonReseedUtils jsonReseedUtils;
    private JSONArray jsonUserList;
    private ArrayList<UserObj> listaUsuarios;
    private RecyclerView recyclerView;
    private TextView textViewNoUsers;
    private UserAdapter adapter;

    public FragmentUsersList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userToken = getArguments().getString("token");
        }
        requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();
        jsonReseedUtils = new JsonReseedUtils();
        listaUsuarios = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerUser);
        textViewNoUsers = view.findViewById(R.id.textNoUsers);
        progressBarUsers = view.findViewById(R.id.progressBarUsers);

        if(listaUsuarios != null){
            recyclerView.setVisibility(View.VISIBLE);
            textViewNoUsers.setVisibility(View.GONE);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new UserAdapter(listaUsuarios, this);

            recyclerView.setAdapter(adapter);
        }else {
            Log.e("Lista usuarios", "Esta vacio.");
            recyclerView.setVisibility(View.GONE);
            textViewNoUsers.setVisibility(View.VISIBLE);
        }
        requestData(userToken);

        return view;
    }

    private void requestData(String userToken) {

        UserListRequest userListRequest = new UserListRequest(userToken,null, requestQueue);
        JSONObject jsResponse = new JSONObject();

        activateProgressBar(true);
        userListRequest.sendRequest(new VolleyResponseInterface() {
            @Override
            public void onError(String message) {
                Log.e("Error login: ", message);
                activateProgressBar(false);
                Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public boolean onResponse(Object response) {
                JSONArray jsResponse = (JSONArray) response;
                Log.i("Respuesta user info", jsResponse.toString());
                refreshContent(jsResponse);
                activateProgressBar(false);
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
            progressBarUsers.setVisibility(View.VISIBLE);
        }else {
            progressBarUsers.setVisibility(View.GONE);
        }

    }

    /**
     * Metodo usado para refrescar el contenido del recyclerview
     * @param jsonObject objeto de la respuesta de la request.
     */
    private void refreshContent(JSONArray jsonObject) {
        this.jsonUserList = jsonObject;
        extractUsers(jsonObject);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * Metodo para extraer los usuarios del JSON.
     */
    private void extractUsers(JSONArray jsonUserList) {
        this.listaUsuarios.clear();
        listaUsuarios.addAll(jsonReseedUtils.convertJsonArrayToUserList(jsonUserList));
    }

    @Override
    public void onItemClicked(int posicion) {

    }
}