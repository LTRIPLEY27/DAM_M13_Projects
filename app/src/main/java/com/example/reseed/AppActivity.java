package com.example.reseed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.reseed.adapter.TaskAdapter;
import com.example.reseed.objects.TaskObj;

import java.util.ArrayList;
import java.util.Calendar;

public class AppActivity extends AppCompatActivity {

    TaskAdapter adapter;

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

        // data to populate the RecyclerView with
        ArrayList<TaskObj> animalNames = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            animalNames.add(new TaskObj(
                    "Tarea " + Integer.toString(i),
                    "Description de la tarea " + Integer.toString(i),
                    1,
                    Calendar.getInstance().getTimeInMillis(),
                    Calendar.getInstance().getTimeInMillis()
            ));
        }

        setContentView(R.layout.activity_app);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}