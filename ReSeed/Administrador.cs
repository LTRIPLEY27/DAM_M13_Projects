using GMap.NET.MapProviders;
using GMap.NET;
using GMap.NET.WindowsForms.Markers;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using GMap.NET.WindowsForms;
using System.Net;
using System.Diagnostics.Eventing.Reader;
using System.ComponentModel.Design;
using Newtonsoft.Json.Serialization;
using Newtonsoft.Json;
using System.Security.Policy;
using System.Text.Json.Nodes;
using System.Text.Json;
using Newtonsoft.Json.Linq;

namespace ReSeed
{
    public partial class Administrador : Form
    {

        //URLs-END POINT
        private String URL_registrarUsuarios = "https://t-sunlight-381215.lm.r.appspot.com/register";
        private String URL_usuariosRegistrados = "https://t-sunlight-381215.lm.r.appspot.com/results/usuarios";
        private String URL_elimarUsuario = "https://t-sunlight-381215.lm.r.appspot.com/delete/typus/usuario/id/";//A falta de añador a ID del usuario
        private String URL_modificarUsuario = "https://t-sunlight-381215.lm.r.appspot.com/update/value/";//tecnico/id/ o admin/id/
        private String URL_modificarUsuarioLogueado = "https://t-sunlight-381215.lm.r.appspot.com/update-user";

        //VARIABLES GLOBALES MAPAS
        //ivate GMarkerGoogle marcador;//Instanciamos marcadores
        private GMapOverlay capaMarcado;//Instanciamos la capa donde se añadirán los marcadores

        //OBJETO USUARIO
        private Usuario usuario;
        private Conexion_BD conexion = new Conexion_BD();
        Utilidades utilidades = new Utilidades();


        //VARIABLE PARA ALMACENAR TOKEN QUE RECIBO DE LA CLASE Conexion_BD
        private String TOKEN_form3;
        private String usuarioLogueado;

        //PASAMOS AL CONTRUCTOR FORM3 EL STRING QUE RECIBIREMOS
        public Administrador(String TOKEN_Login, String usuarioLogin)
        {
            TOKEN_form3 = TOKEN_Login;//Indicamos que el String que recibiremos será igual al
            usuarioLogueado = usuarioLogin;

            InitializeComponent();

            //CARGAMOS MAPA Y CARACTERISTICAS AL INICIAR EL FORM
            mapaInicio();

            //CARGAMOS REGISTRO DE USUARIOS AL ARRANCAR EL FORM
            mostrarRegistro();

            //CARGAMOS LOS DATOS DEL PERFIL
            datosPerfil();

            //BOTÓN VALIDA_ACTUALIZACIÓN USUARIO ESTARA DESACTIVADO POR DEFECTO
            button_VALIDAMODIFICACION.Enabled = false;

        }

        //********************** ALTA TAREAS ************************************

        #region MAPA PRECARGA INICIAL
        public void mapaInicio()
        {
            gMapControl1.DragButton = MouseButtons.Left;
            gMapControl1.CanDragMap = true;
            gMapControl1.MapProvider = GMapProviders.GoogleMap;
            gMapControl1.Position = new PointLatLng(40.463667, -3.74922);//COORDENADAS ESPAÑA
            gMapControl1.MinZoom = 0;//Minimo zoom que el usuario puede realizar
            gMapControl1.MaxZoom = 24;//Maximo zoom que el usuario puede realizar
            gMapControl1.Zoom = 5;//zoom que inicializaremos por defecto (5- dado que queremos que se centre en España)
            gMapControl1.AutoScroll = true;
            gMapControl1.ShowTileGridLines = false;//Quitamos las lineas de coordenadas
        }
        #endregion

        #region BOTÓN CIERRE APLICACIÓN
        /*
         * Este botón cierra la aplicación
         */
        private void btn_principal_Click(object sender, EventArgs e)
        {

            MessageBox.Show("Gracias por utilizar nuestro Programa. Esperamos verte pronto!", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);
            Application.Exit();

        }
        #endregion

