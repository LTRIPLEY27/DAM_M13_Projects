﻿using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity.Core.Common.CommandTrees.ExpressionBuilder;
using Xunit.Sdk;
using System.Net.Http.Headers;
using System.Security.Policy;
using System.Net.Http.Json;
using Microsoft.Extensions.Primitives;

namespace ReSeed
{
    internal class Utilidades
    {
        private Conexion_BD conexion = new Conexion_BD();

        #region MÉTODOS-UTILIDADES JSON
        /*
         * MÉTODO QUE TRANSFORMA UN OBJETO USUARIO EN Objeto JSon
         */
        public JObject JsonUsuario(Usuario usuario)
        {
            JObject json = new JObject();
            json.Add("nombre", usuario.Nombre);
            json.Add("apellido", usuario.Apellido);
            json.Add("user", usuario.User);
            json.Add("password", usuario.Password);
            json.Add("email", usuario.Mail);
            json.Add("telefono", usuario.NumeroTelefono);
            json.Add("rol", usuario.Rol);

            return json;

        }

        /*
         * MÉTODO QUE PARA SERIALIZAR UN objeto Usuario y delver el String
         */
        public String serializarUsuario(Usuario usuario)
        {
            String jsonUsuario = JsonConvert.SerializeObject(usuario);
            return jsonUsuario;

        }

        /*
         * MÉTODO QUE TRANSFORMA UN OBJETO USUARIO EN Objeto JSon (sin password)
         * --> Este uso es exlusivo para MODIFICAR USUARIOS.
         * Ello se debe a que la API precisa de un json con unos parámetros específicos
         */
        public JObject JsonUsuario_sinPassword(Usuario usuario)
        {
            JObject json = new JObject();
            json.Add("nombre", usuario.Nombre);
            json.Add("apellido", usuario.Apellido);
            json.Add("user", usuario.User);
            json.Add("email", usuario.Mail);
            json.Add("telefono", usuario.NumeroTelefono);
            json.Add("rol", usuario.Rol);

            return json;
        }

        /*
         * MÉTODO QUE TRANSFORMA UN OBJETO USUARIO EN Objeto JSon (con password)
         * --> Este uso es exlusivo para MODIFICAR USUARIOS
         * Ello se debe a que la API precisa de un json con unos parámetros específicos
         */
        public JObject JsonUsuario_conPassword(Usuario usuario)
        {
            JObject json = new JObject();
            json.Add("nombre", usuario.Nombre);
            json.Add("apellido", usuario.Apellido);
            json.Add("user", usuario.User);
            json.Add("password", usuario.Password);
            json.Add("email", usuario.Mail);
            json.Add("telefono", usuario.NumeroTelefono);
            json.Add("rol", usuario.Rol);

            return json;
        }

        /*
         * MÉTODO QUE PARA SERIALIZAR UN objeto Post y delver el String
         */
        public String serializarUsuario(Post usuario)
        {
            String jsonUsuario = JsonConvert.SerializeObject(usuario);
            return jsonUsuario;

        }

        /*------------------------------------------
        * Método genérico obtenerDatosUserLogueadoJson
        * -----------------------------------------
        * Obtendremos el json del usuario logueado.
        * Pasaremos como parámetro el @token.
        * @return es el objeto json
        */
        public JObject obtenerDatosUserLogueadoJson(String token)
        {
            //Declaramos objeto json a null
            JObject json = null;
            //URL ENDPOINT perfil
            String URL_perfil = "https://reseed-385107.ew.r.appspot.com/perfil";

            //Instanciamos objeto HttpClient
            HttpClient client = new HttpClient();

            //Usamos el token
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            //Enviamos solicitud a la API
            var response = client.GetAsync(URL_perfil).Result;

            if (response.IsSuccessStatusCode)
            {
                var res = response.Content.ReadAsStringAsync().Result;
                json = JObject.Parse(res);
                return json;

            }

            return json;
        }

        /*
         * Método @obtenerAtributoPerfil
         * -----------------------------
         * -Pasamos un parámetro (Objeto usuario logueado json--> @usuarioJson)
         * -Pasamos por parámetro (Strin atributo --> @atributoPerfilDeseado)
         * -Obtendremos de ese objeto usuario json el atributo pasado por parametro en formato String
         * El @return será el atributo de ese usuario logueado que hemos pasado por parámetro
         */
        public String obtenerAtributoPerfil(JObject usuarioJson, String atributoPerfilDeseado)
        {

            return (String)usuarioJson.GetValue(atributoPerfilDeseado);

        }

