package com.reseed.requests;

import android.content.Context;
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
import com.reseed.util.VolleyResponseListener;


import org.json.JSONObject;

public class LoginAuthRequest {

    private String email;
    private String password;

    private Context context;
    private RequestQueue requestQueue;

    /**
     * Constructor of the class.
     * @param email email from the user
     * @param password password from user
     */
    public LoginAuthRequest(String email, String password, RequestQueue requestQueue){
        setEmail(email);
        setPassword(password);
        setRequestQueue(requestQueue);
    }

    public void sendRequest(final VolleyResponseListener listener){

        String urlPostLogin = "https://t-sunlight-381215.lm.r.appspot.com/auth/";

        JSONObject jsonCallObject = new JSONObject();
        try {
            jsonCallObject.put("email",getEmail());
            jsonCallObject.put("password",getPassword());
        }catch (Exception e){
            Log.e("Error login request: ", e.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.POST, urlPostLogin, jsonCallObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Log.i("Response login: ", response.toString());
                    listener.onResponse(response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Todo controlar el timeout de la primera request al server.

                if( error instanceof NetworkError) {
                    //handle your network error here.
                    listener.onError("No hay connexion a la red.");
                } else if( error instanceof ServerError) {
                    //handle if server error occurs with 5** status code
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
        });

        getRequestQueue().add(req);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRequestQueue(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
