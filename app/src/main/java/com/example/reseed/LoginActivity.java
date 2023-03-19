package com.example.reseed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.reseed.model.ModelLoginAuth;
import com.example.reseed.util.VolleyResponseListener;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button loginB;
    TextView userTextView, passwordTextView;

    private String responseLogin,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // pedimos que sea full screeen
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //-----

        setContentView(R.layout.activity_login);



        loginB = (Button) findViewById(R.id.loginButton);
        userTextView = (TextView) findViewById(R.id.editTextEmailAddress);
        passwordTextView = (TextView) findViewById(R.id.editTextPassword);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendLogin(userTextView.getText().toString(), passwordTextView.getText().toString());
            }
        });
    }

    private void sendLogin(String email, String password){

        ModelLoginAuth login = new ModelLoginAuth(email, password,getApplicationContext());

        login.sendRequest(new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(Object response) {

                JSONObject jsResponse = (JSONObject)response;
                try {
                    setResponseLogin(jsResponse.getString("value"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Context context = getApplicationContext();


                Toast toast = Toast.makeText(context,responseLogin,Toast.LENGTH_LONG);
                toast.show();

                //openNewActivity();
            }
        });
    }

    private void openNewActivity(){
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }

    private void setResponseLogin(String response){
        this.responseLogin = response;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}