        #endregion  

        #region MÉTODO QUE RETORNA EL PASSWORD DEL USUARIO PASADO POR PARÁMETRO
        /*
         * Método obtenerPassword
         * -----------------------
         * Recibe 2 parámetros:
         * @usuario
         * @token
         * 
         * Busca en la lista de usuarios al usuario pasado por parámetro y retorna el password.
         * @return password
         */
        public String obtenerPassword(List<Post> listaUsuarios, String id)
        {
            //Variable booleana para salida LOOP
            Boolean semaforo = false;
            //Variable rol que contendrá el rol del usuario pasado por parámetro
            String password = null;

            for (int i = 0; i < listaUsuarios.Count && !semaforo; i++)
            {
                if (id.Equals(listaUsuarios[i].Id))
                {

                    password = listaUsuarios[i].Password;
                    semaforo = true;

                }
            }

            return password;
        }

        /*
        * Método obtenerTelefono
        * -----------------------
        * Recibe 2 parámetros:
        * @usuario
        * @token
        * 
        * Busca en la lista de usuarios al usuario pasado por parámetro y retorna el telefono.
        * @return password
        */
        public String obtenerTelefono(List<Post> listaUsuarios, String id)
        {

            //Variable rol que contendrá el rol del usuario pasado por parámetro
            String telefono = null;

            for (int i = 0; i < listaUsuarios.Count; i++)
            {
                if (id.Equals(listaUsuarios[i].Id))
                {
                    telefono = listaUsuarios[i].NumeroTelefono;
                }
            }

            return telefono;
        }

        #endregion

        #region MÉTODO filtraUsuariosPorRolASYNC
        /*---------------------------------
         * MÉTODO filtraUsuariosPorRolASYNC
         * --------------------------------
         * Muestra los datos del usuario dependiendo de su rol.
         * Pasamos tres parámetros: @URL,@token,@data
         * 
         */
        public async void usuariosFiltoRolASYNC(String URL, String token, DataGridView data)
        {
            JObject json = null;

            HttpClient client = new HttpClient();

            //autentficar token
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            //enviamos peticion
            var response = await client.GetAsync(URL);
            //si la peticion es exitosa
            if (response.IsSuccessStatusCode)
            {
                //leemos resutado
                var res = response.Content.ReadAsStringAsync().Result;
                //tranformamos el restultado en un array json
                JArray jArray = JArray.Parse(res);
                //recorremos arry json
                for (int i = 0; i < jArray.First.Count(); i++)
                {
                    String usuarioJson = jArray[0][i].ToString();//obtenemos el usuario
                    json = JObject.Parse(usuarioJson);//lo transformamos a objeto json
                    String id = json.GetValue("id").ToString();//obtenemos id
                    String user = json.GetValue("user").ToString();//obtenemos user
                    String email = json.GetValue("email").ToString();//obtenemos mail
                    String telefono = json.GetValue("telefono").ToString();//obtenemos telefono

                    data.Rows.Add(id, user, email, telefono);//añadimos los datos al datagrid

                }
            }
        }

        #endregion

        #region RELLENAR COMBOBOX SECCIÓN ADMINISTRACIÓN TAREAS
        /*-------------------------
         * Método @rellenarComboBox
         * ------------------------
         * Recibe por parámetro:
         * -@URL
         * -@token
         * -@listaTecnicos
         * 
         * Solicitamos la lista de técnicos a la API.
         * Si la respuesta es true,obtendremos el @content.
         * Ese @content lo convertimos en JArray @arrayTecnicsJson para poder recorrer los usuarios tecnicos.
         * De cada tecnico nos interesará obtener su id y su user para mostrarlos en combobox.
         * 
         * 
         */
        public async void rellenarComboBox(String URL, String token, ComboBox listaTecnicos)
        {
            List<Post> listaTecnics = null;
            JObject json;

            HttpClient client = new HttpClient();

            //autorización TOKEN    
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            //esperamos la respuesta de la API con el ENDPOINT enviado
            var response = await client.GetAsync(URL);

            //si la respuesta es true...
            if (response.IsSuccessStatusCode)
            {
                //Obtenemos el resultado de la respuesta
                var content = response.Content.ReadAsStringAsync().Result;
                //Convertimos la respuesta en un JArray json
                JArray arrayTecnicsJson = JArray.Parse(content);

                //recorremos LOOP
                for (int i = 0; i < arrayTecnicsJson.First.Count(); i++)
                {
                    String usuarioJson = arrayTecnicsJson[0][i].ToString();
                    json = JObject.Parse(usuarioJson);
                    String id = json.GetValue("id").ToString();
                    String user = json.GetValue("user").ToString();

                    listaTecnicos.Items.Add(id + "-" + user);

                }
            }
        }

