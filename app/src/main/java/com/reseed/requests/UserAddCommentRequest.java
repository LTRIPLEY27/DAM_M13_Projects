package com.reseed.requests;

import android.util.Log;

import com.android.volley.AuthFailureError;
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

public class UserAddCommentRequest {

	private String token, urlPostLogin, description, tarea, tecnico, admin;
	private RequestQueue requestQueue;
	JSONObject jsonObjectUser;

	/**
	 * Constructor de la clase.
	 *
	 * @param token Token proporcionado al iniciar sesión.
	 * @param description Cuerpo del mensaje.
	 * @param tarea id de tarea con la que se relaciona.
	 * @param tecnico tecnico que escribe el mensaje.
	 * @param admin administrador de la tarea.
	 * @param requestQueue   created on activity.
	 */
	public UserAddCommentRequest(String token, String description, String tarea, String tecnico, String admin, RequestQueue requestQueue) {
		setToken(token);
		setRequestQueue(requestQueue);

		setDescription(description);
		setTarea(tarea);
		setTecnico(tecnico);
		setAdmin(admin);

	}




	public void sendRequest(final VolleyResponseInterface listener) {

		urlPostLogin = "https://t-sunlight-381215.lm.r.appspot.com/post-mensaje";

		JSONObject jsonCallObject = new JSONObject();
		try {
			jsonCallObject.put("descripcion",getDescription());
			jsonCallObject.put("tarea",getTarea());
			jsonCallObject.put("tecnico",getTecnico());
			jsonCallObject.put("admin",getAdmin());
		} catch (Exception e) {
			Log.e("Error user update request: ", e.getMessage());
		}

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

		getRequestQueue().add(req);

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;

	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;

	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;

	}
}