        #region BOTÓN CANCELAR COORDENADAS
        /*
         * El botón cancela restaura todos los valores, es decir, elimina todos los campos
         * que hayamos editado haciendo un RESET
         */
        private void btn_cancelar_Click(object sender, EventArgs e)
        {

            calendario.ResetText();//reseteamos el dia configurado. Se configura al dia actual

            textBox_latitud.ResetText();//borramos latitud
            textBox_longitud.ResetText();//borramos longitud
            textBox_comentarios.ResetText();//borramos comentarios

            comboBox_tareas.ResetText();//borramos tareas
            comboBox_usuarios.ResetText();//borramos usuarios

            registroCoordenadas.Rows.Clear();//borramos todas las coordenadas del datagrid
            capaMarcado.Clear();//eliminamos la capa poligono que dibujamos en el mapa

        }
        #endregion

        #region OBTENCIÓN COORDENADAS (LATITUD Y LONGITUD)
        /*
         * Hacemos un evento que funcionará haciendo doble click en el mapa:
         * -Obtendremos la longitud y la latitud y almacenaremos los datos en los textbox correspondientes.
         */
        private void gMapControl1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            //Obtenemos los datos de la latitud y la longitud del lugar donde se pulse
            double latitud = gMapControl1.FromLocalToLatLng(e.X, e.Y).Lat;
            double longitud = gMapControl1.FromLocalToLatLng(e.X, e.Y).Lng;

            //Ubicamos las coordenas en el lugar de los textboxs correspondientes
            textBox_latitud.Text = latitud.ToString();
            textBox_longitud.Text = longitud.ToString();

        }
        #endregion

        #region REGISTRAR COORDENADAS EN DATAGRID
        /*
         *Este botón se encarga bde registrar las coordenadas en el dataGrid 
         */
        private void boton_registroCoordenadas_Click(object sender, EventArgs e)
        {
            double latitud = Convert.ToDouble(textBox_latitud.Text);
            double longitud = Convert.ToDouble(textBox_longitud.Text);

            if (latitud != null && longitud != null)//si los datos no son vacios --> agregamos al gridData
            {

                registroCoordenadas.Rows.Add(latitud, longitud);

            }
            else
            {
                MessageBox.Show("No ha introducido coordenadas");
            }

        }
        #endregion

        #region DIBUJAR POLÍGONO
        /*
         * Este botón se encarga de crear una capa encima del mapa y unir los puntos
         * de las diferentes coordenadas almacenadas en el dataGrid.
         * Con los trazos en todos los puntos se creará la figura geométrica que representará
         * la zona que se tiene que trabajar en el mapa (tipo de tarea específica)
         */
        private void btn_dibujarpoligono_Click(object sender, EventArgs e)
        {

            capaMarcado = new GMapOverlay();//objeto de la nueva capa que irá encima del mapa
            List<PointLatLng> puntos = new List<PointLatLng>();//listado que contendrá los puntos marcados en el mapa

            //necesitaremos las variables para almacenar los datos (longitud y latitud)
            double latitud;
            double longitud;

            //Recorremos los datos del grid con un FOR y los añadimos los puntos al LIST
            for (int i = 0; i < registroCoordenadas.Rows.Count - 1; i++)//Restamos a la longitud maxima 1 (dado que por defecto aparece coordenada 0,0)
            {
                latitud = Convert.ToDouble(registroCoordenadas.Rows[i].Cells[0].Value);//añadimos lat (columna inicial pos=0)
                longitud = Convert.ToDouble(registroCoordenadas.Rows[i].Cells[1].Value);//añadimos long (segunda columna pos=1)
                puntos.Add(new PointLatLng(latitud, longitud));//añadimos información al list

            }

            GMapPolygon crearcionPoligono = new GMapPolygon(puntos, "Poligono");//Creamos objeto poligono al cual le pasamos las coordenadas del list
            capaMarcado.Polygons.Add(crearcionPoligono);//añadimos a la capa poligonos el poligono creado con las coordenadas
            gMapControl1.Overlays.Add(capaMarcado);//añadimos la capa al mapa
            //con estas dos lineas de zoom vamos actualizando el mapa
            gMapControl1.Zoom = gMapControl1.Zoom + 1;
            gMapControl1.Zoom = gMapControl1.Zoom - 1;


        }
        #endregion

