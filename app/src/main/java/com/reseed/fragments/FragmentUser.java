package com.reseed.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reseed.R;

public class FragmentUser extends Fragment {

    String nombre, apellido, user, email, telefono, rol, userToken, userId;

    TextView nombreTextView, apellidoTextView, emailTextView,telefonoTextView, tipoTextView;

    public FragmentUser() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        //nombreTextView, apellidoTextView, emailTextView,telefonoTextView, tipoTextView;

        nombreTextView = view.findViewById(R.id.nombreEditText);
        apellidoTextView = view.findViewById(R.id.apellidoEditText);
        emailTextView = view.findViewById(R.id.emailEditText);
        telefonoTextView = view.findViewById(R.id.telefonoEditText);
        tipoTextView = view.findViewById(R.id.tipoEditText);


        nombreTextView.setText(this.nombre);
        apellidoTextView.setText(this.apellido);
        emailTextView.setText(this.email);
        telefonoTextView.setText(this.telefono);
        tipoTextView.setText(this.rol);


        return view;
    }
}