        #endregion

        /*
         * @mensajesRemitenteSYNC
         * Obtiene como parámetris un Strin mail, un String token y un String URL
         * -1 Obtenemos la lista de usuarios registrados en la base de datos a través del método @ObtenerUsuarios
         * de la clase Conexion_BD
         * 2-Recorremos la lista de usuarios, si encontramos un usuario con el @mail pasado por parametro, saldremos del bucle
         * con @encontrado (Boolean) y obtendremos su user, el cual devolveremos @return
         */
        public async Task<String> mensajesRemitenteASYNC(String mail, String token, String URL)
        {
            Boolean encontrado = false;
            String remitente = null;
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(token, URL);

            for (int i = 0; i < listaUsuarios.Count && !encontrado; i++)
            {
                if (mail.Equals(listaUsuarios[i].Email))
                {
                    remitente = listaUsuarios[i].User;
                    encontrado = true;
                }
            }
            return remitente;
        }

        /*
      * @mensajesDestinatarioSYNC
      * Obtiene como parámetris un Strin id, un String token y un String URL
      * -1 Obtenemos la lista de usuarios registrados en la base de datos a través del método @ObtenerUsuarios
      * de la clase Conexion_BD
      * 2-Recorremos la lista de usuarios, si encontramos un usuario con el @id pasado por parametro, saldremos del bucle
      * con @encontrado (Boolean) y obtendremos su user, el cual devolveremos @return
      */
        public async Task<String> mensajeDestinatarioASYNC(String id, String token, String URL)
        {
            Boolean encontrado = false;
            String destinatario = null;
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(token, URL);

            for (int i = 0; i < listaUsuarios.Count && !encontrado; i++)
            {
                if (id.Equals(listaUsuarios[i].Id))
                {
                    destinatario = listaUsuarios[i].User;
                    encontrado = true;
                }
            }
            return destinatario;
        }

        /*
         * FILTRAR POR TIPO DE TAREAS-COMBOBOX
         */
        public String filtrarTareas(ComboBox comboTareas, String tipoTarea)
        {
            int index = comboTareas.SelectedIndex;

            switch (index)
            {
                case 0:
                    tipoTarea = "REPLANTAR";
                    break;

                case 1:
                    tipoTarea = "LIMPIEZA";
                    break;

                case 2:
                    tipoTarea = "ANALISIS";
                    break;

                default:
                    break;
            }

            return tipoTarea;
        }

        /*
        * FILTRAR POR ESTADO DE TAREAS-COMBOBOX
        */
        public String filtrarEstado(ComboBox comboTareas, String estadoTarea)
        {

            int index = comboTareas.SelectedIndex;

            switch (index)
            {
                case 0:
                    estadoTarea = "IN_PROGRESS";
                    break;

                case 1:
                    estadoTarea = "NEW";
                    break;

                case 2:
                    estadoTarea = "DONE";
                    break;

                case 3:
                    estadoTarea = "TO_DO";
                    break;

                case 4:
                    estadoTarea = "ON_HOLD";
                    break;

                efault:
                    break;
            }

            return estadoTarea;
        }

        /*
         * Metodo filtrarTareaPorTipoASYNC
         * Recibe por parámetro un String tipoTarea, String token, String URL, ListBox listadoTareas
         * Mostramos en el listado de tareas los datos que nos interesan
         */
        public async void filtrarTareaPorTipoASYNC(String tipoTarea, String token, String URL, ListBox listadoTareas)
        {
            List<Tarea> listaTareas = await conexion.listaTareASYNC(token, URL);

            foreach (Tarea tarea in listaTareas)
            {
                String descripcion = tarea.Name;
                String fechaFin = tarea.Fecha_culminacion;

                listadoTareas.Items.Add("DESCRIPCIÓN : " + descripcion +
                        "--" + "FECHA LÍMITE: " + fechaFin);
            }
        }

