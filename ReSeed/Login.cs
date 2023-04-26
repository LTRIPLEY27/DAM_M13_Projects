using Newtonsoft.Json.Linq;
using System.Net;
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

        //VARIABLES DE CONEXION_URL
        private static String URL_LOGIN = "https://t-sunlight-381215.lm.r.appspot.com/auth/";
        private Conexion_BD conexion = new Conexion_BD();

        public Form1()
        {
            InitializeComponent();
        }


        private void Form1_Load(object sender, EventArgs e)
        {

        }

        //BOTÓN SALIR método btn_salir_Clic  --> CIERRA PROGRAMA
        private void btn_salir_Click(object sender, EventArgs e)
        {

            MessageBox.Show("Gracias por utilizar nuestro Programa. Esperamos verte pronto!", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);
            //Se cjerra el programa
            Application.Exit();//cerramos form actual

        }
        
        /*
         * Botón btn_conectar_Click
         * Asigno los valores de los TextBox a las variables correspondientes:
         * @nombreUsuario
         * @passwordUsuario
         * 
         * LLamamos al método LOGIN de la clase CONEXION_BD, al que le pasamos el usuario, el password y la URL y se hace la petición
         * a la BD.
         * Si la respuesta es TRUE: 
         *                 -Se ha conectado administrador y nos envia al menu principal del admin
         *                 -Se ha conectado un tecnico y nos envia al menu principal del tecnico
         * Si la respuesta es FALSE:
         *                 - Se muestra el mensaje de error
         */
        private async void btn_conectar_Click(object sender, EventArgs e)
        {

            //Añadimos a los datos que introduce el usuario una comilla simple delante y detrás de la cadena
            //Hacemos esto para que el forma JSon de @parametros sea el adecuado y no de errores
            nombreUsuario = textBox_usuario.Text.ToString();
            passwordUsuario = textBox_password.Text.ToString();


            conexion.login(nombreUsuario, passwordUsuario, URL_LOGIN);
            
        }

      
        
    }
}