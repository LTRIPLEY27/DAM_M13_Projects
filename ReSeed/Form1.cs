using Newtonsoft.Json.Linq;
using RestSharp;
using System.Net.Http;
using System.Net.Http.Json;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Text.Json.Nodes;

namespace ReSeed
{

    public partial class Form1 : Form
    {
        //VARIABLES DE USUARIO
        private String nombreUsuario;
        private String passwordUsuario;
        //VARIABLES DE CONEXION **PRUEBAS**
        private String url = "https://jsonplaceholder.typicode.com/posts/1";
        private String url2 = "https://jsonplaceholder.typicode.com/posts";
        private String loginURL = "http://restapi.adequateshop.com/api/authaccount/login";

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        //BOTÓN SALIR --> CIERRA PROGRAMA
        private void btn_salir_Click(object sender, EventArgs e)
        {

            //Se cjerra el programa
            this.Close();//cerramos form actual

        }

        /*
         * Este botón abrirá el formulario de registro y ocultará 
         * la pantalla principal
         * */
        private void btn_registrar_Click(object sender, EventArgs e)
        {

            Form2 registro = new Form2();//Instanciamos el form2

            registro.Show();//mostramos form2

            this.Hide();//ocultamos form1

        }

        private async void btn_conectar_Click(object sender, EventArgs e)
        {
            //Añadimos a los datos que introduce el usuario una comilla simple delante y detrás de la cadena
            //Hacemos esto para que el forma JSon de @parametros sea el adecuado y no de errores
            nombreUsuario = "'" + textBox_usuario.Text.ToString() + "'";
            passwordUsuario = "'" + textBox_password.Text.ToString() + "'";
            
            //HTTPCLIENT --> Hacer un POST**Petición API a la cual le enviamos datos y esperamos respuesta
            HttpClient client = new HttpClient();//objeto de HttpClient
            String parametros = "{'email':"+nombreUsuario+",'password':"+passwordUsuario+"}";//String @parametros 
            //client.DefaultRequestHeaders.Add("Authorization", "_TOKEN_");
            dynamic jsonString = JObject.Parse(parametros);//convertimos @parametros a formato JSon

            var httpContent = new StringContent(jsonString.ToString(),Encoding.UTF8,"application/json");//Agregamos contendio a la http, para ello,pasamos el JSon creado anteriormante a strin, lo codificamos a UTF8 y aplicamos el parametro applicarion/json
            var response = client.PostAsync(loginURL, httpContent).Result;//@response- utilizaremos el metodo PostAsync pasandole por parametro la url y el contendio
            var res = response.Content.ReadAsStringAsync().Result;//@res-> lleemos el contenido
            dynamic json = JObject.Parse(res);//transformamos ese contenido @res a JSOn

            MessageBox.Show(json.ToString());//Mostramos por pantalla
       
        }
    }
}