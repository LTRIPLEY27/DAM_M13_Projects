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
import com.reseed.util.adapter.requests.SingletonReqQueue;
import com.reseed.util.adapter.requests.UserUpdateRequest;
import com.reseed.util.EncryptUtils;
import com.reseed.interfaces.VolleyResponseInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentUserConfig extends Fragment {

	JSONObject jsonUser;
	TextView textViewNombre, textViewApellido, textViewEmail, textViewTelefono, textViewUser, textViewCambiarPasswd;
	Button changePassButton, buttonChangePasswordAccept, buttonChangePasswordCancel;
	EditText editTextPasswordActual, editTextPasswordNuevo, editTextPasswordRepite;
	String actualPasswordString, originalEncryptedPasswd, token;

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
				enablePasswordChange(View.GONE, View.VISIBLE);

			}
		});

		buttonChangePasswordAccept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (comprovarPasswd(
						editTextPasswordActual.getText().toString(),
						editTextPasswordNuevo.getText().toString(),
						editTextPasswordRepite.getText().toString())) {

					changeStatusInputUserPasswd(false);

					enablePasswordChange(View.GONE, View.VISIBLE);
					sendUpdateRequest(editTextPasswordRepite.getText().toString());

				}
			}
		});

		buttonChangePasswordCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				enablePasswordChange(View.VISIBLE, View.GONE);

			}
		});

		return view;
	}

	/**
	 * Activa o desactiva la edicion de los controles de input del password del usuario.
	 * @param status Boolean, true} si se quieren activar, false para desactivar.
	 */
	private void changeStatusInputUserPasswd(Boolean status) {
		editTextPasswordActual.setEnabled(status);
		editTextPasswordNuevo.setEnabled(status);
		editTextPasswordRepite.setEnabled(status);
		buttonChangePasswordAccept.setEnabled(status);
		buttonChangePasswordCancel.setEnabled(status);
	}

	/**
	 * Activa la visivilidad de los controles segun si se quiere ver la información del usuario
	 * o la edición de la contraseña.
	 * @param infoElements Elementos de información del usuario
	 * @param editElements Elementos de edición de la contraseña
	 */
	private void enablePasswordChange(int infoElements, int editElements) {
		textViewNombre.setVisibility(infoElements);
		textViewApellido.setVisibility(infoElements);
		textViewEmail.setVisibility(infoElements);
		textViewTelefono.setVisibility(infoElements);
		textViewUser.setVisibility(infoElements);
		changePassButton.setVisibility(infoElements);
		textViewCambiarPasswd.setVisibility(editElements);
		editTextPasswordActual.setVisibility(editElements);
		editTextPasswordNuevo.setVisibility(editElements);
		editTextPasswordRepite.setVisibility(editElements);
		buttonChangePasswordAccept.setVisibility(editElements);
		buttonChangePasswordCancel.setVisibility(editElements);
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


		userUpdate.sendRequest(new VolleyResponseInterface() {
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
				changeStatusInputUserPasswd(true);
			}

			@Override
			public boolean onResponse(Object response) {

				//Si la respuesta
				JSONObject jsResponse = (JSONObject) response;
				try {
					Toast toast = Toast.makeText(requireContext(), jsResponse.getString("password"), Toast.LENGTH_LONG);
					toast.show();

					((AppActivity)requireActivity()).logoutMenuCall(null);


				} catch (JSONException e) {
					Log.e("jsResponse error: ", e.getMessage());
				}

				return false;
			}
		});

	}
}