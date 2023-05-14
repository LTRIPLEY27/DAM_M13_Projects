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

public class JsonDeleteRequest {

	private String token, idTarea, urlPostLogin;

	JSONObject data;

	private RequestQueue requestQueue;



	/**
	 * Constructor of the class.
	 *
	 * @param token
	 * @param url
	 * @param requestQueue   created on activity.
	 */
	public JsonDeleteRequest(String token, String url, RequestQueue requestQueue) {
		setToken(token);
		this.urlPostLogin = url;
		setData(data);
		setIdTarea(idTarea);
		setRequestQueue(requestQueue);
	}


	public void sendRequest(final VolleyResponseInterface listener) {


		JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, urlPostLogin,
				null, new Response.Listener<JSONObject>(){

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
					listener.onResponse("ok");
					/*}else {
						listener.onError("Datos incorrectos.");
					}*/

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

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public String getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

}
