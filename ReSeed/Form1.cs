using RestSharp;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Security.Cryptography.X509Certificates;
using System.Text.Json.Nodes;

namespace ReSeed
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        //BOT�N SALIR --> CIERRA PROGRAMA
        private void btn_salir_Click(object sender, EventArgs e)
        {

            this.Close();//cerramos form actual

        }

        /*
         * Este bot�n abrir� el formulario de registro y ocultar� 
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
            //Datos que introduce el usuario
            String nombreUsuario = textBox_usuario.Text;
            String passwordUsuario = textBox_password.Text;

            //URL de API server
            String url = "https://api.chucknorris.io/jokes/hvj9bov5qoscyakmzylsag";//URL de APIrest server

            //@client objeto HTTP al que le pasamos la URL a consultar
            var client = new RestClient(url);

            //almacenamos en la variable content los parametros que deseamos buscar jutno con la codificaci�n UTF8 y a�adiendo que buscaremos en json
            //var content = new StringContent ("{\"usuario\":"+nombreUsuario+",\"password\":"+passwordUsuario+"}", System.Text.Encoding.UTF8,"application/json");

            //@request, variable a la cual le pasamos la URL y el m�todo correspondiente para tratar con la BD
            var request = new RestRequest(url, Method.Get);

            //@response,es la ejecuci�n de la orden que damos al cliente HTTP de la BD
            RestResponse response = client.Execute(request);


            if (response != null)  //si el resultado de response no es nulo, significa que el usuario y pass son correctos     
            {
                MessageBox.Show("El usuario ha iniciado sesi�n correctamente.", "LOGUEADO CORRECTAMENTE");//mostramos mensaje
                Form3 form3 = new Form3();//declaramos el formulario del administrador
                form3.Show();//mostramos formulario

                this.Hide ();//ocultamos formulario en curso
            } else
            {
                MessageBox.Show("Usuario inexistente o contrase�a incorrecta","ERROR LOGIN");//si la respuesta es nula significa que el usuario y password no se han encontrado, es que el usuario o la contrase�a no estan en BD
            }


        }
    }
}