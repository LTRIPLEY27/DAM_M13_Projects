package com.reseed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.reseed.fragments.FragmentTaskCreationOne;
import com.reseed.fragments.FragmentTaskCreationTwo;
import com.reseed.fragments.FragmentTaskList;
import com.reseed.fragments.FragmentTaskUpdateOne;
import com.reseed.fragments.FragmentTaskUpdateTwo;
import com.reseed.fragments.FragmentUserConfig;
import com.reseed.fragments.FragmentUsersList;
import com.reseed.fragments.FragmentTask;
import com.reseed.interfaces.FragmentCreateTaskInterface;
import com.reseed.interfaces.FragmentTaskListInterface;
import com.reseed.objects.UserObj;
import com.reseed.util.JsonReseedUtils;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class AppActivity extends AppCompatActivity implements FragmentTaskListInterface, FragmentCreateTaskInterface {

	DrawerLayout drawerLayout;
	UserObj userObj;
	TextView textViewUsername, textViewEmail;
	FragmentContainerView fragmentContainerView;
	// NavigationView del menu_lateral lateral.
	NavigationView navigationView;

	// int bottomMenuSelected que controla que menu esta seleccionado, 1- user menu , 2 - tasks menu, 3 - stadistics
	// int floatingMenuSelection controla el menu flotante seleccionado, 0- nada , 1- eliminar, 2- modificar, 3- crear
	int bottomMenuSelected, floatingMenuSelection;
	Boolean popUpMenuVisible;

	FragmentManager fragmentManager;
	String encryptedPasswd, tokenUsuario;
	private JSONObject userJSONInfo;
	private BottomNavigationView bottomNavigationView;
	private FloatingActionButton floatingMenuButton,floatingCreateButton, floatingModifyButton, floatingDeleteButton;
	private final JsonReseedUtils jsonReseedUtils = new JsonReseedUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

		// menu flotante en posicion neutra.
		floatingMenuSelection = 0;

		// Guardamos el password encriptado para posterior uso
		encryptedPasswd = getIntent().getStringExtra("encryptedPasswd");

		//Extraemos UserObj del userJSONInfo.
		extractUser();

		// Creamos el fragment manager
		fragmentManager = getSupportFragmentManager();

		// Iniciamos el layout de activity_app.
		setContentView(R.layout.activity_app);

		// Buscamos el drawerLayout para poder interactuar con el.
		drawerLayout = findViewById(R.id.drawer_layer);
		fragmentContainerView = findViewById(R.id.fragmentContainerView);

		// Navegacion inferior
		this.bottomNavigationView = findViewById(R.id.bottomNavigationView);

		// Botones flotantes para el menu admin de edicion.
		this.floatingMenuButton = findViewById(R.id.floatingMenuButton);
		this.floatingCreateButton = findViewById(R.id.floatingCreateButton);
		this.floatingModifyButton = findViewById(R.id.floatingModifButton);
		this.floatingDeleteButton = findViewById(R.id.floatingDeleteButton);


		// TODO hacer la separacion de tipo de usuario.

		if(userObj.getTipoUsuario().equalsIgnoreCase("tecnic")){

			disableAllAdminTaskButtons(true);

		} else if (userObj.getTipoUsuario().equalsIgnoreCase("admin")) {
			disableAllAdminTaskButtons(false);
		}

		// Listener de la navegación inferior.
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

		floatingMenuButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				Log.i("Boton flotante","click!");
				popUpEditMenu();


			}
		});

		floatingCreateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// activamos la seleccion de creacion
				floatingMenuSelection = 3;
				popUpEditMenu();

				if(bottomMenuSelected == 2){
					tasksCreateFragmentCall();
				}

			}
		});

		floatingModifyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// activamos la seleccion de motificacion
				floatingMenuSelection = 2;
				popUpEditMenu();
			}
		});


		floatingDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// activamos la seleccion de eliminacion
				floatingMenuSelection = 1;

				popUpEditMenu();

			}
		});



		setUserDataToNavMenu();
		tasksFragmentCall(null);
	}


	/**
	 * Metodo que controla la visivilidad del menu de edición del admin.
	 */
	private void popUpEditMenu() {
		if(popUpMenuVisible){
			floatingDeleteButton.setVisibility(View.GONE);
			floatingModifyButton.setVisibility(View.GONE);
			floatingCreateButton.setVisibility(View.GONE);
			popUpMenuVisible = false;
		}else{
			floatingDeleteButton.show();
			floatingModifyButton.show();
			floatingCreateButton.show();

			floatingDeleteButton.setVisibility(View.VISIBLE);
			floatingModifyButton.setVisibility(View.VISIBLE);
			floatingCreateButton.setVisibility(View.VISIBLE);

			popUpMenuVisible = true;
		}
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
			this.tokenUsuario = getIntent().getStringExtra("token");

			this.userObj = jsonReseedUtils.convertToUserObj(convertJson);
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
	 * @param item menu de donde proviene el click
	 */
	public void aboutMenuCall(MenuItem item) {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void configMenuCall(MenuItem item) {

		// todo disable pop up menu

		drawerLayout.closeDrawer(GravityCompat.START);

		FragmentUserConfig fragmentUserConfig = new FragmentUserConfig();

		Bundle bundleArgs = new Bundle();
		bundleArgs.putString("data", userJSONInfo.toString());
		bundleArgs.putString("token", tokenUsuario);
		bundleArgs.putString("encryptedPasswd", encryptedPasswd);
		fragmentUserConfig.setArguments(bundleArgs);

		//fragmentContainerView.removeAllViewsInLayout();
		fragmentManager.beginTransaction()
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

		// todo disable pop up menu

		bottomMenuSelected = 1;

		FragmentUsersList fragmentUsersList = new FragmentUsersList();

		Bundle bundleArgs = new Bundle();
		//bundleArgs.putString("data", userJSONInfo.toString());
		bundleArgs.putString("token", tokenUsuario);
		fragmentUsersList.setArguments(bundleArgs);

		//fragmentContainerView.removeAllViewsInLayout();
		fragmentManager.beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentUsersList, null)
				.commit();
	}

	/**
	 * Metodo usado para llamar al fragmento de lista de tareas.
	 *
	 * @param item el menu_lateral de donde proviene.
	 */
	public void tasksFragmentCall(@Nullable MenuItem item) {


		// ponemos la seleccion en neutro.
		floatingMenuSelection = 0;

		bottomMenuSelected = 2;


		//fragmentContainerView.removeAllViewsInLayout();
		FragmentTaskList fragmentTaskList = new FragmentTaskList();

		Bundle bundleArgs = new Bundle();
		//bundleArgs.putString("data", userJSONInfo.toString());
		bundleArgs.putString("token", tokenUsuario);
		bundleArgs.putString("typeUser", userObj.getTipoUsuario());

		fragmentTaskList.setArguments(bundleArgs);


		fragmentManager.beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentTaskList, null)
				.commit();
	}

	/**
	 * Metodo usado para llamar al fragmento de lista de usuarios.
	 *
	 * @param item el menu_lateral de donde proviene.
	 */
	public void stadisticsFragmentCall(MenuItem item) {

		bottomMenuSelected = 3;

		/*FragmentUsersList fragmentUsersList = new FragmentUsersList();

		Bundle bundleArgs = new Bundle();
		//bundleArgs.putString("data", userJSONInfo.toString());
		bundleArgs.putString("token", tokenUsuario);
		fragmentUsersList.setArguments(bundleArgs);

		//fragmentContainerView.removeAllViewsInLayout();
		fragmentManager.beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentUsersList, null)
				.commit();*/
	}

	/**
	 * Metodo usado para llamar al fragmento de creación de tareas.
	 *
	 */
	public void tasksCreateFragmentCall() {

		// activamos la seleccion neutra
		floatingMenuSelection = 0;

		// todo disable pop up menu

		bottomMenuSelected = 0;

		//fragmentContainerView.removeAllViewsInLayout();
		FragmentTaskCreationOne fragmentTaskCreate = new FragmentTaskCreationOne();

		Bundle bundleArgs = new Bundle();
		//bundleArgs.putString("data", userJSONInfo.toString());
		bundleArgs.putString("token", tokenUsuario);
		bundleArgs.putString("typeUser", userObj.getTipoUsuario());

		fragmentTaskCreate.setArguments(bundleArgs);

		fragmentManager.beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentTaskCreate, null)
				.commit();
	}

	public void disableAllAdminTaskButtons(Boolean option){

		if (option){
			bottomNavigationView.getMenu().findItem(R.id.menu_users).setChecked(false);
			bottomNavigationView.getMenu().findItem(R.id.menu_users).setEnabled(false);

			bottomNavigationView.getMenu().findItem(R.id.menu_stadistics).setChecked(false);
			bottomNavigationView.getMenu().findItem(R.id.menu_stadistics).setEnabled(false);

			bottomNavigationView.setSelectedItemId(R.id.menu_tasks);
			floatingMenuButton.setVisibility(View.GONE);
		}else{
			bottomNavigationView.getMenu().findItem(R.id.menu_users).setChecked(true);
			bottomNavigationView.getMenu().findItem(R.id.menu_users).setEnabled(true);

			bottomNavigationView.getMenu().findItem(R.id.menu_stadistics).setChecked(true);
			bottomNavigationView.getMenu().findItem(R.id.menu_stadistics).setEnabled(true);

			// Visivilidad del menu de edicion.
			floatingMenuButton.setVisibility(View.VISIBLE);
			popUpMenuVisible = false;

			bottomNavigationView.setSelectedItemId(R.id.menu_tasks);
		}


	}

	/**
	 * Metodo llamado desde el {@link FragmentTaskList} ya que esta implementado en {@link FragmentTaskListInterface},
	 * se llama cuando se hace un clic en el item del {@link androidx.recyclerview.widget.RecyclerView}
	 * @param jsonData datos del item en formato String, pero convertible a json, conversión realizada
	 * en {@link JsonReseedUtils}.
	 */
	@Override
	public void onEnvioDatosTarea(String jsonData) {
		Log.i("Recycler View Click!!",jsonData);
		Log.i("FloatingMenuSelection!!",String.valueOf(floatingMenuSelection));



		if(floatingMenuSelection == 0){

			// si el floatingMenuSelection es 0 lanza el visor de la tarea.

			FragmentTask taskFragment = new FragmentTask();

			Bundle bundleArgs = new Bundle();

			// Datos del item clicado, en este caso json pasado a String.
			bundleArgs.putString("data", jsonData);
			bundleArgs.putString("tipoUsuario", userObj.getTipoUsuario());
			bundleArgs.putString("nombreUsuario", userObj.getUser());
			bundleArgs.putString("token", tokenUsuario);

			taskFragment.setArguments(bundleArgs);

			fragmentManager.beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.fragmentContainerView, taskFragment, null)
					.commit();
		} else if (floatingMenuSelection == 1) {
			// si el floatingMenuSelection es 1 lanza eliminar la tarea.

			AlertDialog.Builder builder1 = new AlertDialog.Builder(AppActivity.this);
			builder1.setMessage("Queres eliminar la tarea?");
			builder1.setCancelable(true);

			builder1.setPositiveButton(
					"Aceptar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			builder1.setNegativeButton(
					"Cancelar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alert11 = builder1.create();
			alert11.show();





		} else if (floatingMenuSelection == 2) {

			// si el floatingMenuSelection es 2 lanza el editor de la tarea.

			Log.i("Recycler View Click!!",jsonData);

			FragmentTaskUpdateOne taskUpdateFragment = new FragmentTaskUpdateOne();

			Bundle bundleArgs = new Bundle();

			// Datos del item clicado, en este caso json pasado a String.
			bundleArgs.putString("data", jsonData);
			bundleArgs.putString("tipoUsuario", userObj.getTipoUsuario());
			bundleArgs.putString("nombreUsuario", userObj.getUser());
			bundleArgs.putString("token", tokenUsuario);

			taskUpdateFragment.setArguments(bundleArgs);

			fragmentManager.beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.fragmentContainerView, taskUpdateFragment, null)
					.commit();

		}

	}

	@Override
	public void onEnvioCrearTarea(String data, int idUsuario) {

		Log.i("Creacion tarea next!!",data);

		FragmentTaskCreationTwo fragmentTaskCreationTwo = new FragmentTaskCreationTwo();

		Bundle bundleArgs = new Bundle();

		// Datos del item clicado, en este caso json pasado a String.
		bundleArgs.putString("data", data);
		bundleArgs.putString("tipoUsuario", userObj.getTipoUsuario());
		bundleArgs.putString("nombreUsuario", userObj.getUser());
		bundleArgs.putString("token", tokenUsuario);
		bundleArgs.putInt("idUsuario",idUsuario);

		fragmentTaskCreationTwo.setArguments(bundleArgs);

		fragmentManager.beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentTaskCreationTwo, null)
				.commit();

	}

	public void llamadaFragmentUpdateTwo(String data, int idUsuario, String oldData){
		Log.i("Creacion tarea next!!",data);

		FragmentTaskUpdateTwo fragmentTaskUpdateTwo = new FragmentTaskUpdateTwo();

		Bundle bundleArgs = new Bundle();

		// Datos del item clicado, en este caso json pasado a String.
		bundleArgs.putString("data", data);
		bundleArgs.putString("oldData", oldData);
		bundleArgs.putString("tipoUsuario", userObj.getTipoUsuario());
		bundleArgs.putString("nombreUsuario", userObj.getUser());
		bundleArgs.putString("token", tokenUsuario);
		bundleArgs.putInt("idUsuario",idUsuario);

		fragmentTaskUpdateTwo.setArguments(bundleArgs);

		fragmentManager.beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragmentContainerView, fragmentTaskUpdateTwo, null)
				.commit();
	}
	public void deleteTask(){

	}
}