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
import com.reseed.AppActivity;
import com.reseed.R;
import com.reseed.interfaces.VolleyResponseInterface;
import com.reseed.requests.JsonPostRequest;
import com.reseed.requests.JsonPutRequest;
import com.reseed.requests.SingletonReqQueue;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentUpdateUser extends Fragment {

    String nombre, apellido, user, email, telefono, rol, userToken, userId;

    Boolean esAdmin;

    EditText editTextApellido, editTextEmail, editTextTelefono, editTextPass, editTextPassRepeat;

    TextInputEditText editTextNombre;

    TextInputLayout editTextNombreError, editTextApellidoError, emailEditTextError, telefonoEditTextError, passwordEditTextError, passwordRepEditTextError;

    Button buttonGuardar;

    CheckBox checkBoxAdmin;

    RequestQueue requestQueue;

    JSONObject userJson;

    public FragmentUpdateUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instanciamos la requestqueue
        requestQueue = SingletonReqQueue.getInstance(requireContext()).getRequestQueue();

        if (getArguments() != null) {
            userToken = getArguments().getString("token");
            this.user = getArguments().getString("user");
            this.nombre = getArguments().getString("nombre");
            this.apellido = getArguments().getString("apellido");
            this.userId = getArguments().getString("userId");
            this.telefono = getArguments().getString("telefono");
            this.email = getArguments().getString("email");

            if (getArguments().getString("tipoUser").equals("ADMIN")) {
                rol = "ADMIN";
            } else {
                rol = "TECNIC";
            }


            this.user = getArguments().getString("tipoUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);

        // preparamos los componentes.

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

        /**
         * Listeners de los botones.
         */
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validarDatos(view)) {
                    nombre = editTextNombre.getText().toString();
                    apellido = editTextApellido.getText().toString();
                    email = editTextEmail.getText().toString();
                    telefono = editTextTelefono.getText().toString();
                    esAdmin = checkBoxAdmin.isSelected();

                    // nombre de user, suma parte del nombre con el apellido.
                    user = nombre.toLowerCase().substring(2, 3).concat(apellido.toLowerCase());

                    // cuando validamos los datos lanzamos el metodo para preparar el json.
                    if (createUserJson(view)) {
                        createUser();
                    }
                }
            }
        });

        // llenamos los componentes con la informacion del usuario.
        popularComponentes();

        return view;
    }

    /**
     * Metodo para crear el json de la request.
     *
     * @param view
     * @return Devuelve true si el json se crea correctamente, de lo contrario, devuelve false.
     */
    private boolean createUserJson(View view) {

        this.userJson = new JSONObject();

        try {

            userJson.put("nombre", this.nombre);
            userJson.put("apellido", this.apellido);
            userJson.put("user", this.user);
            userJson.put("email", this.email);
            userJson.put("telefono", this.telefono);
            if (checkBoxAdmin.isSelected()) {
                userJson.put("rol", "ADMIN");
            } else {
                userJson.put("rol", "TECNIC");
            }


        } catch (JSONException exception) {
            Log.e("Error en create user", exception.getMessage());
            return false;
        }

        return true;

    }

    /**
     * Metodo que comprueva los datos de los componentes,  antes de hacer el registro.
     *
     * @param view
     * @return
     */
    private boolean validarDatos(View view) {

        boolean noError = true;

        if (editTextNombre.getText().length() == 0) {
            editTextNombreError.setError("No puede estar vacio.");
            noError = false;
        } else if (editTextNombre.getText().length() < 3) {
            editTextNombreError.setError("El nombre es muy corto.");
        } else {
            editTextNombreError.setError(null);
        }

        if (editTextApellido.getText().length() == 0) {
            editTextApellidoError.setError("No puede estar vacio.");
            noError = false;
        } else if (editTextApellido.getText().length() < 3) {
            editTextApellidoError.setError("El apellido es muy corto.");
        } else {
            editTextApellidoError.setError(null);
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
            emailEditTextError.setError("Email invalido");
            noError = false;
        } else {
            emailEditTextError.setError(null);
        }

        if (editTextTelefono.getText().length() < 9) {
            telefonoEditTextError.setError("Telefono invalido.");
            noError = false;
        } else {
            telefonoEditTextError.setError(null);
        }
        return noError;
    }

    /**
     * Metodo para realizar la request de creacion del usuario.
     */
    private void createUser() {
        String url = "https://reseed-385107.ew.r.appspot.com/update/value/tecnico/id/";
        url = url.concat(this.userId);

        JsonPutRequest saveUserRequest = new JsonPutRequest(this.userToken, url, this.userJson, requestQueue);

        saveUserRequest.sendRequest(new VolleyResponseInterface() {
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
                Log.i("Respuesta", jsResponse.toString());

                Toast toast = Toast.makeText(requireContext(), "Usuario actualizado.", Toast.LENGTH_SHORT);
                toast.show();

                ((AppActivity) requireActivity()).usersFragmentCall(null);

                return false;
            }
        });
    }

    /**
     * Metodo para llenar los componentes con informacion.
     */
    private void popularComponentes() {
        editTextNombre.setText(this.nombre);
        editTextApellido.setText(this.apellido);
        editTextEmail.setText(this.email);
        editTextTelefono.setText(this.telefono);

        if (this.rol.equals("ADMIN")) {
            checkBoxAdmin.setChecked(true);
        } else {
            checkBoxAdmin.setChecked(false);
        }
    }
}