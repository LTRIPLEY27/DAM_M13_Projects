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

public class JsonPostRequest {

	private String token, urlPostLogin, tarea;
	private RequestQueue requestQueue;
	JSONObject jsonObjectUser, jsonData;

	/**
	 * Constructor de la clase.
	 *
	 * @param token Token proporcionado al iniciar sesión.
	 * @param data objeto JSON del mensaje.
	 * @param requestQueue   created on activity.
	 */
	public JsonPostRequest(String token, JSONObject data,String url, RequestQueue requestQueue) {

		this.urlPostLogin = url;

		setToken(token);
		setJsonData(data);
		setRequestQueue(requestQueue);
	}

	/**
	 * Metodo para enviar la request
	 * @param listener recive el listener de la clase donde se crea.
	 */
	public void sendRequest(final VolleyResponseInterface listener) {

		//urlPostLogin = "https://reseed-385107.ew.r.appspot.com/post-mensaje";

		JSONObject jsonCallObject = getJsonData();

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlPostLogin,
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

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;

	}
	public void setJsonData(JSONObject jsonObject){
		this.jsonData = jsonObject;
	}
	public JSONObject getJsonData() {
		return jsonData;
	}
}
