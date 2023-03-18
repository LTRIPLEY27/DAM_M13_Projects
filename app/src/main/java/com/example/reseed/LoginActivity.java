package com.example.reseed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
                sendRequest();
                /*if(sendLogin(userTextView.getText().toString(), passwordTextView.getText().toString())){

                    Context context = getApplicationContext();
                    CharSequence text = responseLogin;

                    Toast toast = Toast.makeText(context,text,Toast.LENGTH_LONG);
                    toast.show();

                }*/
                //openNewActivity();
            }
        });
    }

    private boolean sendLogin(String email, String password){

        /*ModelLoginAuth login = new ModelLoginAuth(email, password,getApplicationContext());
        if(login.sendRequest()){
            setResponseLogin(login.getIp());
            return true;
        }else {
            return false;
        }*/




        return false;
    }


    public void sendRequest(){

        //String urlGet = "https://httpbin.org/ip";
        String urlGetChuck = "https://api.chucknorris.io/jokes/random?category=dev";

        StringRequest getRequest = new StringRequest(Request.Method.GET, urlGetChuck, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    setIp(jsonObject.getString("value"));
                    Log.i("Info login:", getIp());
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

        Volley.newRequestQueue(this).add(getRequest);
        //return true if ip have info.
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