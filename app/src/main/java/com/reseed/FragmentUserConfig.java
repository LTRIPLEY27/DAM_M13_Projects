package com.reseed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.reseed.util.JsonReseedUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentUserConfig extends Fragment {

    JSONObject jsonUser;
    JsonReseedUtils jsonReseedUtils;
    TextView textViewNombre, textViewApellido, textViewEmail, textViewTelefono, textViewUser;
    Button changePassButton;
    EditText editTextPassword;

    public FragmentUserConfig() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            try {
                jsonUser = new JSONObject(getArguments().getString("data"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_config, container, false);

        textViewNombre = view.findViewById(R.id.textViewConfigNombre);
        textViewApellido = view.findViewById(R.id.textViewConfigApellido);
        textViewEmail = view.findViewById(R.id.textViewConfigEmail);
        textViewTelefono = view.findViewById(R.id.textViewConfigTelefono);
        textViewUser = view.findViewById(R.id.textViewConfigTypeuser);
        changePassButton = view.findViewById(R.id.buttonChangePassword);
        editTextPassword = view.findViewById(R.id.editTextTextPassword);

        try {
            textViewNombre.setText(jsonUser.getString("nombre"));
            textViewApellido.setText(jsonUser.getString("apellido"));
            textViewEmail.setText(jsonUser.getString("email"));
            textViewTelefono.setText(jsonUser.getString("telefono"));
            textViewUser.setText(jsonUser.getString("rol"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassButton.setVisibility(View.GONE);
                editTextPassword.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}