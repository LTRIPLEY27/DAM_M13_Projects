package com.reseed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hacemos que la pantalla de la aplicacion sea full screeen.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }*/

        setContentView(R.layout.activity_main);

        ConstraintLayout mConstrainLayout = findViewById(R.id.tutorialLayout);

        //Todo realizar el tutorial de uso de la app usando multiples fragments.

        mConstrainLayout.setOnClickListener(view -> {

            Toast.makeText(MainActivity.this, "You Clicked.", Toast.LENGTH_SHORT).show();

            openNewActivity();

        });
    }

    /**
     * Abrimos la Actividad de login.
     */
    public void openNewActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}