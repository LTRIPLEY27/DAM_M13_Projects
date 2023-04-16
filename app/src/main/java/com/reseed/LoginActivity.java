package com.reseed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.reseed.requests.UserPpdateRequest;
import com.reseed.requests.SingletonReqQueue;
import com.reseed.requests.UserInfoRequest;
import com.reseed.util.EncryptUtils;
import com.reseed.util.VolleyResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button loginB;
    TextView userTextView, passwordTextView;
    RequestQueue requestQueue;
    EncryptUtils encryptUtils;
    private String loginToken, encryptedPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hacemos que la pantalla de la aplicacion sea full screeen.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        /*supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }*/

        setContentView(R.layout.activity_login);

        loginB = findViewById(R.id.loginButton);
        userTextView = findViewById(R.id.editTextEmailAddress);
        passwordTextView = findViewById(R.id.editTextPassword);

        // instanciamos la requestqueue
        requestQueue = SingletonReqQueue.getInstance(this.getApplicationContext()).getRequestQueue();

        // inicializamos las utilidades de encriptación.
        encryptUtils = new EncryptUtils();

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidEmail(userTextView.getText().toString())) {
                    sendLoginRequest(userTextView.getText().toString(), passwordTextView.getText().toString());
                } else {
                    Log.e("Error email: ", "format email incorrect.");

                    Toast toast = Toast.makeText(getApplicationContext(), "Direccion de correo incorrecta.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    /**
     * Metodo para comprovar que el formato de correo introducido es correcto,
     * antes de realizar ninguna request.
     *
     * @param email correo para comprobar.
     * @return retorna false si esta en blanco o no concuerda el formato, en caso contrario true.
     */
    public static boolean isValidEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    /**
     * Metodo usado para realizar el login, se usa la clase {@link UserPpdateRequest}, y sobre ella
     * se le asigna un listener para el evento de la request.
     *
     * @param email    Email del usuario
     * @param password Contraseña del usuario
     */
    private void sendLoginRequest(String email, String password) {



        UserPpdateRequest login = new UserPpdateRequest(email, password, requestQueue);

        login.sendRequest(new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                /**/
                Log.e("Error login: ", message);

                if (message.contains("403")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Email o password incorrecto.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public boolean onResponse(Object response) {

                //Si la respuesta

                JSONObject jsResponse = (JSONObject) response;
                try {
                    setLoginToken(jsResponse.getString("token"));

                } catch (JSONException e) {
                    Log.e("jsResponse error: ", e.getMessage());
                }

                Toast toast = Toast.makeText(getApplicationContext(), loginToken, Toast.LENGTH_LONG);
                toast.show();

                /**
                 *  encriptamos el password y lo pasamos por si mas adelante el usuario
                 *  quiere cambiarlo en {@link FragmentUserConfig}.
                 */
                encryptedPasswd = encryptUtils.encryptPasswordMd5(password);

                getUserInfo(loginToken);
                return false;
            }
        });
    }

    /**
     * Abre la actividad de la app (la parte principal de trabajo).
     */
    private void openAppActivity(JSONObject jsonObject) {
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("jsonObject", jsonObject.toString());
        intent.putExtra("token", getLoginToken());
        intent.putExtra("encryptedPasswd",encryptedPasswd);
        startActivity(intent);
    }

    private void getUserInfo(String token) {
        UserInfoRequest userInfoRequest = new UserInfoRequest(token, requestQueue,false);

        userInfoRequest.sendRequest(new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                /**/
                Log.e("Error login: ", message);

                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.show();

            }

            @Override
            public boolean onResponse(Object response) {
                JSONObject jsResponse = (JSONObject) response;
                Log.i("Respuesta user info",jsResponse.toString());
                openAppActivity(jsResponse);
                return false;
            }
        });
        //This is for Headers If You Needed
    }

    /**
     * Guarda el token de la request a la DB.
     * @param token
     */
    private void setLoginToken(String token) {
        this.loginToken = token;
    }

    /**
     * Recupera el token del usuario guardado.
     *
     * @return token del usuario.
     */
    private String getLoginToken() {
        return loginToken;
    }
}