        #region ELIMINAR FILA SELECCIONADA DATAGRID COORDENADAS
        /*
         * Botón que eliminará la fila seleccionada
         */
        private void btn_eliminarCoordenada_Click(object sender, EventArgs e)
        {

            if (registroCoordenadas.Rows.Count == 1)//si hay solo una fila, NO ELIMINAMOS (por defecto ponemos una fila vacia)
            {
                MessageBox.Show("No hay coordenadas que eliminar.", "MENSAJE ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);//Nostramos mensaje

            }
            else//si por el contrario hay mas de una fila...
            {
                registroCoordenadas.Rows.RemoveAt(registroCoordenadas.CurrentRow.Index);//elimiamos la fila que selecciona el usuario
                capaMarcado.Clear();//borramos capa de dibujo poligono
            }

        }
        #endregion

        #region ELIMINAR TODAS LAS COORDENADAS DEL DATAGRID
        /*
         * Metodo que elimina todas las coordenadas del mapa y desdibuja
         */
        private void btn_eliminarTodasLasCoordenadas_Click(object sender, EventArgs e)
        {
            if (registroCoordenadas.Rows.Count == 1)//si hay solo una fila, NO ELIMINAMOS (por defecto ponemos una fila vacia)
            {
                MessageBox.Show("No hay coordenadas que eliminar.", "MENSAJE ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);//Nostramos mensaje

            }
            else//si por el contrario hay mas de una fila...
            {
                registroCoordenadas.Rows.Clear();//elimiamos todas las filas
                capaMarcado.Clear();//borramos capa de dibujo poligono
            }
        }
        #endregion

        //******************** GESTIÓN USUARIOS *********************************

        #region ALTA DE USUARIOS
        private void button_ENVIAR_Click(object sender, EventArgs e)
        {
            //Declaramos las variables que compondrán el objeto usuario
            String nombre;
            String apellido;
            String user;
            String password;
            String email;
            String telefono;
            String rol = null;

            //Con este condicional IF controlamos que el usuario solo pueda marcar una opción 
            //y en consecuéncia determinar si será técnico o no
            if (checkBox_TECNIC.Checked)
            {
                rol = "TECNIC";
                checkBox_ADMIN.Enabled = false;//desactivamos checkbox admin

            }
            else if (checkBox_ADMIN.Checked)
            {
                rol = "ADMIN";
                checkBox_TECNIC.Enabled = false;//desactivamos checkbox tecnic
            }

            else
            {
                MessageBox.Show("Solo es posible marcar una opción para rol!", "ERROR ROL", MessageBoxButtons.OK, MessageBoxIcon.Error);
                checkBox_ADMIN.Enabled = true;
                checkBox_TECNIC.Enabled = true;
            }

            //Variable repetición password
            String password_repeticíon = textBox_PASSWORD_CONFIRM.Text;

            //Asignamos los textBox que introducirá el usuario
            nombre = textBox_NOMBRE.Text;
            apellido = textBox_APELLIDO.Text;
            telefono = textBox_TELEFONO.Text;//Casteamos a INT el telefono
            user = textBox_user.Text;
            email = textBox_MAIL.Text;
            password = textBox_PASSWORD.Text;


            //Instanciamos objeto usuario y le pasamos sus parámetros correspondientes
            usuario = new Usuario(nombre, apellido, user, password, email, telefono, rol);

            //Si el password introducido por el usuario coincide con su repetición...
            if (password.Equals(password_repeticíon))
            {
                conexion.altaUsuario(usuario, TOKEN_form3, URL_registrarUsuarios);

                //una vez el usuario está registrado,limpiamos los textbox
                textBox_NOMBRE.Clear();
                textBox_APELLIDO.Clear();
                textBox_TELEFONO.Clear();
                textBox_user.Clear();
                textBox_MAIL.Clear();
                textBox_PASSWORD.Clear();
                textBox_PASSWORD_CONFIRM.Clear();

                checkBox_ADMIN.Checked = false;
                checkBox_TECNIC.Checked = false;

            }
            else
            {//Si las contraseñas no coinciden al confirmar, mostraremos mensaje de erro al usuario
                MessageBox.Show("Las contraseñas introducidas no coinciden. Por favor, revise la contraseña y introdúzcala nuevamente. Gracias", "ERROR CONTRASEÑA", MessageBoxButtons.OK, MessageBoxIcon.Error);
                //Enviamos el focus() a la contraseña puesta que es erronea
                textBox_PASSWORD.Focus();
            }

        }
        #endregion

        #region MOSTRAR REGISTRO DE USUARIOS
        /*
         * Este botón permitairá MOSTRAR USUARIOS registrados en la base de datos
         */
        private async void mostrarRegistro()
        {
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(TOKEN_form3, URL_usuariosRegistrados);

            for (int i = 0; i < listaUsuarios.Count; i++)
            {
                String id = listaUsuarios[i].Id;
                String user = listaUsuarios[i].User;
                String email = listaUsuarios[i].Email;

                //Agregamos Información al Datagrid de usuarios
                dataGridView_usuarios.Rows.Add(id, user, email);

                //Añadimos los usuarios al Combobox_usuarios para asignarle tareas
                comboBox_usuarios.Items.Add(id + " " + user);

            }
        }
        #endregion

        #region ELIMINAR USUARIO
        /*
         * Botón ELIMINAR usuarios
         */
        private async void button2_Click(object sender, EventArgs e)
        {

            //Obtenemos la lista de usuarios
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(TOKEN_form3, URL_usuariosRegistrados);
            //Obtenemos la posición en el Datagrid
            int numeroSeleccionado = dataGridView_usuarios.CurrentRow.Index;
            //Creamos variable que almacena POSICION
            int posicionUsuarioEnList = 0;

            //Recorremos el listado de Usuarios
            for (int i = 0; i < listaUsuarios.Count; i++)
            {
                posicionUsuarioEnList = i;//Misma estructuta y posición entre Datagrid y List

                if (posicionUsuarioEnList == numeroSeleccionado)//cuando la posición del Datagrid sea igual a la posición del LIST...
                {
                    String id = listaUsuarios[i].Id;//Obtenemos la Id del Usuario
                    //Mostramos al Usuario la pregunta de si esta seguro de eliminar al usuario n...
                    DialogResult result = MessageBox.Show("¿Está seguro de querer eliminar al usuario con ID: " + id + "de la base de datos?",
                        "ELIMINAR USUARIO", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Warning);
                    if (result == DialogResult.Yes)//si esta seguro en eliminar...Eliminamos de la BD, del Datagrid y mostramos mensaje
                    {
                        conexion.eliminarUsuarioAsync(URL_elimarUsuario, TOKEN_form3, id);
                        dataGridView_usuarios.Rows.RemoveAt(i);
                        MessageBox.Show("Usuario con ID: " + listaUsuarios[i].Id + ", ha sido eliminado de la base de datos correctamente.",
                            "ELIMINAR USUARIO", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                    else if (result == DialogResult.No)//si no esta seguro en eliminar usuario...Mostramos mensaje
                    {
                        MessageBox.Show("No se ha eliminado ningún registro.",
                           "ELIMINAR USUARIO", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                    else
                    {
                        MessageBox.Show("Eliminación del usuario cancelada",//si cancela, mostramos mensaje
                           "ELIMINAR USUARIO", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }

                }
            }
        }
        #endregion

        #region ACTUALIZAR USUARIOS
        /*
         * Botón ACTUALIZAR
         * Limpia por completo el registro datagrid y carga nuevamente todos los usuarios de la base de datos
         */
        private void button3_ACTUALIZARLISTA_Click(object sender, EventArgs e)
        {
            dataGridView_usuarios.Rows.Clear();
            mostrarRegistro();
        }
        #endregion

        #region MODIFICAR USUARIO
        /*
         * Botón para MODIFICAR usuarios
         * ------------------------------
         * Esta acción consta de dos botones.
         * @button1_Click_1 - El usuario seleccionará un usuario del datagrid.
         * Cuando le de al botón, enviará todos los datos a sus campos correspondientes para que sean
         * viaualizados.
         * Hay campos que no estarán activados para que el usuario no modifique por error.
         * 
         * Cuando hayamos modificado el dato/s en cuestión, daremos al botón @button_VALIDAMODIFICACION_Click.
         * Lo que hace este botón es recomponer el usuario con sus modificaciones y enviarlo a su endpoint correpondiente.
         * Si todo ha ido bien,mostraremos monsaje correspondiente. Si por el contrario no se ha modificado, mostraremos mensaje
         * tambien correponsiente para que el usuario este informado en todo momento.
         * 
         */
        private async void button1_Click_1(object sender, EventArgs e)
        {
            //boolean para salir del bucle
            Boolean salirLOOP = false;

            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(TOKEN_form3, URL_usuariosRegistrados);

            //Obtenemos la posición en el Datagrid
            int numeroSeleccionado = dataGridView_usuarios.CurrentRow.Index;
            //Creamos variable que almacena POSICION
            int posicionUsuarioEnList = 0;

            //Recorremos el listado de Usuarios
            for (int i = 0; i < listaUsuarios.Count && !salirLOOP; i++)
            {
                posicionUsuarioEnList = i;//Misma estructuta y posición entre Datagrid y List

                if (posicionUsuarioEnList == numeroSeleccionado)//cuando la posición del Datagrid sea igual a la posición del LIST...
                {
                    if (!listaUsuarios[i].Email.Equals(usuarioLogueado))//Si el usuario que queremos modificar no es el usuario logueado...
                    {

                        //Obtenemos todos los datos del user seleccionado en el datagrid
                        String id = listaUsuarios[i].Id;
                        String nombre = listaUsuarios[i].Nombre;
                        String apellido = listaUsuarios[i].Apellido;
                        String usuario = listaUsuarios[i].User;
                        String telefono = listaUsuarios[i].NumeroTelefono;
                        String email = listaUsuarios[i].Email;
                        String password = listaUsuarios[i].Password;
                        String passwordRepetido = password;
                        String rol = listaUsuarios[i].Rol;

                        //Asignamos cada dato a su campor textbox con la finalidad de mostrar los mismos
                        textBox_SECRET_ID.Text = id;
                        textBox_NOMBRE.Text = nombre;
                        textBox_APELLIDO.Text = apellido;
                        textBox_user.Text = usuario;
                        textBox_TELEFONO.Text = telefono;
                        textBox_MAIL.Text = email;
                        textBox_PASSWORD.Text = password;
                        textBox_PASSWORD_CONFIRM.Text = passwordRepetido;

                        //En función del Strin rol que nos llegue, marcaremos el checkbox correspondiente
                        if (rol.Equals("ADMIN"))
                        {
                            checkBox_ADMIN.Checked = true;
                            checkBox_TECNIC.Checked = false;
                        }
                        else
                        {
                            checkBox_TECNIC.Checked = true;
                            checkBox_ADMIN.Checked = false;
                        }

                        //EL ADMINISTRADOR PODRÁ MODIFICAR EL TEÉFONO Y EL PASSWORD DE TODOS LOS USUARIOS DE LA BASE DE DATOS
                        textBox_NOMBRE.Enabled = false;
                        textBox_APELLIDO.Enabled = false;
                        textBox_user.Enabled = false;
                        textBox_MAIL.Enabled = false;
                        checkBox_ADMIN.Enabled = false;
                        checkBox_TECNIC.Enabled = false;

                        button_ENVIAR.Enabled = false;//Ocultamos botón 
                        button_VALIDAMODIFICACION.Enabled = true;//botón valida_modificación activado

                        button3_ACTUALIZARLISTA.Enabled = false;
                        button_ELIMINAR.Enabled = false;
                        button_MODIFICAR.Enabled = false;

                        textBox_TELEFONO.Enabled = true;
                        textBox_PASSWORD.Enabled = true;
                        textBox_PASSWORD_CONFIRM.Enabled = true;

                    }
                    else//si el usuario que se queire modificar es el logueado, lanzamos mensaje indicanfo que se dirija a su Perfil de usuario
                    {
                        MessageBox.Show("No se puede modificar tu perfil de usuario en esta sección. Por favor, dirijase " +
                        "a la pestaña Perfil Usuario para modificar sus datos, gracias.", "ERROR MODIFICAR USUARIO", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                        salirLOOP = true;
                    }
                }

            }
        }

        private async void button_VALIDAMODIFICACION_Click(object sender, EventArgs e)
        {
            //Objeto json
            JObject jsonUser = null;

            //OBTENEMOS ID DEL CAMPO QUE OCULTAMOS EN LA INTERFICIE
            String id = textBox_SECRET_ID.Text;

            //VARIABLES DE OBJETO USER
            String nombre = textBox_NOMBRE.Text;
            String apellido = textBox_APELLIDO.Text;
            String usuario = textBox_user.Text;
            String telefono = textBox_TELEFONO.Text;
            String email = textBox_MAIL.Text;
            String rol = null;
            String password = textBox_PASSWORD.Text;
            String confirmarPassword = textBox_PASSWORD_CONFIRM.Text;

            String URL_typeUser = null;//declaramos esta variable para controlar la dirección URL END POINT

            if (checkBox_ADMIN.Checked)
            {
                rol = "ADMIN";
                URL_typeUser = "admin";
            }
            else
            {
                rol = "TECNIC";
                URL_typeUser = "tecnico";
            }

            //si los passwords son iguales
            if (password.Equals(confirmarPassword))
            {
                //si el password no se ha modificado...
                if (password.Equals(await utilidades.obtenerPassword(email, TOKEN_form3)))
                {
                    //Recomponemos el objeto con las modificaciones
                    Usuario user = new Usuario(nombre, apellido, usuario, password, email, telefono, rol);
                    //transformamos en objeto json sin password (contraseña va hasheada en base de datos, asi evitamos que se corrompa usuario)
                    jsonUser = utilidades.JsonUsuario_sinPassword(user);

                }
                else//sino la contraseña ha sido modificada por el usuario
                {
                    //Recomponemos el objeto con las modificaciones
                    Usuario user = new Usuario(nombre, apellido, usuario, password, email, telefono, rol);
                    //transformamos en objeto json incluyendo el usuario
                    jsonUser = utilidades.JsonUsuario_conPassword(user);
                }

                //ANADIMOS AL END POINT API LA ID DEL USUARIO
                String URL_modificarUser = URL_modificarUsuario + URL_typeUser + "/id/" + id;
                //LLAMAMOS AL METODO CORRESPONDIENTE DE Y LE PASAMOS LOS PARAMETROS PARA COMUNICARNOS CON API
                conexion.modificarUsuarioAsync(URL_modificarUser, TOKEN_form3, jsonUser.ToString());

                //VACIAMOS TEXTBOXES
                textBox_NOMBRE.Clear();
                textBox_APELLIDO.Clear();
                textBox_user.Clear();
                textBox_TELEFONO.Clear();
                textBox_MAIL.Clear();
                checkBox_ADMIN.Enabled = true;//habilitamos checkbox
                checkBox_TECNIC.Enabled = true;//habilitamos checkbox
                checkBox_ADMIN.Checked = false;//desmarcamos checkbox
                checkBox_TECNIC.Checked = false;//desmarcamos checkbox
                textBox_PASSWORD.Clear();
                textBox_PASSWORD_CONFIRM.Clear();
                //GESTIONAMOS VISIBILIDAD BOTONES
                button_ENVIAR.Enabled = true;
                button_ELIMINAR.Enabled = true;
                button3_ACTUALIZARLISTA.Enabled = true;
                button_MODIFICAR.Enabled = true;
                button_VALIDAMODIFICACION.Enabled = false;

            }
            else//si los password no coinciden...hay error..-->mostramos al usuario

            {
                MessageBox.Show("Error al confirmar contraseña. Rogamos revise y vuelva a interntarlo.",
                    "ERROR CREDENCIALES", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

        }

        #endregion

        #region FILTRAR BUSQUEDA


        #endregion

        #region BOTÓN CANCELAR (USUARIOS)
        /*
         * BOTÓN CANCELAR_USUARIOS
         * -----------------------
         * Este botón se encargará de limpiar los textbox y de ocultar y activat botones
         */
        private void button_CANCELAR_USUARIOS_Click(object sender, EventArgs e)
        {
            //VACIAMOS TEXTBOXES
            textBox_NOMBRE.Clear();
            textBox_APELLIDO.Clear();
            textBox_user.Clear();
            textBox_TELEFONO.Clear();
            textBox_MAIL.Clear();
            checkBox_ADMIN.Enabled = false;
            checkBox_TECNIC.Enabled = false;
            textBox_PASSWORD.Clear();
            textBox_PASSWORD_CONFIRM.Clear();

            //GESTIONAMOS VISIBILIDAD BOTONES
            button_CANCELAR_USUARIOS.Enabled = true;
            button_ENVIAR.Enabled = true;
            button_ELIMINAR.Enabled = true;
            button3_ACTUALIZARLISTA.Enabled = true;
            button_MODIFICAR.Enabled = true;
            button_VALIDAMODIFICACION.Enabled = false;
        }

        #endregion

        //****************** GESTIÓN MI PERFIL **************************

        #region PERFIL USUARIO

        /*
         * Método que obtiene los datos del usuario logueado y los añade a los textbox
         * de la pestaña Perfil Usuario
         */
        public async void datosPerfil()
        {
            Boolean salirBucle = false;
            //Lista de usuarios
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(TOKEN_form3, URL_usuariosRegistrados);

            //Recorremos lista de usuarios
            for (int i = 0; i < listaUsuarios.Count && !salirBucle; i++)
            {
                if (usuarioLogueado.Equals(listaUsuarios[i].Email))
                {

                    textBox_IDPERFIL.Text = listaUsuarios[i].Id;
                    textBox_USUARIOPERFIL.Text = listaUsuarios[i].User;
                    textBox_telefonoMIPERFIL.Text = listaUsuarios[i].NumeroTelefono;
                    textBox_PASSWORDPERFIL.Text = listaUsuarios[i].Password;
                    textBox_CONFIRMA_PASSWORDPERFIL.Text = listaUsuarios[i].Password;

                    salirBucle = true;
                }
            }
            //Por defecto, desactivamos lo que no es posible modificar:
            textBox_IDPERFIL.Enabled = false;
            textBox_USUARIOPERFIL.Enabled = false;
        }

        /*
        * Botón que modificar password o mail en mi perfil
        * ------------------------------------------------
        * Se gestiona  a través del método editarPerfilASYNC de clase Conexion_BD
        */
        private async void button_MIPERFIL_MODIFICA_PASSWORD_Click(object sender, EventArgs e)
        {
            String id = textBox_IDPERFIL.Text;
            String user = textBox_USUARIOPERFIL.Text;
            String telefono = textBox_telefonoMIPERFIL.Text;
            String password = textBox_PASSWORDPERFIL.Text;
            String repeatPassword = textBox_CONFIRMA_PASSWORDPERFIL.Text;

            conexion.editarPerfilASYNC(password, telefono, TOKEN_form3, URL_modificarUsuarioLogueado, URL_usuariosRegistrados, id);

        }

        #endregion




    }
}
