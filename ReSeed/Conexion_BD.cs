using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.DirectoryServices;
using System.Linq;
using System.Text;
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


        //CONXTRUCTOR CONEXION
        public Conexion_BD(String usuario, String password, String URL)
        {
            this.usuario = usuario;
            this.password = password;
            this.URL = URL;
        }

        public Conexion_BD()
        {

        }

        //GETTERS Y SETTERS
        public String Usuario
        {
            get { return usuario; }
            set { usuario = value; }

        }

        public String Password
        {
            get { return password; }
            set { password = value; }

        }

        public String url
        {
            get { return URL; }
            set { URL = value; }
        }


        /*
         * MÉTODO ASYNCRON login
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
         *6- Con un if, comprobamos si la respuesta de la api TRUE O FALSE.
         *Si la respuesta es TRUE
         *     ---> Si el usuario es 'eduard@fantasymail.com'
         *          -Mostramos mensaje de inicio de sesión correcta y somos redirigidos al menu principal ADMINISTRADOR
         *     ---> SiNo,cualquier otro registrado será redirigido al menú principal TECNICO.     
         *Sino la respuesta es FALSE
         *     ---> Se muestra mensaje ERROR. Usuario o password incorrecto.
         */
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
            String TOKEN = res.ToString();//@TOKEN_almacena TOKEN inicio sesion usuario;

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
                    Form3 form3 = new Form3();
                    Form1 form1 = new Form1();
                    form3.Show();//mostramos menu principal admin
                    form1.Hide();//ocultamos form login

                }
                else
                {

                    MessageBox.Show("Sesión iniciada correctamente.", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);//Mensaje sesión validada
                    Form4 form4 = new Form4();
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


    }

}



    

    

