package com.example.reseed.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class ModelLoginAuth {

    private String email;
    private String password;

    private Context context;
    private String ip;

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
    }


    public boolean sendRequest(){

        //String urlGet = "https://httpbin.org/ip";
        String urlGetChuck = "https://api.chucknorris.io/jokes/random?category=dev";

        StringRequest getRequest = new StringRequest(Request.Method.GET, urlGetChuck, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    setIp(jsonObject.getString("value"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error login", error.getMessage());

            }
        });

        Volley.newRequestQueue(getContext()).add(getRequest);
        //return true if ip have info.
        return !ip.isEmpty();
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
