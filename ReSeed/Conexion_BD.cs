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

        public async void login(String usuario, String password, String URL)
        {
            //objeto de la clase HTTPCLIENT
            client = new HttpClient();

            //Definimos los parametros a consultar a la BD y los parseamos en JObject
            String parametrosJson = "{'email':" + usuario + ",'password':" + password + "}";
            dynamic jsonString = JObject.Parse(parametrosJson);

            //creamos el contenido de la petición http a partir de @jsonString
            content = new StringContent(jsonString.ToString(), Encoding.UTF8, "application/json");
            //En @response almacenamos la respuesta del servidor (si se encuentra es TRUE sino es FALSE)
            response = client.PostAsync(URL, content).Result;
            //Almacenamos el TOKEN en la variable String @TOKEN
            var res = response.Content.ReadAsStringAsync().Result;//@res-> lleemos el contenido
            //String TOKEN = res.ToString();//@TOKEN_almacena TOKEN inicio sesion usuario;
            //Convertimos la respuesta BD en objeto JSon obteniendo el valor 'token' de la BD y despues lo convertimos a String,.
            JObject json = JObject.Parse(res);
            json.GetValue("token");
            String token = (String)json["token"];


            //Mostraremos por pantalla si la conexión es exitosa o no.
            //Si es exitosa y se loguea el admin, será redirigido a al menu principal admin
            //Si es exitosa y no se loguea el admin. el usuario técnico será redirigido a su menu principal
            //Si no es exitosa, mostraremos mensaje error
            if (response.IsSuccessStatusCode)//si la respuesta es que existe el usuario y la contraseña introducida por el usuario...
            {
                if (usuario.Equals("'eduard@fantasymail.com'"))
                {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    //MessageBox.Show(TOKEN);
                    Administrador form3 = new Administrador(token);//Enviamos el TOKEN al form3
                    Form1 form1 = new Form1();
                    form3.Show();//mostramos menu principal admin
                    form1.Hide();//ocultamos form login

                }
                else
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

        /*
         * Método ObtenerUsuarios asíncrono
         * ---------------------------------
         * Este método envía una solicitud a la API a través de una dirección web (End Point)
         * y retorna un array JSon de objetos usuario.
         * Recorremos este array Json y cada usuario lo transformamos en objeto de clase y lo añadimos
         * al List.
         * 
         */
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

        public async void modificarUsuarioAsync (Usuario usuario, String token, String URL)
        {

           
        }

        public async void eliminarUsuarioAsync (String token, String URL,Usuario usuario)
        {
           
            HttpClient client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            //borramos en la apirest a razón de su endpount con la id correspondiente del usuario
            var response = await client.DeleteAsync(URL+usuario.User);

            if (response.IsSuccessStatusCode)
            {
                MessageBox.Show("Usuario borrado correctamente.","INFORMACIÓN",MessageBoxButtons.OK, MessageBoxIcon.Information);
            } else
            {
                MessageBox.Show("El usuario no se ha podido borrar.", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
          
        }



    }

}



    

    

