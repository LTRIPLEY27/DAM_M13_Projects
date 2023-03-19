package com.example.reseed.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reseed.util.VolleyResponseListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.CacheRequest;

public class ModelLoginAuth {

    private String email;
    private String password;

    private Context context;
    private String ip;
    private RequestQueue requestQueue;

    /**
     * Constructor empty.
     */
    public ModelLoginAuth(){
    }

    /**
     * Constructor of the class.
     * @param email email from the user
     * @param password password from user
     */
    public ModelLoginAuth(String email, String password, Context context){
        this.setEmail(email);
        this.setPassword(password);
        this.setContext(context);
        requestQueue = Volley.newRequestQueue(getContext());
    }


    public void sendRequest(final VolleyResponseListener listener){




        //String urlGet = "https://httpbin.org/ip";
        String urlGetChuck = "https://api.chucknorris.io/jokes/random?category=dev";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlGetChuck, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.i("Response login: ", response.get("value").toString());
                    listener.onResponse(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
                Log.e("Error login", error.getMessage());

            }
        });

        requestQueue.add(jsonObjectRequest);


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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
