using Newtonsoft.Json.Linq;
using System;
using System.CodeDom;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Data.Common;
using System.DirectoryServices;
using System.Dynamic;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Reflection;
using System.Text;
using System.Text.Json;
using System.Text.Json.Nodes;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Xunit.Sdk;
using JsonSerializer = System.Text.Json.JsonSerializer;
using Newtonsoft.Json;
using System.Net.Http.Json;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

namespace ReSeed
{
    internal class Conexion_BD
    {
        //ATRIBUROS DE CONEXION
        private String usuario;
        private String password;
        private String URLlistarTareasTecnicos = "https://reseed-385107.ew.r.appspot.com/results/tareas";

        HttpClient client;
        HttpContent content;
        HttpResponseMessage response;
        Utilidades utilidades;

        /*--------------
         * MÉTODO LOGIN
         * -------------
         * Método que recibe 3 parametros: String usuario, String password, String URL
         * -Declaramos un objeto HTTPClient
         * -Construimos un objeto Json a partir del usuario y password recibido por parámetro.
         * -Convertimos ese objeto json a string.
         * -@content contiene el json.ToString() junto con el Encoder y el tipo.
         * -En @response esperaremos el resultado del envio del json a la base datos.
         * -@res almacena el valor de la respuesta (@respone).
         * -Obtenemos el @token a traves del la respuesta y el json (lo usaremos mas adelante con las acciones
         * que podamos realizar con nuestro usuario logueado.
         * - Obtenemos la lista de usuarios registrados, si el usuario pasado por parametro se ecnuentra en la lista, obtenemos
         * su rol. De esta manera si la respuesta de la BD es true(200):
         * -->si user es ademin--> nos dirigirá al menú principal admin (junto con el token autentificación)
         * --> si el user es tecnicpo--> nos dirigirá al menú principal tecnico.(junto con el token autentificación)
         * 
         */
        #region MÉTODO LOGIN
        public async void login(String usuario, String password, String URL)
        {

            //objeto de la clase HTTPCLIENT
            client = new HttpClient();

            //Definimos los parametros a consultar a la BD y los parseamos en JObject
            JObject json = new JObject();
            json.Add("email", usuario);
            json.Add("password", password);

            //Convertimos el obejot json en String
            String jsonString = json.ToString();

            //creamos el contenido de la petición http a partir de @jsonString
            content = new StringContent(jsonString, Encoding.UTF8, "application/json");

            //En @response almacenamos la respuesta del servidor (si se encuentra es TRUE sino es FALSE)
            response = client.PostAsync(URL, content).Result;

            //Mostraremos por pantalla si la conexión es exitosa o no.
            //Si es exitosa y se loguea un admin, será redirigido a al menu principal admin
            //Si es exitosa y no se loguea el admin. el usuario técnico será redirigido a su menu principal
            //Si no es exitosa, mostraremos mensaje error
            if (response.IsSuccessStatusCode)//si la respuesta es que existe el usuario y la contraseña introducida por el usuario...
            {
                //Almacenamos el TOKEN en la variable String @TOKEN
                var res = response.Content.ReadAsStringAsync().Result;//@res-> lleemos el contenido
                //Convertimos la respuesta BD en objeto JSon obteniendo el valor 'token' de la BD y despues lo convertimos a String,.
                json = JObject.Parse(res);
                json.GetValue("token");
                String token = (String)json["token"];

                //INSTANCIAMOS CLASE UTILIDADES
                utilidades = new Utilidades();

                /*
                Obtenemos el objeto json del usuario logueado con el método @obtenerDatosUserLogueadoJson de
                la clase Utilidades. Despúes creamos un String con el valor el nombre del atributo que cuyo valor
                queremos recuperar. Acto seguido utilizamos el método de la clase Utilidades,@obtenerAtributoPerfil y le
                pasamos por parámetro el objeto json usuario logueado y el string con el atributo deseado.
                De esta forma obtendremos el rol del usuario logueado ADMIN o TECNICO
                */
                String atributoPerfilDeseado = "rol";
                JObject perfilUsuario = utilidades.obtenerDatosUserLogueadoJson(token);
                String rol = utilidades.obtenerAtributoPerfil(perfilUsuario, atributoPerfilDeseado);

                if (rol.Equals("ADMIN"))
                {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    //MessageBox.Show(TOKEN);
                    Administrador form3 = new Administrador(token, usuario);//Enviamos el TOKEN al form3 y el usuario logueado
                    Form1 form1 = new Form1();
                    form3.Show();//mostramos menu principal admin
                    form1.Hide();//ocultamos form login


                }
                else if (rol.Equals("TECNIC"))
                {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    Tecnico form4 = new Tecnico(token);
                    Form1 form1 = new Form1();
                    form4.Show();//mostramos menu principal tecnico
                    form1.Hide();//ocultamos form login

                }
            }

            else//sino...
            {
                MessageBox.Show("Usuario o contraseña incorrectos.", "MENSAJE ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);//Mensaje erro LOGIN
            }

        }

        #endregion

        /*-------------------
         * MÉTODO altaUsuario
         * ------------------
         * Recibirá 3 parámetros: un objeto Usuario, un String token y un Strin URL.
         * Crearemos objeto @json con todos los atributos de usuario.
         * Después de declarar un objeto HttpClient y de validar nuestro Token de inicio de sesión,
         * pasaremos el @json codificado a UTF8 a llamada API End Point.
         * Esperamos la respuesta y si es correcta (true codigo 200), mostraremos mensaje correspondiente. En caso de que 
         * la respuesta sea false,mostraremos el mensaje oportuno.
         */
        #region MÉTODO ALTA USUARIO
        public async void altaUsuario(Usuario usuario, String token, String URL)
        {
            //Creación Usuario en json
            JsonObject json = new JsonObject();
            json.Add("nombre", usuario.Nombre);
            json.Add("apellido", usuario.Apellido);
            json.Add("user", usuario.User);
            json.Add("password", usuario.Password);
            json.Add("email", usuario.Mail);
            json.Add("telefono", usuario.NumeroTelefono);
            json.Add("rol", usuario.Rol);

            //var json = JsonConvert.SerializeObject(usuario).ToLower();
            HttpClient client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            var content = new StringContent(json.ToString(), Encoding.UTF8, "application/json");
            var response = await client.PostAsync(URL, content);

            if (response.IsSuccessStatusCode)
            {
                MessageBox.Show("Usuario registrado correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            else
            {
                MessageBox.Show("Error al dar de alta un usuario.", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

        }
        #endregion

        /*----------------------------------
         * MÉTODO ObtenerUsuarios asíncrono
         * ---------------------------------
         * Este método envía una solicitud a la API a través de una dirección web (End Point)
         * y retorna un array JSon de objetos usuario.
         * Recorremos este array Json y cada usuario lo transformamos en objeto de clase y lo añadimos
         * al List.
         * @return se devuelve la lista de objetos de usuarios
         */
        #region MÉTODO ASÍNCRONO OBTENER USUARIO
        public async Task<List<Post>> ObtenerUsuarios(String token, String URL)
        {
            //Lista dónde almacenaremos los usuarios de respuesta EndPoint
            List<Post> usuariosRegistrados = new List<Post>();

            HttpClient client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            //respuesta de la petición (true or false)
            var respuesta = await client.GetAsync(URL);

            if (respuesta.IsSuccessStatusCode)
            {
                //variable String que recibe el resultado de la petición
                var content = respuesta.Content.ReadAsStringAsync().Result;
                //Transformamos esta respuesta en Array
                JArray listaUsuarios = JArray.Parse(content);
                //Recorremos todo el Array (es bidimensoinal pero solo tiene una fila por eso solo un LOOP FOR)
                for (int i = 0; i < listaUsuarios.First.Count(); i++)
                {
                    String usuarioJson = listaUsuarios[0][i].ToString();//@usuarioJson almacena usuario en posición [0][i]

                    Post usuario = JsonConvert.DeserializeObject<Post>(usuarioJson);//@usuario es el objeto de clase donde se deserializa @usuarioJson

                    usuariosRegistrados.Add(usuario);//@ usuariosRegistrados es el List donde se almacenan @usuario

                }

            }
            else

            {
                MessageBox.Show("Error al conectar con el Servidor. Intentelo más tarde.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }

            return usuariosRegistrados;

        }
        #endregion

        /*-----------------------------
         * MÉTODO modificarUsuarioAsync
         * ----------------------------
         * Recibe 3 parámetros: @URL, @token,@content
         * No retorna nada-
         * Tras utilizar nuestro token enviamos el @content junto con @URL a la Api y esperamos respuesta
         */
        #region MÉTODO ASÍNCRONO MODIFICAR USUARIO
        public async void modificarUsuarioAsync(String URL, String token, String json)
        {
            //declaramos objeto client
            client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            //fromamos el contenido que enviaremos al end point
            var content = new StringContent(json.ToString(), Encoding.UTF8, "application/json");
            //respuesta
            var response = await client.PutAsync(URL, content);

            if (response.IsSuccessStatusCode)
            {
                MessageBox.Show("Usuario modificado correctamente", "MODIFICAR", MessageBoxButtons.OK, MessageBoxIcon.Information);

            }
            else
            {
                MessageBox.Show("Error al intentar modifciar usuario.", "ERROR MODIFICAR", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }

        }
        #endregion

        /*----------------------------
         * MÉTODO eliminarUsuarioAsync
         * ---------------------------
         * Recibirá 3 parámetros (@URL, @token, @idUser)
         * Utilizamos nuestro Token de inicio de sesión
         * Enviamos a la API la URL End Point añadiendo el IdUsuario y esperamos respuesta
         */
        #region MÉTODO ELIMINAR USUARIO
        public async void eliminarUsuarioAsync(String URL, String token, String idUser)
        {

            HttpClient client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            //borramos en la apirest a razón de su endpount con la id correspondiente del usuario
            var response = await client.DeleteAsync(URL + idUser);


        }

        #endregion


        /*-------------------------
        * Método atributosParaPerfil
        * ------------------------
        * -Parametros que recibe:
        * @listaUsuarios
        * @id
        * @nuevoTelefono
        * @nuevoPassword
        * @URL
        * @token
        * 
        * Bucaremos por toda la lista de usuarios al usuario con la id pasada por parámetro.
        * -Si lo encontramos, obtendremos todos sus atributos.
        * 
        * Con condicional if:
        * -Controlamos si se ha modificado o no el teléfono o el password.
        * -En caso que el password NO se modifique, contruiremos su objeto JSon con los parámetros
        * que requiere el envío al ENDPOINT.
        * En este caso no enviaremos la contraseña por problemas que puedan haber con el hash.
        * En cualquier otro caso (telefono modificado o no modificado + modificación password --> Enviaremos el Json
        * incluyendo los datos del password.
        * Cada validación del botón lanzará un menjaje al usuario para informarle de lo realizado.
        * Estos mensajes los sacamos en MessageBox despues de confirmar el resultado del @response         *
        *                     
        */
        #region EDITAR PERFIL

        public void atributosParaPerfil(List<Post> listaUsuarios, String id, String nuevoTelefono,
            String nuevoPassword, String confirmaPassword, String URL, String token)
        {

            JObject userJSon = null;
            String jsonContent = null;
            Usuario user = null;

            Boolean usuarioEncontrado = false;

            HttpClient client = new HttpClient();
            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            for (int i = 0; i < listaUsuarios.Count && !usuarioEncontrado; i++)
            {
                if (listaUsuarios[i].Id.Equals(id))
                {
                    String nombre = listaUsuarios[i].Nombre;
                    String apellido = listaUsuarios[i].Apellido;
                    String usuario = listaUsuarios[i].User;
                    String telefono = listaUsuarios[i].NumeroTelefono;
                    String email = listaUsuarios[i].Email;
                    String password = listaUsuarios[i].Password;
                    String rol = listaUsuarios[i].Rol;

                    if (password.Equals(nuevoPassword))
                    {
                        if (telefono.Equals(nuevoTelefono))
                        {
                            MessageBox.Show("No ha modificado ningún dato de perfil.", "INFORMACION PERFIL",
                                MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }
                        else
                        {
                            user = new Usuario(nombre, apellido, usuario, nuevoTelefono, email, password, rol);
                            userJSon = new JObject();
                            userJSon.Add("nombre", nombre);
                            userJSon.Add("apellido", apellido);
                            userJSon.Add("user", usuario);
                            userJSon.Add("email", email);
                            userJSon.Add("telefono", nuevoTelefono);
                            userJSon.Add("rol", rol);

                            jsonContent = userJSon.ToString();

                            var content = new StringContent(jsonContent, Encoding.UTF8, "application/json");
                            var response = client.PutAsync(URL, content).Result;

                            if (response.IsSuccessStatusCode)
                            {

                                MessageBox.Show("Número de teléfono modificado correctamente.", "INFORMACION PERFIL",
                                   MessageBoxButtons.OK, MessageBoxIcon.Information);
                            }

                        }


                    }
                    else if (!password.Equals(nuevoPassword) && nuevoPassword.Equals(confirmaPassword))
                    {
                        if (!telefono.Equals(nuevoTelefono))
                        {
                            user = new Usuario(nombre, apellido, usuario, nuevoTelefono, email, nuevoPassword, rol);
                            userJSon = new JObject();
                            userJSon.Add("nombre", nombre);
                            userJSon.Add("apellido", apellido);
                            userJSon.Add("user", usuario);
                            userJSon.Add("email", email);
                            userJSon.Add("telefono", nuevoTelefono);
                            userJSon.Add("password", nuevoPassword);
                            userJSon.Add("rol", rol);

                            jsonContent = userJSon.ToString();

                            var content = new StringContent(jsonContent, Encoding.UTF8, "application/json");
                            var response = client.PutAsync(URL, content).Result;

                            if (response.IsSuccessStatusCode)
                            {
                                MessageBox.Show("Número de teléfono y contraseña, modificados correctamente.", "INFORMACION PERFIL",
                               MessageBoxButtons.OK, MessageBoxIcon.Information);
                            }


                        }
                        else
                        {
                            user = new Usuario(nombre, apellido, usuario, telefono, email, nuevoPassword, rol);
                            userJSon = new JObject();
                            userJSon.Add("nombre", nombre);
                            userJSon.Add("apellido", apellido);
                            userJSon.Add("user", usuario);
                            userJSon.Add("email", email);
                            userJSon.Add("telefono", telefono);
                            userJSon.Add("password", nuevoPassword);
                            userJSon.Add("rol", rol);

                            jsonContent = userJSon.ToString();

                            var content = new StringContent(jsonContent, Encoding.UTF8, "application/json");
                            var response = client.PutAsync(URL, content).Result;

                            if (response.IsSuccessStatusCode)
                            {
                                MessageBox.Show("Contraseña modificada correctamente.", "INFORMACION PERFIL",
                               MessageBoxButtons.OK, MessageBoxIcon.Information);
                            }
                        }
                    }
                    else
                    {
                        MessageBox.Show("Las contraseñas no coinciden. Por favor, introduzca nuevamente la contraseña.Gracias",
                            "INFORMACION PERFIL", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    }
                    usuarioEncontrado = true;
                }
            }
        }
        #endregion

        //****************************************************************************************************//
        //******************************         TAREAS       ************************************************//
        /*
         * --------------Método crearTarea-------------
         * Recibe como 7 parámetros:
         * @String URL_tarea
         * @String token
         * @String json
         * @String ubicacionJson
         * @String URL_ubicacion
         * @String URL_Coordenadas
         * @List<Coordenada>coordenadas
         * 
         * 1-Primero obtenedremos el idTarea enviando el @json a @URL_tarea.
         * 2-Despúes pasaremos  @ubicacionJson al END POINT @String URL_ubicacion+idTarea. La respuesta de la base de datos la
         * deserializaremos en nuestro objeto Ubicacion y obtendremos idUbicacion.
         * 3-De cada coordenada de la lista de coordenadas:
         * -Transformaremos lada coordenda (latitud/longitud) en un objeto json que enviaremos al ENDPOINT URL_Coordenadas+idUbicacion.
         * -Esto lo que hará es añadir todos las coordendas de la lista (longitud,latitud) a variable (mapa []) de la base de datos.
         */
        #region MÉTODO CREAR TAREA
        public async void crearTarea(String URL_tarea, String token, String json, String ubicacionJson,
            String URL_ubicacion, String URL_Coordenadas, List<Coordenada> coordenadas, String URL_mensajes, String mensaje,
            String remitente, String destinatario)
        {

            HttpClient client = new HttpClient();
            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            //Creamos el contenido que enviaremos a la URL
            var content = new StringContent(json, Encoding.UTF8, "application/json");
            //Enviamos el contenido
            var response = client.PostAsync(URL_tarea, content).Result;
            //Si la respuesta es true...
            if (response.IsSuccessStatusCode)
            {
                //Mostramos mensaje al usuario
                //MessageBox.Show("Tarea creada correctamente.","INFORMACION TAREAS",MessageBoxButtons.OK,MessageBoxIcon.Information);
                //Almacenamos la respuesta en un String
                String respuesta = await response.Content.ReadAsStringAsync();
                //Convertimos la respuesta JSON en objeto Tarea
                Tarea tareaUser = JsonConvert.DeserializeObject<Tarea>(respuesta);
                //Obtenemos la Id de tarea
                String idTarea = tareaUser.Id;
                //MessageBox.Show("ID TAREA = " + idTarea);--> OBTENCIÓN IDTAREA EXITOSA

                //URL que será el ENDPOINT pasado por parámetro con la idTarea
                String URL_crearUbicacionConId = URL_ubicacion + idTarea;
                //Creamos el content de ubicacion (String ubicacion pasado por parámetro)
                var contentUbicacion = new StringContent(ubicacionJson, Encoding.UTF8, "application/json");
                //Enviamos al contentUbicacion a al ENDPOINT/idTarea
                var responseUbicacion = client.PostAsync(URL_crearUbicacionConId, contentUbicacion).Result;
                //Si la respuesta es true
                if (responseUbicacion.IsSuccessStatusCode)
                {
                    //Mostramos mensaje al usuario
                    //MessageBox.Show("Ubicacion añadida correctamente.", "INFORMACION TAREAS", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    //Mostramos la respuesta
                    String respuestaUbicacion = await responseUbicacion.Content.ReadAsStringAsync();
                    //Convertimos la respuesta en objeto Ubicacion
                    Ubicacion ubicacion = JsonConvert.DeserializeObject<Ubicacion>(respuestaUbicacion);
                    //Obtenmos la id de Ubicacion
                    String idUbicacion = ubicacion.Id;
                    //Añadimos a la URL_Coordenas el idUbicacion
                    String URL_crearCoordenadas = URL_Coordenadas + idUbicacion;
                    //Recorremos la lista de coordenadas 
                    for (int i = 0; i < coordenadas.Count; i++)
                    {
                        Coordenada coordenada = coordenadas[i];

                        JObject jsonCoordendas = new JObject();
                        jsonCoordendas.Add("latitud", coordenada.Latitud);
                        jsonCoordendas.Add("longitud", coordenada.Longitud);

                        var contentCoordendas = new StringContent(jsonCoordendas.ToString(), Encoding.UTF8, "application/json");
                        var responseCoordendas = client.PostAsync(URL_crearCoordenadas, contentCoordendas);

                    }

                }
                //creamos el json para enviar mensajes al usuario con su EN POINT correspondiente
                JObject jsonMensaje = new JObject();
                jsonMensaje.Add("descripcion", mensaje);
                jsonMensaje.Add("tarea", idTarea);
                jsonMensaje.Add("tecnico", destinatario);
                jsonMensaje.Add("admin", remitente);

                var contentMensaje = new StringContent(jsonMensaje.ToString(), Encoding.UTF8, "application/json");
                var responseMensaje = client.PostAsync(URL_mensajes, contentMensaje);

                MessageBox.Show("Tarea registrada correctamente en base de datos.", "INFORMACION CREAR TAREA", MessageBoxButtons.OK,
                    MessageBoxIcon.Information);

            }
            else
            {
                MessageBox.Show("Error al crear tarea.");
            }

        }
        #endregion


        /*--------------
         * @listaTareASYNC
         * -------------
         * Método asyncrono que recibe un String @token y un String @URL.
         * Obtendremos el array de tareas Json. Recooreremos este array y poda objeto json
         * lo deserializaremos en objeto Tarea y lo añadiremos al list de tareas.
         * @return será el listado de objetos Tarea
         */
        #region MÉTODO PARA OBTENER LISTADO TAREAS TECNICOS (COMO ADMINISTRADORES)
        public async Task<List<Tarea>> listaTareASYNC(String token, String URL)
        {
            List<Tarea> listaTareas = new List<Tarea>();

            HttpClient client = new HttpClient();

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            var response = client.GetAsync(URL).Result;

            if (response.IsSuccessStatusCode)
            {

                var resultado = response.Content.ReadAsStringAsync().Result;

                JArray jsonTareas = JArray.Parse(resultado);

                for (int i = 0; i < jsonTareas.First.Count(); i++)
                {

                    String jsonTarea = jsonTareas[0][i].ToString();

                    Tarea tarea = JsonConvert.DeserializeObject<Tarea>(jsonTarea);

                    listaTareas.Add(tarea);

                }
            }

            return listaTareas;

        }
        #endregion


        /*
         * ------------------------
         * Método cargarTareasASYNC
         * ------------------------
         * Este método recorre el listado de tareas.
         * Si en este listado encontramos al usuario pasador por parámetro, obtenemos su tipo de tarea, la descripción
         * tarea y la fecha fin de tarea.
         * Estos datos los mostraremos en el listBox pasado por parámetro.
         */
        #region MÉTODO PARA MOSTRAR TAREAS
        public async void cargarTareasASYNC(String usuario, String token, String URL, ListBox listBoxTareas)
        {

            List<Tarea> listaTareas = await this.listaTareASYNC(token, URL);

            for (int i = 0; i < listaTareas.Count; i++)

            {
                if (usuario.Equals(listaTareas[i].Tecnico))
                {
                    String idTarea = listaTareas[i].Id;
                    String tipoTarea = listaTareas[i].TArea;
                    String descripcion = listaTareas[i].Name;
                    String fecha_finalizacion = listaTareas[i].Fecha_culminacion;

                    listBoxTareas.Items.Add(idTarea + "--" + "TIPO: " + tipoTarea + "--" + "DESCRIPCIÓN : " + descripcion +
                        "--" + "FECHA LÍMITE: " + fecha_finalizacion);

                }

            }

        }
        #endregion


        /*
         * ------------------
         * Método eliminarTareaASYNC
         * -----------------
         * Recibe 3 parámetros:
         * -String @token
         * -String @URLusuario
         * -String @idTarea
         * 
         * El método eliminará la tarea del ENDPOINT pasado @URL(la URL apunta ya el la idTarea seleccionado en la 
         * clase Administrador.
         * El uso del parámetro @idTarea es para recorrer la lista de tareas y si encontramos la idTarea
         * pasada por parámetro.la eliminamos.
         * De esta forma, eliminamos la tarea de la base de datos y de la lista de tareas
         */
        #region MÉTODO PARA ELIMINAR TAREAS
        public async void eliminarTareaASYNC(String token, String URLusuario, String idTarea)
        {

            List <Tarea> listaTareas = await this.listaTareASYNC (token, URLusuario);

            HttpClient client = new HttpClient();

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            var response = await client.DeleteAsync(URLusuario);

            if (response.IsSuccessStatusCode)
            {
                MessageBox.Show("Tarea eliminada de la base de datos correctamente.", "INFORMACIÓN TAREAS", MessageBoxButtons.OK, MessageBoxIcon.Information);

            } else {
                MessageBox.Show("No se ha podido eliminar la tarea de la base de datos", "INFORMACIÓN TAREAS", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            //Borramos la la Tarea con ID pasada por parámetro de la lista de tareas
            foreach (Tarea tarea in listaTareas)
            {
                if (tarea.Id.Equals(idTarea))
                {
                    listaTareas.Remove(tarea);
                }
            }
        }
        #endregion

        //*************************************TECNICOS*******************************************************//

        /*
         * ----------------------------------
         * Método listaTareasTecnicoASYNC
         * ----------------------------------
         * Recibe como parámetros el @String token i  @String URL.
         * Hacemos una petición GET al ENDPOINT y obtenemos un objeto que contiene todos los datos del técnico logueado.
         * Parseamos la respuesta de la BD a objeto JSOn (caso que la respuesta de la BD sea correcta (200-true))
         * Extraemos el JArray de tareas del Objeto JSOn.
         * Recorremos el array json de tareas. Pos cada iteración del array deserializamos en objeto tarea y añadimos
         * dicho objeto a un List <Tarea>.
         * Esta lista de tareas la devolveremos  @return
         */

        #region Método @listaTareasTecnicoASYNC. Usaremos para obtener la lista de tareas del técnico logueado
        public async Task <List<Tarea>> listaTareasTecnicoASYNC (String token,String URL)
        {
            Tarea tarea = null;
            List<Tarea> listaTareasTecnico = new List<Tarea>();
            HttpClient client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            var response = await client.GetAsync(URL);

            if (response.IsSuccessStatusCode)
            {

                var resultado = response.Content.ReadAsStringAsync().Result;

                JObject jsonUser = JObject.Parse(resultado);

                JArray jsonUserTarea = (JArray)jsonUser.GetValue("tarea");

                for (int i = 0; i < jsonUserTarea.Count; i++)
                {
                    tarea = JsonConvert.DeserializeObject<Tarea>(jsonUserTarea[i].ToString());
                    listaTareasTecnico.Add(tarea);
                }

            }

            return listaTareasTecnico;
        }
        #endregion

        public async Task <String> obteneTareaUsuarioID (String token, String URL)
        {
            String respuesta = null;

            HttpClient client = new HttpClient();

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            var response = await client.GetAsync(URL);

            if (response.IsSuccessStatusCode)
            {
                respuesta = response.Content.ReadAsStringAsync().Result;
            }

            return respuesta;
        }

    }
}







