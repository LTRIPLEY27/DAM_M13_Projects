package com.reseed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.reseed.interfaces.FragmentTaskListInterface;
import com.reseed.interfaces.RecyclerViewInterface;
import com.reseed.objects.UserObj;
import com.reseed.util.JsonReseedUtils;
import com.reseed.util.adapter.TaskAdapter;
import com.reseed.objects.TaskObj;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity implements FragmentTaskListInterface {

	TaskAdapter adapter;
	DrawerLayout drawerLayout;
	UserObj userObj;
	ImageView image_menu_btn;
	TextView textViewUsername, textViewEmail;
	FragmentContainerView fragmentContainerView;
	// NavigationView del menu_lateral lateral.
	NavigationView navigationView;
	Integer bottomMenuSelected;
	String encryptedPasswd;
	private JSONObject userJSONInfo;

	private ArrayList<TaskObj> userTaskObjs;

	private BottomNavigationView bottomNavigationViewTasks;

	private final JsonReseedUtils jsonReseedUtils = new JsonReseedUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

		// Guardamos el password encriptado para posterior uso
		encryptedPasswd = getIntent().getStringExtra("encryptedPasswd");

		//Extraemos UserObj del userJSONInfo.
		extractUser();

		// Menu seleccionado por defecto.

		bottomMenuSelected = 1;

		// Iniciamos el layout de activity_app.
		setContentView(R.layout.activity_app);

		// Buscamos el drawerLayout para poder interactuar con el.
		drawerLayout = findViewById(R.id.drawer_layer);
		fragmentContainerView = findViewById(R.id.fragmentContainerView);


		// TODO hacer la separacion de tipo de usuario.

		if(userObj.getTipoUsuario().equalsIgnoreCase("tecnic")){

			disableAllAdminTaskButtons();

		} else if (userObj.getTipoUsuario().equalsIgnoreCase("admin")) {

		}

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

		bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {

				Log.i("Id bottom menu_lateral", String.valueOf(item.getItemId()));
				Log.i("Id fragment", String.valueOf(item.getTitle()));

				int menuId = item.getItemId();

				if (R.id.menu_users == menuId) {
					usersFragmentCall(item);
					return true;
				} else if (R.id.menu_tasks == menuId) {
					tasksFragmentCall(item);
					return true;
				} else if (R.id.menu_stadistics == menuId) {
					aboutMenuCall(item);
					return true;
				}
				return true;
			}
		});

		setUserDataToNavMenu();
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
			String userToken = getIntent().getStringExtra("token");

			this.userObj = jsonReseedUtils.convertToUserObj(convertJson, userToken);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Extraemos la información del userObj para recoger la información del usuario para mostrarla en el menu_lateral lateral.
	 */
	private void setUserDataToNavMenu() {
		// Buscamos el texto para poner el nombre del usuario.

		navigationView = findViewById(R.id.lateral_Menu);
		View header = navigationView.getHeaderView(0);
		textViewUsername = header.findViewById(R.id.user_name_text);
		textViewEmail = header.findViewById(R.id.user_email_text);
		textViewUsername.setText(String.format("%s %s", userObj.getNombre(), userObj.getApellido()));
		textViewEmail.setText(userObj.getEmail());
	}

	/**
	 * Metodo para abrir el menu_lateral lateral.
	 *
	 * @param drawerLayout el layout donde esta ubicado el menu_lateral.
	 */
	private void abrirMenu(DrawerLayout drawerLayout) {
		drawerLayout.open();
	}


	/**
	 * Metodo llamado cuando se hace clic en el menu_lateral de logout. Y se vuelve al menu_lateral de login.
	 *
	 * @param item recibe el menu_lateral donde se ha hecho clic.
	 */
	public void logoutMenuCall(@Nullable MenuItem item) {

		Intent intent = new Intent(this, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	/**
	 * Metodo llamado cuando se hace clic en el menu_lateral de "Acerca de Reseed".
	 *
	 * @param item
	 */
	public void aboutMenuCall(MenuItem item) {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void configMenuCall(MenuItem item) {
		drawerLayout.closeDrawer(GravityCompat.START);

		FragmentUserConfig fragmentUserConfig = new FragmentUserConfig();

		Bundle bundleArgs = new Bundle();
		bundleArgs.putString("data", userJSONInfo.toString());
		bundleArgs.putString("token", userObj.getTokenUsuario());
		bundleArgs.putString("encryptedPasswd", encryptedPasswd);
		fragmentUserConfig.setArguments(bundleArgs);

		//fragmentContainerView.removeAllViewsInLayout();
		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentUserConfig, null)
				.commit();
	}

	/**
	 * Metodo usado para llamar al fragmento de lista de usuarios.
	 *
	 * @param item el menu_lateral de donde proviene.
	 */
	public void usersFragmentCall(MenuItem item) {
		bottomMenuSelected = 0;

		//fragmentContainerView.removeAllViewsInLayout();
		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, FragmentUsersList.class, null)
				.commit();
	}

	/**
	 * Metodo usado para llamar al fragmento de lista de tareas.
	 *
	 * @param item el menu_lateral de donde proviene.
	 */
	public void tasksFragmentCall(@Nullable MenuItem item) {
		bottomMenuSelected = 1;

		//fragmentContainerView.removeAllViewsInLayout();
		FragmentTaskList fragmentTaskList = new FragmentTaskList();

		Bundle bundleArgs = new Bundle();
		bundleArgs.putString("data", userJSONInfo.toString());
		fragmentTaskList.setArguments(bundleArgs);


		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentTaskList, null)
				.commit();
	}

	public void disableAllAdminTaskButtons(){

	}

	@Override
	public void onEnvioDatos(String data) {
		Log.i("Recycler View Click!!",data);
		FragmentTask fragmentTask = new FragmentTask();
		//fragmentContainerView.removeAllViewsInLayout();
		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentTask, null)
				.commit();
	}
}