﻿using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Data.Common;
using System.DirectoryServices;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json.Nodes;
using System.Threading.Tasks;

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
                    Form3 form3 = new Form3(token);//Enviamos el TOKEN al form3
                    Form1 form1 = new Form1();
                    form3.Show();//mostramos menu principal admin
                    form1.Hide();//ocultamos form login

                }
                else
                {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    Form4 form4 = new Form4(token);
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
            //String parametros = "{'nombre': '" + usuario.Nombre + "','apellido':'" + usuario.Apellido + "','telefono' :'" + usuario.NumeroTelefono + "'" +
            //",'email':'" + usuario.Mail +"','password' :'" + usuario.Password +"'}"; 
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
            //client.DefaultRequestHeaders.Add("Authorization","Bearer "+token);
            //var contentType = new MediaTypeWithQualityHeaderValue("application/json");
            //client.DefaultRequestHeaders.Accept.Add(contentType);
           //client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
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

        public async void listaUsuarios (String token, String URL)
        {

           
        }



    }

}



    

    
