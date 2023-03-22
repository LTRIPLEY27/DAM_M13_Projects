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
        private String loginURL = "https://t-sunlight-381215.lm.r.appspot.com/auth/";

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
         * MÉTODO btn_registrar_Click
         * - Una vez se presione este botón, llevará al usuasiro a un nuevo form donde podrá introducir sus datos
         * para registrarse el la base de datos de nuestra aplicación.
         * Al redirigirnos al form de registro, el form actual se ocultará
         * */
        private void btn_registrar_Click(object sender, EventArgs e)
        {

            Form2 registro = new Form2();//Instanciamos el form2

            registro.Show();//mostramos form2

            this.Hide();//ocultamos form1

        }

        /*
         * MÉTODO ASYNCRON btn_conectar_Click
         * ___________________________________
         * 
         * Por un lado almacenaremos en las variables @nombreUsuario y @passwordUsuario, los datos introducidos por el usuario.
         * Además, añadiremos una comilla delante y detrás de cada String dándole la forma deseada para el JSon.
         * 
         * 1-Instanciaremos un objeto de la clase HTTPCLIENT
         * 2-Crearemos un variable del tipo String @parametros para almacenar los datos introducidos por el usuario con las pautas
         * del formato JSon de la APIRest.
         * 3-Crearemos un objeto dynamic @jsonString y lo parseamos con JObject pasandole por parámetro la variable @parametros. Básicamente
         * convertimos los parámetros en formato json.
         * 4-En una variable @httpconten agregamos contendio a la http, para ello,pasamos el JSon creado anteriormante a string, 
         * lo codificamos a UTF8 y aplicamos el parametro applicarion/json
         *5-En una variable @response almacenaremos la respuesta obtenida de la api. Usaremos el objeto @client de la clase HTTPCLIENT llamando
         *a su método PostAsync (puesto que enviaremos datos a la API i esperaremos recibir una respuesta), pasandole la URL y el contenido.
         *6-En la variable @res almacenamos la respuesta @response.
         *7- Con un if, comprobamos si la respuesta de la api es valaida o no.
         *            -Mostramos mensaje por pantalla donde indicamos que el LOGIN es correcto.
         *            -Con otro if, verificamos si se trata de un rol administrador o tecnico para poder deribarlo a su menú principal 
         *            correspondiente.
         *                 -Si por el contrario la respuesta de la api nos devuelve no valido, mostraremos un mensaje al usuario donde
         *                 se le indica que el usuario introducido o la contraseña no son correctos.
         */
        private async void btn_conectar_Click(object sender, EventArgs e)
        {
            //Añadimos a los datos que introduce el usuario una comilla simple delante y detrás de la cadena
            //Hacemos esto para que el forma JSon de @parametros sea el adecuado y no de errores
            nombreUsuario = "'" + textBox_usuario.Text.ToString() + "'";
            passwordUsuario = "'" + textBox_password.Text.ToString() + "'";

            //HTTPCLIENT --> Hacer un POST**Petición API a la cual le enviamos datos y esperamos respuesta
            HttpClient client = new HttpClient();//objeto de HttpClient
            String parametros = "{'email':" + nombreUsuario + ",'password':" + passwordUsuario + "}";//String @parametros 
            //client.DefaultRequestHeaders.Add("Authorization", "_TOKEN_");
            dynamic jsonString = JObject.Parse(parametros);//convertimos @parametros a formato JSon

            var httpContent = new StringContent(jsonString.ToString(), Encoding.UTF8, "application/json");//enviamos datos a http
            var response = client.PostAsync(loginURL, httpContent).Result;//@response- utilizaremos el metodo PostAsync pasandole por parametro la url y el contendio
            var res = response.Content.ReadAsStringAsync().Result;//@res-> lleemos el contenido
            String TOKEN = res.ToString();//@TOKEN_almacena TOKEN inicio sesion usuario
            //dynamic json = JObject.Parse(res);//transformamos ese contenido @res a JSOn

            if (response.IsSuccessStatusCode)//si la respuesta es que existe el usuario y la contraseña introducida por el usuario...
            {
                if (nombreUsuario.Equals("'eduard@fantasymail.com'")) {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    //MessageBox.Show("TOKEN :" + TOKEN);
                    Form3 form3 = new Form3();
                    form3.Show();
                    this.Hide();

                } else
                {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    //MessageBox.Show("TOKEN :" + TOKEN);
                    Form4 form4 = new Form4();
                    form4.Show();
                    this.Hide();

                }

            } 

            else//sino...
            {
                MessageBox.Show("Usuario o contraseña incorrectos.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Warning);//Mensaje erro LOGIN
                //MessageBox.Show("TOKEN :" + TOKEN);
            }

        }
    }
}