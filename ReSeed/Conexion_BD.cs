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

namespace ReSeed
{
    internal class Conexion_BD
    {
        //ATRIBUROS DE CONEXION
        private String usuario;
        private String password;
        private String URL;

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
                String rol = utilidades.obtenerAtributoPerfil(perfilUsuario,atributoPerfilDeseado);
                
                if (rol.Equals("ADMIN"))
                {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    //MessageBox.Show(TOKEN);
                    Administrador form3 = new Administrador(token,usuario);//Enviamos el TOKEN al form3 y el usuario logueado
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
        public async void altaUsuario (Usuario usuario, String token, String URL)
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

            var content = new StringContent (json.ToString(),Encoding.UTF8, "application/json");
            var response = await client.PostAsync(URL, content);

            if (response.IsSuccessStatusCode)
            {
                MessageBox.Show("Usuario registrado correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);
            } else
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
        public async Task <List<Post>> ObtenerUsuarios (String token, String URL)
        {
            //Lista dónde almacenaremos los usuarios de respuesta EndPoint
            List <Post> usuariosRegistrados = new List <Post> ();

            HttpClient client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            //respuesta de la petición (true or false)
            var respuesta = await client.GetAsync (URL);

            if (respuesta.IsSuccessStatusCode)
            {
                //variable String que recibe el resultado de la petición
                var content = respuesta.Content.ReadAsStringAsync().Result;
                //Transformamos esta respuesta en Array
                JArray listaUsuarios = JArray.Parse (content);
                //Recorremos todo el Array (es bidimensoinal pero solo tiene una fila por eso solo un LOOP FOR)
                for (int i = 0; i < listaUsuarios.First.Count();i++)
                {
                    String usuarioJson = listaUsuarios[0][i].ToString();//@usuarioJson almacena usuario en posición [0][i]

                    Post usuario = JsonConvert.DeserializeObject<Post>(usuarioJson);//@usuario es el objeto de clase donde se deserializa @usuarioJson

                    usuariosRegistrados.Add(usuario);//@ usuariosRegistrados es el List donde se almacenan @usuario

                }

            } else

            {
                MessageBox.Show("Error al conectar con el Servidor. Intentelo más tarde.","INFORMACIÓN",MessageBoxButtons.OK,MessageBoxIcon.Exclamation);
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
        public async void modificarUsuarioAsync (String URL,String token,String json)
        {
            //declaramos objeto client
            client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            //fromamos el contenido que enviaremos al end point
            var content = new StringContent(json.ToString(),Encoding.UTF8, "application/json");
            //respuesta
            var response = await client.PutAsync(URL,content);

            if (response.IsSuccessStatusCode) {
                MessageBox.Show("Usuario modificado correctamente", "MODIFICAR", MessageBoxButtons.OK, MessageBoxIcon.Information);

            } else
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
        public async void eliminarUsuarioAsync (String URL, String token,String idUser)
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
        * -En caso que el password NO se modifique, contruiremos su objeto JSon con el metodo @JsonUsuario_sinPassword
        * de la clase utilidades. Este objeto no enviará el password a la API para evitar probemas de HASH.
        * En cualquier otro caso (telefono modificado o no modificado + modificación password --> Enviaremos objeto JSon 
        * con todos sus datos con el método @JsonUsuario_conPassword de la clase Utilidades.
        * 
        * Cada validación del botón lanzará un menjaje al usuario para informarle de lo realizado.
        * Estos mensajes los sacamos en MessageBox en retrimiento de usar la respues del @response (true or false)
        *
        *                     
        */
        #region EDITAR PERFIL

        public void atributosParaPerfil(List<Post> listaUsuarios, String id, String nuevoTelefono, String nuevoPassword, String URL, String token)
        {

            Usuario user = null;
            JObject userJSon = null;
            Boolean usuarioEncontrado = false;

            for (int i = 0; i < listaUsuarios.Count && !usuarioEncontrado; i++)
            {
                if (listaUsuarios[i].Id.Equals(id))
                {
                    String nombre = listaUsuarios[i].Nombre;
                    String apellido = listaUsuarios[i].Apellido;
                    String usuario = listaUsuarios[i].User;
                    String password = listaUsuarios[i].Password;
                    String email = listaUsuarios[i].Email;
                    String telefono = listaUsuarios[i].NumeroTelefono;
                    String rol = listaUsuarios[i].Rol;

                    if (!nuevoPassword.Equals(utilidades.obtenerPassword(listaUsuarios, id)))
                    {
                        if (!nuevoTelefono.Equals(utilidades.obtenerTelefono(listaUsuarios, id)))
                        {
                            user = new Usuario(nombre, apellido, usuario, nuevoPassword, email, nuevoTelefono, rol);

                            userJSon = utilidades.JsonUsuario_conPassword(user);
                            MessageBox.Show("Teléfono y contraseña modificados correctamente.", "INFORMACION PERFIL", MessageBoxButtons.OK, MessageBoxIcon.Information);

                        }
                        else
                        {
                            user = new Usuario(nombre, apellido, usuario, nuevoPassword, email, telefono, rol);
                            userJSon = utilidades.JsonUsuario_conPassword(user);
                            MessageBox.Show("Contraseña modificada correctamente.", "INFORMACION PERFIL", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }


                    }
                    else if (nuevoPassword.Equals(utilidades.obtenerPassword(listaUsuarios, id)))
                    {
                        if (nuevoTelefono.Equals(utilidades.obtenerTelefono(listaUsuarios, id)))
                        {
                            MessageBox.Show("No ha hecho ningun cambio en su perfil de usuario.", "INFORMACION PERFIL", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }
                        else
                        {
                            user = new Usuario(nombre, apellido, usuario, password, email, nuevoTelefono, rol);
                            userJSon = utilidades.JsonUsuario_sinPassword(user);
                            MessageBox.Show("Teléfono modificado correctamente.", "INFORMACION PERFIL", MessageBoxButtons.OK, MessageBoxIcon.Information);

                        }
                    }
                }
            }

            usuarioEncontrado = true;

            HttpClient client = new HttpClient();
            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            var content = new StringContent(userJSon.ToString(), Encoding.UTF8, "application/json");
            var response = client.PutAsync(URL, content);

        }

        #endregion


    }

}



    

    

