# Reseed

## Mobile app

Documentación de la aplicacion mobile.

### Activities

---

#### MainActivity.java

Clase inicial, a partir de esta clase se inicia la app.

Desde este punto después de un tutorial se llama al apartado de login.

#### LoginActivity.java

En esta clase se controla el login del usuario.

#### AppActivity.java

Clase principal de la aplicación, gestiona las tareas del usuario, junto con su informacion.

### SubClasses

---

#### ModelLoginAuth.java

##### _public_ ModelAuth(String email, String password, Context context)

Constructor del ModelLoginAuth.

* **email**: correo electronico en formaro de String xxx@xxx.xxx
* **password**: contraseña del usuario en forma de String, se aceptam todos los caracteres, pero no puede ser _null_.
* **context**: context de la aplicación.

##### _public_ void sendRequest(final VolleyResponseListener listener)

Envio del _request_ al servidor en formato post para realizar el login de la app, se utilizan los datos del constructor.

* **listener**: listener que se va a usar para recuperar el request a la activity base.

##### _public_ String getEmail()

Recupera el correo electronico en formato String.

##### _public_ void setEmail(String email)

Guarda el correo electronico.

* **email**: correo electronico en formaro de String.

##### _public_ String getPassword()

<!-- (mala practica)? -->
Recupera el correo password en formato String.

##### _public_ void setPassword(String password)

Guarda el password para usar en el request.

* **password**: contraseña del usuario en forma de String.

##### _public_ Context getContext()

Recupera el context donde esta cargada la clase para ser usado en la requestQueue.

##### _public_ void setContext(Context context)

Guarda el context para ser usado en la requestQueue.

* **context**: guarda el context.