        /*
         * Método ObtenerCoordenadasTecnico
         * Recibe por parámetro una lista de tareas y una id tarea.
         * Recorre la lista de tareas y devolvemos el array de coordenadas de esa id tarea pasada por parametro
         */
        public Coordenada[] ObtenerCoordenadasTecnico (List<Tarea>listaTareas,String idTarea)
        {
            Boolean encontradaIdTarea = false;

            Coordenada [] mapa = null; 
            //recorremos LOOP y en funcion de la posision, mostraremos las coordenadas
            for (int i = 0; i < listaTareas.Count && !encontradaIdTarea; i++)
            {
                if (idTarea.Equals(listaTareas[i].Id))
                {
                    if (listaTareas[i].Ubicacion.Mapa != null)

                    mapa = listaTareas[i].Ubicacion.Mapa;

                    encontradaIdTarea = true;
                    
                } 
            }

            return mapa;
        }

        /*
         * Método datosPerfilTecnicoASYNC
         * Recibe como parámetro el @token y la @URL END POINT
         * Devolvemos el json del perfil
         */
        public async Task<String> datosPerfilTecnicoASYNC(String token, String URL)
        {
            String userPerfil = null;

            HttpClient client = new HttpClient();

            //necesitamos token
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            var response = await client.GetAsync(URL);

            if (response.IsSuccessStatusCode)
            {
                var jsonRespuesta = response.Content.ReadAsStringAsync().Result;

                userPerfil = jsonRespuesta.ToString();



            }

            return userPerfil;
        }

        /*
         * Método atributosCambiadosPerfiltecnicoASYNC
         * Parámetros que recibe: @String URL_GETPERFIL,@String URL_UPDATE ,@String token, @String nuevoTelefono
         * Devolvemos el objeto usuario para poder gestionar si hay modificacion de contraseñas.
         * El mismo método verifica si se modifica el telefono y guarda los cambios
         */
        public async Task <Post> atributosCambiadosPerfiltecnicoASYNC(String URL_GETPERFIL,String URL_UPDATE ,String token, String nuevoTelefono)
        {

            Post userUpdate = null;

            HttpClient client = new HttpClient();
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            String usuarioLogueado = await this.datosPerfilTecnicoASYNC(token,URL_GETPERFIL);

            JObject usuario = JObject.Parse(usuarioLogueado);
            String nombre = usuario.GetValue("nombre").ToString();
            String apellido = usuario.GetValue("apellido").ToString();
            String user = usuario.GetValue("user").ToString();
            String email = usuario.GetValue("email").ToString();
            String telefono = usuario.GetValue("telefono").ToString();
            String rol = usuario.GetValue("rol").ToString();
            

            if (telefono.Equals(nuevoTelefono))
            {
                
                JObject usuarioUpdate = new JObject();
                usuarioUpdate.Add("nombre", nombre);
                usuarioUpdate.Add("apellido", apellido);
                usuarioUpdate.Add("user", user);
                usuarioUpdate.Add("email", email);
                usuarioUpdate.Add("telefono", telefono);
                usuarioUpdate.Add("rol", rol);

                var content = new StringContent(usuarioUpdate.ToString(), Encoding.UTF8, "application/json");
                var response = client.PutAsync(URL_UPDATE, content).Result;

                if (response.IsSuccessStatusCode)
                {
                    var resultado = response.Content.ReadAsStringAsync().Result;

                    userUpdate = JsonConvert.DeserializeObject<Post>(resultado);

                }

            } else
            {
                JObject usuarioUpdate = new JObject();
                usuarioUpdate.Add("nombre", nombre);
                usuarioUpdate.Add("apellido", apellido);
                usuarioUpdate.Add("user", user);
                usuarioUpdate.Add("email", email);
                usuarioUpdate.Add("telefono", nuevoTelefono);
                usuarioUpdate.Add("rol", rol);

                var content = new StringContent(usuarioUpdate.ToString(),Encoding.UTF8,"application/json");
                var response = client.PutAsync(URL_UPDATE, content).Result;

                MessageBox.Show("Teléfono modificado correctamente.", "INFORMACION PERFIL",
                   MessageBoxButtons.OK, MessageBoxIcon.Information);

                if (response.IsSuccessStatusCode)
                {
                    var resultado = response.Content.ReadAsStringAsync().Result;

                    userUpdate = JsonConvert.DeserializeObject<Post>(resultado);

                }


            }

            return userUpdate;

        }
    }
}