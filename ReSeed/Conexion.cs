using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Security.Cryptography.X509Certificates;


namespace ReSeed
{

    internal class Conexion
    {
        private HttpClient client;
        private Usuario usuario;

        private String URL = "http://localhost:8080/admin/tareas";
        private static Form1 form_login = new Form1();
        private static Form3 form_admin = new Form3();


        public Conexion (HttpClient cliente, Usuario usuario)
        {
            this.client = cliente;
            this.usuario = usuario;
        }

        public HttpClient Client
        {
            get { return client; }
            set { client = value; }
        }

        public Usuario Usuario
        {
            get { return usuario; }
            set { usuario = value; }
        }

        public async void ConexionBD (HttpClient client, String usuario, String password)
        {
            //Instanciamos obljeto HttpCliente
            client = new HttpClient ();
            //Sentencia para buscar en archivo JSON un usuario y contraseña pasados por parámetro. 
            //Se almacenará en @var content
            var content = new StringContent("{\"usuario\":" + usuario + ",\"password\":" + password + "}", System.Text.Encoding.UTF8, "application/json");
            //variable response-> utilizamos metodo asyncron al que le pasamos la URL donde esta el server y el contenido que queremos encontrar
            var response = await client.PostAsync(URL,content);

            if (response != null)  //si el resultado de response no es nulo, significa que el usuario y pass son correctos     
            {
                MessageBox.Show("El usuario ha iniciado sesión correctamente.", "LOGUEADO CORRECTAMENTE");//mostramos mensaje

                form_admin.Show();//mostramos formulario administador

                form_login.Hide();//ocultamos login (el que esta en curso)
            }
            else
            {
                MessageBox.Show("Usuario inexistente o contraseña incorrecta", "ERROR LOGIN");//si la respuesta es nula, es que el usuario o la contraseña no estan en BD
            }

        }

    }

    
   

}
