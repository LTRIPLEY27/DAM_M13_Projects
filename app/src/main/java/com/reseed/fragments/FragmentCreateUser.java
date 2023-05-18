package com.reseed.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.reseed.R;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.requests.JsonPostRequest;
import com.reseed.requests.SingletonReqQueue;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentCreateUser extends Fragment {

    String nombre, apellido, user, email, telefono, rol, userToken;

    Boolean esAdmin;

    EditText editTextApellido, editTextUser, editTextEmail, editTextTelefono, editTextRol, editTextPass, editTextPassRepeat;

    TextInputEditText editTextNombre;

    TextInputLayout editTextNombreError, editTextApellidoError, emailEditTextError,telefonoEditTextError,passwordEditTextError,passwordRepEditTextError;

    Button buttonGuardar;

    CheckBox checkBoxAdmin;

    RequestQueue requestQueue;

    JSONObject userJson;

    public FragmentCreateUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instanciamos la requestqueue
        requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();

        if (getArguments() != null) {
            userToken = getArguments().getString("token");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        /*{
                "nombre" : "Steffy",
                "apellido" : "Steffasson",
                "user" : "steffy",
                "password" : "steffy2323",
                "email" : "steffy@fantasymail.com",
                "telefono" : "33-999-999",
                "rol" : "ADMIN"
        }*/

        editTextNombre = view.findViewById(R.id.nombreEditText);
        editTextApellido = view.findViewById(R.id.apellidoEditText);
        editTextEmail = view.findViewById(R.id.emailEditText);
        editTextTelefono = view.findViewById(R.id.telefonoEditText);
        checkBoxAdmin = view.findViewById(R.id.checkAdministrador);
        buttonGuardar = view.findViewById(R.id.buttonSave);

        editTextNombreError = view.findViewById(R.id.nombreEditTextError);
        editTextApellidoError = view.findViewById(R.id.apellidoEditTextError);
        emailEditTextError = view.findViewById(R.id.emailEditTextError);
        telefonoEditTextError = view.findViewById(R.id.telefonoEditTextError);
        passwordEditTextError = view.findViewById(R.id.passwordEditTextError);
        passwordRepEditTextError = view.findViewById(R.id.passwordRepEditTextError);


        /**
         * Listeners de los botones.
         */
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarDatos(view)){
                    nombre = editTextNombre.getText().toString();
                    apellido = editTextApellido.getText().toString();
                    email = editTextEmail.getText().toString();
                    telefono = editTextTelefono.getText().toString();
                    esAdmin = checkBoxAdmin.isSelected();

                    // nombre de user, suma parte del nombre con el apellido.
                    user = nombre.toLowerCase().substring(2,3).concat(apellido.toLowerCase());
                }


                if(createUserJson(view)){

                }
            }
        });

        return view;
    }

    private boolean createUserJson(View view){

        userJson = new JSONObject();


            /*userJson.put("nombre",this.nombre);
            userJson.put("apellido",this.apellido);
            userJson.put("user",this.user);

            userJson.put("password",);
            userJson.put("email",);
            userJson.put("telefono",);
            userJson.put("rol",);*/




        return false;
    }

    private boolean validarDatos(View view) {

        if(editTextNombre.getText().length() == 0) {
            editTextNombreError.setError("No puede estar vacio.");
            return false;
        } else if (editTextNombre.getText().length() < 3) {
            editTextNombreError.setError("El nombre es muy corto.");
        }else{
            editTextNombreError.setError(null);
        }

        if (editTextApellido.getText().length() == 0){
            editTextApellidoError.setError("No puede estar vacio.");
            return false;
        } else if (editTextApellido.getText().length() < 3) {
            editTextApellidoError.setError("El apellido es muy corto.");
        }else{
            editTextApellidoError.setError(null);
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()){
            emailEditTextError.setError("Email invalido");
            return false;
        }else {
            emailEditTextError.setError(null);
        }

        if (editTextTelefono.getText().length() == 0){
            editTextTelefono.setBackgroundColor(Color.RED);
            return false;
        }

        if(!(editTextPass.length() > 6) || !(editTextPass.getText().equals(editTextPassRepeat.getText()))){
            editTextPass.setBackgroundColor(Color.RED);
            editTextPassRepeat.setBackgroundColor(Color.RED);
            return false;
        }

        return true;
    }

    private void createUser(){
        String url = "https://reseed-385107.ew.r.appspot.com/register";

        JsonPostRequest saveTaskRequest = new JsonPostRequest(userToken, userJson, url, requestQueue);

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

                Toast toast = Toast.makeText(requireContext(), "Guardado parte 1 de 3", Toast.LENGTH_SHORT);
                toast.show();

                /*try {
                    salvarUbicacionMapa(token,jsResponse.getInt("id"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }*/

                return false;
            }
        });
    }
}