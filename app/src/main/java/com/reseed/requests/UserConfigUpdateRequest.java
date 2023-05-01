package com.reseed.requests;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.reseed.interfaces.VolleyResponseInterface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserConfigUpdateRequest {

	private String token, urlPostLogin, passwordString;
	private RequestQueue requestQueue;
	JSONObject jsonObjectUser;

	/**
	 * Constructor of the class.
	 *
	 * @param passwordString
	 * @param requestQueue   created on activity.
	 */
	public UserConfigUpdateRequest(String token, JSONObject jsonObjectUser, String passwordString, RequestQueue requestQueue) {
		setToken(token);
		setRequestQueue(requestQueue);
		setJsonObjectUser(jsonObjectUser);
		setPasswordString(passwordString);
	}




	public void sendRequest(final VolleyResponseInterface listener) {

		urlPostLogin = "https://reseed-385107.ew.r.appspot.com/update-user";

		JSONObject jsonCallObject = new JSONObject();
		try {
			jsonCallObject.put("nombre",getJsonObjectUser().getString("nombre"));
			jsonCallObject.put("apellido",getJsonObjectUser().getString("apellido"));
			jsonCallObject.put("user",getJsonObjectUser().getString("user"));
			jsonCallObject.put("password",getPasswordString());
			jsonCallObject.put("email",getJsonObjectUser().getString("email"));
			jsonCallObject.put("telefono",getJsonObjectUser().getString("telefono"));
			jsonCallObject.put("rol",getJsonObjectUser().getString("rol"));
		} catch (Exception e) {
			Log.e("Error user update request: ", e.getMessage());
		}

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, urlPostLogin,
				jsonCallObject, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d("Respuesta user info test", response.toString());

				// Enviamos la informacion de la respuesta al FragmentUserConfig.
				listener.onResponse(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (error instanceof NetworkError) {
					//Comprueva los errores de red.
					listener.onError("No hay connexion a la red.");
				} else if (error instanceof ServerError) {
					//gestiona los errores 5XX del servidor.
					listener.onError("No hay connexion al servidor.");
				} else if (error instanceof AuthFailureError) {
					//handle if authFailure occurs.This is generally because of invalid credentials
					listener.onError("Credenciales invalidas.");
				} else if (error instanceof ParseError) {
					//handle if the volley is unable to parse the response data.
					listener.onError("Datos incorrectos.");
				} else if (error instanceof NoConnectionError) {
					//handle if no connection is occurred
					listener.onError("No hay connexion al servidor.");
				} else if (error instanceof TimeoutError) {
					//handle if socket time out is occurred.
					listener.onError("Error del servidor, prueba en 30 segundos.");
				}
			}
		}) {

			/**
			 * Modificamos el header para enviar la autentificacion a través de un Beater Token.
			 */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				//headers.put("Content-Type", "application/json");
				headers.put("Authorization", "Bearer " + getToken());
				return headers;
			}
		};

		// Politica de la request
		int TIMEOUT_MS = 10000;      //10 segundos

		req.setRetryPolicy(new DefaultRetryPolicy(
				TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		getRequestQueue().add(req);

	}
	private void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}
	public String getPasswordString(){
		return passwordString;
	}
	private void setJsonObjectUser(JSONObject jsonObjectUser) {
		this.jsonObjectUser = jsonObjectUser;
	}

	private JSONObject getJsonObjectUser() {
		return jsonObjectUser;
	}

	public String getToken() {
		return token;
	}

	private void setToken(String token) {
		this.token = token;
	}

	private void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}
}
