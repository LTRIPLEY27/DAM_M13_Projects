package com.reseed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    ArrayList<TaskObj> userTaskObjs;

    private JsonReseedUtils jsonReseedUtils = new JsonReseedUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        /**
         * Creamos el objeto que lista las tareas, se usará para llenar el TaskAdapter.
         */
        ArrayList<TaskObj> listaTareas = new ArrayList<>();

        //Extract UserObj from userJSONInfo.
        extractUser();

        // Iniciamos el layout de activity_app.
        setContentView(R.layout.activity_app);

        // Buscamos el drawerLayout para poder interactuar con el.
        drawerLayout = findViewById(R.id.drawer_layer);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);


        // Buscamos el texto para poner el nombre del usuario.
        navigationView = findViewById(R.id.lateral_Menu);
        View header = navigationView.getHeaderView(0);
        textViewUsername = header.findViewById(R.id.user_name_text);
        textViewEmail = header.findViewById(R.id.user_email_text);
        textViewUsername.setText(String.format("%s %s", userObj.getNombre(), userObj.getApellido()));
        textViewEmail.setText(userObj.getEmail());

        tasksFragmentCall(null);

    }



    /**
     * Extraemos el user del json.
     */
    private void extractUser() {
        // cogemos la info del usuario del LoginActivity.
        // recuperar la info del intent para usar la clase JsonReseedUtils para convertir a userobj.

        JSONObject convertJson;
        try {
            convertJson = new JSONObject(getIntent().getStringExtra("jsonObject"));
            this.userJSONInfo = convertJson;
            userToken = getIntent().getStringExtra("token");

            this.userObj = jsonReseedUtils.convertToUserObj(convertJson, userToken);
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
                .add(R.id.fragmentContainerView, FragmentUsersList.class, null)
                .commit();
    }
    /**
     * Metodo usado para llamar al fragmento de lista de tareas.
     * @param item el menu de donde proviene.
     */
    public void tasksFragmentCall( @Nullable MenuItem item) {
        fragmentContainerView.removeAllViewsInLayout();
        FragmentTaskList fragmentTaskList = new FragmentTaskList();

        Bundle bundleArgs = new Bundle();
        bundleArgs.putString("data", userJSONInfo.toString());
        fragmentTaskList.setArguments(bundleArgs);


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, fragmentTaskList, null)
                .commit();
    }
}