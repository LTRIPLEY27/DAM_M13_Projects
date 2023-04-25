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

public class UserInfoRequest {

    private String token;
    private RequestQueue requestQueue;
    private Boolean isDemoReq;
    private String urlPostLogin;

    /**
     * Constructor of the class.
     * @param token email from the user
     * @param requestQueue created on activity.
     */
    public UserInfoRequest(String token, RequestQueue requestQueue, Boolean isDemoReq){
        setToken(token);
        setRequestQueue(requestQueue);
        setDemoReq(isDemoReq);
    }

    public void sendRequest(final VolleyResponseInterface listener){

        if(getDemoReq()){
            urlPostLogin = "https://7fb95452-32bb-4186-a691-5be768b33401.mock.pstmn.io/perfil";
        }else{

            urlPostLogin = "https://t-sunlight-381215.lm.r.appspot.com/perfil";
        }


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,urlPostLogin,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respuesta user info request", response.toString());

                // Enviamos la informacion de la respuesta al LoginActivity.
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof NetworkError) {
                    //Comprueva los errores de red.
                    listener.onError("No hay connexion a la red.");
                } else if( error instanceof ServerError) {
                    //gestiona los errores 5XX del servidor.
                    listener.onError("No hay connexion al servidor.");
                } else if( error instanceof AuthFailureError) {
                    //handle if authFailure occurs.This is generally because of invalid credentials
                    listener.onError("Credenciales invalidas.");
                } else if( error instanceof ParseError) {
                    //handle if the volley is unable to parse the response data.
                    listener.onError("Datos incorrectos.");
                } else if( error instanceof NoConnectionError) {
                    //handle if no connection is occurred
                    listener.onError("No hay connexion al servidor.");
                } else if( error instanceof TimeoutError) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRequestQueue(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public Boolean getDemoReq() {
        return isDemoReq;
    }

    public void setDemoReq(Boolean demoReq) {
        isDemoReq = demoReq;
    }
}
