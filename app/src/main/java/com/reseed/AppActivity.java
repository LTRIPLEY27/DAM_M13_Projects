package com.reseed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.reseed.objects.UserObj;
import com.reseed.util.JsonReseedUtils;
import com.reseed.util.adapter.TaskAdapter;
import com.reseed.objects.TaskObj;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity {

    TaskAdapter adapter;
    DrawerLayout drawerLayout;
    UserObj userObj;
    ImageView image_menu_btn;
    TextView textViewUsername, textViewEmail;

    FragmentContainerView fragmentContainerView;

    // NavigationView del menu lateral.
    NavigationView navigationView;
    //LinearLayout del header del menu.

    private String loginUserInfo;
    private JSONObject userJSONInfo;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        /**
         * Creamos el objeto que lista las tareas, se usará para llenar el TaskAdapter.
         */
        ArrayList<TaskObj> listaTareas = new ArrayList<>();

        /*for (int i = 0; i < 10; i++) {
            listaTareas.add(new TaskObj(
                    "Tarea " + i,
                    "Description de la tarea " + i,
                    "Analisis",
                    Calendar.getInstance().getTimeInMillis(),
                    Calendar.getInstance().getTimeInMillis()
            ));
        }*/

        // Iniciamos el layout de activity_app.
        setContentView(R.layout.activity_app);

        // Buscamos el drawerLayout para poder interactuar con el.
        drawerLayout = findViewById(R.id.drawer_layer);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);

        extractUser();


        // Buscamos el texto para poner el nombre del usuario.
        navigationView = findViewById(R.id.lateral_Menu);
        View header = navigationView.getHeaderView(0);
        textViewUsername = header.findViewById(R.id.user_name_text);
        textViewEmail = header.findViewById(R.id.user_email_text);
        textViewUsername.setText(userObj.getNombre() + " " + userObj.getApellido());
        textViewEmail.setText(userObj.getEmail());


        //TODO que se vean las tareas.
        // Ponemos en marcha el recyclerViewTask.
        /*Fragment fragmentTasks = findViewById(R.id.fragmentTasks);
        RecyclerView recyclerViewTask = findViewById(R.id.recyclerTask);
        recyclerViewTask.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(listaTareas);
        adapter.setClickListener(this);
        recyclerViewTask.setAdapter(adapter);*/

        //Cargamos la informacion del nombre de usuario.

    }

    /**
     * Extraemos el user del json.
     */
    private void extractUser(){
        // cogemos la info del usuario del LoginActivity.
        //TODO recuperar la info del intent para usar la clase JsonReseedUtils para convertir a userobj y a TaskObj  OK!!

        JSONObject convertJson;
        try {
            convertJson = new JSONObject(getIntent().getStringExtra("jsonObject"));
            this.userJSONInfo = convertJson;
            userToken = getIntent().getStringExtra("token");

            JsonReseedUtils jsonReseedUtils = new JsonReseedUtils();
            this.userObj = jsonReseedUtils.convertToUserObj(convertJson,userToken);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Metodo para abrir el menu lateral.
     *
     * @param drawerLayout el layout donde esta ubicado el menu.
     */
    private void abrirMenu(DrawerLayout drawerLayout) {
        drawerLayout.open();
    }


    /**
     * Metodo llamado cuando se hace clic en el menu de logout. Y se vuelve al menu de login.
     *
     * @param item recibe el menu donde se ha hecho clic.
     */
    public void logoutMenuCall(MenuItem item) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo llamado cuando se hace clic en el menu de "Acerca de Reseed".
     *
     * @param item
     */
    public void aboutMenuCall(MenuItem item) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo usado para llamar al fragmento de lista de usuarios.
     * @param item el menu de donde proviene.
     */
    public void usersFragmentCall(MenuItem item) {

        fragmentContainerView.removeAllViewsInLayout();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, fragment_users_list.class, null)
                .commit();


    }
    /**
     * Metodo usado para llamar al fragmento de lista de tareas.
     * @param item el menu de donde proviene.
     */
    public void tasksFragmentCall(MenuItem item) {
        fragmentContainerView.removeAllViewsInLayout();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, fragment_task_list.class, null)
                .commit();
    }
}