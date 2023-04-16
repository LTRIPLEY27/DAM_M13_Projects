package com.reseed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.UserUpdateRequest;
import com.reseed.util.EncryptUtils;
import com.reseed.util.JsonReseedUtils;
import com.reseed.util.VolleyResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentUserConfig extends Fragment {

	JSONObject jsonUser;
	JsonReseedUtils jsonReseedUtils;
	TextView textViewNombre, textViewApellido, textViewEmail, textViewTelefono, textViewUser, textViewCambiarPasswd;
	Button changePassButton, buttonChangePasswordAccept, buttonChangePasswordCancel;
	EditText editTextPasswordActual, editTextPasswordNuevo, editTextPasswordRepite;
	String actualPasswordString, passwordString, repeatPasswordString, originalEncryptedPasswd, token;

	RequestQueue requestQueue;

	public FragmentUserConfig() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			try {
				jsonUser = new JSONObject(getArguments().getString("data"));
				token = getArguments().getString("token");
				actualPasswordString = getArguments().getString("encryptedPasswd");
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}

		try {
			// instanciamos la requestqueue
			requestQueue = SingletonReqQueue.getInstance(requireContext().getApplicationContext()).getRequestQueue();
		}catch (IllegalStateException exception){
			Log.e("Error en la RequestQueue",exception.getMessage());
		}


		//todo hacer el update request del usuario.

		/**
		 * Guardamos los datos recividos del password de {@link AppActivity}
		 */
		originalEncryptedPasswd = getArguments().getString("encryptedPasswd");
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
		textViewCambiarPasswd = view.findViewById(R.id.textViewChangePass);
		editTextPasswordActual = view.findViewById(R.id.editTextPasswordActual);
		editTextPasswordNuevo = view.findViewById(R.id.editTextPasswordNuevo);
		editTextPasswordRepite = view.findViewById(R.id.editTextPasswordRepite);
		changePassButton = view.findViewById(R.id.buttonChangePassword);
		buttonChangePasswordAccept = view.findViewById(R.id.buttonChangePasswordAccept);
		buttonChangePasswordCancel = view.findViewById(R.id.buttonChangePasswordCancel);

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
				textViewNombre.setVisibility(View.GONE);
				textViewApellido.setVisibility(View.GONE);
				textViewEmail.setVisibility(View.GONE);
				textViewTelefono.setVisibility(View.GONE);
				textViewUser.setVisibility(View.GONE);
				changePassButton.setVisibility(View.GONE);
				textViewCambiarPasswd.setVisibility(View.VISIBLE);
				editTextPasswordActual.setVisibility(View.VISIBLE);
				editTextPasswordNuevo.setVisibility(View.VISIBLE);
				editTextPasswordRepite.setVisibility(View.VISIBLE);
				buttonChangePasswordAccept.setVisibility(View.VISIBLE);
				buttonChangePasswordCancel.setVisibility(View.VISIBLE);

			}
		});

		buttonChangePasswordAccept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (comprovarPasswd(
						editTextPasswordActual.getText().toString(),
						editTextPasswordNuevo.getText().toString(),
						editTextPasswordRepite.getText().toString())) {

					sendUpdateRequest(editTextPasswordRepite.getText().toString());

				}
			}
		});

		buttonChangePasswordCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				textViewNombre.setVisibility(View.VISIBLE);
				textViewApellido.setVisibility(View.VISIBLE);
				textViewEmail.setVisibility(View.VISIBLE);
				textViewTelefono.setVisibility(View.VISIBLE);
				textViewUser.setVisibility(View.VISIBLE);
				changePassButton.setVisibility(View.VISIBLE);

				textViewCambiarPasswd.setVisibility(View.GONE);
				editTextPasswordActual.setVisibility(View.GONE);
				editTextPasswordNuevo.setVisibility(View.GONE);
				editTextPasswordRepite.setVisibility(View.GONE);
				buttonChangePasswordAccept.setVisibility(View.GONE);
				buttonChangePasswordCancel.setVisibility(View.GONE);

			}
		});

		return view;
	}

	/**
	 * Comprueva que el password actual concuerda y que los contenidos de el password
	 * se quiere cambiar junto con la repeticion de este es correcta.
	 *
	 * @param actualPasswordString
	 * @param passwordString
	 * @param repeatPasswordString
	 * @return devuelve true si es correcto false si hay algun error.
	 */
	private boolean comprovarPasswd(String actualPasswordString, String passwordString, String repeatPasswordString) {

		EncryptUtils encryptUtils = new EncryptUtils();
		String actualPasswdEncrypted = encryptUtils.encryptPasswordMd5(actualPasswordString);

		if (actualPasswdEncrypted.contentEquals(originalEncryptedPasswd)) {
			if(passwordString.contentEquals(repeatPasswordString)){
				Log.i("Password info", "password correcto.");
				return true;
			}
		}
		Log.e("Password info", "password incorrecto.");
		return false;
	}

	/**
	 * Metodo usado para realizar el update del usuario, se usa la clase {@link FragmentUserConfig}, y sobre ella
	 * se le asigna un listener para el evento de la request.
	 *
	 * @param passwordString del usuario
	 */
	private void sendUpdateRequest(String passwordString) {



		UserUpdateRequest userUpdate = new UserUpdateRequest(token, jsonUser, passwordString, requestQueue);


		userUpdate.sendRequest(new VolleyResponseListener() {
			@Override
			public void onError(String message) {
				/**/
				Log.e("Error login: ", message);

				if (message.contains("403")) {
					Toast toast = Toast.makeText(requireContext(), "Update incorrecto.", Toast.LENGTH_LONG);
					toast.show();
				} else {
					Toast toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG);
					toast.show();
				}
			}

			@Override
			public boolean onResponse(Object response) {

				//Si la respuesta
				JSONObject jsResponse = (JSONObject) response;
				try {
					Toast toast = Toast.makeText(requireContext(), jsResponse.getString("password"), Toast.LENGTH_LONG);
					toast.show();

					((AppActivity)getActivity()).logoutMenuCall(null);


				} catch (JSONException e) {
					Log.e("jsResponse error: ", e.getMessage());
				}

				return false;
			}
		});

	}
}